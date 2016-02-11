package com.wlangiewicz.workouttracker.services

import com.wlangiewicz.workouttracker.dao.UserDao
import com.wlangiewicz.workouttracker.domain._

import scala.util.Random

class UserService(userDao: UserDao) {
  def signUp(signUpRequest: SignUpUserRequest): Either[UnsuccessfulUserSignUpResponse, SuccessfulUserSignUpResponse] = {
    if (userDao.loginExists(signUpRequest.login)) {
      Left(UnsuccessfulUserSignUpResponse())
    } else {
      val newUserId = userDao.nextUserId
      val apiKey = ApiKey(randomApiKey)
      userDao.add(User(newUserId, signUpRequest.login, signUpRequest.password, apiKey))
      Right(SuccessfulUserSignUpResponse(apiKey))
    }
  }

  def randomApiKey = Random.alphanumeric.take(16).mkString
}
