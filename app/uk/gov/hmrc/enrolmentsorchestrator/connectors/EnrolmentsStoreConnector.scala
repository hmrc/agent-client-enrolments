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

import play.api.Logging
import play.api.http.HttpVerbs

import javax.inject.{Inject, Singleton}
import uk.gov.hmrc.enrolmentsorchestrator.config.AppConfig
import uk.gov.hmrc.enrolmentsorchestrator.connectors.ConnectorUtils.hashString
import uk.gov.hmrc.enrolmentsorchestrator.models.DelegatedGroupIds
import uk.gov.hmrc.http.{HeaderCarrier, HttpClient, HttpReads, HttpResponse}

import scala.concurrent.{ExecutionContext, Future}
import uk.gov.hmrc.http.HttpReads.Implicits._

import scala.util.{Failure, Success}

@Singleton()
class EnrolmentsStoreConnector @Inject() (httpClient: HttpClient, appConfig: AppConfig)(implicit ec: ExecutionContext) extends Logging {

  lazy val enrolmentsStoreBaseUrl: String = appConfig.enrolmentsStoreBaseUrl

  // Query Groups who have an allocated Enrolment
  def es1GetPrincipalGroups(enrolmentKey: String)(implicit hc: HeaderCarrier): Future[HttpResponse] = {
    val url = s"$enrolmentsStoreBaseUrl/enrolment-store-proxy/enrolment-store/enrolments/$enrolmentKey/groups?type=principal"
    httpClient.GET[HttpResponse](url)
  }

  def assignEnrolment(credId: String, enrolmentKey: String)(implicit hc: HeaderCarrier): Future[Unit] = {
    val url = s"$enrolmentsStoreBaseUrl/enrolment-store-proxy/enrolment-store/users/$credId/enrolments/$enrolmentKey"
    httpClient.POSTEmpty[HttpResponse](url).map(_ => ())
  }

  def es1GetDelegatedGroups(enrolmentKey: String)(implicit hc: HeaderCarrier, rds: HttpReads[DelegatedGroupIds]): Future[DelegatedGroupIds] = {
    val url = s"$enrolmentsStoreBaseUrl/enrolment-store-proxy/enrolment-store/enrolments/$enrolmentKey/groups?type=delegated"
    httpClient
      .GET[HttpResponse](url)
      .andThen {
        case Success(response) => logger.info(s"[GG-5898] GET /enrolments/${hashString(enrolmentKey)}/groups returned ${response.status}")
        case Failure(_)        => logger.error(s"[GG-5898] GET /enrolments/${hashString(enrolmentKey)}/groups failed")
      }
      .map(rds.read(HttpVerbs.POST, url, _))
  }

  def es9DeallocateDelegatedEnrolment(groupId: String, enrolmentKey: String)(implicit hc: HeaderCarrier): Future[HttpResponse] = {
    val url = s"$enrolmentsStoreBaseUrl/enrolment-store-proxy/enrolment-store/groups/$groupId/enrolments/$enrolmentKey?keepAgentAllocations=false"
    httpClient
      .DELETE[HttpResponse](url)
      .andThen {
        case Success(response) => logger.info(s"[GG-5898] DELETE /groups/:groupId/enrolments/${hashString(enrolmentKey)} returned ${response.status}")
        case Failure(_)        => logger.error(s"[GG-5898] DELETE /groups/:groupId/enrolments/${hashString(enrolmentKey)} failed")
      }
  }

}
