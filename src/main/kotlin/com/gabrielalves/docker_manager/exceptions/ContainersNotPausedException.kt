package com.gabrielalves.docker_manager.exceptions

class ContainersNotPausedException : RuntimeException {

    constructor(message: String) : super(message)
}