package com.example.myapplication.ui.main.utilities

import android.app.Application
import com.example.myapplication.bussiness.*
import com.example.myapplication.data.Service
import com.example.myapplication.data.ServiceFactory
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.model.User
import com.example.myapplication.ui.favoriteuser.FavoriteViewModelFactory
import com.example.myapplication.ui.main.MainViewModelFactory

object InjectorUtils {
    fun provideMainViewModelFactory(application: Application): MainViewModelFactory =
        MainViewModelFactory(
            application,
            getRepository(application)
        )

    fun provideFavoriteViewModelFactory(application: Application): FavoriteViewModelFactory =
        FavoriteViewModelFactory(
            application,
            getRepository(application)
        )

    private fun getDB(application: Application): AppDatabase =
        AppDatabase.getInstance(application)

    private fun getService(): Service =
        ServiceFactory.getService()

    private fun getUserMapper(): Mapper<String, User> =
        RandomUserMapper()

    private fun getRepository(application: Application): Repository =
        UserRepository(
            service = getService(),
            dao = getDB(application).userDao(),
            randomUserMapper = getUserMapper()
        )
}