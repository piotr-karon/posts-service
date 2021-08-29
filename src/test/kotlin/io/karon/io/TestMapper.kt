package io.karon.io

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

val testMapper = ObjectMapper().apply {
    registerModule(KotlinModule())
}
