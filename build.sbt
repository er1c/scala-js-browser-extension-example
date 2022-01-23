import com.typesafe.sbt.packager.SettingsHelper._

val Versions = new {
  val scala = "2.13.7"
  val scalaJSReact = "2.0.1"
  val scalaCss = "1.0.0"

  val webextensionPolyfill = "0.8.0"
  val webextensionPolyfillTypes = "0.8.2"

  val reactJS = "17.0.2"
  val reactJSTypes = "17.0.38"
  val reactJSDOMTypes = "17.0.11"
}

ThisBuild / organization := "io.github.er1c"
ThisBuild / homepage     := Some(url("https://github.com/er1c/scala-js-browser-extension-example"))
ThisBuild / licenses     := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / description  := "Example browser extension written in Scala."
ThisBuild / developers   := List(
  Developer("ericpeters", "Eric Peters", "eric@peters.org", url("https://github.com/er1c"))
)
ThisBuild / scmInfo      := Some(ScmInfo(url(s"https://github.com/er1c/scala-js-browser-extension-example"), s"git@github.com:er1c/scala-js-browser-extension-example.git"))
ThisBuild / scalaVersion := Versions.scala

// Manually update ci config via `sbt githubWorkflowGenerate`

ThisBuild / githubWorkflowOSes  := Seq("ubuntu-latest", "macos-latest", "windows-latest")
ThisBuild / githubWorkflowEnv   := Map.empty
ThisBuild / githubWorkflowBuild := Seq(
  WorkflowStep.Sbt(
    commands = List("universal:packageBin", "checkArchive"),
    env = Map.empty
  )
)

ThisBuild / githubWorkflowPublishTargetBranches := Nil

ThisBuild / scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-encoding",
  "UTF-8",
  "-Xfatal-warnings",
  "-language:_",
  // Warn when dead code is identified.
  "-Ywarn-dead-code",
  // Warn when numerics are widened.
  "-Ywarn-numeric-widen",
  "-language:postfixOps",
)

lazy val plugin = project
  .in(file("plugin"))
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(ScalablyTypedConverterPlugin)
  .enablePlugins(ScalaJSBundlerPlugin)
  .enablePlugins(UniversalPlugin)
  .settings(
    /* disabled because it somehow triggers many warnings */
    scalaJSLinkerConfig := scalaJSLinkerConfig.value.withSourceMap(false),
    Compile / fastOptJS / webpackDevServerExtraArgs += "--mode=development",
    Compile / fullOptJS / webpackDevServerExtraArgs += "--mode=production",
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core" % Versions.scalaJSReact,
      "com.github.japgolly.scalajs-react" %%% "extra" % Versions.scalaJSReact,
      "com.github.japgolly.scalacss" %%% "core" % Versions.scalaCss,
      "com.github.japgolly.scalacss" %%% "ext-react" % Versions.scalaCss
    ),
    stFlavour := Flavour.ScalajsReact,
    Compile / npmDependencies ++= Seq(
      "webextension-polyfill" -> Versions.webextensionPolyfill,
      "@types/webextension-polyfill" -> Versions.webextensionPolyfillTypes,
      "react" -> Versions.reactJS,
      "@types/react" -> Versions.reactJSTypes,
      "react-dom" -> Versions.reactJS,
      "@types/react-dom" -> Versions.reactJSDOMTypes,
    ),
    scalaJSUseMainModuleInitializer := true,
    // Map all assets produced by the ScalaJs Bundler to their location within the archive
    Universal / mappings ++= (Compile / fullOptJS / webpack).value.map { f =>
      f.data -> s"assets/${f.data.getName()}"
    },
    topLevelDirectory := None,
    makeDeploymentSettings(Universal, Universal / packageBin, "zip"),
    useYarn := true,

    TaskKey[Unit]("checkArchive") := {

      val expected : List[String] = List(
        "icon.png",
        "index.html",
        "manifest.json",
        "images/graphic.png",
        "assets/plugin-opt-bundle.js",
      )

      val archive = (Universal / packageBin).value
      assert(archive.exists() && archive.isFile())

      val entries = new ZipHelper(archive).entries

      assert(expected.size == entries.size, s"expected.size: ${expected.size} != entries.size: ${entries.size}")
      assert(expected.forall(e => entries.contains(e)), s"expected: ${expected} != entries: ${entries}")
    },
  )
