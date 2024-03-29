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

package uk.gov.hmrc.enrolmentsorchestrator

import org.scalatest.OptionValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.mvc.{Request, Result}
import play.api.test.FakeRequest
import uk.gov.hmrc.http.HeaderCarrier

import java.nio.charset.StandardCharsets.UTF_8
import java.util.Base64
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait UnitSpec extends AnyWordSpec with Matchers with OptionValues {

  implicit val headerCarrier: HeaderCarrier = HeaderCarrier()
  implicit val request: Request[_] = FakeRequest()
  implicit val defaultTimeout: FiniteDuration = 5.seconds

  def await[A](future: Future[A])(implicit timeout: Duration): A = Await.result(future, timeout)

  def status(result: Future[Result]): Int = await(result).header.status

  def encodeToBase64(string: String): String = Base64.getEncoder.encodeToString(string.getBytes(UTF_8))

}
