package com.gabrielalves.docker_manager.controllers

import com.gabrielalves.docker_manager.service.DockerService
import com.github.dockerjava.api.model.Container
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/containers")
class DockerContainersController(private val dockerService: DockerService) {

    @GetMapping("")
    fun listContainers(@RequestParam(required = false, defaultValue = "true") showAll: Boolean): List<Container> {
        return dockerService.listContainers(showAll)
    }

    @PostMapping("/{id}/start")
    fun startContainer(@PathVariable id: String) {
        dockerService.startContainer(id)
    }

    @PostMapping("{id}/stop")
    fun stopContainer(@PathVariable id: String) {
        dockerService.stopContainer(id)
    }

    @DeleteMapping("/{id}")
    fun deleteContainer(@PathVariable id: String) {
        dockerService.deleteContainer(id)
    }

    @PostMapping("")
    fun createContainer(@RequestParam imageName: String) {
        dockerService.createContainer(imageName)
    }
}