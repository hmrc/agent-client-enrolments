/*
 * Copyright 2020 HM Revenue & Customs
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

import org.mockito.ArgumentMatchers.{any, contains}
import org.mockito.Mockito.when
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.enrolmentsorchestrator.UnitSpec
import uk.gov.hmrc.enrolmentsorchestrator.config.AppConfig
import uk.gov.hmrc.http.{HeaderCarrier, HttpResponse}
import uk.gov.hmrc.play.bootstrap.http.HttpClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class EnrolmentsStoreConnectorSpec extends UnitSpec with MockitoSugar with ScalaFutures {

  val mockHttpClient: HttpClient = mock[HttpClient]
  val mockAppConfig: AppConfig = mock[AppConfig]

  implicit val headerCarrier: HeaderCarrier = HeaderCarrier()

  val connector = new EnrolmentsStoreConnector(mockHttpClient, mockAppConfig)

  "EnrolmentsStoreConnector" should {
    "connect to EnrolmentsStore and return HttpResponse" in {

      val testHttpResponse = HttpResponse(200)
      val enrolmentKey = "enrolmentKey"

      when(mockHttpClient.GET[HttpResponse](contains(enrolmentKey))(any(), any(), any())).thenReturn(Future.successful(testHttpResponse))

      connector.es1GetPrincipalGroups(enrolmentKey).futureValue shouldBe testHttpResponse

    }
  }
}