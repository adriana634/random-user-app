package com.example.randomuser

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.randomuser.service.RandomUserService
import com.example.randomuser.service.RetrofitClient
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RandomUserServiceTest {
    private lateinit var randomUserService: RandomUserService

    @Before
    fun setUp() {
        randomUserService = RetrofitClient.randomUserService
    }

    @Test
    fun getRandomUsers_shouldReturnValidResponse() = runBlocking {
        try {
            val numberOfUsers = 5
            val response = randomUserService.getRandomUsers(numberOfUsers, 1, "exampleSeed")

            assertTrue(response.isSuccessful)

            assertNotNull(response.body())
            assertTrue(response.body()?.results?.isNotEmpty() == true)

        } catch (e: Exception) {
            fail("Exception occurred: ${e.message}")
        }
    }
}