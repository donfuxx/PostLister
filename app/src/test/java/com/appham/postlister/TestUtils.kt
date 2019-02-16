package com.appham.postlister

/**
 * Helper to invoke protected methods
 */
fun invokeProtectedMethod(testClass: Any, name: String) {
    val method = testClass.javaClass.getDeclaredMethod(name)
    method.isAccessible = true
    method.invoke(testClass)
}