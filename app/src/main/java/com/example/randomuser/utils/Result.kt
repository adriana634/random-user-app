package com.example.randomuser.utils

/**
 * Represents the result of an operation that can either be a success containing data of type [T]
 * or an error containing an error message.
 */
sealed class Result<out T> {
    /**
     * Represents a successful result of an operation, containing data of type [T].
     *
     * @property data The data associated with the success.
     * @param T The type of data contained in the success.
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Represents an error result of an operation, containing an error message.
     *
     * @property message The error message associated with the error.
     */
    data class Error(val message: String) : Result<Nothing>()

    /**
     * Executes the given [action] only if the current instance is a [Success]. It allows handling
     * success-specific logic without affecting the transformation operations in a chain.
     *
     * @param action Action to be executed if the current instance is a [Success].
     * @return The original instance.
     */
    fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) {
            action(data)
        }
        return this
    }

    /**
     * Executes the given [action] only if the current instance is an [Error]. It allows handling
     * error-specific logic without affecting the transformation operations in a chain.
     *
     * @param action Action to be executed if the current instance is an [Error].
     * @return The original instance.
     */
    fun onError(action: (String) -> Unit): Result<T> {
        if (this is Error) {
            action(message)
        }
        return this
    }

    /**
     * Applies the given [transform] function to the data inside a [Success] instance,
     * returning a new [Result] with the transformed data. If the current instance is an [Error],
     * it returns the original [Error] instance unchanged.
     *
     * @param transform Function to transform the data inside a [Success] instance.
     * @return New [Result] with the transformed data or the original instance if it's an [Error].
     */
    fun <R> map(transform: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
        }
    }
}
