package com.example.myapplication.ui.main

import android.app.Application
import android.text.Spannable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.bussiness.Repository
import com.example.myapplication.data.local.model.User
import com.example.myapplication.model.Either
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    val avatar: ObservableField<String> = ObservableField<String>()
    val displayInfo: ObservableField<Spannable> = ObservableField<Spannable>()

    val messageInfo: MutableLiveData<String> = MutableLiveData<String>()

    private lateinit var currentUser: User

    fun getRandomUser() {
        viewModelScope.launch(Dispatchers.IO) {
            with(repository.getRandomUser()) {
                when (this) {
                    is Either.Left -> messageInfo.postValue(this.left!!.message)
                    is Either.Right -> this.right
                        ?.also { currentUser = it }
                        .apply {
                            avatar.set(this?.picture)
                            displayInfo.set(
                                StringFormat.formatUserCardInfo(
                                    getApplication(),
                                    "My name is",
                                    "${currentUser.name.title} ${currentUser.name.first} ${currentUser.name.last}"
                                )
                            )
                        }
                }
            }
        }
    }

    fun addCurrentUserToFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            with(repository.addFavoriteUser(currentUser)) {
                when (this) {
                    is Either.Right -> messageInfo.postValue("User add to favorite store")
                    is Either.Left -> messageInfo.postValue("Favorite User add failed")
                }
            }
        }
    }

    fun onUserInfoAction(type: UserInfoType) {
        when (type) {
            UserInfoType.NAME -> Pair(
                "My name is",
                "${currentUser.name.title} ${currentUser.name.first} ${currentUser.name.last}"
            )
            UserInfoType.ADDRESS -> Pair(
                "My address is",
                "${currentUser.location.street}, ${currentUser.location.city} ${currentUser.location.state}"
            )
            UserInfoType.DOB -> Pair(
                "My day of birth is",
                SimpleDateFormat("MMM d yyyy").format(Date(currentUser.dateOfBirth))
            )
            UserInfoType.PHONE -> Pair("My phone is", currentUser.phone)
            UserInfoType.LOCK -> Pair("No Content", "don't know")
        }.apply {
            displayInfo.set(StringFormat.formatUserCardInfo(getApplication(), first, second))
        }
    }
}