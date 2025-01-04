package com.gabrielalves.docker_manager.exceptions

class ContainerNotStartedException : RuntimeException {

    constructor(message: String) : super(message)
}