package com.example.cookare.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookare.model.*
import com.example.cookare.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ListGenerateViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
) : ViewModel() {

    val resListGenerate: MutableState<Map<String, Int>> = mutableStateOf(mapOf())
    init {
//        generateList()
    }

    fun generateList(){
        viewModelScope.launch {
            val res = repository.generateList(
                token = token,
                request = GetAllRecipe(null)
            )
            resListGenerate.value = res
        }
    }
}