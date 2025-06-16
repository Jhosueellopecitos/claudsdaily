package com.puce.claudsdaily.services

import com.puce.claudsdaily.models.entities.Assignment
import com.puce.claudsdaily.repositories.AssignmentRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AssignmentService(private val assignmentRepository: AssignmentRepository) {

    fun getAllAssignments(): List<Assignment> = assignmentRepository.findAll()

    fun getAssignmentsByUserId(userId: UUID): List<Assignment> =
        assignmentRepository.findByUserId(userId)

    fun getAssignmentById(id: UUID): Assignment? =
        assignmentRepository.findById(id).orElse(null)

    fun createAssignment(assignment: Assignment): Assignment =
        assignmentRepository.save(assignment)

    fun updateAssignment(id: UUID, updated: Assignment): Assignment? {
        val existing = getAssignmentById(id) ?: return null
        val newAssignment = updated.copy(id = existing.id)
        return assignmentRepository.save(newAssignment)
    }

    fun deleteAssignment(id: UUID): Boolean {
        return if (assignmentRepository.existsById(id)) {
            assignmentRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}