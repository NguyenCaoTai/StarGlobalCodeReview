package com.example.myapplication.bussiness

import com.example.myapplication.data.local.model.User
import com.example.myapplication.model.Either
import com.example.myapplication.model.Error

interface Repository {
    suspend fun getRandomUser(): Either<Error, User>
    suspend fun addFavoriteUser(user: User): Either<Error, Unit>
    suspend fun getAllFavoriteUser(): Either<Error, List<User>>
}