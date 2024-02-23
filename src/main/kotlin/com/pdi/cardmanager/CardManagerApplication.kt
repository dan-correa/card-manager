package com.pdi.cardmanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class CardManagerApplication

fun main(args: Array<String>) {
	runApplication<CardManagerApplication>(*args)
}
