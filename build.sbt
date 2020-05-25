import scala.io.Source

name := "azure-driver"

organization := "com.headstorm"

description := "Azure Scala Drivers"

version := "0.2.0"

scalaVersion := "2.13.1"

homepage := Some(url("http://www.headstorm.com"))

developers := List(
  Developer(id="CharlesAHunt", name="Charles A Hunt",
    email="chunt@headstorm.com", url=url("http://www.headstorm.com")),
  Developer(id="NickLundin", name="Nick Lundin",
    email="nlundin@headstorm.com", url=url("http://www.headstorm.com"))
)


parallelExecution in Test := false

shellPrompt := { state => scala.Console.YELLOW + "[" + scala.Console.CYAN + Project.extract(state).currentProject.id + scala.Console.YELLOW + "]" + scala.Console.RED + " $ " + scala.Console.RESET }

enablePlugins(JavaAppPackaging)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

lazy val wartremoverSettings = Seq(
  wartremoverWarnings in (Compile, compile) ++= Warts.allBut(Wart.ImplicitParameter, Wart.Any,
    Wart.NonUnitStatements, Wart.Nothing, Wart.DefaultArguments)
)

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true
  )

lazy val settings =
  commonSettings ++
    wartremoverSettings ++
    scalafmtSettings

lazy val storage = (project in file("storage"))
  .settings(
    name := "storage",
    settings,
    libraryDependencies ++= commonDependencies
  )

val circeVersion                 = "0.13.0"
val odinVersion                  = "0.7.0"

val commonDependencies = Seq(
  "com.beachape" %% "enumeratum" % "1.6.0",
  "com.beachape" %% "enumeratum-circe" % "1.6.0",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-literal" % circeVersion,

  "com.azure" % "azure-storage-blob" % "12.6.0",

  "com.softwaremill.sttp.client" %% "core" % "2.1.0-RC1",
  "com.softwaremill.sttp.client" %% "http4s-backend" % "2.1.0-RC1",
  "com.softwaremill.sttp.client" %% "circe" % "2.1.0-RC1",

  "co.fs2" %% "fs2-core" % "2.3.0",

  "org.typelevel" %% "cats-core" % "2.1.1",
  "org.typelevel" %% "cats-effect" % "2.1.2",

  "com.github.pureconfig" %% "pureconfig" % "0.12.2",
  "com.github.valskalla" %% "odin-core" % odinVersion,
  "com.github.valskalla" %% "odin-slf4j" % odinVersion,
  "com.github.valskalla" %% "odin-json" % odinVersion,
  "com.github.valskalla" %% "odin-extras" % odinVersion,

  "org.scalatest" %% "scalatest" % "3.1.1" % Test,
  "org.typelevel" %% "cats-laws" % "2.0.0" % Test,
  "org.typelevel" %% "discipline-core" % "1.0.0" % Test,
  "org.typelevel" %% "discipline-scalatest" % "1.0.1" % Test
)

import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._

promptTheme := com.scalapenos.sbt.prompt.PromptThemes.ScalapenosTheme

val compilerOptions = Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
  "-Ybackend-parallelism", "8", // Enable paralellisation — change to desired number!
  "-Ycache-plugin-class-loader:last-modified", // Enables caching of classloaders for compiler plugins
  "-Ycache-macro-class-loader:last-modified", // and macro definitions. This can lead to performance improvements.
)