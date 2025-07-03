package com.puce.claudsdaily.services

import com.puce.claudsdaily.dto.request.UserRequest
import com.puce.claudsdaily.models.entities.Users
import com.puce.claudsdaily.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {



    fun getAllUsers(): List<Users> =
        userRepository.findAll()

    fun getUserById(id: UUID): Users? =
        userRepository.findById(id).orElse(null)



    @Transactional
    fun createUser(req: UserRequest): Users =
        userRepository.save(
            Users(
                username = req.username.trim(),
                email = req.email.trim()
            )
        )


    @Transactional
    fun updateUser(id: UUID, req: UserRequest): Users? {
        val existing = getUserById(id) ?: return null
        val updated = existing.copy(
            username = req.username.trim(),
            email = req.email.trim()
        )
        return userRepository.save(updated)
    }


    @Transactional
    fun deleteUser(id: UUID): Boolean =
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
}
