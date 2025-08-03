package com.nova.mvvmtoolkit.internal

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File

/**
 * A custom [RequestBody] implementation that wraps a [File] and provides upload progress callbacks.
 *
 * This is especially useful when uploading large files via multipart requests and showing progress to the user.
 *
 * @param file The file to upload.
 * @param onProgress Optional lambda that will be invoked with the current upload progress (0 to 100).
 */
internal class ProgressRequestBody(
    private val file: File,
    private val contentType: String,
    private val onProgress: ((bytesWritten: Long) -> Unit)?
): RequestBody() {
    override fun contentType(): MediaType? = contentType.toMediaTypeOrNull()
    override fun contentLength(): Long = file.length()

    override fun writeTo(sink: BufferedSink) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        file.inputStream().use { input ->
            var uploaded = 0L
            var read: Int
            while (input.read(buffer).also { read = it } != -1) {
                sink.write(buffer, 0, read)
                uploaded += read
                onProgress?.invoke(read.toLong())
            }
        }
    }
}