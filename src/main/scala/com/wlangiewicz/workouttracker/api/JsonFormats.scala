package com.wlangiewicz.workouttracker.api

import com.wlangiewicz.workouttracker.domain._
import spray.json._

trait JsonFormats extends DefaultJsonProtocol {
  implicit val ApiKeyFormat = jsonFormat1(ApiKey)
  implicit val SignUpUserRequestFormat = jsonFormat2(SignUpUserRequest)
  implicit val LoginUserRequestFormat = jsonFormat2(LoginUserRequest)
  implicit val UnsuccessfulUserSignUpResponseFormat = jsonFormat0(UnsuccessfulUserSignUpResponse)
  implicit val SuccessfulUserSignUpResponseFormat = jsonFormat1(SuccessfulUserSignUpResponse)
  implicit val SuccessfulUserLoginResponseFormat = jsonFormat1(SuccessfulUserLoginResponse)
  implicit val UnsuccessfulUserLoginResponseFormat = jsonFormat0(UnsuccessfulUserLoginResponse)

}
