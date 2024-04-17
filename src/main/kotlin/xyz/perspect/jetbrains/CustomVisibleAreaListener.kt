package xyz.perspect.jetbrains

import com.intellij.openapi.editor.event.VisibleAreaEvent
import com.intellij.openapi.editor.event.VisibleAreaListener

class CustomVisibleAreaListener : VisibleAreaListener {
    override fun visibleAreaChanged(visibleAreaEvent: VisibleAreaEvent) {
        try {
            if (!didChange(visibleAreaEvent)) return
            if (!Perspect.isAppActive()) return
            val document = visibleAreaEvent.editor.document
            val file = Perspect.getFile(document) ?: return
            val project = visibleAreaEvent.editor.project ?: return
            if (!Perspect.isProjectInitialized(project)) return
            val editor = visibleAreaEvent.editor
            val offset = editor.caretModel.offset
            val lineStats = Perspect.getLineStats(document, offset)
            Perspect.appendHeartbeat(file, project, false, lineStats)
        } catch (e: Exception) {
            Perspect.debugException(e)
        }
    }

    private fun didChange(visibleAreaEvent: VisibleAreaEvent): Boolean {
        val oldRect = visibleAreaEvent.oldRectangle ?: return true
        val newRect = visibleAreaEvent.newRectangle ?: return false
        return newRect.x != oldRect.x || newRect.y != oldRect.y
    }
}
