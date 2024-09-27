package com.moviematcher.data.di

import com.moviematcher.data.BuildConfig
import com.moviematcher.data.network.interceptor.ApiKeyInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

const val BASE_URL = "https://api.themoviedb.org/3/"

val jsonModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
}

val okhttpModule = module {
    single<HttpClientEngine> {
        OkHttp.create {
            config {
                interceptors().addAll(
                    listOf(
                        ApiKeyInterceptor(BuildConfig.TMDB_KEY_API),
                    )
                )
            }
        }
    }

    single<HttpClient> {
        HttpClient(get()) {
            install(ContentNegotiation) {
                json(get())
            }
            defaultRequest {
                url(BASE_URL)
            }
        }

    }
}

val networkModule = module {
    includes(jsonModule)
    includes(okhttpModule)
}