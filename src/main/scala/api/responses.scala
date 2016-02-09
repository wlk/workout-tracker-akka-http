package api

sealed trait UserSignUpResponse

case class SuccessfulUserSignUpResponse() extends UserSignUpResponse

case class UnsuccessfulUserSignUpResponse() extends UserSignUpResponse



sealed trait RecordWorkoutResponse

case class SuccessfulRecordWorkoutResponse() extends RecordWorkoutResponse

case class UnsuccessfulRecordWorkoutResponse() extends RecordWorkoutResponse