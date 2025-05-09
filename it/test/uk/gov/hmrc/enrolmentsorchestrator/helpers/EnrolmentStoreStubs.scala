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

import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.stubbing.StubMapping

trait EnrolmentStoreStubs {

  def startESProxyWireMockServerFullHappyPath: StubMapping = {
    stubFor(
      get(urlEqualTo("/enrolment-store-proxy/enrolment-store/enrolments/HMRC-AS-AGENT~AgentReferenceNumber~AARN123/groups?type=principal"))
        .willReturn(
          aResponse()
            .withStatus(200)
            .withBody("""{"principalGroupIds":["90ccf333-65d2-4bf2-a008-01dfca702161"]}""")
        )
    )

    stubFor(
      get(urlEqualTo("/enrolment-store-proxy/enrolment-store/enrolments/HMRC-AS-AGENT~AgentReferenceNumber~AARN123/users"))
        .willReturn(aResponse().withStatus(204))
    )

    stubFor(
      delete(
        urlEqualTo(
          "/tax-enrolments/groups/90ccf333-65d2-4bf2-a008-01dfca702161/enrolments/HMRC-AS-AGENT~AgentReferenceNumber~AARN123"
        )
      )
        .willReturn(aResponse().withStatus(204))
    )
  }

  def startESProxyWireMockServerReturn204: StubMapping = {
    stubFor(
      get(urlEqualTo("/enrolment-store-proxy/enrolment-store/enrolments/HMRC-AS-AGENT~AgentReferenceNumber~AARN123/groups?type=principal"))
        .willReturn(aResponse().withStatus(204))
    )
  }

  def startDeleteEnrolmentsForGroup: StubMapping = {
    stubFor(
      get(urlEqualTo("/enrolment-store-proxy/enrolment-store/enrolments/HMRC-MTD-VAT~VRN~123456789/groups?type=delegated"))
        .willReturn(
          aResponse()
            .withStatus(200)
            .withBody("""{"delegatedGroupIds":["90ccf333-65d2-4bf2-a008-01dfca702162"]}""")
        )
    )

    stubFor(
      delete(
        urlEqualTo(
          "/enrolment-store-proxy/enrolment-store/groups/90ccf333-65d2-4bf2-a008-01dfca702162/enrolments/HMRC-MTD-VAT~VRN~123456789?keepAgentAllocations=false"
        )
      )
        .willReturn(aResponse().withStatus(204))
    )
  }
}
