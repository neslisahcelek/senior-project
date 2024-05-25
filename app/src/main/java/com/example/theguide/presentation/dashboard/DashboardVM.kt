package com.example.theguide.presentation.dashboard

import Recommendation
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theguide.domain.model.PlaceModel
import com.example.theguide.domain.model.User
import com.example.theguide.domain.usecase.place.GetRecommendationUseCase
import com.example.theguide.domain.usecase.wishlist.WishListUseCases
import com.example.theguide.util.Util
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardVM @Inject constructor(
    private val getRecommendationUseCase: GetRecommendationUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    private val wishListCollection = Firebase.firestore.collection("users")

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.LoadDashboard -> {
                loadDashboard()
                getRecommendations(user = action.user)
            }

            is DashboardAction.AddToWishList -> addToWishList(action.userId, action.place)
            is DashboardAction.RemoveFromWishList -> removeFromWishList(action.userId, action.place)
        }
    }

    private fun removeFromWishList(userId: String?, wish: PlaceModel) {
        if (userId == null) {
            Log.d("removeFromWishList error", "userId is null")
            return
        }
        val document =
            wishListCollection.document(userId).collection("wishlist").document(wish.id.toString())

        CoroutineScope(IO).launch {
            try {
                Tasks.await(document.delete())
            } catch (exception: Exception) {
                Log.d("removeFromWishList", "Error deleting document: ", exception)
            }
        }
    }

    private fun addToWishList(userId: String?, wish: PlaceModel) {
        if (userId == null) {
            Log.d("addToWishList error", "userId is null")
            return
        }
        val document =
            wishListCollection.document(userId).collection("wishlist").document(wish.id.toString())
        CoroutineScope(IO).launch {
            try {
                Tasks.await(
                    document.set(
                        wish.toMap(),
                        SetOptions.merge()
                    )
                )
                Log.d("addToWishList", "DocumentSnapshot added with ID: ${document.id}")
            } catch (exception: Exception) {
                Log.d("addToWishList", "Error adding document: ", exception)
            }
        }
    }

    private fun getRecommendations(user: User?) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = getRecommendationUseCase.execute(
                userId = user?.id ?: ""
            )
            Log.d("getRecommendation", "${result.data?.size} ${result.message}")
            if (result.data != null) {
                _state.update {
                    it.copy(
                        places = result.data
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        error = result.message
                    )
                }
                Log.d("getRecommendation", result.message ?: "")
            }
        }
    }

    private fun loadDashboard() {
        val placeList = Util.getPlaceList()
        _state.update {
            it.copy(
                places = placeList
            )
        }
    }
}