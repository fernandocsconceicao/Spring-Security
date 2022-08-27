package com.example.springkotlinuserauthsqldatasource.gateways.repositories.h2.models

import com.example.springkotlinuserauthsqldatasource.entities.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class UserModel(
    @Id
    @Column(name = "username", nullable = false)
    val username: String? = null,

    @Column(name = "email", nullable = false)
    val email: String? = null,

    @Column(name = "password", nullable = false)
    private val password: String? = null,

    @Column(name = "enabled", nullable = false)
    val enabled: Boolean? = null,
) {
    fun toDomain(): User {
        return User(
            username = username!!,
            email = email!!,
            password = password!!,
            enabled = enabled!!,
        )
    }
}