package com.example.theguide.data.repository

import com.example.theguide.data.mapper.toPlaceModelList
import com.example.theguide.data.remote.PlacesAPI
import com.example.theguide.data.remote.dto.RatingInfo
import com.example.theguide.data.remote.dto.RecommendInfo
import com.example.theguide.domain.model.PlaceModel
import com.example.theguide.domain.repository.PlaceRepository
import com.example.theguide.domain.resource.Resource

class PlaceRepositoryImpl(
    private val placesAPI: PlacesAPI
) : PlaceRepository {
    override suspend fun createUser(userId: String): Resource<String> {
        return try {
            val result = placesAPI.createUser(userId)
            Resource.Success(result.message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error occurred (addUser)")
        }
    }

    override suspend fun addRating(userId: String, placeId: Int, rating: Double): Resource<String> {
        val ratingInfo = RatingInfo(userId, placeId, rating)
        return try {
            val response = placesAPI.addRating(ratingInfo)
            Resource.Success(response.message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error occurred (addRating)")
        }
    }

    override suspend fun getRecommendation(
        userId: String,
        recommendationLimit: Int,
        districtList: List<String>
    ): Resource<List<PlaceModel>> {
        return try {
            Resource.Success(
                placesAPI.getRecommendation(
                    recommendInfo = RecommendInfo(
                        userId = userId,
                        recommendationLimit = recommendationLimit,
                        districtList = districtList
                    )
                ).toPlaceModelList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("An error occurred (getPlaces)")
        }
    }

    override suspend fun addToWishList(userId: String, placeId: Int): Resource<String> {
        return try {
            Resource.Success(placesAPI.addToWishList(userId, placeId).message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error occurred (addToWishList)")
        }
    }

    override suspend fun removeFromWishList(userId: String, placeId: Int): Resource<String> {
        return try {
            Resource.Success(placesAPI.removeFromWishList(userId, placeId).message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error occurred (removeFromWishList)")
        }
    }

    override suspend fun getWishList(userId: String): Resource<List<Int>> {
        return try {
            Resource.Success(placesAPI.getWishList(userId).wishList)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error occurred (getWishList)")
        }
    }

    override suspend fun getTopPlaces(): Resource<List<PlaceModel>> {
        return try {
            Resource.Success(
                placesAPI.getTop5().toPlaceModelList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("An error occurred (getTop5)")
        }
    }
}