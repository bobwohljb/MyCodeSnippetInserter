package org.example.mycodesnippetinserter

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.BoxLayout
import javax.swing.Box
import javax.swing.JPanel

/**
 * The main UI component for the MyKotlinCodeSnippets tool window.
 * This class creates a panel with buttons for each available Kotlin code snippet action.
 */
class MyKotlinCodeSnippetsToolWindow(private val project: Project, private val toolWindow: ToolWindow) {

    /**
     * Creates and returns the content for the tool window.
     */
    fun getContent(): JComponent {
        // Create main panel with BorderLayout
        val mainPanel = JBPanel<JBPanel<*>>()
        mainPanel.layout = BorderLayout()

        // Create a panel for the title
        val titlePanel = JBPanel<JBPanel<*>>()
        titlePanel.layout = BorderLayout()
        val titleLabel = JBLabel("MyKotlinCodeSnippets")
        titleLabel.border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
        titlePanel.add(titleLabel, BorderLayout.CENTER)

        // Create a panel for the buttons with BoxLayout for vertical arrangement
        val buttonsPanel = JBPanel<JBPanel<*>>()
        buttonsPanel.layout = BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)
        buttonsPanel.border = BorderFactory.createEmptyBorder(5, 10, 10, 10)

        // Create a list of available actions
        val actions = listOf(
            ActionInfo("Data Class", "Insert or create a Kotlin data class template",
                       DataClassHandler(), "MyDataClass"),
            ActionInfo("Singleton", "Insert or create a Kotlin singleton pattern template",
                       SingletonHandler(), "MySingleton"),
            ActionInfo("Extension Functions", "Insert or create Kotlin extension function templates",
                       ExtensionFunctionHandler(), "MyExtensions"),
            ActionInfo("Coroutine Patterns", "Insert or create Kotlin coroutine pattern templates",
                       CoroutineHandler(), "MyCoroutines")
        )

        // Add buttons for each action
        for (actionInfo in actions) {
            // Create a panel for each action with two buttons side by side
            val actionPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
            actionPanel.alignmentX = JComponent.LEFT_ALIGNMENT
            actionPanel.maximumSize = Dimension(Int.MAX_VALUE, 40) // Fixed height for the panel

            // Insert button
            val insertButton = JButton("Insert ${actionInfo.title}")
            insertButton.toolTipText = "Insert ${actionInfo.description} into current editor"

            // Add action listener to execute the action when button is clicked
            insertButton.addActionListener {
                executeAction(actionInfo.action)
            }

            // Create File button
            val createButton = JButton("Create ${actionInfo.title} File")
            createButton.toolTipText = "Create a new file with ${actionInfo.description}"

            // Add action listener to create a new file when button is clicked
            createButton.addActionListener {
                createNewFile(actionInfo)
            }

            // Add buttons to the panel
            actionPanel.add(insertButton)
            actionPanel.add(createButton)

            // Add the panel to the buttons panel
            buttonsPanel.add(actionPanel)
            buttonsPanel.add(Box.createVerticalStrut(10)) // Add some spacing between action panels
        }

        // Add components to the main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH)
        mainPanel.add(JBScrollPane(buttonsPanel), BorderLayout.CENTER)

        return mainPanel
    }

    /**
     * Executes the given action with the current editor.
     */
    private fun executeAction(action: AnAction) {
        // Get the current editor
        val editor = FileEditorManager.getInstance(project).selectedTextEditor
        if (editor != null) {
            // Create a data context with the editor
            val dataContext = object : DataContext {
                override fun getData(dataId: String): Any? {
                    return when {
                        CommonDataKeys.PROJECT.`is`(dataId) -> project
                        CommonDataKeys.EDITOR.`is`(dataId) -> editor
                        else -> null
                    }
                }
            }

            // Clone the action's presentation
            val clonedPresentation = action.templatePresentation.clone()

            // Execute the action
            val actionEvent = AnActionEvent(
                null,
                dataContext,
                ActionPlaces.UNKNOWN,
                clonedPresentation, // Use cloned presentation
                ActionManager.getInstance(),
                0
            )
            action.actionPerformed(actionEvent)
        }
    }

    /**
     * Creates a new file with the given action info.
     */
    private fun createNewFile(actionInfo: ActionInfo) {
        // Get the code snippet from the action
        val handler = actionInfo.action as EditorHandler
        val snippet = handler.getCodeSnippet()

        // Create a new file with the snippet
        FileCreator.createKotlinFile(project, actionInfo.defaultFileName, snippet)
    }

    /**
     * Data class to hold information about an action.
     */
    private data class ActionInfo(
        val title: String,
        val description: String,
        val action: AnAction,
        val defaultFileName: String
    )
}