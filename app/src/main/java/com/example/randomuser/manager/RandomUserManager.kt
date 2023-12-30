package com.example.randomuser.manager

import android.util.Log
import com.example.randomuser.model.Location
import com.example.randomuser.model.User
import com.example.randomuser.service.RandomUser
import com.example.randomuser.service.RandomUserResponse
import com.example.randomuser.service.RandomUserService
import com.example.randomuser.utils.Result
import retrofit2.Response
import java.io.IOException

/**
 * Manager class responsible for handling network operations related to random users.
 *
 * @property randomUserService The service interface for making network requests related to random users.
 */
class RandomUserManager(private val randomUserService: RandomUserService) {

    companion object {
        // TAG for logging purposes.
        private val TAG = RandomUserManager::class.java.simpleName
    }

    /**
     * Maps a [RandomUser] to a User.
     *
     * @param randomUser The [RandomUser] to be mapped.
     * @return The mapped [User].
     */
    private fun mapToUser(randomUser: RandomUser): User {
        val name ="${randomUser.name.title} ${randomUser.name.first} ${randomUser.name.last}"
        val location = Location(randomUser.location.coordinates.latitude, randomUser.location.coordinates.longitude)

        return User(
            name,
            randomUser.email,
            randomUser.gender,
            randomUser.picture.thumbnail,
            randomUser.picture.medium,
            randomUser.registered.date,
            randomUser.cell,
            location
        )
    }

    /**
     * Filters out duplicate users, applies the age filter, and maps them to [User] objects.
     *
     * @param randomUsers The list of [RandomUser] objects.
     * @return The list of mapped [User] objects without duplicates and with age >= 18.
     */
    private fun filterAndMapUsers(randomUsers: List<RandomUser>?): List<User> {
        val distinctUsers = randomUsers
            ?.distinctBy { it.email }
            ?.filter { it.registered.age >= 18 }

        return distinctUsers?.map { mapToUser(it) } ?: emptyList()
    }

    /**
     * Handles the network response and maps it to a Result.
     *
     * @param response The network response to be handled.
     * @return Result containing either the list of users or an error message.
     */
    private fun handleResponse(response: Response<RandomUserResponse>): Result<List<User>> {
        return if (response.isSuccessful) {
            val users = filterAndMapUsers(response.body()?.results)
            Result.Success(users)
        } else {
            Log.e(TAG, "API error: ${response.code()} ${response.message()}")
            Result.Error("Failed to fetch users. Please try again later.")
        }
    }

    /**
     * Fetches random users from the network.
     *
     * @return Result containing either the list of users or an error message.
     */
    suspend fun getRandomUsers(numberOfUsers: Int): Result<List<User>> {
        return try {
            val response = randomUserService.getRandomUsers(numberOfUsers)
            handleResponse(response)
        } catch (e: IOException) {
            Log.e(TAG, "Network error", e)
            Result.Error("Failed to fetch users. Please try again later.")
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error", e)
            Result.Error("An unexpected error occurred.")
        }
    }
}