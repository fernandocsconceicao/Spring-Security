package com.example.springkotlinuserauthsqldatasource.gateways.repositories.h2

import com.example.springkotlinuserauthsqldatasource.entities.exceptions.NotFoundException
import com.example.springkotlinuserauthsqldatasource.usecases.ports.UserRepositoryPort
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryPortImpl(val userRepository: UserRepository) : UserRepositoryPort {
    override fun getNumberOfUsers() = userRepository.findAll().count()
    override fun findAll() = userRepository.findAll().map { it.toDomain() }
    override fun findUserByEmail(email: String) =
        userRepository.findByEmail(email)?.toDomain() ?: throw NotFoundException("user not found")
}