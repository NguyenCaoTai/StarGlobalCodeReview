package com.example.myapplication.bussiness

import com.example.myapplication.data.Service
import com.example.myapplication.data.local.UserDao
import com.example.myapplication.data.local.model.User
import com.example.myapplication.model.Either
import com.example.myapplication.model.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.IllegalArgumentException

class UserRepository(
    private val service: Service,
    private val dao: UserDao,
    private val randomUserMapper: Mapper<String, User>
) : Repository {
    override suspend fun getRandomUser(): Either<Error, User> =
        withContext(Dispatchers.IO) {
            service.random()
                .execute()
                .let {
                    when (it.isSuccessful) {
                        false -> Either.Left(Error(it.errorBody()?.string() ?: "Not define"))
                        else -> when (it.body()) {
                            null -> Either.Left<Error, User>(Error("Data is empty"))
                            else -> try {
                                Either.Right<Error, User>(
                                    randomUserMapper.transform(
                                        it.body()!!.string()
                                    )
                                )
                            } catch (ex: IllegalArgumentException) {
                                Either.Left<Error, User>(Error("Data is empty"))
                            }
                        }
                    }
                }
        }

    override suspend fun addFavoriteUser(user: User): Either<Error, Unit> =
        try {
            dao.insert(user)
            Either.Right<Error, Unit>(Unit)
        } catch (ex: Exception) {
            Either.Left<Error, Unit>(Error(ex.message ?: "Insert failed"))
        }

    override suspend fun getAllFavoriteUser(): Either<Error, List<User>> =
        withContext(Dispatchers.IO) {
            try {
                Either.Right<Error, List<User>>(
                    dao.getAll()
                )
            } catch (ex: Exception) {
                Either.Left<Error, List<User>>(Error(ex.message ?: "Get favorite user failed"))
            }
        }
}