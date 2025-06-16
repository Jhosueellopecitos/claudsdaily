package com.puce.claudsdaily.repositories

import com.puce.claudsdaily.models.entities.Assignment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AssignmentRepository : JpaRepository<Assignment, UUID> {
    fun findByUserId(userId: UUID): List<Assignment>
}