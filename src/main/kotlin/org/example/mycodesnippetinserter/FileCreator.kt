package org.example.mycodesnippetinserter

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import java.io.File

/**
 * Utility class for creating new files with code snippets.
 */
object FileCreator {

    /**
     * Creates a new Kotlin file with the given name and content.
     * Prompts the user for a file name and location.
     *
     * @param project The current project
     * @param fileName Suggested file name (without extension)
     * @param content The content to put in the file
     * @return The created file, or null if the operation was cancelled
     */
    fun createKotlinFile(project: Project, fileName: String, content: String): VirtualFile? {
        // Ask user for file name
        val userFileName = Messages.showInputDialog(
            project,
            "Enter file name (without extension):",
            "Create New Kotlin File",
            Messages.getQuestionIcon(),
            fileName,
            null
        ) ?: return null // User cancelled

        // Ensure the file name is valid
        val validFileName = if (userFileName.endsWith(".kt")) userFileName else "$userFileName.kt"

        // Ask user for file location
        val sourceRoots = project.baseDir.findChild("src")?.findChild("main")?.findChild("kotlin")
        val directory = if (sourceRoots != null) {
            chooseDirectory(project, sourceRoots)
        } else {
            chooseDirectory(project, project.baseDir)
        } ?: return null // User cancelled

        // Create the file
        var resultFile: VirtualFile? = null

        WriteCommandAction.runWriteCommandAction(project) {
            try {
                // Create physical file
                val file = File(directory.path, validFileName)
                if (!file.parentFile.exists()) {
                    file.parentFile.mkdirs()
                }
                file.createNewFile()

                // Refresh VFS to see the new file
                val virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(file)
                    ?: throw IllegalStateException("Failed to create file: $validFileName")

                // Write content to the file
                virtualFile.setBinaryContent(content.toByteArray())

                // Open the file in editor
                FileEditorManager.getInstance(project).openFile(virtualFile, true)

                resultFile = virtualFile
            } catch (e: Exception) {
                Messages.showErrorDialog(
                    project,
                    "Error creating file: ${e.message}",
                    "File Creation Error"
                )
            }
        }

        return resultFile
    }

    /**
     * Shows a directory chooser dialog and returns the selected directory.
     *
     * @param project The current project
     * @param root The root directory to start from
     * @return The selected directory, or null if the operation was cancelled
     */
    private fun chooseDirectory(project: Project, root: VirtualFile): VirtualFile? {
        // For simplicity, we'll just use the root directory for now
        // In a real implementation, you would show a directory chooser dialog
        return root
    }

    /**
     * Extracts the package name from a directory path relative to the source root.
     *
     * @param directory The directory
     * @param sourceRoot The source root
     * @return The package name
     */
    private fun getPackageName(directory: VirtualFile, sourceRoot: VirtualFile): String {
        val relativePath = directory.path.removePrefix(sourceRoot.path).trim('/')
        return if (relativePath.isEmpty()) {
            ""
        } else {
            relativePath.replace('/', '.')
        }
    }
}
