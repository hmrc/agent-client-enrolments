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
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.stubbing.StubMapping

trait AgentClientAuthorisationWireMockSetup {

  val agentClientAuthorisationHost: String = "localhost"
  val agentClientAuthorisationPort: Int = 9432
  val wireMockAgentClientAuthorisationServer = new WireMockServer(wireMockConfig().port(agentClientAuthorisationPort))

  def startDeleteRelationship: StubMapping = {
    WireMock.configureFor(agentClientAuthorisationHost, agentClientAuthorisationPort)
    wireMockAgentClientAuthorisationServer.start()

    stubFor(
      put(urlEqualTo("/agent-client-authorisation/invitations/set-relationship-ended"))
        .withRequestBody(equalToJson(
          """
            |{
            |    "arn": "ZARN1234567",
            |    "clientId": "123456789",
            |    "service": "HMRC-MTD-VAT"
            |}
            |""".stripMargin))
        .willReturn(aResponse().withStatus(200))
    )
  }

}
