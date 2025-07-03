package com.puce.claudsdaily.mapper

import com.puce.claudsdaily.dto.response.*
import com.puce.claudsdaily.models.entities.*

fun Users.toResponse() = UserResponse(id!!, username, email)

fun Assignment.toResponse() = AssignmentResponse(
    id = id!!,
    title = title,
    description = description,
    dueDate = dueDate,
    user = user.toResponse()
)
