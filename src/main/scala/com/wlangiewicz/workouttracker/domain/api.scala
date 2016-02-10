package com.wlangiewicz.workouttracker.domain

sealed trait UserSignUpResponse

case class SuccessfulUserSignUpResponse(apiKey: String) extends UserSignUpResponse

case class UnsuccessfulUserSignUpResponse() extends UserSignUpResponse


sealed trait RecordWorkoutResponse

case class SuccessfulRecordWorkoutResponse() extends RecordWorkoutResponse

case class UnsuccessfulRecordWorkoutResponse() extends RecordWorkoutResponse