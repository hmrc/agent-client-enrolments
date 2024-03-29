import sbt.Setting
import scoverage.ScoverageKeys

object CodeCoverageSettings {
  private val excludedPackages: Seq[String] = Seq(
    "<empty>",
    "Reverse.*",
    "conf.*",
    ".*BuildInfo.*",
    ".*Routes.*",
    ".*RoutesPrefix.*"
  )

  val settings: Seq[Setting[_]] = Seq(
    ScoverageKeys.coverageExcludedPackages := excludedPackages.mkString(";"),
    ScoverageKeys.coverageMinimumStmtTotal := 80,
    ScoverageKeys.coverageFailOnMinimum := true,
    ScoverageKeys.coverageHighlighting := true
  )
}