package com.wlangiewicz.workouttracker.services

import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.domain._
import org.scalatest.BeforeAndAfter

class UserServiceSpec extends WorkoutTrackerSpec with BeforeAndAfter {

  before {
    cleanupUserDao()
  }

  "UserService" should "signUp user when it doesn't exist" in {
    val response = userService.signUp(SignUpUserRequest("newUser", "password")).right.get
    response shouldBe an[SuccessfulUserSignUpResponse]
    val user = userDao.find("newUser", "password").get
    user.userId shouldBe UserId(3)
    user.login shouldBe "newUser"
    user.apiKey.apiKey.length shouldBe 16
  }

  it should "not signUp user when it already exists" in {
    userService.signUp(SignUpUserRequest("newUser", "password")).right.get shouldBe an[SuccessfulUserSignUpResponse]
    userService.signUp(SignUpUserRequest("newUser", "password")).left.get shouldBe an[UnsuccessfulUserSignUpResponse]
    val user = userDao.find("newUser", "password").get
    user.userId shouldBe UserId(3)
    user.login shouldBe "newUser"
  }

  it should "find users by API key" in {
    userService.signUp(SignUpUserRequest("newUser", "password"))
    val userByLoginAndPassword = userDao.find("newUser", "password").get
    val apiKey = userByLoginAndPassword.apiKey
    val userByApiKey = userDao.findByApiKey(apiKey).get
    userByLoginAndPassword shouldBe userByApiKey
  }
}
