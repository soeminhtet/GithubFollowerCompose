package com.smh.githubfollowercompose.presentation.screen.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel(){

    private var _searchName : MutableState<String> = mutableStateOf("")
    val searchName : State<String> get() = _searchName

    private var _searchError = mutableStateOf("")
    val searchError get() = _searchError

    fun onSearchNameChange(name : String) { _searchName.value = name }

    fun clearSearchName() { _searchName.value = "" }


    fun onSearch() : Boolean {
        _searchError.value = ""
        return if (searchName.value.isNotEmpty()) {
            true
        } else {
            _searchError.value = "Empty search name"
            false
        }
    }
}