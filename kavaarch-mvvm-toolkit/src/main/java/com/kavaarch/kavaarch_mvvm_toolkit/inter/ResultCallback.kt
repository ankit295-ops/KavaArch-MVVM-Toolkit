package com.kavaarch.kavaarch_mvvm_toolkit.inter

interface ResultCallback {
    fun onResponse(response: Any?)
    fun onError(error: Any?)
}

interface UploadProgressCallback {
    fun onProgress(progress: Int)
}