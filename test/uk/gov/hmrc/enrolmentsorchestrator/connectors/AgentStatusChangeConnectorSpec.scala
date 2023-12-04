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

package uk.gov.hmrc.enrolmentsorchestrator.connectors

import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.contains
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.enrolmentsorchestrator.UnitSpec
import uk.gov.hmrc.enrolmentsorchestrator.config.AppConfig
import uk.gov.hmrc.http.HttpResponse
import uk.gov.hmrc.http.HttpClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AgentStatusChangeConnectorSpec extends UnitSpec with MockitoSugar {

  val mockHttpClient: HttpClient = mock[HttpClient]
  val mockAppConfig: AppConfig = mock[AppConfig]

  val connector = new AgentStatusChangeConnector(mockHttpClient, mockAppConfig)

  "AgentStatusChangeConnector" should {
    "connect to AgentStatusChange and return HttpResponse" in {
      val testHttpResponse = HttpResponse(200, "")
      val arn = "arn"
      when(mockHttpClient.DELETE[HttpResponse](contains(arn), any)(any, any, any)).thenReturn(Future.successful(testHttpResponse))
      await(connector.agentStatusChangeToTerminate(arn)) shouldBe testHttpResponse
    }
  }

}
