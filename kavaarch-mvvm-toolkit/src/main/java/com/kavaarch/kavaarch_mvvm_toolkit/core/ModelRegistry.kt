package com.kavaarch.kavaarch_mvvm_toolkit.core

/**
 * Object that holds and manages registration of all model classes and the base API URL.
 *
 * This registry allows dynamic mapping of model names (as strings) to their corresponding data classes,
 * which are used to parse the API response dynamically at runtime.
 */
internal object ModelRegistry {

    private var baseUrl = ""
    private val mapModel = mutableMapOf<String, Class<out Any>>()

    /**
     * Initializes the registry with base URL and map of model names to classes.
     *
     * @param baseUrl The base URL used for network requests.
     * @param models A map containing the model name as key and the corresponding class type as value.
     */
    fun init(baseUrl: String, mapModel: Map<String, Class<out Any>>) {
        this.baseUrl = baseUrl
        this.mapModel.clear()
        this.mapModel.putAll(mapModel)
    }

    /**
     * Retrieves the model class associated with the provided model name.
     *
     * @param modelName The key associated with the model.
     * @return The class type corresponding to the model name.
     * @throws IllegalStateException if the model class is not found.
     */
    fun <T : Any> getModelClass(name: String): Class<T>? {
        @Suppress("UNCHECKED_CAST")
        return mapModel[name] as? Class<T>
    }

    /**
     * Retrieves the registered base URL.
     *
     * @return The base URL string.
     * @throws IllegalStateException if baseUrl is not initialized.
     */
    fun getBaseUrl() = baseUrl
}