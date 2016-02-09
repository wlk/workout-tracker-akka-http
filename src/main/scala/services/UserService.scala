package services

import api._
import domain.{SignUpUserRequest, User, UserId}

class UserService {
  val users = scala.collection.mutable.Set(
    User(UserId(1), "user", "password")
  )

  def find(login: String, password: String): Option[User] = {
    users.find(u => u.login == login && u.password == password)
  }

  def findByUserId(userId: UserId): Option[User] = {
    users.find(u => u.userId == userId)
  }

  def signUp(signUpRequest: SignUpUserRequest): UserSignUpResponse = {
    if (loginExists(signUpRequest.login)) {
      UnsuccessfulUserSignUpResponse()
    } else {
      val newUserId = nextUserId
      users.add(User(newUserId, signUpRequest.login, signUpRequest.password))
      SuccessfulUserSignUpResponse()
    }

  }

  private def nextUserId = {
    val currentMaxUserId = users.map(_.userId.value).max
    UserId(currentMaxUserId + 1)
  }

  def loginExists(login: String) = users.exists(_.login == login)
}
