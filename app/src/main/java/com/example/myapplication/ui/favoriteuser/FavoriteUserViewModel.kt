package com.example.myapplication.ui.favoriteuser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bussiness.Repository
import com.example.myapplication.model.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteUserViewModel(
    application: Application,
    repository: Repository
) : AndroidViewModel(application) {
    val favoriteUser = MutableLiveData<Either<String, List<FavoriteUser>>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavoriteUser()
                .let {
                    when (it) {
                        is Either.Left ->
                            Either.Left<String, List<FavoriteUser>>(
                                it.left!!.message
                            )
                        is Either.Right ->
                            Either.Right<String, List<FavoriteUser>>(
                                it.right!!.map { user ->
                                    FavoriteUser(
                                        thumbnail = user.picture,
                                        info = "${user.name.title} ${user.name.first} ${user.name.last}"
                                    )
                                })
                    }
                }
                .run {
                    favoriteUser.postValue(this)
                }
        }
    }
}