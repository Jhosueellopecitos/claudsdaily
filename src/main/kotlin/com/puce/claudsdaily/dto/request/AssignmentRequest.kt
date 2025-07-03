package com.puce.claudsdaily.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.util.*



data class AssignmentRequest(
    @field:NotBlank(message = "El título es obligatorio")
    val title: String,

    val description: String? = null,
    val dueDate: LocalDateTime? = null,

    @field:NotNull(message = "El userId es obligatorio")
    val userId: UUID
)