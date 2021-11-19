import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-28"  % "5.16.0"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %%  "bootstrap-test-play-28"     % "5.16.0"              % "test, it",
    "com.typesafe.play"       %%  "play-test"                  % current               % "test, it",
    "org.mockito"             %% "mockito-scala-scalatest"     % "1.16.37"             % "test, it",
    "org.scalatestplus.play"  %%  "scalatestplus-play"         % "3.1.3"               % "test, it",
    "org.pegdown"             %   "pegdown"                    % "1.6.0"               % "test, it",
    "com.vladsch.flexmark"    % "flexmark-all"                 % "0.35.10"             % "test, it",
    "com.github.tomakehurst"  %   "wiremock-jre8"              % "2.31.0"              % "test, it",
    "uk.gov.hmrc"             %%  "service-integration-test"   % "1.1.0-play-28"       % "test, it",
    "com.fasterxml.jackson.module" %% "jackson-module-scala"   % "2.12.3"              % "test, it"
  )

}
