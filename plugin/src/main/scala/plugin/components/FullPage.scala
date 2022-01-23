package plugin.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object FullPage {
  private lazy val Component = ScalaComponent.builder[Unit]
    .renderStatic {
      <.div(
        ^.cls := "container",
        <.section(
          ^.cls := "container",
          <.img(
            ^.src  := "/images/graphic.png",
            ^.alt  := "Welcome Graphic"
          ),
          <.h1("Full Page Example"),
          <.p(
          s"""|This is the full page portion of the Chrome extension (with some basic
              |styling added) toggled by clicking the button in the popup portion.
              |Please read the <b>README.md</b> for more information.
              |""".stripMargin)
        )
      )
    }
    .build

  def apply() = Component()
}
