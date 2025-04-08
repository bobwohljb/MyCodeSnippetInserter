package org.example.mycodesnippetinserter

import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * Handler for inserting Kotlin extension function templates.
 * This action inserts common extension function patterns.
 */
class ExtensionFunctionHandler : EditorHandler() {

    override fun getCodeSnippet(): String {
        return """
            |// String extensions
            |fun String.toTitleCase(): String {
            |    if (this.isEmpty()) return this
            |    return this.split(" ")
            |        .map { word -> 
            |            if (word.isEmpty()) word 
            |            else word[0].uppercase() + word.substring(1).lowercase() 
            |        }
            |        .joinToString(" ")
            |}
            |
            |// Collection extensions
            |fun <T> List<T>.secondOrNull(): T? {
            |    return if (this.size >= 2) this[1] else null
            |}
            |
            |// Extension property example
            |val String.isValidEmail: Boolean
            |    get() {
            |        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+${'$'}"
            |        return this.matches(emailRegex.toRegex())
            |    }
            |
            |// Extension with receiver and parameters
            |fun <T> MutableList<T>.addIfNotExists(element: T): Boolean {
            |    return if (!this.contains(element)) {
            |        this.add(element)
            |        true
            |    } else {
            |        false
            |    }
            |}
            |""".trimMargin()
    }

    override fun update(e: AnActionEvent) {
        super.update(e)
        e.presentation.text = "Insert Extension Functions"
        e.presentation.description = "Insert Kotlin extension function templates"
    }
}