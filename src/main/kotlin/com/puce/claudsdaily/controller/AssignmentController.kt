package com.puce.claudsdaily.controller

import com.puce.claudsdaily.models.entities.Assignment
import com.puce.claudsdaily.services.AssignmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/assignments")
class AssignmentController(private val assignmentService: AssignmentService) {

    @GetMapping
    fun getAll(): List<Assignment> = assignmentService.getAllAssignments()

    @GetMapping("/user/{userId}")
    fun getByUserId(@PathVariable userId: UUID): List<Assignment> =
        assignmentService.getAssignmentsByUserId(userId)

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<Assignment> {
        val found = assignmentService.getAssignmentById(id)
        return if (found != null) ResponseEntity.ok(found)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun create(@RequestBody assignment: Assignment): Assignment =
        assignmentService.createAssignment(assignment)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody assignment: Assignment): ResponseEntity<Assignment> {
        val existing = assignmentService.getAssignmentById(id) ?: return ResponseEntity.notFound().build()
        val updated = assignmentService.createAssignment(assignment.copy(id = id))
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        val existing = assignmentService.getAssignmentById(id) ?: return ResponseEntity.notFound().build()
        assignmentService.deleteAssignment(id)
        return ResponseEntity.noContent().build()
    }
}
