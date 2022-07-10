package com.example.cookare.ui.food

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookare.model.Recipe
import com.example.cookare.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FoodScreenViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
): ViewModel()
{
    val recipes1: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val recipes2: MutableState<List<Recipe>> = mutableStateOf(listOf())
    init {
        viewModelScope.launch {
            val result1 = repository.search(
                token = token,
                page = 1,
                query = "beef"
            )

            val result2 = repository.search(
                token = token,
                page = 1,
                query = "chicken"
            )
            recipes1.value = result1
            recipes2.value = result2
        }
    }
}