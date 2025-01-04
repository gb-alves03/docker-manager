package com.gabrielalves.docker_manager.service

import com.gabrielalves.docker_manager.exceptions.ContainerNotStartedException
import com.gabrielalves.docker_manager.exceptions.ContainersNotPausedException
import com.gabrielalves.docker_manager.exceptions.ImageNotDownloadedException
import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.*
import org.springframework.stereotype.Service

@Service
class DockerService(private val dockerClient: DockerClient) {

    fun listContainers(all: Boolean): List<Container> {
        return dockerClient.listContainersCmd().withShowAll(all).exec()
    }

    fun listImages(): List<Image> {
        return dockerClient.listImagesCmd().exec()
    }

    fun pullImage(imageName: String, tag: String = "latest"): String {
        val image = "$imageName:$tag"

        return try {
            dockerClient.pullImageCmd(image)
                .start()
                .awaitCompletion()
            "Image '$image' downloaded with success."
        } catch (e: Exception) {
            throw ImageNotDownloadedException("Error to download the image '$image': ${e.message}")
        }
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

    fun pauseAllContainers(): String {
        return try {
            val containers = dockerClient.listContainersCmd()
                .withStatusFilter("running")
                .exec()

            containers.forEach { container ->
                dockerClient.pauseContainerCmd(container.id).exec()
            }
            "All the containers were paused with success"
        } catch (e: Exception) {
            throw ContainersNotPausedException("Error to pause the containers: ${e.message}")
        }
    }

    fun deleteContainer(containerId: String) {
        dockerClient.removeContainerCmd(containerId).exec()
    }

    fun runContainer(imageName: String, portMapping: String): String {
        return try {
            val container = dockerClient.createContainerCmd(imageName)
                .withExposedPorts(ExposedPort.tcp(8080))
                .withPortBindings(PortBinding.parse(portMapping))
                .exec()

            dockerClient.startContainerCmd(container.id).exec()
            "Container with the image '$imageName' started with success"
        } catch (e: RuntimeException) {
            throw ContainerNotStartedException("Error to start the ocntainer '$imageName': ${e.message}")
        }
    }

    fun getDockerInfo(): Info {
        return dockerClient.infoCmd().exec()
    }
}