//package xyz.perspect.jetbrains
//
//import com.intellij.compiler.server.BuildManagerListener
//import com.intellij.openapi.compiler.CompilationStatusListener
//import com.intellij.openapi.compiler.CompileContext
//import com.intellij.openapi.project.Project
//import org.jetbrains.annotations.NotNull
//import java.awt.EventQueue
//import java.util.*
//
//class CustomBuildManagerListener : BuildManagerListener, CompilationStatusListener {
//    override fun buildStarted(@NotNull project: Project, @NotNull sessionId: UUID, isAutomake: Boolean) {
//        if (!Perspect.isAppActive() || !Perspect.isProjectInitialized(project)) return
//        EventQueue.invokeLater {
//            val file = Perspect.getCurrentFile(project) ?: return@invokeLater
//            Perspect.isBuilding = true
//            Perspect.appendHeartbeat(file, project, false, null)
//        }
//    }
//
//    override fun buildFinished(@NotNull project: Project, @NotNull sessionId: UUID, isAutomake: Boolean) {
//        Perspect.isBuilding = false
//    }
//
//    override fun compilationFinished(aborted: Boolean, errors: Int, warnings: Int, @NotNull compileContext: CompileContext) {
//        Perspect.isBuilding = false
//    }
//
//    override fun automakeCompilationFinished(errors: Int, warnings: Int, @NotNull compileContext: CompileContext) {
//        Perspect.isBuilding = false
//    }
//}
