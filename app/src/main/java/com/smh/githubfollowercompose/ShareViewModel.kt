package com.smh.githubfollowercompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.usecase.UseCases
import com.smh.githubfollowercompose.utility.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ShareViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private var _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    val followers : Flow<PagingData<FollowerEntity>>

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite = _isFavourite.asStateFlow()

    private val _errorState = MutableStateFlow<ErrorState>(ErrorState.Idle)
    val errorState = _errorState.asStateFlow()

    init {
        followers = _username.flatMapLatest {
            checkUserIsFavourite(it)
            getFollowers(it)
        }
    }

    private fun getFollowers(username : String) = useCases
        .getFollowersUseCase(
            name = username,
            searchFollower = ""
        )
        .distinctUntilChanged()
        .cachedIn(viewModelScope)

    private fun checkUserIsFavourite(name : String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = useCases.getFollowerDetailUseCase(name)) {
                is ResponseData.Exception -> {
                    _errorState.value = when (response.exception) {
                        is ConnectException -> ErrorState.NoConnection
                        is SocketTimeoutException -> ErrorState.ConnectionSlow
                        else -> ErrorState.Fail(message = response.exception.localizedMessage)
                    }
                }
                is ResponseData.Failed -> { _errorState.emit(ErrorState.Fail(status = response.status, message = response.message)) }
                is ResponseData.Success -> {
                    val id = response.data.id
                    useCases.checkAlreadyFollowed(id).collectLatest {
                        _isFavourite.emit(it)
                    }
                }
            }
        }
    }

    fun changeUsername(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _username.emit(name)
        }
    }
}

sealed class ErrorState {
    object Idle : ErrorState()
    object NoConnection : ErrorState()
    object ConnectionSlow : ErrorState()
    data class Fail(val status : Int? = null,val message : String? = null) : ErrorState()
}