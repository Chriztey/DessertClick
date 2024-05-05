package com.example.dessertclicker.model

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.dessertclicker.R
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.data.DessertUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel () {

    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState : StateFlow<DessertUiState> = _uiState.asStateFlow()

    fun onDessertClicked() {
        //Update the revenue


        _uiState.update { currentState ->
            val dessertsSold = currentState.dessertsSold + 1
            val nextDessertIndex = determineDessertIndex(dessertsSold)
            currentState.copy(
                totalProfit = currentState.totalProfit + currentState.currentDessertPrice,
                currentDessertIndex = nextDessertIndex,
                dessertsSold = dessertsSold,
                currentDessertPrice = dessertList[nextDessertIndex].price,
                currentDessertImageId = dessertList[nextDessertIndex].imageId
            )
        }
    }




    private fun determineDessertIndex(
        dessertsSold: Int
    ): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (dessertsSold >= dessertList[index].startProductionAmount) {
                dessertIndex = index
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertIndex
    }


}