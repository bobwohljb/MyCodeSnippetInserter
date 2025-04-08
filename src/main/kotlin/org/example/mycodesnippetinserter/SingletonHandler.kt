package org.example.mycodesnippetinserter

import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * Handler for inserting a Kotlin singleton pattern template.
 * This action inserts a singleton implementation using Kotlin's object declaration.
 */
class SingletonHandler : EditorHandler() {

    override fun getCodeSnippet(): String {
        return """
            |object MySingleton {
            |    // Singleton properties
            |    private var instance: MySingleton? = null
            |    private val lock = Any()
            |    
            |    // Configuration properties
            |    var config: Map<String, String> = mapOf()
            |    
            |    // Initialize with default values
            |    init {
            |        println("Singleton initialized")
            |    }
            |    
            |    // Example method
            |    fun doSomething(input: String): String {
            |        return "Processed: ${'$'}{input}"
            |    }
            |    
            |    // Example of thread-safe lazy initialization (alternative approach)
            |    fun getInstance(): MySingleton {
            |        if (instance == null) {
            |            synchronized(lock) {
            |                if (instance == null) {
            |                    instance = MySingleton
            |                }
            |            }
            |        }
            |        return instance!!
            |    }
            |}
            |""".trimMargin()
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.text = "Insert Singleton"
        e.presentation.description = "Insert a Kotlin singleton pattern template"
    }
}