package com.example.myapplication

import android.content.Context
import android.text.TextUtils
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.UserDao
import com.example.myapplication.utilities.TestUtils
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@LargeTest
class UserDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun createDb() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            AppDatabase::class.java
        ).build()
        userDao = database.userDao()

        TestUtils.testUsers
            .forEach { database.userDao().insert(it) }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun test_get_user() = runBlocking {
        val expect = 4

        val user = TestUtils.userOriginal.copy(ssn = "322-31-3333")
        userDao.insert(user)

        val result = userDao.getAll().size

        MatcherAssert.assertThat(
            result,
            CoreMatchers.equalTo(expect)
        )
    }

    @Test
    @Throws(Exception::class)
    fun insert_duplication_user() = runBlocking {
        val expect = 4
        try {
            val user = TestUtils.userOriginal.copy(ssn = "322-31-3333")
            userDao.insert(user)
            //insert 2nd
            userDao.insert(user)

        } catch (ex: Exception) {
            val result = userDao.getAll().size

            MatcherAssert.assertThat(
                result,
                CoreMatchers.equalTo(expect)
            )
        }
    }

    @Test
    @Throws(Exception::class)
    fun delete_user() = runBlocking {
        val user = TestUtils.userOriginal.copy(ssn = "322-31-3333")

        val expectInsert = 4
        userDao.insert(user)

        val resultInsert = userDao.getAll().size
        MatcherAssert.assertThat(
            resultInsert,
            CoreMatchers.equalTo(expectInsert)
        )

        val expectDelete = 3
        userDao.delete(user)

        val resultDelete = userDao.getAll().size
        MatcherAssert.assertThat(
            resultDelete,
            CoreMatchers.equalTo(expectDelete)
        )
    }
}