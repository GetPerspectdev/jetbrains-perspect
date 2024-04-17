package xyz.perspect.jetbrains

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.WindowManager
import com.intellij.util.Consumer
import com.intellij.util.ui.UIUtil
import org.jetbrains.annotations.*
import javax.swing.Icon
import java.awt.event.MouseEvent

class CustomStatusBar : StatusBarWidgetFactory {

    @NotNull
    override fun getId(): String {
        return "Perspect"
    }

    @Nls
    @NotNull
    override fun getDisplayName(): String {
        return "Perspect"
    }

    override fun isAvailable(project: Project): Boolean {
        return true
    }

    @NotNull
    override fun createWidget(project: Project): StatusBarWidget {
        return PerspectStatusBarWidget(project)
    }

    override fun disposeWidget(widget: StatusBarWidget) {}

    override fun canBeEnabledOn(statusBar: StatusBar): Boolean {
        return true
    }

    inner class PerspectStatusBarWidget(val project: Project) : StatusBarWidget {

        val statusBar: StatusBar? = WindowManager.getInstance().getStatusBar(project)

        @Contract(pure = true)
        override fun ID(): String {
            return "Perspect"
        }

        @Nullable
        override fun getPresentation(): StatusBarWidget.WidgetPresentation? {
            return StatusBarPresenter(this)
        }

        override fun install(statusBar: StatusBar) {}

        override fun dispose() {}

        inner class StatusBarPresenter(val widget: PerspectStatusBarWidget) : StatusBarWidget.MultipleTextValuesPresentation, StatusBarWidget.Multiframe {

            override fun getPopupStep(): ListPopup? {
                Perspect.openDashboardWebsite()
                Perspect.updateStatusBarText()
                widget.statusBar?.updateWidget("Perspect")
                return null
            }

            override fun getSelectedValue(): String? {
                return Perspect.getStatusBarText()
            }

            override fun getIcon(): Icon? {
                val theme = if (UIUtil.isUnderDarcula()) "dark" else "light"
                return IconLoader.getIcon("status-bar-icon-$theme-theme.svg", Perspect::class.java)
            }

            override fun getTooltipText(): String? {
                return null
            }

            override fun getClickConsumer(): Consumer<MouseEvent>? {
                // Not used; use getPopupStep to handle click events
                return null
            }

            override fun copy(): StatusBarWidget {
                return PerspectStatusBarWidget(widget.project)
            }

            @NonNls
            @NotNull
            override fun ID(): String {
                return "Perspect"
            }

            override fun install(statusBar: StatusBar) {}

            override fun dispose() {
                Disposer.dispose(widget)
            }
        }
    }
}
