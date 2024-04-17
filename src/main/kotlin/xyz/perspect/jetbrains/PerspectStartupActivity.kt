package xyz.perspect.jetbrains

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import org.jetbrains.annotations.NotNull

class PerspectStartupActivity : StartupActivity.Background {
    override fun runActivity(@NotNull project: Project) {
        Perspect.checkApiKey()
    }
}
