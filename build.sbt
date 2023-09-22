import scoverage.ScoverageKeys._
import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings


val appName = "agent-client-enrolments"

coverageExcludedPackages :=
  """<empty>;
    |Reverse.*;
    |conf.*;
    |.*BuildInfo.*;
    |.*Routes.*;
    |.*RoutesPrefix.*;""".stripMargin
coverageMinimumStmtTotal := 80
coverageFailOnMinimum := true
coverageHighlighting := true
Test / parallelExecution := false


lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(PlayKeys.playDefaultPort := 9456)
  .settings(
    majorVersion := 0,
    libraryDependencies ++= AppDependencies.compile ++ AppDependencies.test
  )
  .settings(scalaVersion := "2.13.8")
  .settings(publishingSettings: _*)
  .configs(IntegrationTest)
  .settings(integrationTestSettings(): _*)
  .settings(ScalariformSettings())
  .settings(ScoverageSettings())
  .settings(SilencerSettings())
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(integrationTestSettings())
  .settings(resolvers += Resolver.jcenterRepo)
  .settings(
    publishArtifact in(Compile, packageDoc) := false,
    sources in(Compile, doc) := Seq.empty
  )
