package com.store

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MainApp

fun main(args: Array<String>) {
    runApplication<MainApp>(*args)
}
