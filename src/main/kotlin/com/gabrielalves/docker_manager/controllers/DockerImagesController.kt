package com.gabrielalves.docker_manager.controllers

import com.gabrielalves.docker_manager.service.DockerService
import com.github.dockerjava.api.command.PullImageCmd
import com.github.dockerjava.api.command.PushImageCmd
import com.github.dockerjava.api.model.Image
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/images")
class DockerImagesController(val dockerService: DockerService) {

    @GetMapping("")
    fun listImages(): List<Image> {
        return dockerService.listImages()
    }

    @GetMapping("/{imageName}/filter")
    fun filterImages(@PathVariable imageName: String): List<Image> {
        return dockerService.filterImages(imageName)
    }

    @PostMapping("/pull")
    fun pullImage(
        @RequestParam("name") imageName: String,
        @RequestParam("tag", defaultValue = "latest") tag: String
    ): ResponseEntity<String> {
        return try {
            val message = dockerService.pullImage(imageName, tag)
            ResponseEntity.ok(message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
        }
    }
}