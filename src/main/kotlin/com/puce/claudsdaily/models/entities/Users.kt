// models/entities/User.kt
package com.puce.claudsdaily.models.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    val email: String
)
