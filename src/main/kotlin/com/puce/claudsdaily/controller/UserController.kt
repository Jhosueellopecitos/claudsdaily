package com.puce.claudsdaily.controller

import com.puce.claudsdaily.dto.request.UserRequest
import com.puce.claudsdaily.dto.response.UserResponse
import com.puce.claudsdaily.exception.NotFoundException
import com.puce.claudsdaily.mapper.toResponse
import com.puce.claudsdaily.services.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    /** GET  /api/users  – lista completa */
    @GetMapping
    fun getAll(): List<UserResponse> =
        userService.getAllUsers().map { it.toResponse() }

    /** GET  /api/users/{id}  – detalle */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): UserResponse =
        userService.getUserById(id)?.toResponse()
            ?: throw NotFoundException("User $id not found")

    /** POST  /api/users  – crear */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody req: UserRequest): UserResponse =
        userService.createUser(req).toResponse()

    /** PUT  /api/users/{id}  – actualizar */
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody req: UserRequest
    ): UserResponse {
        val updated = userService.updateUser(id, req)
            ?: throw NotFoundException("User $id not found")
        return updated.toResponse()
    }

    /** DELETE  /api/users/{id}  – eliminar */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        val ok = userService.deleteUser(id)
        if (!ok) throw NotFoundException("User $id not found")
    }
}
