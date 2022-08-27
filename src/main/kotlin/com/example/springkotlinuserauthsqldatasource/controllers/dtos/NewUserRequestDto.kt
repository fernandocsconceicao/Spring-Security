package com.example.springkotlinuserauthsqldatasource.controllers.dtos

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class NewUserRequestDto(
    @field:NotBlank
    @field:Size(min = 3, max = 20)
    val username: String? = null,

    @field:NotBlank
    @field:Email
    val email: String? = null,

    @field:NotBlank
    @field:Size(min = 6, max = 20)
    val password: String? = null,

    @field:NotBlank
    val enabled: Boolean? = true,
)
