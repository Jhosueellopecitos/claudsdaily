package com.puce.claudsdaily.controller


import com.puce.claudsdaily.models.entities.Users
import com.puce.claudsdaily.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<Users> = userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<Users> {
        val user = userService.getUserById(id)
        return if (user != null) ResponseEntity.ok(user)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createUser(@RequestBody user: Users): Users = userService.createUser(user)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody user: Users): ResponseEntity<Users> {
        val existing = userService.getUserById(id) ?: return ResponseEntity.notFound().build()
        val updated = userService.createUser(user.copy(id = id))
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        val existing = userService.getUserById(id) ?: return ResponseEntity.notFound().build()
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}
