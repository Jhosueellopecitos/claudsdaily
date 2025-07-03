package com.puce.claudsdaily.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.util.*

data class UserRequest(
    @field:NotBlank val username: String,
    @field:Email val email: String
)
