package org.example.mycodesnippetinserter

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

/**
 * Factory class for creating the MyKotlinInserter tool window.
 * This class is registered in plugin.xml and is responsible for creating the tool window content.
 */
class MyKotlinInserterToolWindowFactory : ToolWindowFactory {
    
    /**
     * Creates the content for the tool window.
     * 
     * @param project The current project
     * @param toolWindow The tool window to create content for
     */
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyKotlinInserterToolWindow(project, toolWindow)
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(myToolWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}