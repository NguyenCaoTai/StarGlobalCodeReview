package com.example.myapplication.bussiness

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.data.Service
import com.example.myapplication.data.local.UserDao
import com.example.myapplication.data.local.model.User
import com.example.myapplication.model.Either
import com.example.myapplication.model.Error
import com.example.myapplication.utilities.TestUtils
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import retrofit2.Response
import retrofit2.mock.Calls
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class UserRepositoryTest {

    lateinit var repository: Repository
    lateinit var service: Service
    lateinit var userDao: UserDao
    lateinit var userMapper: Mapper<String, User>

    @Before
    fun setUp() {
        service = Mockito.mock(Service::class.java)
        userDao = Mockito.mock(UserDao::class.java)
        userMapper = RandomUserMapper()
        repository = UserRepository(
            service = service,
            dao = userDao,
            randomUserMapper = userMapper
        )
    }

    @Test
    fun return_random_user_when_service_response_body_success() = runBlocking {
        Mockito.`when`(service.random())
            .thenReturn(
                Calls.response(
                    Response.success<ResponseBody>(
                        ResponseBody.create(
                            MediaType.parse("application/json"),
                            TestUtils.randomUserJsonString
                        )
                    )
                )
            )

        val expect = Either.Right<Error, User>(TestUtils.userOriginal)

        val result = repository.getRandomUser()

        MatcherAssert.assertThat(
            result.right,
            CoreMatchers.equalTo(expect.right)
        )
    }

    @Test
    fun return_error_message_when_service_response_body_fail() = runBlocking {

        val errorMessage = "this is error"

        Mockito.`when`(service.random())
            .thenReturn(
                Calls.response(
                    Response.error<ResponseBody>(
                        500,
                        ResponseBody.create(
                            MediaType.parse("application/json"),
                            errorMessage
                        )
                    )
                )
            )

        val expect = Either.Left<Error, User>(Error(errorMessage))

        val result = repository.getRandomUser()

        MatcherAssert.assertThat(
            result.left,
            CoreMatchers.equalTo(expect.left)
        )

        Assert.assertEquals(
            result.left?.message,
            errorMessage
        )
    }

    @Test
    fun add_favorite_user_success() = runBlocking {
        Mockito.doNothing()
            .`when`(userDao)
            .insert(TestUtils.userOriginal)

        val expect = Either.Right<Error, Unit>(Unit)

        val result = repository.addFavoriteUser(TestUtils.userOriginal)

        MatcherAssert.assertThat(
            result.right,
            CoreMatchers.equalTo(expect.right)
        )
    }

    @Test
    fun return_error_message_when_dao_insert_success() = runBlocking {
        val message = "Mock Insert fail"

        Mockito
            .doAnswer { throw Exception(message) }
            .`when`(userDao)
            .insert(TestUtils.userOriginal)

        val expect = message

        val result = repository.addFavoriteUser(TestUtils.userOriginal)

        Assert.assertEquals(
            expect,
            result.left!!.message
        )
    }

    @Test
    fun get_all_favorite_user_success() = runBlocking {
        Mockito.`when`(userDao.getAll())
            .thenReturn(TestUtils.testUsers)

        val expect = Either.Right<Error, List<User>>(TestUtils.testUsers)

        val result = repository.getAllFavoriteUser()

        MatcherAssert.assertThat(
            result.right,
            CoreMatchers.equalTo(expect.right)
        )
    }

    @Test
    fun return_error_message_when_dao_get_user_list_fail() = runBlocking {
        val message = "Mock get favorite fail"

        Mockito
            .doAnswer { throw Exception(message) }
            .`when`(userDao)
            .getAll()

        val expect = message

        val result = repository.getAllFavoriteUser()

        Assert.assertEquals(
            expect,
            result.left!!.message
        )
    }
}