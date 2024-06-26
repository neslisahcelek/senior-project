package com.example.theguide.domain.usecase.place

import com.example.theguide.domain.repository.PlaceRepository
import com.example.theguide.domain.resource.Resource
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
){
    suspend fun execute(userId : String): Resource<String> {
        return placeRepository.createUser(userId)
    }
}