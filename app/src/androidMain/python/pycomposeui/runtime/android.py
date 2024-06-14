from java import jclass
from java.chaquopy import JavaClass


_runtime = jclass("io.github.thisisthepy.pycomposeui.RuntimeKt")
print("Compose Runtime:", _runtime)
_runtime_android = jclass("io.github.thisisthepy.pycomposeui.Runtime_androidKt")
print("Compose Android Runtime:", _runtime_android)
ComposableWrapper = _runtime.composableWrapper
print("Composable Wrapper:", ComposableWrapper)
ComposableLambdaImpl = jclass("androidx.compose.runtime.internal.ComposableLambdaImpl")
print("Composable Lambda:", ComposableLambdaImpl)
_rememberSaveable = _runtime_android.rememberSaveableWrapper
print("Remember Saveable:", _rememberSaveable)
rememberSaveable = _rememberSaveable
