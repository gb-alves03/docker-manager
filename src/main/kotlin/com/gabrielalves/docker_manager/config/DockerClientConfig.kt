package com.gabrielalves.docker_manager.config

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.RemoteApiVersion
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import java.time.Duration

@Configuration
class DockerClientConfig {

    @Value("\${docker.socket.path}")
    private lateinit var dockerSocketPath: String


    @Bean
    @Lazy(false)
    fun buildDockerClient(): DockerClient {

        val dockerClientConfigBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder()

        if (::dockerSocketPath.isInitialized && dockerSocketPath.startsWith("unix://")) {
            dockerClientConfigBuilder
                .withDockerHost(dockerSocketPath)
                .withApiVersion(RemoteApiVersion.VERSION_1_24)
                .withDockerTlsVerify(false)
        }

        val dockerClientConfig = dockerClientConfigBuilder.build()

        val dockerHttpClient = ApacheDockerHttpClient.Builder()
            .dockerHost(dockerClientConfig.dockerHost)
            .maxConnections(5)
            .connectionTimeout(Duration.ofMillis(300))
            .responseTimeout(Duration.ofSeconds(3))
            .build()

        val client = DockerClientBuilder.getInstance(dockerClientConfig)
            .withDockerHttpClient(dockerHttpClient)
            .build()

        client.pingCmd().exec()

        return client
    }
}