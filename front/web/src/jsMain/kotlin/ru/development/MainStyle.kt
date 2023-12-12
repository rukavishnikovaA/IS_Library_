package ru.development

import org.jetbrains.compose.web.css.*

object MainStyle : StyleSheet() {

    val fillAbsolute by style {
        position(Position.Absolute)
        top(0.px)
        left(0.px)
        right(0.px)
        bottom(0.px)
    }
    val fitContent by style {
        property("width", "fit-content")
        property("height", "fit-content")
    }

    val column by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
    }

    val row by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
    }

    val search by style {
        alignItems(AlignItems.Center)
        marginBottom(40.px)
    }

    val menu by style {

    }

    val inputWithTitle by style {
        width(568.px)
        marginTop(12.px)

        self + firstChild style {
            color(Color("rgba(0, 0, 0, 0.70)"))
            fontSize(24.px)
            fontWeight(500)
        }
    }

    val authInput by style {
        alignItems(AlignItems.Center)

        paddingLeft(47.px)
        paddingRight(47.px)
        paddingTop(36.px)
        paddingBottom(36.px)

        backgroundColor(Color.white)
        borderRadius(10.px)
        property("margin", "auto")
    }

    val selected by style { }

    val item by style {
        color(Color("#443C73"))
        fontSize(24.px)
        fontWeight(800)

        paddingLeft(25.px)
        paddingRight(25.px)
        paddingTop(10.px)
        paddingBottom(10.px)

        self + className(selected) style {
            borderRadius(10.px)
            backgroundColor(Color.white)
        }
    }

    val newsList by style {

    }

    val newsItem by style {
        borderRadius(10.px)
        backgroundColor(Color.white)

        paddingTop(64.px)
        paddingLeft(46.px)
        paddingRight(46.px)
        paddingBottom(64.px)

        marginBottom(50.px)
    }

    init {
        style(selector("input")) {
            height(60.px)
            border(width = 4.px, style = LineStyle.Solid, color = Color("#989BF8"))
            borderRadius(10.px)

            padding(12.px, 20.px)
            display(DisplayStyle.InlineBlock)
            boxSizing("border-box")

            fontSize(18.pt)
        }

        style(selector("table")) {
            fontSize(18.pt)
        }

        style(child(selector("table"), selector("tr"))) {
            backgroundColor(Color.azure)
            padding(10.px, 20.px)
        }
    }
}
