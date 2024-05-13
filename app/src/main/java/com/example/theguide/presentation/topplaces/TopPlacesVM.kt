package com.example.theguide.presentation.topplaces

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.theguide.R
import com.example.theguide.domain.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopPlacesVM @Inject constructor() : ViewModel() {
    var state by mutableStateOf(TopPlacesState())
        private set

    init {
        loadTopPlaces()
    }

    fun onAction(action: TopPlacesAction) {
        when (action) {
            is TopPlacesAction.LoadTopPlaces -> loadTopPlaces()
            is TopPlacesAction.NavigateToPlaceDetails -> navigateToPlaceDetails(action.placeId)
        }
    }

    private fun navigateToPlaceDetails(placeId: String) {

    }

    private fun loadTopPlaces() {
        val placeList = listOf(
            Place(
                id = 2,
                name = "Understone",
                rating = 4.5,
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQR_abBtnzBFl_-kLkB-fbC-nskMexTTiE7w9GroVJTGA&s",
            ),
            Place(
                id = 1,
                name = "Walkers",
                rating = 4.5,
                imageUrl = "https://lh3.googleusercontent.com/p/AF1QipP5WCtkTdnMTOPErh_wT_2mvaoGvxGvkqMajlvl=s1360-w1360-h1020-rw",
            ),
            Place(
                id = 3,
                name = "Restaurant",
                rating = 4.5,
                imageUrl = "https://lh3.googleusercontent.com/places/ANXAkqF4Zu9H-23naAAe8lm4du88xkuNIhp-uBF-MSWb03-bKYz6uXR0_NDiDZnkgSIJ_Uxl2ctJ85TACMuLVWVTzMnaeCws6DamgM4=s1600-w400",
            )
        )
        state = state.copy(
            category = "Kafe",
            topPlaces = placeList
        )
    }


}