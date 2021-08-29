package io.karon.service

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.json.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

internal class PostServiceTest {

    @Test
    fun `Should fetch and deserialize`() {
        val client = HttpClient(MockEngine) {
            install(JsonFeature)
            engine {
                addHandler { req ->
                    when (req.url.fullUrl) {
                        "https://jsonplaceholder.typicode.com/posts" -> {
                            val responseHeaders =
                                headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                            respond(twoPosts, HttpStatusCode.OK, responseHeaders)
                        }
                        else -> error("Bad url")
                    }
                }
            }
        }

        val service = PostService(client)

        val posts = runBlocking {
            service.getAllPosts()
        }

        val postWithId1 = posts.firstOrNull { it.id == 1L }
        val postWithId2 = posts.firstOrNull { it.id == 2L }
        assertNotNull(postWithId1, "There should exist post with id==1")
        assertNotNull(postWithId2, "There should exist post with id==2")

    }
}

private val twoPosts = """
[
  {
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
  },
  {
    "userId": 1,
    "id": 2,
    "title": "qui est esse",
    "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
  }
]
""".trimIndent()

private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"
