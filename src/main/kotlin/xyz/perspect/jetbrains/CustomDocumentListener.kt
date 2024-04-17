package xyz.perspect.jetbrains

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class CustomDocumentListener : DocumentListener {
    override fun documentChanged(event: DocumentEvent) {
        try {
            if (!Perspect.isAppActive()) return
            val document = event.document
            val file = Perspect.getFile(document)
            if (file == null) return
            val project = Perspect.getProject(document)
            if (!Perspect.isProjectInitialized(project)) return
            val lineStats = Perspect.getLineStats(document, event.offset)
            Perspect.appendHeartbeat(file, project, false, lineStats)
        } catch (e: Exception) {
            Perspect.debugException(e)
        }
    }

    override fun beforeDocumentChange(event: DocumentEvent) {}
}
