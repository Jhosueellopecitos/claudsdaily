package com.puce.claudsdaily.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.puce.claudsdaily.dto.request.UserRequest
import com.puce.claudsdaily.models.entities.Users
import com.puce.claudsdaily.services.UserService
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired lateinit var mvc: MockMvc
    @Autowired lateinit var om: ObjectMapper

    @MockBean lateinit var userService: UserService

    @Test
    fun `POST users returns 201`() {
        val req   = UserRequest("tester", "t@test.com")
        val saved = Users(UUID.randomUUID(), req.username, req.email)

        whenever(userService.createUser(req)).thenReturn(saved)

        mvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.username", `is`("tester")))
    }

    @Test
    fun `GET users by id returns 404 when not found`() {
        val id = UUID.randomUUID()
        whenever(userService.getUserById(id)).thenReturn(null)

        mvc.perform(get("/api/users/$id"))
            .andExpect(status().isNotFound)
    }

    // --------------- LIST (GET /api/users) ---------------
    @Test
    fun `GET users returns 200 with list`() {
        val list = listOf(
            Users(UUID.randomUUID(), "a", "a@x.com"),
            Users(UUID.randomUUID(), "b", "b@x.com")
        )
        whenever(userService.getAllUsers()).thenReturn(list)

        mvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()", `is`(2)))
    }

    // --------------- UPDATE (PUT /api/users) ---------------
    @Test
    fun `PUT users returns 200`() {
        val id = UUID.randomUUID()
        val req = UserRequest("new", "new@x.com")
        val updated = Users(id, req.username, req.email)

        whenever(userService.updateUser(id, req)).thenReturn(updated)

        mvc.perform(
            put("/api/users/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(req))
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.username", `is`("new")))
    }

    // --------------- DELETE happy path 204 ---------------
    @Test
    fun `DELETE users returns 204`() {
        val id = UUID.randomUUID()
        whenever(userService.deleteUser(id)).thenReturn(true)

        mvc.perform(delete("/api/users/$id"))
            .andExpect(status().isNoContent)
    }

    // --------------- DELETE 404 ---------------
    @Test
    fun `DELETE users returns 404 when not found`() {
        val id = UUID.randomUUID()
        whenever(userService.deleteUser(id)).thenReturn(false)

        mvc.perform(delete("/api/users/$id"))
            .andExpect(status().isNotFound)
    }

}

