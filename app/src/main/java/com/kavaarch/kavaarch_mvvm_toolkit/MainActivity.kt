package com.kavaarch.kavaarch_mvvm_toolkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kavaarch.kavaarch_mvvm_toolkit.api.MVVMToolkit
import com.kavaarch.kavaarch_mvvm_toolkit.inter.ResultCallback
import com.nova.mvvmtoolkittest.model.PhotosModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MVVMToolkit.observeApi<PhotosModel>(
            this,
            method = "GET",
            endpoint = "users",
            modelName = "UserModel",
            requestBody = null,
            callback = object : ResultCallback {
                override fun onResponse(response: Any?) {
                    println("Success: $response")
                }

                override fun onError(error: Any?) {
                    println("Error: $error")
                }
            }
        )
    }
}