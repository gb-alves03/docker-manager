package com.gabrielalves.docker_manager.config

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.DockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.core.RemoteApiVersion
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import java.time.Duration

@Configuration
class DockerClientConfig {

    @Value("\${docker.socket.path}")
    private lateinit var dockerSocketPath: String

    private val logger = LoggerFactory.getLogger(DockerClientConfig::class.java)


    @Bean
    @Lazy(false)
    fun buildDockerClient(): DockerClient {

        val dockerClientConfigBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost("tcp://localhost:2375")
            .withDockerTlsVerify(false)
            .build()

        logger.info("Using Docker Socket Path: $dockerSocketPath")
        logger.info("Host configured: ${dockerClientConfigBuilder.dockerHost}")

        val dockerHttpClient = ApacheDockerHttpClient.Builder()
            .dockerHost(dockerClientConfigBuilder.dockerHost)
            .maxConnections(5)
            .connectionTimeout(Duration.ofSeconds(10))
            .responseTimeout(Duration.ofSeconds(30))
            .build()

        return DockerClientImpl.getInstance(dockerClientConfigBuilder, dockerHttpClient)
    }
}