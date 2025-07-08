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
    @Test
    fun `GET assignments returns 200 with list`() {
        val list = listOf(
            Assignment(UUID.randomUUID(), "T1", null, null, user),
            Assignment(UUID.randomUUID(), "T2", null, null, user)
        )
        whenever(assignmentService.getAllAssignments()).thenReturn(list)

        mvc.perform(get("/api/assignments"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()", `is`(2)))
    }


    @Test
    fun `GET assignment by id returns 200`() {
        val a = Assignment(UUID.randomUUID(), "Task", null, null, user)
        whenever(assignmentService.getAssignmentById(a.id!!)).thenReturn(a)

        mvc.perform(get("/api/assignments/${a.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title", `is`("Task")))
    }


    @Test
    fun `GET assignments by user returns 200`() {
        val list = listOf(Assignment(UUID.randomUUID(), "T", null, null, user))
        whenever(assignmentService.getAssignmentsByUserId(userId)).thenReturn(list)

        mvc.perform(get("/api/assignments/user/$userId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()", `is`(1)))
    }


    @Test
    fun `PUT assignments returns 200`() {
        val id = UUID.randomUUID()
        val req = AssignmentRequest("Upd", "upd", null, userId)
        val updated = Assignment(id, req.title, req.description, null, user)

        whenever(assignmentService.updateAssignment(id, req)).thenReturn(updated)

        mvc.perform(
            put("/api/assignments/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title", `is`("Upd")))
    }


    @Test
    fun `DELETE assignment returns 204`() {
        val id = UUID.randomUUID()
        whenever(assignmentService.deleteAssignment(id)).thenReturn(true)

        mvc.perform(delete("/api/assignments/$id"))
            .andExpect(status().isNoContent)
    }


    @Test
    fun `DELETE assignment returns 404 when not found`() {
        val id = UUID.randomUUID()
        whenever(assignmentService.deleteAssignment(id)).thenReturn(false)

        mvc.perform(delete("/api/assignments/$id"))
            .andExpect(status().isNotFound)
    }


    @Test
    fun `POST assignments returns 400 when title blank`() {
        val badReq = AssignmentRequest("", null, null, userId) // title en blanco

        mvc.perform(
            post("/api/assignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(badReq))
        )
            .andExpect(status().isBadRequest)
    }
}

