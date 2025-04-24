/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.enrolmentsorchestrator.helpers

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, SuiteMixin}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.WsTestClient
import uk.gov.hmrc.audit.WSClient

import java.nio.charset.StandardCharsets.UTF_8
import java.util.Base64
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait TestSetupHelper
    extends AnyWordSpec
    with Matchers
    with BeforeAndAfterEach
    with SuiteMixin
    with BeforeAndAfterAll
    with ScalaFutures
    with IntegrationPatience
    with GuiceOneServerPerSuite {

  val es9DeleteBaseUrl = "/enrolments-orchestrator/agents"
  val testARN = "AARN123"

  val wiremockPort: Int    = 11111
  val wiremockHost: String = "localhost"

  override def beforeAll(): Unit = {
    startWiremock()
    super.beforeAll()
  }

  override def afterAll(): Unit = {
    stopWiremock()
    super.afterAll()
  }

  override def beforeEach(): Unit = {
    resetWiremock()
    super.beforeEach()
  }

  def config: Map[String, String] = Map(
    "metrics.enabled" -> "false",
    "auditing.enabled" -> "false",
    "microservice.metrics.graphite.enabled" -> "false",
    "microservice.services.auth.port" -> wiremockPort.toString,
    "microservice.services.agent-client-relationships.port" -> wiremockPort.toString,
    "microservice.services.agent-status-change.port" -> wiremockPort.toString,
    "microservice.services.enrolment-store-proxy.port" -> wiremockPort.toString,
    "microservice.services.tax-enrolments.port" -> wiremockPort.toString
  )

  override lazy val app: Application = new GuiceApplicationBuilder()
    .configure(config)
    .build()

  val wsClient: WSClient = app.injector.instanceOf[WSClient]

  implicit val defaultTimeout: FiniteDuration = 3.minutes

  def await[A](future: Future[A])(implicit timeout: Duration): A = Await.result(future, timeout)

  def basicAuth(string: String): String = Base64.getEncoder.encodeToString(string.getBytes(UTF_8))

  def resource(path: String): String =
    s"http://localhost:$port$path"

  lazy val wmConfig: WireMockConfiguration = wireMockConfig().port(wiremockPort)
  lazy val wireMockServer: WireMockServer  = new WireMockServer(wmConfig)

  def startWiremock(): Unit = {
    wireMockServer.start()
    WireMock.configureFor(wiremockHost, wiremockPort)
  }

  def stopWiremock(): Unit = wireMockServer.stop()

  def resetWiremock(): Unit = WireMock.reset()
}
