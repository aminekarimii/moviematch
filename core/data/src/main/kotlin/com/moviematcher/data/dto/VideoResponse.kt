package com.moviematcher.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class VideoResponse(
    val results: List<Video>
)

@Serializable
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)
