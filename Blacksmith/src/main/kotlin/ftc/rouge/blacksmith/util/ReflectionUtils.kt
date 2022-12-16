package ftc.rouge.blacksmith.util

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

fun Any.getMethod(name: String, vararg params: Class<*>): Method {
    return this::class.java.getMethod(name, *params)
}

inline fun <reified T> Any.invokeMethod(name: String, vararg params: Pair<Any, Class<*>>): T {
    return this.getMethod(name, *params.map { it.second }.toTypedArray()).invoke(this, *params.map { it.first }.toTypedArray()) as T
}

inline fun <reified T> Any.invokeMethodInfer(name: String, vararg params: Any): T {
    return this.invokeMethod(name, *params.map { it to it::class.java }.toTypedArray())
}

inline fun <reified T> Any.invokeMethodRethrowing(name: String, vararg params: Pair<Any, Class<*>>): T {
    try {
        return this.invokeMethod(name, *params)
    } catch (e: InvocationTargetException) {
        throw e.targetException
    }
}

inline fun <reified T> Any.invokeMethodRethrowingInfer(name: String, vararg params: Any): T {
    try {
        return this.invokeMethodInfer(name, *params)
    } catch (e: InvocationTargetException) {
        throw e.targetException
    }
}

