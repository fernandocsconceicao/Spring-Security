package com.example.springkotlinuserauthsqldatasource.entities.exceptions

class NotFoundException(
    override val message: String?
) : RuntimeException(message)