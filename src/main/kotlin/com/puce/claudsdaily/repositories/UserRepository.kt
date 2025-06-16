package com.puce.claudsdaily.repositories

import com.puce.claudsdaily.models.entities.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<Users, UUID> {
    fun findByUsername(username: String): Optional<Users>
}