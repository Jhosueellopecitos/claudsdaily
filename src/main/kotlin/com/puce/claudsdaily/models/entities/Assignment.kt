package com.puce.claudsdaily.models.entities
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
@Entity
@Table(name = "assignments")
data class Assignment(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    val title: String,

    val description: String? = null,

    val dueDate: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: Users
)