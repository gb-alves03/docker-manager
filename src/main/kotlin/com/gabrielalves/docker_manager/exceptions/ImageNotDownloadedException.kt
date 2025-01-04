package com.gabrielalves.docker_manager.exceptions

class ImageNotDownloadedException : RuntimeException {

    constructor(message: String) : super(message)
}