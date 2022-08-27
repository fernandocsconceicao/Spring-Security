package com.example.springkotlinuserauthsqldatasource

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinUserAuthPostgresqlApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinUserAuthPostgresqlApplication>(*args)
}
