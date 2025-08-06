package com.nova.mvvmtoolkit.core

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kavaarch.kavaarch_mvvm_toolkit.core.ModelRegistry
import com.nova.mvvmtoolkit.internal.ProgressRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.File

/**
 * Repository class responsible for making network calls including dynamic API requests
 * and multipart file uploads using OkHttp.
 */
internal class ModelRepository {
    private val client = OkHttpClient()
    private val gson = Gson()

    /**
     * Makes a dynamic HTTP request and parses the response into the provided model class.
     *
     * @param T The type of model expected in the response.
     * @param method The HTTP method (e.g., "GET", "POST", "PUT", etc.).
     * @param endpoint The API endpoint to hit.
     * @param modelClass The model class to deserialize the response JSON.
     * @param requestBody Optional request body object for POST/PUT requests.
     * @return [Result] wrapping either the deserialized model or an error.
     */
    suspend fun <T : Any, R : Any> call(
        method: String,
        modelClass: Class<T>? = null,
        url: String,
        body: R?
    ): Result<Any> = withContext(Dispatchers.IO) {
        return@withContext try {
            val fullUrl = ModelRegistry.getBaseUrl() + url

            // Build request
            val builder = Request.Builder().url(fullUrl)

            if (method.uppercase() == "POST" && body != null) {
                val json = gson.toJson(body)
                val requestBody = json.toRequestBody("application/json".toMediaType())
                builder.post(requestBody)
            }

            val request = builder.build()
            val response = client.newCall(request).execute()
            val body: ResponseBody? = response.body
            var checkedResponse = "Response is null"
            if (body != null) {
                checkedResponse = body.string()
            }

            // to handle parsing errors
            if (checkedResponse == "Response is null") {
                return@withContext Result.failure(Exception("Response is null"))
            } else {
                try {
                    val responseString = handleResponse(checkedResponse)
                    if (responseString.isNotEmpty()) {
                        if (modelClass != null) {
                            if (responseString.startsWith("[")) {
                                Result.success(
                                    Gson().fromJson(
                                        responseString,
                                        TypeToken.getParameterized(
                                            ArrayList::class.java,
                                            modelClass
                                        ).type
                                    )
                                )
                            } else {
                                Result.success(
                                    Gson().fromJson(
                                        responseString,
                                        TypeToken.getParameterized(
                                            modelClass
                                        ).type
                                    )
                                )
                            }
                        } else {
                            Result.success(responseString)
                        }
                    } else {
                        Result.success(responseString)
                    }
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

     /**
     * Uploads multiple files with form fields using a multipart request.
     *
     * @param modelClass The model class to deserialize the response into (can be null).
     * @param endpoint The API endpoint to send the multipart request to.
     * @param files List of [Pair]s containing field name and file to upload.
     * @param formFields Optional form fields to be included in the request.
     * @param onProgress Optional lambda for progress updates (0–100).
     * @return [Result] wrapping either the parsed response or an error.
     */
    suspend fun <T : Any> uploadMultipartMultiple(
        modelClass: Class<T>? = null,
        endpoint: String,
        files: List<Pair<String, File>>,
        formFields: Map<String, String> = emptyMap(),
        onProgress: ((Int) -> Unit)? = null
    ): Result<Any> = withContext(Dispatchers.IO) {
        return@withContext try {
            val fullUrl = ModelRegistry.getBaseUrl() + endpoint

            // Build multipart body manually with progress tracking
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

            val totalBytes = files.sumOf { it.second.length() }
            var uploadedBytes = 0L

            for ((partName, file) in files) {
                val fileBody = ProgressRequestBody(
                    file,
                    "application/octet-stream",
                    onProgress = { bytes ->
                        uploadedBytes += bytes
                        val progress = ((uploadedBytes * 100) / totalBytes).toInt()
                        onProgress?.invoke(progress.coerceIn(0, 100))
                    }
                )
                builder.addFormDataPart(partName, file.name, fileBody)
            }

            for ((key, value) in formFields) {
                builder.addFormDataPart(key, value)
            }

            val request = Request.Builder()
                .url(fullUrl)
                .post(builder.build())
                .build()

            val response = client.newCall(request).execute()
            val body: ResponseBody? = response.body
            var checkedResponse = "Response is null"
            if (body != null) {
                checkedResponse = body.string()
            }

            // to handle parsing errors
            if (checkedResponse == "Response is null") {
                return@withContext Result.failure(Exception("Response is null"))
            } else {
                try {
                    val responseString = handleResponse(checkedResponse)
                    if (responseString.isNotEmpty()) {
                        if (modelClass != null) {
                            if (responseString.startsWith("[")) {
                                Result.success(
                                    Gson().fromJson(
                                        responseString,
                                        TypeToken.getParameterized(
                                            ArrayList::class.java,
                                            modelClass
                                        ).type
                                    )
                                )
                            } else {
                                Result.success(
                                    Gson().fromJson(
                                        responseString,
                                        TypeToken.getParameterized(
                                            modelClass
                                        ).type
                                    )
                                )
                            }
                        } else {
                            Result.success(responseString)
                        }
                    } else {
                        Result.success(responseString)
                    }
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun handleResponse(body: String?): String {
        if (body != null) {
            if (body.replace("\n".toRegex(), "").trim().startsWith("[")) {
                val array = JSONArray(body)
                if (array.length() > 0) {
                    return array.toString()
                }
            } else {
                val data = JSONTokener(body.replace("\n".toRegex(), "").trim()).nextValue()
                if (data is JSONObject) {
                    val jSONObject = JSONObject(body)
                    return jSONObject.toString()
                } else {
                    return data.toString()
                }
            }
        } else {
            return ""
        }
        return ""
    }
}