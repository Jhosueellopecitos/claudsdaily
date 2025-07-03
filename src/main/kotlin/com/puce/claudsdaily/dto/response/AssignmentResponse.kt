package com.puce.claudsdaily.dto.response

import java.time.LocalDateTime
import java.util.*

data class AssignmentResponse(
    val id: UUID,
    val title: String,
    val description: String?,
    val dueDate: LocalDateTime?,
    val user: UserResponse
)
