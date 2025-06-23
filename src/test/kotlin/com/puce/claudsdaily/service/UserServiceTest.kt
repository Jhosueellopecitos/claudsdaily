package com.puce.claudsdaily.service

import com.puce.claudsdaily.models.entities.Users
import com.puce.claudsdaily.repositories.UserRepository
import com.puce.claudsdaily.services.UserService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class UserServiceTest {

    private val userRepository: UserRepository = mockk()
    private val userService = UserService(userRepository)

    @Test
    fun `getAllUsers returns list of users`() {
        val users = listOf(
            Users(UUID.randomUUID(), "jhosue", "jhosue@example.com"),
            Users(UUID.randomUUID(), "claudia", "claudia@example.com")
        )
        every { userRepository.findAll() } returns users

        val result = userService.getAllUsers()

        assertEquals(2, result.size)
        assertEquals("jhosue", result[0].username)
        verify(exactly = 1) { userRepository.findAll() }
    }

    @Test
    fun `getUserById returns user when found`() {
        val userId = UUID.randomUUID()
        val user = Users(userId, "jhosue", "jhosue@example.com")
        every { userRepository.findById(userId) } returns Optional.of(user)

        val result = userService.getUserById(userId)

        assertNotNull(result)
        assertEquals(user.username, result?.username)
        verify { userRepository.findById(userId) }
    }

    @Test
    fun `getUserById returns null when user not found`() {
        val userId = UUID.randomUUID()
        every { userRepository.findById(userId) } returns Optional.empty()

        val result = userService.getUserById(userId)

        assertNull(result)
        verify { userRepository.findById(userId) }
    }

    @Test
    fun `createUser saves and returns user`() {
        val user = Users(UUID.randomUUID(), "clauds", "clauds@example.com")
        every { userRepository.save(user) } returns user

        val result = userService.createUser(user)

        assertEquals("clauds", result.username)
        verify { userRepository.save(user) }
    }
}
