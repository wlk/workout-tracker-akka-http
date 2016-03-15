package com.wlangiewicz.workouttracker.api

import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.domain._
import spray.json._

trait JsonFormats extends DefaultJsonProtocol {

  implicit val DateTimeFormat = new JsonFormat[DateTime] {
    override def write(obj: DateTime): JsValue = JsNumber(obj.getMillis)

    override def read(json: JsValue): DateTime = json match {
      case JsNumber(millis) => new DateTime(millis.toLong)
      case _                => throw new ApiException("incorrect json date format")
    }
  }

  implicit val ApiKeyFormat = jsonFormat1(ApiKey)
  implicit val SignUpUserRequestFormat = jsonFormat2(SignUpUserRequest)
  implicit val LoginUserRequestFormat = jsonFormat2(LoginUserRequest)
  implicit val UnsuccessfulUserSignUpResponseFormat = jsonFormat0(UnsuccessfulUserSignUpResponse)
  implicit val SuccessfulUserSignUpResponseFormat = jsonFormat1(SuccessfulUserSignUpResponse)
  implicit val SuccessfulUserLoginResponseFormat = jsonFormat1(SuccessfulUserLoginResponse)
  implicit val UnsuccessfulUserLoginResponseFormat = jsonFormat0(UnsuccessfulUserLoginResponse)

  implicit val UserIdFormat = jsonFormat1(UserId)
  implicit val WorkoutIdFormat = jsonFormat1(WorkoutId)

  implicit val RecordWorkoutRequestFormat = jsonFormat4(RecordWorkoutRequest)
  implicit val SuccessfulRecordWorkoutResponseFormat = jsonFormat1(SuccessfulRecordWorkoutResponse)
  implicit val UnsuccessfulRecordWorkoutResponseFormat = jsonFormat0(UnsuccessfulRecordWorkoutResponse)

  implicit val WorkoutFormat = jsonFormat6(Workout)
  implicit val ReportFormat = jsonFormat5(Report)
  implicit val DeleteWorkoutRequestFormat = jsonFormat1(DeleteWorkoutRequest)
  implicit val UpdateWorkoutRequestFormat = jsonFormat1(UpdateWorkoutRequest)

}
