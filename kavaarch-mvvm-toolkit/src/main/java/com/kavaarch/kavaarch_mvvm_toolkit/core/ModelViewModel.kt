package com.nova.mvvmtoolkit.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * [ModelViewModel] is a generic ViewModel used to perform dynamic API requests.
 *
 * It uses [ModelRepository] to make network requests and emits the result via [LiveData].
 */
internal class ModelViewModel(private val modelClass: Class<*>?) : ViewModel() {

    private val liveDataMap = mutableMapOf<String, MutableLiveData<Result<Any>>>()

    /**
     * Performs an API call and returns the result wrapped in a [LiveData] object.
     *
     * @param T The type of data expected in the API response.
     * @param method The HTTP method to be used (e.g., "GET", "POST").
     * @param endpoint The specific API endpoint to hit.
     * @param modelClass The data class to which the response should be deserialized.
     * @param requestBody Optional request body for POST/PUT requests.
     * @return [LiveData] that emits a [Result] containing the response data or error.
     */
    fun getLiveData(
        method: String,
        endpoint: String,
        requestBody: Any? = null
    ): LiveData<Result<Any>> {
        val key = "$method::$endpoint"
        val liveData = liveDataMap.getOrPut(key) { MutableLiveData() }

        viewModelScope.launch {
            val result = ModelRepository().call(method, modelClass, endpoint, requestBody)
            liveData.postValue(result)
        }

        return liveData
    }

    /**
     * Makes a multipart file upload request and returns a [LiveData] that emits progress and result.
     *
     * @param endpoint The API endpoint to upload the files to.
     * @param files A list of key-value pairs, where the key is the form field name and the value is the [File] to upload.
     * @param formFields Optional form fields to include with the request.
     * @param onProgress Optional callback to track upload progress as a percentage.
     * @return [LiveData] emitting the result of the multipart upload.
     */
    fun getMultipartLiveData(
        endpoint: String,
        files: List<Pair<String, File>>,
        formFields: Map<String, String> = emptyMap(),
        onProgress: ((Int) -> Unit)? = null
    ): LiveData<Result<Any>> {
        val key = "MULTIPART::$endpoint"
        val liveData = liveDataMap.getOrPut(key) { MutableLiveData() }

        viewModelScope.launch {
            val result: Result<Any> = ModelRepository().uploadMultipartMultiple(
                modelClass,
                endpoint,
                files,
                formFields,
                onProgress
            )
            liveData.postValue(result)
        }

        return liveData
    }
}