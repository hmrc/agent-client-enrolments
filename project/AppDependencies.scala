import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-30"  % "8.3.0"
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"                     %%  "bootstrap-test-play-30"     % "8.3.0"              % Test,
    "org.mockito"                     %%  "mockito-scala-scalatest"    % "1.17.30"            % Test
  )

}
