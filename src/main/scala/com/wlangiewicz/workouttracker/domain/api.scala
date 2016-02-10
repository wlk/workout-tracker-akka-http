package com.wlangiewicz.workouttracker.domain

case class SuccessfulUserSignUpResponse(apiKey: ApiKey)

case class UnsuccessfulUserSignUpResponse()

case class SuccessfulUserLoginResponse(apiKey: ApiKey)

case class UnsuccessfulUserLoginResponse()

case class SuccessfulRecordWorkoutResponse(workoutId: WorkoutId)

case class UnsuccessfulRecordWorkoutResponse()
