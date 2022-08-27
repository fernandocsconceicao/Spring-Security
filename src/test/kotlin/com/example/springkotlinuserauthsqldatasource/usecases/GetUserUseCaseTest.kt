package com.example.springkotlinuserauthsqldatasource.usecases

import com.example.springkotlinuserauthsqldatasource.entities.User
import com.example.springkotlinuserauthsqldatasource.usecases.ports.UserRepositoryPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
internal class GetUserUseCaseTest {

    @Mock
    lateinit var userRepositoryPort: UserRepositoryPort

    @InjectMocks
    lateinit var getUserUseCase: GetUserUseCase

    @Test
    fun `get all users should return list of users`() {
        val users = listOf(
            User(username = "suzan", "suzan@example.com", "1234", true),
            User(username = "john", "john+alias@example.com", "1234", false),
        )
        whenever(userRepositoryPort.findAll()).thenReturn(users)
        val result = getUserUseCase.all()
        verify(userRepositoryPort).findAll()
        assertThat(result).isEqualTo(result)
    }

    @Test
    fun `get all users should return empty list`() {
        getUserUseCase.all()
        verify(userRepositoryPort).findAll()
    }

    @Test
    fun `count all users should return integer`() {
        getUserUseCase.countAll()
        verify(userRepositoryPort).getNumberOfUsers()
    }

    @Test
    fun `by email should return User`() {
        getUserUseCase.byEmail("email@example.com")
        verify(userRepositoryPort).findUserByEmail(eq("email@example.com"))
    }
}