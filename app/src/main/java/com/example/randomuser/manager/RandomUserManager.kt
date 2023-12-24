package com.example.randomuser.manager

import android.util.Log
import com.example.randomuser.features.userList.model.User
import com.example.randomuser.service.RandomUser
import com.example.randomuser.service.RandomUserResponse
import com.example.randomuser.service.RandomUserService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.io.IOException

/**
 * Manager class responsible for handling network operations related to random users.
 *
 * @property randomUserService The service interface for making network requests related to random users.
 */
class RandomUserManager(private val randomUserService: RandomUserService) {

    /**
     * TAG for logging purposes.
     */
    companion object {
        private val TAG = RandomUserManager::class.java.simpleName
    }

    /**
     * Fetches random users from the network.
     *
     * @return Response containing the list of users or an error response.
     */
    suspend fun getRandomUsers(numberOfUsers: Int): Response<List<User>> {
        return try {
            val response = randomUserService.getRandomUsers(numberOfUsers)
            handleResponse(response)
        } catch (e: IOException) {
            Log.e(TAG, "Network error", e)
            Response.error(500, createErrorResponse("Network error"))
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error", e)
            Response.error(500, createErrorResponse("Unexpected error"))
        }
    }

    /**
     * Handles the network response and maps it to a custom result.
     *
     * @param response The network response to be handled.
     * @return Response containing the list of users or an error response.
     */
    private fun handleResponse(response: Response<RandomUserResponse>): Response<List<User>> {
        return if (response.isSuccessful) {
            val users = filterAndMapUsers(response.body()?.results)
            Response.success(users)
        } else {
            Log.e(TAG, "API error: ${response.code()} ${response.message()}")
            Response.error(response.code(), createErrorResponse("API error"))
        }
    }

    /**
     * Filters out duplicate users and maps them to [User] objects.
     *
     * @param randomUsers The list of [RandomUser] objects.
     * @return The list of mapped [User] objects without duplicates.
     */
    private fun filterAndMapUsers(randomUsers: List<RandomUser>?): List<User> {
        val distinctUsers = randomUsers?.distinctBy { it.email }
        return distinctUsers?.map { mapToUser(it) } ?: emptyList()
    }

    /**
     * Maps a [RandomUser] to a User.
     *
     * @param randomUser The [RandomUser] to be mapped.
     * @return The mapped [User].
     */
    private fun mapToUser(randomUser: RandomUser): User {
        return User(
            "${randomUser.name.title} ${randomUser.name.first} ${randomUser.name.last}",
            randomUser.email,
            randomUser.picture.thumbnail
        )
    }

    /**
     * Creates an error response body with the given error message.
     *
     * @param errorMessage The error message.
     * @return The error response body.
     */
    private fun createErrorResponse(errorMessage: String): ResponseBody {
        return "$errorMessage".toResponseBody("application/json".toMediaTypeOrNull())
    }
}