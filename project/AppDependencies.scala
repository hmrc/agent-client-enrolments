import play.core.PlayVersion.current
import sbt._

object AppDependencies {

  val bootstrapVersion = "9.0.0"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"             %% "bootstrap-backend-play-30"  % bootstrapVersion
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"                     %%  "bootstrap-test-play-30"     % bootstrapVersion     % Test,
    "org.mockito"                     %%  "mockito-scala-scalatest"    % "1.17.37"            % Test
  )

}
