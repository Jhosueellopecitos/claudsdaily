package com.puce.claudsdaily.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.puce.claudsdaily.dto.request.AssignmentRequest
import com.puce.claudsdaily.models.entities.Assignment
import com.puce.claudsdaily.models.entities.Users
import com.puce.claudsdaily.services.AssignmentService
import com.puce.claudsdaily.services.UserService
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.*

@WebMvcTest(AssignmentController::class)
class AssignmentControllerTest(
    @Autowired val mvc: MockMvc,
    @Autowired val om: ObjectMapper
) {


    @MockBean lateinit var assignmentService: AssignmentService
    @MockBean lateinit var userService: UserService


    private val user = Users(UUID.randomUUID(), "tester", "t@test.com")
    private val userId = user.id ?: error("user id is null")


    @Test
    fun `POST assignments returns 201`() {
        val req = AssignmentRequest(
            title = "Task",
            description = "Desc",
            dueDate = LocalDateTime.now().plusDays(1),
            userId = userId
        )
        val saved = Assignment(UUID.randomUUID(), req.title, req.description, req.dueDate, user)

        whenever(userService.getUserById(userId)).thenReturn(user)
        whenever(assignmentService.createAssignment(req, user)).thenReturn(saved)

        mvc.perform(
            post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.title", `is`("Task")))

        verify(userService).getUserById(userId)
        verify(assignmentService).createAssignment(req, user)
    }

    @Test
    fun `POST assignments returns 404 when user not found`() {
        val req = AssignmentRequest("Task", null, null, UUID.randomUUID())
        whenever(userService.getUserById(req.userId)).thenReturn(null)

        mvc.perform(
            post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req))
        )
            .andExpect(status().isNotFound)

        verify(userService).getUserById(req.userId)
    }

    @Test
    fun `PUT assignments returns 404 when assignment not found`() {
        val id = UUID.randomUUID()
        val req = AssignmentRequest("Task", null, null, userId)
        whenever(assignmentService.updateAssignment(id, req)).thenReturn(null)

        mvc.perform(
            put("/api/assignments/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req))
        )
            .andExpect(status().isNotFound)

        verify(assignmentService).updateAssignment(id, req)
    }
}

