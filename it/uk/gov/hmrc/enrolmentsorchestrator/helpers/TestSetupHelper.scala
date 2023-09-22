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

import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, SuiteMixin}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.test.WsTestClient

import java.nio.charset.StandardCharsets.UTF_8
import java.util.Base64
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import UrlHelper.-/

trait TestSetupHelper extends AnyWordSpec
  with Matchers
  with WsTestClient
  with BeforeAndAfterEach
  with SuiteMixin
  with BeforeAndAfterAll
  with ScalaFutures
  with IntegrationPatience
  with GuiceOneServerPerSuite
  with EnrolmentStoreWireMockSetup
  with AgentStatusChangeWireMockSetup
  with AgentClientAuthorisationWireMockSetup {

  val es9DeleteBaseUrl = "/enrolments-orchestrator/agents"
  val testARN = "AARN123"

  implicit val defaultTimeout: FiniteDuration = 3.minutes

  def await[A](future: Future[A])(implicit timeout: Duration): A = Await.result(future, timeout)

  def basicAuth(string: String): String = Base64.getEncoder.encodeToString(string.getBytes(UTF_8))

  def resource(path: String): String =
    s"http://localhost:$port/${-/(path)}"

}

object UrlHelper {
  def -/(uri: String) =
    if (uri.startsWith("/")) uri.drop(1) else uri
}
