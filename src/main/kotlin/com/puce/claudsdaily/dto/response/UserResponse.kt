package com.puce.claudsdaily.dto.response

import java.util.*

data class UserResponse(
    val id: UUID,
    val username: String,
    val email: String
)