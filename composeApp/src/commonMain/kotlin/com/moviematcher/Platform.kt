package com.moviematcher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform