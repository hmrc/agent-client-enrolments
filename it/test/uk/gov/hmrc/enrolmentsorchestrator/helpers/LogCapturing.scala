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

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.{Level, Logger}
import ch.qos.logback.core.read.ListAppender
import play.api.LoggerLike

import scala.jdk.CollectionConverters.CollectionHasAsScala

trait LogCapturing {

  def withCaptureOfLoggingFrom(loggerLikes: LoggerLike*)(body: (=> List[ILoggingEvent]) => Unit): Unit = {
    val appender = new ListAppender[ILoggingEvent]()
    appender.start()

    for (loggerLike <- loggerLikes) {
      val logger = loggerLike.logger.asInstanceOf[Logger]
      logger.addAppender(appender)
      logger.setLevel(Level.DEBUG)
      logger.setAdditive(true)
    }

    body(appender.list.asScala.toList)
  }
}
