package org.example.mycodesnippetinserter

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project

/**
 * Base class for handling editor interactions for code snippet insertion.
 * This class provides common functionality for inserting code snippets into the editor.
 */
abstract class EditorHandler : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val project = e.getData(CommonDataKeys.PROJECT) ?: return

        val snippet = getCodeSnippet()
        insertSnippet(editor, project, snippet)
    }

    /**
     * Inserts the given code snippet at the current caret position in the editor.
     */
    private fun insertSnippet(editor: Editor, project: Project, snippet: String) {
        val document = editor.document
        val caretModel = editor.caretModel
        val offset = caretModel.offset

        WriteCommandAction.runWriteCommandAction(project) {
            document.insertString(offset, snippet)
            caretModel.moveToOffset(offset + snippet.length)
        }
    }

    /**
     * Returns the code snippet to be inserted.
     * This method should be implemented by subclasses to provide specific code patterns.
     */
    abstract fun getCodeSnippet(): String

    /**
     * Checks if the action should be enabled for the current context.
     * By default, the action is enabled if an editor is available.
     */
    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor != null
    }
}
