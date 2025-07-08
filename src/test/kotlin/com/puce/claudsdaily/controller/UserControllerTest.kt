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
}

