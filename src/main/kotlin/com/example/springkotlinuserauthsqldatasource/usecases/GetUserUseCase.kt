package com.example.springkotlinuserauthsqldatasource.usecases

import com.example.springkotlinuserauthsqldatasource.usecases.ports.UserRepositoryPort
import org.springframework.stereotype.Service

@Service
class GetUserUseCase(val userRepositoryPort: UserRepositoryPort) {
    fun all() = userRepositoryPort.findAll()
    fun countAll() = userRepositoryPort.getNumberOfUsers()
    fun byEmail(email: String) = userRepositoryPort.findUserByEmail(email)
}