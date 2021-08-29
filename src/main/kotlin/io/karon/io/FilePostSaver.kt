package io.karon.io

import com.fasterxml.jackson.databind.ObjectMapper
import io.karon.model.Post
import java.io.File

class FilePostSaver(
    private val mapper: ObjectMapper
) {

    fun savePostsToFile(posts: List<Post>, directoryPath: String = "posts") {
        val dir = File(directoryPath)
        dir.mkdirs()

        posts.forEach {
            val file = File("$directoryPath/${it.id}.json")
            mapper.writeValue(file, it)
        }
    }
}
