package com.examples.coding.networkingdemoone.datasource.remote.httpurlconnection

import java.net.HttpURLConnection
import java.net.URL

class HttpUrlConnectionHelper {

    fun getResponse(url : String) : String {
        var returnString = ""
        val url = URL(url)
        val connention = url.openConnection() as HttpURLConnection

        val stream = connention.inputStream
        var read = stream.read()

        while(read > 0) {
            returnString = "$returnString${read.toChar()}"
            read = stream.read()
        }

        stream.close()
        return returnString
    }
}