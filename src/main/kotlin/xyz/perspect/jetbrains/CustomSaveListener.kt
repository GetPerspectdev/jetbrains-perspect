package xyz.perspect.jetbrains

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManagerListener
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull

class CustomSaveListener : FileDocumentManagerListener {
    override fun beforeDocumentSaving(document: Document) {
        try {
            if (!Perspect.isAppActive()) return
            val file = Perspect.getFile(document)
            if (file == null) return
            val project = Perspect.getProject(document)
            if (!Perspect.isProjectInitialized(project)) return
            var lineStats = LineStats()
            if (project != null) {
                val editor: Editor? = FileEditorManager.getInstance(project).selectedTextEditor
                if (editor != null) {
                    val offset = editor.caretModel.offset
                    lineStats = Perspect.getLineStats(document, offset)
                }
            }
            Perspect.appendHeartbeat(file, project, true, lineStats)
        } catch (e: Exception) {
            Perspect.debugException(e)
        }
    }

    override fun beforeAllDocumentsSaving() {}

    override fun beforeFileContentReload(@NotNull file: VirtualFile, @NotNull document: Document) {}

    override fun fileWithNoDocumentChanged(@NotNull file: VirtualFile) {}

    override fun fileContentReloaded(@NotNull file: VirtualFile, @NotNull  document: Document) {}

    override fun fileContentLoaded(@NotNull file: VirtualFile, @NotNull  document: Document) {}

    override fun unsavedDocumentsDropped() {}
}
