package io.karon

import com.fasterxml.jackson.databind.ObjectMapper
import io.karon.io.FilePostSaver
import io.karon.service.PostService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*

object App {

    suspend fun fetchAndSave() {
        val objectMapper = ObjectMapper()
            .apply {
                findAndRegisterModules()
            }

        val client = HttpClient(CIO) {
            engine {
                threadsCount = 1
                endpoint {
                    connectTimeout = 5000
                }
                requestTimeout = 5000
            }
            install(JsonFeature) {
                serializer = JacksonSerializer(objectMapper)
            }
        }

        val service = PostService(client)

        println("Getting posts")
        val posts = service.getAllPosts()
        println("Posts fetched")

        println("Saving posts")
        val saver = FilePostSaver(objectMapper)
        saver.savePostsToFile(posts)
        println("Posts saved")

        client.close()
    }
}
