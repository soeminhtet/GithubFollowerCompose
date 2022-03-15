package com.smh.githubfollowercompose.presentation.screen.favourite

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel(),DefaultLifecycleObserver {

    private val _favourites = MutableStateFlow<List<FavouriteEntity>>(emptyList())
    val favourites = _favourites.asStateFlow()

    private val _deleteEntity = MutableStateFlow<FavouriteEntity?>(null)
    val deleteEntity = _deleteEntity.asStateFlow()

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        getAllFavourites()
    }

    private fun getAllFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getFavouritesUseCase().collectLatest {
                _favourites.value = it
            }
        }
    }

    fun deleteFavorite(favourite : FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.deleteFavouriteUseCase(favourite)
            _deleteEntity.emit(favourite)
        }
    }

    fun undoDeleteFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteEntity.value?.let {
                useCases.insertFavouriteUseCase(it.copy(date = Calendar.getInstance().timeInMillis))
                setDeleteEntityToNull()
            }
        }
    }

    fun setDeleteEntityToNull() {
        viewModelScope.launch(Dispatchers.IO) { _deleteEntity.emit(null) }
    }
}