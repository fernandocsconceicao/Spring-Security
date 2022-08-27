package com.example.springkotlinuserauthsqldatasource.controllers

import com.example.springkotlinuserauthsqldatasource.controllers.dtos.NewUserRequestDto
import com.example.springkotlinuserauthsqldatasource.controllers.dtos.UserResponseDto
import com.example.springkotlinuserauthsqldatasource.usecases.GetUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("users")
class UserController(
    private val getUserUseCase: GetUserUseCase,
) {

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    fun findAllUsers() = getUserUseCase.all().map { user -> UserResponseDto.fromDomain(user) }

    @GetMapping("count")
    @ResponseStatus(HttpStatus.OK)
    fun getNumberOfUsers() = getUserUseCase.countAll()

    @GetMapping("email/{email}")
    @ResponseStatus(HttpStatus.OK)
    fun findUserByEmail(@PathVariable("email") email: String): UserResponseDto {
        val user = getUserUseCase.byEmail(email)
        return UserResponseDto.fromDomain(user)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@Valid @RequestBody newUserRequestDto: NewUserRequestDto): Nothing = TODO("Not implemented yet")
}
