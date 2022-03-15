package com.smh.githubfollowercompose.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import com.smh.githubfollowercompose.domain.usecase.UseCases
import com.smh.githubfollowercompose.utility.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {
    private val _cacheFollower = mutableListOf<FollowerEntity>()

    private val _searchFollower = MutableStateFlow<List<FollowerEntity>>(emptyList())
    val searchFollower : StateFlow<List<FollowerEntity>> = _searchFollower

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery

    private val _followerDetail = MutableStateFlow<FollowerDetailState>(FollowerDetailState.Loading)
    val followerDetail = _followerDetail.asStateFlow()

    fun addCacheFollower(follower : FollowerEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!_cacheFollower.contains(follower)) {
                _cacheFollower.add(follower)
            }
        }
    }

    fun searchFollower() {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchQuery.value.isEmpty()) {
                _searchFollower.value = emptyList()
                return@launch
            }
            _searchFollower.value = _cacheFollower.filter { it.name.lowercase().contains(searchQuery.value.lowercase()) }
        }
    }

    fun onSearchQueryChange(searchQuery : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery.value = searchQuery
            searchFollower()
        }
    }

    fun addOnFavourite(name : String,isFavourite : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = useCases.getFollowerDetailUseCase(username = name)) {
                is ResponseData.Exception -> {}
                is ResponseData.Failed -> { val message = response.message }
                is ResponseData.Success -> {
                    response.data.let {
                        val favouriteEntity = FavouriteEntity(
                            id = it.id,
                            login = it.login,
                            name = it.name ?: "Anonymous",
                            imageUrl = it.avatarUrl
                        )
                        if (isFavourite) useCases.deleteFavouriteUseCase(favourite = favouriteEntity)
                        else useCases.insertFavouriteUseCase(favourite = favouriteEntity)
                    }
                }
            }
        }
    }

    fun getFollowerDetail(name : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _followerDetail.emit(FollowerDetailState.Loading)
            _followerDetail.value = when(val response = useCases.getFollowerDetailUseCase(name)) {
                is ResponseData.Exception -> { FollowerDetailState.Fail(exception = response.exception) }
                is ResponseData.Failed -> { FollowerDetailState.Fail(status = response.status,message = response.message) }
                is ResponseData.Success -> { FollowerDetailState.Success(response.data) }
            }
        }
    }

    fun clearCacheFollower() {
        viewModelScope.launch(Dispatchers.IO) {
            _cacheFollower.clear()
        }
    }
}

sealed class FollowerDetailState {
    object Loading : FollowerDetailState()
    data class Fail(val status : Int? = null, val message : String? = null, val exception: Exception? = null) : FollowerDetailState()
    data class Success(val data : FollowerDetailApiModel) : FollowerDetailState()
}