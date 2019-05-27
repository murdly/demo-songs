package com.akarbowy.utils.resources

import java.io.InputStream


interface ResourceProvider {

    fun openFile(name: String): InputStream

}