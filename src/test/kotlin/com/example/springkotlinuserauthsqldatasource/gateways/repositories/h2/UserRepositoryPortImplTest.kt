package com.example.springkotlinuserauthsqldatasource.gateways.repositories.h2

import com.example.springkotlinuserauthsqldatasource.entities.User
import com.example.springkotlinuserauthsqldatasource.entities.exceptions.NotFoundException
import com.example.springkotlinuserauthsqldatasource.gateways.repositories.h2.models.UserModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@ExtendWith(MockitoExtension::class)
internal class UserRepositoryPortImplTest {

    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var userRepositoryPortImpl: UserRepositoryPortImpl

    @Test
    fun `get number of users should return 2`() {
        val mockListOfUserModel = listOf(
            mock<UserModel>(),
            mock<UserModel>(),
        )
        whenever(userRepository.findAll()).thenReturn(mockListOfUserModel)
        val result = userRepositoryPortImpl.getNumberOfUsers()
        verify(userRepository).findAll()
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `find all users should return 2 results`() {
        val mockListOfUserModel = listOf(
            mock<UserModel>(),
            mock<UserModel>(),
        )
        whenever(userRepository.findAll()).thenReturn(mockListOfUserModel)
        val result = userRepositoryPortImpl.findAll()
        verify(userRepository).findAll()
        mockListOfUserModel.forEach { verify(it).toDomain() }
        assertThat(result).hasSize(2)
    }

    @Test
    fun `find by email should return user`() {
        val mockUserModel = mock<UserModel>()
        val mockUser = mock<User>()
        whenever(userRepository.findByEmail(any<String>())).thenReturn(mockUserModel)
        whenever(mockUserModel.toDomain()).thenReturn(mockUser)
        val result = userRepositoryPortImpl.findUserByEmail("email@example.com")
        verify(userRepository).findByEmail("email@example.com")
        verify(mockUserModel).toDomain()
        assertThat(result).isEqualTo(mockUser)
    }

    @Test
    fun `find by email should throw not found exception`() {
        assertThrows<NotFoundException> {
            userRepositoryPortImpl.findUserByEmail("email@example.com")
        }
        verify(userRepository).findByEmail(any<String>())
    }
}