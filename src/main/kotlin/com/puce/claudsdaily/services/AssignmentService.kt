package com.puce.claudsdaily.services

import com.puce.claudsdaily.dto.request.AssignmentRequest
import com.puce.claudsdaily.models.entities.Assignment
import com.puce.claudsdaily.models.entities.Users
import com.puce.claudsdaily.repositories.AssignmentRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class AssignmentService(
    private val assignmentRepository: AssignmentRepository
) {


    fun getAllAssignments(): List<Assignment> =
        assignmentRepository.findAll()

    fun getAssignmentsByUserId(userId: UUID): List<Assignment> =
        assignmentRepository.findByUserId(userId)

    fun getAssignmentById(id: UUID): Assignment? =
        assignmentRepository.findById(id).orElse(null)


    @Transactional
    fun createAssignment(req: AssignmentRequest, user: Users): Assignment =
        assignmentRepository.save(
            Assignment(
                title       = req.title.trim(),
                description = req.description?.trim(),
                dueDate     = req.dueDate,
                user        = user
            )
        )



    @Transactional
    fun updateAssignment(id: UUID, req: AssignmentRequest): Assignment? {
        val existing = getAssignmentById(id) ?: return null

        val updated = existing.copy(
            title       = req.title.trim(),
            description = req.description?.trim(),
            dueDate     = req.dueDate
        )

        return assignmentRepository.save(updated)
    }



    @Transactional
    fun deleteAssignment(id: UUID): Boolean =
        if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id); true
        } else false
}
