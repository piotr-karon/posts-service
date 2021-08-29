package io.karon.io

import io.karon.model.Post
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

internal class FilePostSaverTest {

    private val dir = "testPosts"

    @AfterEach
    fun `Clean up`() {
        File(dir).deleteRecursively()
    }

    @Test
    fun `Should create files`() {

        val post1 = Post(userId = 0, id = 0, title = "Test", body = "TestBody")
        val post2 = Post(userId = 1, id = 1, title = "Test1", body = "TestBody1")

        val saver = FilePostSaver(testMapper)

        saver.savePostsToFile(listOf(post1, post2), dir)

        val dirFile = File(dir)
        assertTrue(dirFile.isDirectory, "Directory should be directory")

        assertTrue(File("$dir/0.json").exists(), "Post 1 json file should exist")
        assertTrue(File("$dir/1.json").exists(), "Post 2 json file should exist")
    }
}
