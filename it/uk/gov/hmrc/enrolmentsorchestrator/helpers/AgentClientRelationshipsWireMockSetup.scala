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

package uk.gov.hmrc.enrolmentsorchestrator.helpers

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.stubbing.StubMapping

trait AgentClientRelationshipsWireMockSetup {

  val agentClientRelationshipsHost: String = "localhost"
  val agentClientRelationshipsPort: Int = 9434
  val wireMockAgentClientRelationshipsServer = new WireMockServer(wireMockConfig().port(agentClientRelationshipsPort))

  def startDeleteRelationship: StubMapping = {
    WireMock.configureFor(agentClientRelationshipsHost, agentClientRelationshipsPort)
    wireMockAgentClientRelationshipsServer.start()

    stubFor(
      delete(urlEqualTo("/agent/ZARN1234567/service/HMRC-MTD-VAT/client/VRN/123456789"))
        .willReturn(aResponse().withStatus(204))
    )
  }

}
