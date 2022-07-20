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
class StockViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
) : ViewModel() {

    val resStockList: MutableState<Map<String, Int>> = mutableStateOf(mapOf())
    init {
            getStock()
    }

    fun addStock( map: Map<String, Int>){
        viewModelScope.launch {
            val stockList = resStockList.value + map
            repository.addStock(
                token = token,
                stockMap = StockMap(stockMap = stockList)
            )
        }
    }

    fun deleteStock( key: String){
        viewModelScope.launch {
            val stockList = resStockList.value.filterKeys { it != key }
            repository.deleteStock(
                token = token,
                stockMap = StockMap(stockMap = stockList)
            )
        }
    }

    fun getStock(){
        viewModelScope.launch {
            val res = repository.getStock(
                token = token,
                request = GetAllRecipe(null)
            )
            resStockList.value = res
        }
    }
}