package com.krayo.art.data.repositories

import com.krayo.art.data.cloud.data_sources.home.HomeCloudDataSource
import com.krayo.art.data.local.data_sources.home.HomeLocalDataSource
import java.util.concurrent.Flow

class HomeRepository(
    private val homeLocalDataSource: HomeLocalDataSource, // Dependency Injection
    private val homeCloudDataSource: HomeCloudDataSource
) {
    val content: Flow<Example> = homeLocalDataSource.example

    suspend fun refreshContent() {
        homeCloudDataSource.example
    }

    suspend fun addContent(example: Example) {
        homeLocalDataSource.addContent(example)
    }

    suspend fun removeContent(example: Example) {
        homeLocalDataSource.removeContent(example)
    }

    suspend fun getContent(example: Example) {
        homeLocalDataSource.getContent(example)
    }
    suspend fun modifyContent(example: Example) {

    }
}