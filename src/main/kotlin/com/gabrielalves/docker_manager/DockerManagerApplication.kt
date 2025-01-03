package com.gabrielalves.docker_manager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DockerManagerApplication

fun main(args: Array<String>) {
	runApplication<DockerManagerApplication>(*args)
}
