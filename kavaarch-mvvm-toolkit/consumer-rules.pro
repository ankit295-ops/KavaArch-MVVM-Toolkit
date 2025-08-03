# Preserve MVVMToolkit class and its methods
-keep class com.kavaarch.kavaarch_mvvm_toolkit.api.MVVMToolkit {
    <methods>;
}

# Keep all annotations
-keepattributes *Annotation*

# Keep @Keep annotated classes/methods
-keep @androidx.annotation.Keep class * { *; }

# Keep ViewModel subclasses
-keep class androidx.lifecycle.ViewModel
-keep class androidx.lifecycle.** { *; }

# Allow obfuscation for everything else
-dontwarn com.nova.mvvmtoolkit.**