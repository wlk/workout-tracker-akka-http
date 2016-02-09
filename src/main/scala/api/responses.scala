package api

sealed trait UserSignUpResponse

case class SuccessfulUserSignUpResponse() extends UserSignUpResponse

case class UnsuccessfulUserSignUpResponse() extends UserSignUpResponse