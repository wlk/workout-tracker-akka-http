package com.wlangiewicz.workouttracker.services

import com.wlangiewicz.workouttracker.domain._

import scala.util.Random

class UserService {
  val users = scala.collection.mutable.Set(
    User(UserId(1), "user", "password", ApiKey("key"))
  )

  def findByApiKey(apiKey: ApiKey) = {
    users.find(u => u.apiKey == apiKey)
  }

  def apiKeyForUser(login: String, password: String): Option[ApiKey] = {
    find(login, password).map(_.apiKey)
  }

  def find(login: String, password: String): Option[User] = {
    users.find(u => u.login == login && u.password == password)
  }

  def findByUserId(userId: UserId): Option[User] = {
    users.find(u => u.userId == userId)
  }

  def signUp(signUpRequest: SignUpUserRequest): Either[UnsuccessfulUserSignUpResponse, SuccessfulUserSignUpResponse] = {
    if (loginExists(signUpRequest.login)) {
      Left(UnsuccessfulUserSignUpResponse())
    } else {
      val newUserId = nextUserId
      val apiKey = ApiKey(randomApiKey)
      users.add(User(newUserId, signUpRequest.login, signUpRequest.password, apiKey))
      Right(SuccessfulUserSignUpResponse(apiKey))
    }

  }

  def randomApiKey = Random.alphanumeric.take(16).mkString

  private def nextUserId = {
    val currentMaxUserId = users.map(_.userId.value).max
    UserId(currentMaxUserId + 1)
  }

  def loginExists(login: String) = users.exists(_.login == login)
}
