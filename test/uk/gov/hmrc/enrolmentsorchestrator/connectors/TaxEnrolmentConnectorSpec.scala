/*
 * Copyright 2021 HM Revenue & Customs
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

import org.mockito.scalatest.MockitoSugar
import uk.gov.hmrc.enrolmentsorchestrator.UnitSpec
import uk.gov.hmrc.enrolmentsorchestrator.config.AppConfig
import uk.gov.hmrc.http.{HttpClient, HttpResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TaxEnrolmentConnectorSpec extends UnitSpec with MockitoSugar {

  val mockHttpClient: HttpClient = mock[HttpClient]
  val mockAppConfig: AppConfig = mock[AppConfig]

  val connector = new TaxEnrolmentConnector(mockHttpClient, mockAppConfig)

  "EnrolmentsStoreConnector" should {
    "connect to EnrolmentsStore and return HttpResponse" in {
      val testHttpResponse = HttpResponse(200, "")
      val enrolmentKey = "enrolmentKey"
      val groupId = "groupId"
      when(mockHttpClient.DELETE[HttpResponse](contains(groupId), any)(any, any, any)).thenReturn(Future.successful(testHttpResponse))
      await(connector.es9DeallocateGroup(groupId, enrolmentKey)) shouldBe testHttpResponse
    }
  }

}
