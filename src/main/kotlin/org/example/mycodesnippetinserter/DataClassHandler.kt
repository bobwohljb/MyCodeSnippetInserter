package org.example.mycodesnippetinserter

import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * Handler for inserting a Kotlin data class template.
 * This action inserts a basic data class with common properties and methods.
 */
class DataClassHandler : EditorHandler() {

    override fun getCodeSnippet(): String {
        return """
            |data class MyDataClass(
            |    val id: String,
            |    val name: String,
            |    val value: Int = 0,
            |    val isEnabled: Boolean = true
            |) {
            |    // Optional custom methods can be added here
            |    fun toDisplayString(): String {
            |        return "[${'$'}{id}] ${'$'}{name}: ${'$'}{value}"
            |    }
            |    
            |    // Example of a companion object for factory methods
            |    companion object {
            |        fun createDefault(): MyDataClass {
            |            return MyDataClass("default", "Default Item")
            |        }
            |    }
            |}
            |""".trimMargin()
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.text = "Insert Data Class"
        e.presentation.description = "Insert a Kotlin data class template"
    }
}
