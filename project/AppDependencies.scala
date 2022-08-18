import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"  % "7.0.0"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %%  "bootstrap-test-play-28"     % "7.0.0"              % "test, it",
    "com.typesafe.play"       %%  "play-test"                  % current               % "test, it",
    "org.mockito"             %% "mockito-scala-scalatest"     % "1.17.12"              % "test, it",
    "org.scalatestplus.play"  %%  "scalatestplus-play"         % "5.1.0"               % "test, it",
    "org.pegdown"             %   "pegdown"                    % "1.6.0"               % "test, it",
    "com.vladsch.flexmark"    % "flexmark-all"                 % "0.36.8"              % "test, it",
    "com.github.tomakehurst"  %   "wiremock-jre8"              % "2.32.0"              % "test, it",
    "uk.gov.hmrc"             %%  "service-integration-test"   % "1.3.0-play-28"       % "test, it",
    "com.fasterxml.jackson.module" %% "jackson-module-scala"   % "2.13.1"              % "test, it"
  )

}
