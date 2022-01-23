package plugin.css

import plugin.CssSettings._
import scalacss.internal.mutable.GlobalRegistry

object Index extends StyleSheet.Inline {
  import dsl._

  private val headerStyle = style(
    unsafeChild("h1")(
      fontSize(30 px),
      textAlign.center,
    )
  )

  private val paragraphStyle = style(
    unsafeChild("p")(
      fontSize(16 px),
      margin.`0`,
      textAlign.center,
    )
  )

  style(
    unsafeRoot("img")(
      height.auto,
      maxWidth(100 %%),
    ),

    unsafeRoot("body")(
      margin.`0`,
    ),

    unsafeRoot(".container")(
      height(100 vh),
      alignItems.center,
      display.flex,
      flexDirection.column,
      justifyContent.center,
      width(100 vw),
    ),

    unsafeRoot(".popup-content")(
      padding(50 px),
      width(400 px),
      headerStyle,
      paragraphStyle,
    ),

    unsafeRoot(".content")(
      maxWidth(400 px),
      padding(50 px),
      headerStyle,
      paragraphStyle,
    ),
  )

  def load: Unit = {
    GlobalRegistry.register(this)
    GlobalRegistry.onRegistration(_.addToDocument())
  }
}


/** original "chrome-extension-kit/react/react-popup-full-page/css/index.js" example */
/*
img {
  height: auto;
  max-width: 100%;
}

body {
  margin: 0;
}

.container {
  height: 100vh;
  align-items: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100vw;
}

.popup-content {
  padding: 50px;
  width: 400px;
}

.content {
  max-width: 400px;
  padding: 50px;
}

.content h1,
.popup-content h1 {
  font-size: 30px;
  text-align: center;
}

.popup-content p,
.content p {
  font-size: 16px;
  margin: 0;
  text-align: center;
}
 */