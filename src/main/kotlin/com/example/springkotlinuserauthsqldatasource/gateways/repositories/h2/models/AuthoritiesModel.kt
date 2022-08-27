package com.example.springkotlinuserauthsqldatasource.gateways.repositories.h2.models

import javax.persistence.*

@Embeddable
@Table(name = "authorities")
class AuthoritiesModel(
    @Column(name = "username", nullable = false)
    val username: String,

    @Column(name = "authority", nullable = false)
    val authority: String,

    @ManyToOne
    @JoinColumn(name = "username")
    val user: UserModel,
)