package com.puce.claudsdaily.controller

import com.puce.claudsdaily.dto.request.AssignmentRequest
import com.puce.claudsdaily.dto.response.AssignmentResponse
import com.puce.claudsdaily.exception.NotFoundException
import com.puce.claudsdaily.mapper.toResponse
import com.puce.claudsdaily.services.AssignmentService
import com.puce.claudsdaily.services.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/assignments")
class AssignmentController(
    private val assignmentService: AssignmentService,
    private val userService: UserService
) {

    /** GET  /api/assignments  – lista completa */
    @GetMapping
    fun getAll(): List<AssignmentResponse> =
        assignmentService.getAllAssignments().map { it.toResponse() }

    /** GET  /api/assignments/user/{userId}  – filtrar por usuario */
    @GetMapping("/user/{userId}")
    fun getByUser(@PathVariable userId: UUID): List<AssignmentResponse> =
        assignmentService.getAssignmentsByUserId(userId).map { it.toResponse() }

    /** GET  /api/assignments/{id}  – detalle */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): AssignmentResponse =
        assignmentService.getAssignmentById(id)?.toResponse()
            ?: throw NotFoundException("Assignment $id not found")

    /** POST  /api/assignments  – crear */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: AssignmentRequest): AssignmentResponse {
        val user = userService.getUserById(req.userId)
            ?: throw NotFoundException("User ${req.userId} not found")

        val created = assignmentService.createAssignment(req, user)
        return created.toResponse()
    }

    /** PUT  /api/assignments/{id}  – actualizar */
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody req: AssignmentRequest
    ): AssignmentResponse {
        val updated = assignmentService.updateAssignment(id, req)
            ?: throw NotFoundException("Assignment $id not found")
        return updated.toResponse()
    }

    /** DELETE  /api/assignments/{id}  – eliminar */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        val ok = assignmentService.deleteAssignment(id)
        if (!ok) throw NotFoundException("Assignment $id not found")
    }
}
