package io.karon.service

import io.karon.model.Post
import io.ktor.client.*
import io.ktor.client.request.*

class PostService(
    private val client: HttpClient
) {

    suspend fun getAllPosts(): List<Post> {
        return client
            .get("https://jsonplaceholder.typicode.com/posts")
    }
}
