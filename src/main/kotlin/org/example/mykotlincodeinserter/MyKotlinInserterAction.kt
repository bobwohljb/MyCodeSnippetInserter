package org.example.mykotlincodeinserter

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import javax.swing.Icon

/**
 * Action that shows a popup dialog with all available Kotlin code snippet inserters.
 * When an option is selected, it executes the corresponding action.
 */
class MyKotlinInserterAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return

        // Create a list of available actions
        val actions = listOf(
            ActionInfo("Insert Data Class", "Insert a Kotlin data class template",
                       DataClassHandler()),
            ActionInfo("Insert Singleton", "Insert a Kotlin singleton pattern template",
                       SingletonHandler()),
            ActionInfo("Insert Extension Functions", "Insert Kotlin extension function templates",
                       ExtensionFunctionHandler()),
            ActionInfo("Insert Coroutine Patterns", "Insert Kotlin coroutine pattern templates",
                       CoroutineHandler())
        )

        // Create and show the popup
        val popup = JBPopupFactory.getInstance().createListPopup(
            object : BaseListPopupStep<ActionInfo>("Select Kotlin Snippet", actions) {
                override fun getTextFor(value: ActionInfo): String = value.title

                override fun getIconFor(value: ActionInfo): Icon? = value.action.templatePresentation.icon

                override fun onChosen(selectedValue: ActionInfo, finalChoice: Boolean): PopupStep<*>? {
                    if (finalChoice) {
                        // Execute the selected action
                        // Clone the action's presentation
                        val clonedPresentation = selectedValue.action.templatePresentation.clone()

                        val actionEvent = AnActionEvent(
                            e.inputEvent,
                            e.dataContext,
                            e.place,
                            clonedPresentation, // Use cloned presentation
                            ActionManager.getInstance(),
                            e.modifiers
                        )
                        selectedValue.action.actionPerformed(actionEvent)
                    }
                    return FINAL_CHOICE
                }
            }
        )

        // Show the popup
        popup.showInBestPositionFor(editor)
    }

    override fun update(e: AnActionEvent) {
        // Enable the action if an editor is available
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isEnabled = editor != null
    }

    /**
     * Data class to hold information about an action.
     */
    private data class ActionInfo(
        val title: String,
        val description: String,
        val action: AnAction
    )
}