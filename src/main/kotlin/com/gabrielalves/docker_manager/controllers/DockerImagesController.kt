package com.gabrielalves.docker_manager.controllers

import com.gabrielalves.docker_manager.service.DockerService
import com.github.dockerjava.api.model.Image
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/images")
class DockerImagesController(private val dockerService: DockerService) {

    @GetMapping("")
    fun listImages(): List<Image> {
        return dockerService.listImages()
    }

    @GetMapping("/filter")
    fun listImages(@RequestParam(required = false, defaultValue = "image-") filterName: String): List<Image> {
        return dockerService.filterImages(filterName)
    }
}