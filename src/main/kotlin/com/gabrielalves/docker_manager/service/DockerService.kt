package com.gabrielalves.docker_manager.service

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.Container
import com.github.dockerjava.api.model.Image
import org.springframework.stereotype.Service

@Service
class DockerService(private val dockerClient: DockerClient) {

    fun listContainers(all: Boolean): List<Container> {
        return dockerClient.listContainersCmd().withShowAll(all).exec()
    }

    fun listImages(): List<Image> {
        return dockerClient.listImagesCmd().exec()
    }

    fun filterImages(filterName: String): List<Image> {
        return dockerClient.listImagesCmd().withImageNameFilter(filterName).exec()
    }

    fun startContainer(containerId: String) {
        dockerClient.startContainerCmd(containerId).exec()
    }

    fun stopContainer(containerId: String) {
        dockerClient.stopContainerCmd(containerId).exec()
    }

    fun deleteContainer(containerId: String) {
        dockerClient.removeContainerCmd(containerId).exec()
    }

    fun createContainer(imageName: String) {
        dockerClient.createContainerCmd(imageName).exec()
    }
}