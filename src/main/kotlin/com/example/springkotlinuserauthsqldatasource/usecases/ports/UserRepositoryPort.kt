package com.example.springkotlinuserauthsqldatasource.usecases.ports

import com.example.springkotlinuserauthsqldatasource.entities.User

interface UserRepositoryPort {
    fun getNumberOfUsers(): Int
    fun findAll(): List<User>
    fun findUserByEmail(email: String): User
}