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

package uk.gov.hmrc.enrolmentsorchestrator.config

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.enrolmentsorchestrator.models.BasicAuthentication
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class AppConfig @Inject() (config: Configuration, servicesConfig: ServicesConfig) {

  val auditingEnabled: Boolean = config.get[Boolean]("auditing.enabled")
  val graphiteHost: String = config.get[String]("microservice.metrics.graphite.host")

  val authBaseUrl: String = servicesConfig.baseUrl("auth")
  val enrolmentsStoreBaseUrl: String = servicesConfig.baseUrl("enrolment-store-proxy")
  val taxEnrolmentsBaseUrl: String = servicesConfig.baseUrl("tax-enrolments")
  val agentStatusChangeBaseUrl: String = servicesConfig.baseUrl("agent-status-change")
  val agentClientRelationshipsBaseUrl: String = servicesConfig.baseUrl("agent-client-relationships")

  def expectedAuth: BasicAuthentication = {
    val username = config.get[String]("basicAuthentication.username")
    val password = config.get[String]("basicAuthentication.password")

    BasicAuthentication(username, password)
  }

}
