package com.kavaarch.kavaarch_mvvm_toolkit.inter

import androidx.annotation.Keep

@Keep
interface ResultCallback {
    fun onResponse(response: Any?)
    fun onError(error: Any?)
}

@Keep
interface UploadProgressCallback {
    fun onProgress(progress: Int)
}