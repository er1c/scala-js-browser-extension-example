package plugin

import org.scalajs.dom
import CssSettings._
//import scalacss.ScalaCssReact._
import scala.scalajs.js.annotation.JSExport
import japgolly.scalajs.react.extra.router._
import plugin.components._

object Index {

  sealed trait Route
  case object PopupPageRoute extends Route
  case object FullPageRoute extends Route

  private  val routerConfig = RouterConfigDsl[Route].buildConfig { dsl =>
    import dsl._

    (emptyRule
      | staticRoute(root,     FullPageRoute)  ~> render(FullPage())
      | staticRoute("#popup", PopupPageRoute) ~> render(PopupPage())
      ).notFound(redirectToPage(FullPageRoute)(SetRouteVia.HistoryPush))
  }

  private val baseUrl = BaseUrl.fromWindowUrl(_.takeWhile(_ != '#'))
  //private val baseUrl = BaseUrl.fromWindowOrigin

  @JSExport
  def main(args: Array[String]): Unit = {
    plugin.css.Index.addToDocument()
    val container = dom.document.getElementById("root")
    dom.console.info("Router logging is enabled. Enjoy!")
    val router = Router(baseUrl, routerConfig.logToConsole)
    router().renderIntoDOM(container)
  }
}