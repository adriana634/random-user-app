package com.example.randomuser

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.randomuser.manager.RandomUserManager
import com.example.randomuser.service.RandomUserResponse
import com.example.randomuser.service.RandomUserService
import com.example.randomuser.service.RetrofitClient
import com.example.randomuser.utils.Result
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import retrofit2.http.Query
import java.io.IOException

class FakeErrorRandomUserService : RandomUserService {
    override suspend fun getRandomUsers(@Query(value = "results") numberOfUsers: Int): Response<RandomUserResponse> {
        throw IOException("Simulated network error")
    }
}

@RunWith(AndroidJUnit4::class)
class RandomUserManagerInstrumentedTest {

    private val randomUserManager = RandomUserManager(RetrofitClient.randomUserService)
    private val fakeErrorRandomUserManager = RandomUserManager(FakeErrorRandomUserService())

    @Test
    fun getRandomUsers_networkSuccess_returnsMappedUsers(): Unit = runBlocking {
        // Act
        val numberOfUsers = 5
        val result = randomUserManager.getRandomUsers(numberOfUsers)

        // Assert
        assertTrue(result is Result.Success)

        val mappedUsers = (result as Result.Success).data
        assertNotNull("Mapped users should not be null", mappedUsers)
        assertNotEquals("Mapped users should not be empty", 0, mappedUsers?.size)

        mappedUsers?.forEach { user ->
            assertNotNull("User name should not be null", user.name)
            assertNotNull("User email should not be null", user.email)
            assertNotNull("User picture should not be null", user.pictureThumbnail)
        }
    }

    @Test
    fun getRandomUsers_networkError_returnsErrorResponse() = runBlocking {
        // Act
        val numberOfUsers = 5
        val result = fakeErrorRandomUserManager.getRandomUsers(numberOfUsers)

        // Assert
        assertTrue(result is Result.Error)
    }
}