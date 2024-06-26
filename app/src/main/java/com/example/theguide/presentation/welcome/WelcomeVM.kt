package com.example.theguide.presentation.welcome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theguide.domain.usecase.place.AddRatingUseCase
import com.example.theguide.domain.usecase.place.GetTopPlacesUseCase
import com.example.theguide.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeVM @Inject constructor(
    private val addRatingUseCase: AddRatingUseCase,
    private val getTopPlacesUseCase: GetTopPlacesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(WelcomeState())
    val state = _state.asStateFlow()

    init {
        fetchPlaces()
    }

    private fun fetchPlaces() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = getTopPlacesUseCase.execute()
            val placeList = result.data
            if (!placeList.isNullOrEmpty()) {
                Log.d("WelcomeVM", "placeList: $placeList")
                _state.update {
                    it.copy(
                        placeList = placeList,
                        currentPlace = placeList.first(),
                        currentPlaceIndex = 0,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onAction(action: WelcomeAction) {
        when (action) {
            is WelcomeAction.RatePlace -> addRating(action.userId, action.placeId, action.rating)
        }
    }


    private fun addRating(userId: String, placeId: Int, rating: Double) {
        Log.d("WelcomeVM", "user,place,rating: $userId $placeId $rating")
        viewModelScope.launch {
            val result = addRatingUseCase.execute(
                userId = userId,
                placeId = placeId,
                rating = rating
            )
            Log.d("WelcomeVM", "addRating: ${result.data} ${result.message}")
        }
        if (!state.value.isListCompleted) {
            getNextPlace()
        }
    }

    private fun getNextPlace() {
        _state.update {
            if (state.value.currentPlace == state.value.placeList.last()) {
                it.copy(
                    isListCompleted = true
                )
            } else {
                val nextIndex = state.value.currentPlaceIndex+1
                it.copy(
                    currentPlaceIndex = nextIndex,
                    currentPlace = state.value.placeList[nextIndex]
                )
            }
        }
    }
}