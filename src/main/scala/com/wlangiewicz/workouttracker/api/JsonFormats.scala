package com.wlangiewicz.workouttracker.api

import com.wlangiewicz.workouttracker.domain._
import spray.json._

trait JsonFormats extends DefaultJsonProtocol {
  implicit val SignUpUserRequestFormat = jsonFormat2(SignUpUserRequest.apply)
  implicit val UnsuccessfulUserSignUpResponseFormat = jsonFormat0(UnsuccessfulUserSignUpResponse)
  implicit val SuccessfulUserSignUpResponseFormat = jsonFormat1(SuccessfulUserSignUpResponse)
}
