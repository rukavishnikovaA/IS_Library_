package ru.development.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.encodeToString
import ru.development.Serialization
import ru.development.models.*
import java.io.File
import java.io.FileWriter
import kotlin.random.Random

object FileUtil {
    fun getSelfLocation(): String {
        return File(FileUtil::class.java.getProtectionDomain().codeSource.location.toURI()).path
    }

    fun getResourcesFile(resource: String): File? {
        val url = FileUtil::class.java.getResource("/resources/$resource") ?: FileUtil::class.java.getClassLoader()
            .getResource(resource) ?: return null

        return File(url.file)
    }

    fun getOrCreateGlobalFile(parent: File, path: String): File {
        return File(parent, "./$path").also { if (!it.exists()) it.createNewFile() }
    }
}
