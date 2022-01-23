package plugin.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import typings.webextensionPolyfill.tabsMod.Tabs.CreateCreatePropertiesType

object PopupPage {

  def handleClick: Callback = Callback.apply { _: Any =>
    typings.webextensionPolyfill.mod.tabs.create(
      CreateCreatePropertiesType()
        .setUrl("/index.html")
    )
  }

  private lazy val Component = ScalaComponent.builder[Unit]
    .renderStatic {
      <.div(
        <.section(
          ^.cls := "popup-content",
          <.img(
            ^.src := "/images/graphic.png",
            ^.alt := "Welcome Graphic"
          ),
          <.h1("Popup Example"),
          <.p(
            s"""|This is the popup portion of the Chrome extension (with some basic
                |styling added) toggled by clicking the extension's icon. Please read
                |the <b>README.md</b> for more information.
                |""".stripMargin),
          <.button(
            ^.onClick --> handleClick,
            "View Full Page Portion"
          )
        )
      )
    }
    .build

  def apply() = Component()
}
