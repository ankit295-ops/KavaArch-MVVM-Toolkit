package com.kavaarch.kavaarch_mvvm_toolkit.application

import android.app.Application
import com.kavaarch.kavaarch_mvvm_toolkit.api.MVVMToolkit
import com.nova.mvvmtoolkittest.model.PhotosModel

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        MVVMToolkit.init(
            "https://jsonplaceholder.typicode.com/",
            mapOf("photos" to PhotosModel::class.java)
        )
    }
}