package com.example.randomuser

import com.example.randomuser.utils.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ResultTest {

    @Test
    fun `map should transform data for Success`() {
        // Arrange
        val result: Result<Int> = Result.Success(42)

        // Act
        val mappedResult = result.map { it * 2 }

        // Assert
        assertTrue(mappedResult is Result.Success)
        assertEquals(84, (mappedResult as Result.Success).data)
    }

    @Test
    fun `map should not transform data for Error`() {
        // Arrange
        val result: Result<Int> = Result.Error("Error message")

        // Act
        val mappedResult = result.map { it * 2 }

        // Assert
        assertTrue(mappedResult is Result.Error)
        assertEquals("Error message", (mappedResult as Result.Error).message)
    }

    @Test
    fun `onError should execute action for Error`() {
        // Arrange
        var errorMessage: String? = null
        val result: Result<Int> = Result.Error("Error message")

        // Act
        val resultWithAction = result.onError { errorMessage = it }

        // Assert
        assertTrue(resultWithAction is Result.Error)
        assertEquals("Error message", (resultWithAction as Result.Error).message)
        assertEquals("Error message", errorMessage)
    }

    @Test
    fun `onError should not execute action for Success`() {
        // Arrange
        var errorMessage: String? = null
        val result: Result<Int> = Result.Success(42)

        // Act
        val resultWithAction = result.onError { errorMessage = it }

        // Assert
        assertTrue(resultWithAction is Result.Success)
        assertNull(errorMessage)
    }

    @Test
    fun `onSuccess should execute action for Success`() {
        // Arrange
        var successData: Int? = null
        val result: Result<Int> = Result.Success(42)

        // Act
        val resultWithAction = result.onSuccess { successData = it }

        // Assert
        assertTrue(resultWithAction is Result.Success)
        assertEquals(42, (resultWithAction as Result.Success).data)
        assertEquals(42, successData)
    }

    @Test
    fun `onSuccess should not execute action for Error`() {
        // Arrange
        var successData: Int? = null
        val result: Result<Int> = Result.Error("Error message")

        // Act
        val resultWithAction = result.onSuccess { successData = it }

        // Assert
        assertTrue(resultWithAction is Result.Error)
        assertNull(successData)
    }
}
