package com.wlangiewicz.workouttracker.domain

case class SuccessfulUserSignUpResponse(apiKey: String)

case class UnsuccessfulUserSignUpResponse()

case class SuccessfulRecordWorkoutResponse(workoutId: WorkoutId)

case class UnsuccessfulRecordWorkoutResponse()
