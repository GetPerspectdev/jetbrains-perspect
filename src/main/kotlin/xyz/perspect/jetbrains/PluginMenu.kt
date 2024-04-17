/* ==========================================================
File:        PluginMenu.kt
Description: Adds a Perspect item to the File menu.
Maintainer:  Perspect <support@perspect.com>
License:     BSD, see LICENSE for more details.
Website:     https://perspect.com/
===========================================================*/

package xyz.perspect.jetbrains

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

class PluginMenu : AnAction("Perspect Settings") {
    override fun actionPerformed(e: AnActionEvent) {
        val project: Project? = e.project
        val popup = Settings(project)
        popup.show()
    }
}
