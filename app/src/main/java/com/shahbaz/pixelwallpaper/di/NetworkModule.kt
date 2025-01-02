package com.shahbaz.pixelwallpaper.di

import com.shahbaz.pixelwallpaper.BuildConfig
import com.shahbaz.pixelwallpaper.data.remote.PixelWallpaperApi
import com.shahbaz.pixelwallpaper.data.remote.PixelWallpaperApiImplementation
import com.shahbaz.pixelwallpaper.data.repositiory.PixelWallpaperRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import io.ktor.http.HttpHeaders
import io.ktor.client.request.header


const val Time_out = 3_000

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            engine {
                connectTimeout = Time_out
                socketTimeout = Time_out
            }

            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            println(message)
                        }
                    }
                }
            }


            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, BuildConfig.API_KEY)
            }

        }
    }

    @Provides
    @Singleton
    fun providePixelWallpaperApi(client: HttpClient): PixelWallpaperApi {
        return PixelWallpaperApiImplementation(client)
    }

    @Provides
    @Singleton
    fun providePixelWallpaperRepo(api: PixelWallpaperApi): PixelWallpaperRepo {
        return PixelWallpaperRepo(api)
    }
}