package com.kavaarch.kavaarch_mvvm_toolkit.internal

interface ResultCallback {
    fun onResponse(response: Any?)
}

interface UploadProgressCallback {
    fun onProgress(progress: Int)
}