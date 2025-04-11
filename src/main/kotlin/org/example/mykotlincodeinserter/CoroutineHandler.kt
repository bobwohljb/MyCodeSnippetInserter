package org.example.mykotlincodeinserter

import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * Handler for inserting Kotlin coroutine pattern templates.
 * This action inserts common coroutine patterns for asynchronous programming.
 */
class CoroutineHandler : EditorHandler() {

    override fun getCodeSnippet(): String {
        return """
            |import kotlinx.coroutines.*
            |import kotlin.system.measureTimeMillis
            |
            |// Basic coroutine launch example
            |fun basicCoroutineExample() {
            |    // Create a coroutine scope
            |    val scope = CoroutineScope(Dispatchers.Default)
            |    
            |    // Launch a coroutine
            |    val job = scope.launch {
            |        delay(1000L) // Non-blocking delay for 1 second
            |        println("Coroutine completed!")
            |    }
            |    
            |    // Wait for the coroutine to complete (if needed)
            |    runBlocking {
            |        job.join()
            |    }
            |}
            |
            |// Async/Await pattern
            |suspend fun fetchDataAsync(): List<String> = coroutineScope {
            |    val result1 = async { fetchFromSource1() }
            |    val result2 = async { fetchFromSource2() }
            |    
            |    // Combine results when both are available
            |    val combinedResult = result1.await() + result2.await()
            |    combinedResult
            |}
            |
            |// Example of suspending functions
            |suspend fun fetchFromSource1(): List<String> {
            |    delay(1000L) // Simulate network delay
            |    return listOf("Data1", "Data2")
            |}
            |
            |suspend fun fetchFromSource2(): List<String> {
            |    delay(1500L) // Simulate network delay
            |    return listOf("Data3", "Data4")
            |}
            |
            |// Coroutine with timeout
            |suspend fun fetchWithTimeout(): String {
            |    return withTimeout(2000L) {
            |        // This will throw TimeoutCancellationException if it takes more than 2 seconds
            |        delay(1500L)
            |        "Result after delay"
            |    }
            |}
            |
            |// Error handling in coroutines
            |fun coroutineErrorHandling() {
            |    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            |        println("Caught exception: ${'$'}{exception.message}")
            |    }
            |    
            |    val scope = CoroutineScope(Dispatchers.IO + exceptionHandler)
            |    
            |    scope.launch {
            |        try {
            |            // Code that might throw an exception
            |            throw RuntimeException("Simulated error")
            |        } catch (e: Exception) {
            |            println("Caught inside coroutine: ${'$'}{e.message}")
            |        }
            |    }
            |}
            |""".trimMargin()
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.text = "Insert Coroutine Patterns"
        e.presentation.description = "Insert Kotlin coroutine pattern templates"
    }
}