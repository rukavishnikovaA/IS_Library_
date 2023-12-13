package ru.development.ui

import androidx.compose.runtime.*
import org.jetbrains.compose.web.dom.ContentBuilder
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement
import ru.development.MainStyle

object OverlayScopeProvider {

    data class Overlay(val view: @Composable () -> Unit, val onDisposeRequest: () -> Unit)

    @Composable
    fun provide(onDisposeRequest: () -> Unit, block: @Composable () -> Unit) {


        DisposableEffect(Unit) {
            val overlay = Overlay(block, onDisposeRequest)

            addView(overlay)
            onDispose { removeView(overlay) }
        }
    }

    fun addView(overlay: Overlay) = overlays.add(overlay)

    fun removeView(overlay: Overlay) = overlays.remove(overlay)


    private val overlays = mutableStateListOf<Overlay>()

    @Composable
    fun render() {
        var overlayContainerRef: HTMLDivElement? by remember { mutableStateOf(null) }

        if (overlays.isNotEmpty()) Div(attrs = {
            id("OverlayContainer")
            classes(MainStyle.overlay, MainStyle.fillAbsolute)
            onClick { event ->
                val target = event.target
                if (target is HTMLDivElement && target === overlayContainerRef) overlays.lastOrNull()?.onDisposeRequest?.invoke() }

            ref { view ->
                overlayContainerRef = view
                onDispose { overlayContainerRef = null }
            }
        }) {
            overlays.forEach { overlay -> overlay.view.invoke() }
        }
    }
}

@Composable
fun OverlayView(onDisposeRequest: () -> Unit, block: @Composable () -> Unit) {
    OverlayScopeProvider.provide(onDisposeRequest, block)
}

@Composable
fun Dialog(onDisposeRequest: () -> Unit, content: ContentBuilder<HTMLDivElement>? = null) {
    OverlayView(onDisposeRequest) {
        Div(attrs = { classes(MainStyle.dialog, MainStyle.fillAbsolute, MainStyle.fitContent) }, content = content)
    }
}