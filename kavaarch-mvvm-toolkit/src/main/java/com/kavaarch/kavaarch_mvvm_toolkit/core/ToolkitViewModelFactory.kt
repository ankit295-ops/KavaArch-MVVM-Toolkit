package com.nova.mvvmtoolkit.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class for creating instances of [ToolkitViewModel] with the required model class parameter.
 *
 * This factory is necessary because [ToolkitViewModel] requires a [Class] parameter
 * which is not provided by default in Android's ViewModelProvider.
 *
 * @property modelClass The model class to be associated with the [ToolkitViewModel].
 */
internal class ToolkitViewModelFactory<T : Any>() : ViewModelProvider.Factory {
    private var modelClass: Class<T>? = null

    constructor(modelClass: Class<T>) : this() {
        this.modelClass = modelClass
    }

    /**
     * Creates a new instance of the given [Class], passing in the required [modelClass] to the [ToolkitViewModel].
     *
     * @param modelClass The class of the ViewModel to be created.
     * @return A new instance of [ToolkitViewModel] with the provided model class.
     * @throws IllegalArgumentException if the provided class is not assignable from [ToolkitViewModel].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ModelViewModel(this.modelClass) as T
    }
}