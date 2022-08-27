package com.example.springkotlinuserauthsqldatasource.controllers

import com.example.springkotlinuserauthsqldatasource.entities.User
import com.example.springkotlinuserauthsqldatasource.entities.exceptions.NotFoundException
import com.example.springkotlinuserauthsqldatasource.controllers.dtos.NewUserRequestDto
import com.example.springkotlinuserauthsqldatasource.controllers.dtos.UserResponseDto
import com.example.springkotlinuserauthsqldatasource.controllers.exceptions.ExceptionHandler
import com.example.springkotlinuserauthsqldatasource.usecases.GetUserUseCase
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(MockitoExtension::class)
@WebMvcTest
@ContextConfiguration(classes = [
    UserController::class,
    ExceptionHandler::class,
])
@WithMockUser
internal class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var getUserUseCase: GetUserUseCase

    @Test
    fun `get all users should return 200 with content as list of users`() {
        val users = listOf(
            User("suzan", "suzan@example.com", "1234", true),
            User("john", "john+alias@example.com", "1234", false),
        )
        whenever(getUserUseCase.all()).thenReturn(users)
        val response = mockMvc.perform(get("/users/all"))
            .andExpect { status().isOk }
            .andReturn().response
        verify(getUserUseCase).all()
        assertThat(response.contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(users.map {
            UserResponseDto.fromDomain(it)
        }))
    }

    @Test
    fun `get number of users should return 200 with content as integer`() {
        whenever(getUserUseCase.countAll()).thenReturn(0)
        mockMvc.perform(get("/users/count"))
            .andExpect {
                status().isOk
                content().string("0")
            }
        verify(getUserUseCase).countAll()
    }

    @Test
    fun `get user by email should return 200 with content as user`() {
        val user = User("suzan", "suzan@example.com", "1234", true)
        whenever(getUserUseCase.byEmail(any<String>())).thenReturn(user)
        val response = mockMvc.perform(get("/users/email/{email}", "suzan@example.com"))
            .andExpect { status().isOk }
            .andReturn().response
        verify(getUserUseCase).byEmail(eq("suzan@example.com"))
        assertThat(response.contentAsString).isEqualToIgnoringWhitespace(
            objectMapper.writeValueAsString(
                UserResponseDto.fromDomain(user)
            )
        )
    }

    @Test
    fun `get user by email should return 404 not found`() {
        whenever(getUserUseCase.byEmail(any<String>())).thenThrow(NotFoundException::class.java)
        mockMvc.perform(get("/users/email/{email}", "suzan@example.com"))
            .andExpect { status().isNotFound }
            .andReturn()
        verify(getUserUseCase).byEmail(eq("suzan@example.com"))
    }

    @Test
    fun `create user should return 5xx not implemented`() {
        val user = NewUserRequestDto("suzan", "suzan@example.com", "1234", true)
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
            )
            .andExpect { status().is5xxServerError }
    }

    @Test
    fun `create user should return 400 for invalid user`() {
        val user = NewUserRequestDto("suzan", "suzan@example.com", "1234", true)
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(username = null)
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(username = "\n   \n ")
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(username = "ab")
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(username = "a".repeat(21))
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(email = null)
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(email = "\n   \n ")
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(email = "not.email.format#email.com")
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(password = null)
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(password = "\n    \n ")
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(password = "1".repeat(5))
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(password = "1".repeat(21))
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(enabled = null)
            ))).andExpect { status().isBadRequest }
        mockMvc.perform(
            post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(
                user.copy(enabled = "\n   \n ".toBoolean())
            ))).andExpect { status().isBadRequest }
    }
}