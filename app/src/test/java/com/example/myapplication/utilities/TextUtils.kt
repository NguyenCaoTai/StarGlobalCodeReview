package com.example.myapplication.utilities

import com.example.myapplication.data.local.model.Location
import com.example.myapplication.data.local.model.Name
import com.example.myapplication.data.local.model.User
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object TextUtils {
    fun getTextFromFile(file: String): String =
        Files.lines(Paths.get(ClassLoader.getSystemClassLoader().getResource(file).toURI()))
            .parallel()
            .collect(Collectors.joining())
}