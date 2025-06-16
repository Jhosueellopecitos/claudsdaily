package com.puce.claudsdaily.services

import com.puce.claudsdaily.models.entities.Users
import com.puce.claudsdaily.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<Users> = userRepository.findAll()

    fun getUserById(id: UUID): Users? = userRepository.findById(id).orElse(null)

    fun createUser(user: Users): Users = userRepository.save(user)

    fun updateUser(id: UUID, updatedUser: Users): Users? {
        val existingUser = getUserById(id) ?: return null
        val newUser = updatedUser.copy(id = existingUser.id)
        return userRepository.save(newUser)
    }

    fun deleteUser(id: UUID): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}