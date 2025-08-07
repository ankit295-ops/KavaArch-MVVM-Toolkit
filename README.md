# KavaArch-MVVM-Toolkit

![Release](https://img.shields.io/github/v/release/ankit295-ops/KavaArch-MVVM-Toolkit)
![Stars](https://img.shields.io/github/stars/ankit295-ops/KavaArch-MVVM-Toolkit)
![Issues](https://img.shields.io/github/issues/ankit295-ops/KavaArch-MVVM-Toolkit)
![License](https://img.shields.io/github/license/ankit295-ops/KavaArch-MVVM-Toolkit)
![GitHub topics](https://img.shields.io/github/topics/ankit295-ops/KavaArch-MVVM-Toolkit)

A lightweight, open-source Android MVVM architecture library written in Kotlin. It helps you integrate APIs using **ViewModel, LiveData**, and **Coroutines** — all **without Retrofit** and with **minimal boilerplate**.

---

## ❓ What is KavaArch-MVVM-Toolkit?

KavaArch-MVVM-Toolkit is a plug-and-play Android MVVM toolkit built for developers who want:

- Simpler MVVM implementation
- Retrofit-free architecture
- Easy API integration using Coroutines
- Reusability across projects

Whether you're a beginner or experienced developer, this library helps you build scalable and maintainable Android apps faster.

---

## ✨ Features

- 🚫 No Retrofit required
- ⚡ Fast and simple API integration
- 🧠 ViewModel + LiveData + Coroutines-based
- 📤 Multipart file uploads with progress tracking
- 🧩 Minimal setup – just initialize and go!
- 🔁 Reusable and scalable architecture
- 💡 Works with both Kotlin & Java

---

## 📦 Installation

### 1. Add JitPack to your project

#### Project-level or settings.gradle

Kotlin DSL

<pre><code>
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
</code></pre>
<br>
Groovy

<pre><code>
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
</code></pre>

---

### 2. Add the library to your app module

#### App-level build.gradle
Kotlin DSL<br>
<pre><code>
dependencies {
    implementation("com.github.ankit295-ops:KavaArch-MVVM-Toolkit:1.0.7")
}
</code></pre><br>
Groovy<br>
<pre><code>
dependencies {
    implementation 'com.github.ankit295-ops:KavaArch-MVVM-Toolkit:0.0.1'
}
</code></pre>

---

## 🚀 Quick Start

### 1. Initialize the Toolkit

Kotlin<br>
<pre>
  <code>
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
  </code>
</pre><br>
Java<br>
<pre><code>
package com.example;

import android.app.Application;
import com.kavaarch.kavaarch_mvvm_toolkit.api.MVVMToolkit;
import com.example.model.PhotosModel;

import java.util.HashMap;
import java.util.Map;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Map<String, Class<?>> modelMap = new HashMap<>();
        modelMap.put("photos", PhotosModel.class);

        MVVMToolkit.init("https://jsonplaceholder.typicode.com/", modelMap);
    }
}

</code></pre><br>
### 2. Make an API Call

Kotlin
GET<br>
<pre><code>
package com.kavaarch.kavaarch_mvvm_toolkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kavaarch.kavaarch_mvvm_toolkit.api.MVVMToolkit
import com.kavaarch.kavaarch_mvvm_toolkit.internal.ResultCallback
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
</code></pre><br>
Kotlin POST<br>
<pre><code>
  MVVMToolkit.observeApi<PhotosModel>(
            this,
            "POST",
            "users",
            "Usermodel",
            mapOf("userName" to "test", "password" to "test@123"),
            object : ResultCallback {
                override fun onResponse(response: Any?) {
                    println("Success: $response")
                }

                override fun onError(error: Any?) {
                    TODO("Not yet implemented")
                }
            }
        )
</code></pre><br>
Kotlin Multipart<br>
<pre><code>
  val files = listOf(
            "profileImage" to File("/path/to/image.jpg"),
            "resume" to File("/path/to/resume.pdf")
        )

        val formFields = mapOf(
            "userName" to "testUser",
            "email" to "test@example.com"
        )

        MVVMToolkit.observeMultipartApi<UploadResponseModel>(
            owner = this,
            endpoint = "upload/user/files",
            modelName = "UploadResponseModel",
            files = files,
            formFields = formFields,
            progressCallback = object : UploadProgressCallback {
                override fun onProgress(progress: Int) {
                    println("Upload progress: $progress%")
                }
            },
            responseCallback = object : ResultCallback {
                override fun onResponse(response: Any?) {
                    println("Success: $response")
                }

                override fun onError(error: Any?) {
                    println("Error: $error")
                }
            }
        )
</code></pre>
JAVA GET<br>
<pre><code>
  package com.example;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.kavaarch.kavaarch_mvvm_toolkit.api.MVVMToolkit;
import com.kavaarch.kavaarch_mvvm_toolkit.internal.ResultCallback;
import com.example.model.PhotosModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MVVMToolkit.observeApi(
            this,
            "GET",
            "users",
            "UserModel",
            null,
            new ResultCallback() {
                @Override
                public void onResponse(Object response) {
                    System.out.println("Success: " + response);
                }

                @Override
                public void onError(Object error) {
                    System.out.println("Error: " + error);
                }
            }
        );
    }
}
</code></pre><br>
JAVA POST<br>
<pre><code>
  MVVMToolkit.observeApi(
    PhotosModel.class,
    this,
    "POST",
    "users",
    "Usermodel",
    new HashMap<String, String>() {{
        put("userName", "test");
        put("password", "test@123");
    }},
    new ResultCallback() {
        @Override
        public void onResponse(Object response) {
            System.out.println("Success: " + response);
        }

        @Override
        public void onError(Object error) {
            // Handle error
            System.out.println("Error: " + error);
        }
    }
);
</code></pre><br>
JAVA Multipart<br>
<pre><code>
  List<Pair<String, File>> files = new ArrayList<>();
files.add(new Pair<>("profileImage", new File("/path/to/image.jpg")));
files.add(new Pair<>("resume", new File("/path/to/resume.pdf")));

Map<String, String> formFields = new HashMap<>();
formFields.put("userName", "testUser");
formFields.put("email", "test@example.com");

MVVMToolkit.observeMultipartApi(
    this, // ViewModelStoreOwner
    "upload/user/files",
    "UploadResponseModel",
    files,
    formFields,
    new UploadProgressCallback() {
        @Override
        public void onProgress(int progress) {
            System.out.println("Upload progress: " + progress + "%");
        }
    },
    new ResultCallback() {
        @Override
        public void onResponse(Object response) {
            System.out.println("Success: " + response);
        }

        @Override
        public void onError(Object error) {
            System.out.println("Error: " + error);
        }
    }
);
</code></pre><br>
---
## ☕ Support

If you find this project helpful or use it in your app, consider supporting my work:

<a href="https://www.buymeacoffee.com/ankitmaurya295"> <img src="https://img.buymeacoffee.com/button-api/?text=Buy%20me%20a%20coffee&slug=ankitmaurya295&button_colour=FFDD00&font_colour=000000&font_family=Cookie&outline_colour=000000&coffee_colour=ffffff" alt="Buy Me A Coffee"> </a>
---
## 🛡️ ProGuard / R8 Rules
<pre><code>
-keep class com.kavaarch.kavaarch_mvvm_toolkit.** { *; }
-keep class * extends androidx.lifecycle.ViewModel
</code></pre><br>
---
## 📁 License
MIT License © 2025 Ankit Maurya
---
## 📣 Spread the Word
If this library helped you, give it a ⭐ on GitHub, share it with the Android dev community, or include it in your blogs or videos.
