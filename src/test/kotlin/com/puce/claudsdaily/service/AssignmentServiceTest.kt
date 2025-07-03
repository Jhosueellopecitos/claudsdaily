package com.puce.claudsdaily.service

import com.puce.claudsdaily.dto.request.AssignmentRequest
import com.puce.claudsdaily.models.entities.Assignment
import com.puce.claudsdaily.models.entities.Users
import com.puce.claudsdaily.repositories.AssignmentRepository
import com.puce.claudsdaily.services.AssignmentService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class AssignmentServiceTest {

    private val repo: AssignmentRepository = mockk()
    private val service = AssignmentService(repo)

    private val user = Users(UUID.randomUUID(), "tester", "test@example.com")

    @Test
    fun `createAssignment maps dto and saves`() {
        val dto = AssignmentRequest("Title", "Desc", LocalDateTime.now(), user.id!!)
        val saved = Assignment(
            id = UUID.randomUUID(),
            title = dto.title,
            description = dto.description,
            dueDate = dto.dueDate,
            user = user
        )

        every { repo.save(any<Assignment>()) } returns saved

        val result = service.createAssignment(dto, user)

        assertEquals(dto.title, result.title)
        verify { repo.save(any<Assignment>()) }
    }

    @Test
    fun `updateAssignment returns null if not found`() {
        val id = UUID.randomUUID()
        val dto = AssignmentRequest("T", null, null, user.id!!)
        every { repo.findById(id) } returns Optional.empty()

        val updated = service.updateAssignment(id, dto)

        assertNull(updated)
    }
}
