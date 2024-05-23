package com.example.theguide.domain.usecase.wishlist

import com.example.theguide.domain.repository.PlaceRepository
import javax.inject.Inject

class RemoveFromWishListUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
){
    suspend fun execute(userId: String, placeId: Int) =
        placeRepository.removeFromWishList(userId, placeId)
}