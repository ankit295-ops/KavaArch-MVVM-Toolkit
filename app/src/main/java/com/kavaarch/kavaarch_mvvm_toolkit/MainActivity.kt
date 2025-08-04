package com.kavaarch.kavaarch_mvvm_toolkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kavaarch.kavaarch_mvvm_toolkit.api.MVVMToolkit
import com.kavaarch.kavaarch_mvvm_toolkit.internal.ResultCallback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MVVMToolkit.observeApi(
            this,
            method = "GET",
            endpoint = "users",
            modelName = "UserModel",
            requestBody = null,
            callback = object : ResultCallback {
                override fun onResponse(result: Any?) {
                    println("SuccessOrError: $result")
                }
            }
        )
    }
}