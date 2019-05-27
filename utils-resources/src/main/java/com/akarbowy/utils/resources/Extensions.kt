package com.akarbowy.utils.resources

import java.io.BufferedReader
import java.io.InputStream


fun <T : Any> Class<T>.loadJsonAsText(path: String) =
    getResourceAsStream(path).readText()

fun InputStream.readText() = bufferedReader().use(BufferedReader::readText)