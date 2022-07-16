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
class PlanViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
) : ViewModel() {

    val resPlanList: MutableState<List<Data>> = mutableStateOf(listOf())
    init {
//        viewModelScope.launch {
//            val res = repository.postRecipe(
//                token = token,
//                recipe = recipe
//            )
//            resRecipe.value = res
//        }

//        viewModelScope.launch {
//            val res = repository.searchRecipeById(
//                token = token,
//                recipeIdList = SearchById(listOf(5,6,7))
//            )
//            resRecipeList.value = res
//        }
        viewModelScope.launch {
            val res = repository.getPlan(
                token = token,
                request = GetAllRecipe(null)
            )
            resPlanList.value = res
        }
    }

    fun updatePlan(planId: Int){
        viewModelScope.launch {
            val planList = resPlanList.value.map {  it.recipe  }.map { it.id }.toMutableList()
            planList += planId
            repository.updatePlan(
                token = token,
                plan = Plan(planList = planList.toSet().toList() as List<Int>)
            )
        }
    }

    fun deletePlan(planId: Int){
        viewModelScope.launch {
            val planList = resPlanList.value.map {  it.recipe  }.map { it.id }.toMutableList()
            planList.remove(planId)
            repository.deletePlan(
                token = token,
                plan = Plan(planList = planList.toSet().toList() as List<Int>)
            )
        }
    }

    fun getPlan(){
        viewModelScope.launch {
            val res = repository.getPlan(
                token = token,
                request = GetAllRecipe(null)
            )
            resPlanList.value = res
        }
    }
}