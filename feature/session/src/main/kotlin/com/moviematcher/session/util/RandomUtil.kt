package com.moviematcher.session.util

import kotlin.random.Random

object RandomUtil {
    fun generateUniqueId(length: Int = 11): String {
        val charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { Random.nextInt(0, charPool.length) }
            .map(charPool::get)
            .joinToString("")
    }
}
