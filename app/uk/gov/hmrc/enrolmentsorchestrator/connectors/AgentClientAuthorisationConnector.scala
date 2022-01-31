/*
 * Copyright 2022 HM Revenue & Customs
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
import uk.gov.hmrc.enrolmentsorchestrator.config.AppConfig
import uk.gov.hmrc.enrolmentsorchestrator.connectors.ConnectorUtils.hashString
import uk.gov.hmrc.http.{HeaderCarrier, HttpClient, HttpResponse}
import uk.gov.hmrc.http.HttpReads.Implicits.readRaw

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton()
class AgentClientAuthorisationConnector @Inject() (httpClient: HttpClient, appConfig: AppConfig)(implicit ec: ExecutionContext) extends Logging {
  lazy val baseUrl: String = appConfig.agentClientAuthorisationBaseUrl

  def deleteRelationship(arn: String, service: String, clientIdType: String, clientId: String)(implicit hc: HeaderCarrier): Future[HttpResponse] = {
    val url = s"$baseUrl/agent-client-authorisation/invitations/set-relationship-ended"
    httpClient.PUT(url, Map("arn" -> arn, "clientId" -> clientId, "service" -> service))
      .andThen {
        case Success(response) => logger.info(s"[GG-5898] PUT agent-client-authorisation/invitations/set-relationship-ended for ARN ${hashString(arn)}, clientId ${hashString(clientId)}, service $service returned ${response.status}")
        case Failure(_)        => logger.error(s"[GG-5898] PUT agent-client-authorisation/invitations/set-relationship-ended for ARN ${hashString(arn)}, clientId ${hashString(clientId)}, service $service failed")
      }
  }

}
