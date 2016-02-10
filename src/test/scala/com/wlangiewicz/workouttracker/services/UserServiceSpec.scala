package com.wlangiewicz.workouttracker.services

import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.domain._
import org.scalatest.BeforeAndAfter

class UserServiceSpec extends WorkoutTrackerSpec with BeforeAndAfter {

  before {
    def cleanupUserService() = {
      userService.users.clear()
      userService.users.add(User(UserId(1), "user", "password", ApiKey(userService.randomApiKey)))
    }

    cleanupUserService()
  }

  "UserService" should "not find users by login and password pair if it's incorrect" in {
    userService.find("test", "incorrect") shouldBe None
  }

  it should "find users if login and password are correct" in {
    val user = userService.find("user", "password").get
    user.login shouldBe "user"
    user.userId shouldBe UserId(1)
  }

  it should "signUp user when it doesn't exist" in {
    val response = userService.signUp(SignUpUserRequest("newUser", "password")).right.get
    response shouldBe an[SuccessfulUserSignUpResponse]
    val user = userService.find("newUser", "password").get
    user.userId shouldBe UserId(2)
    user.login shouldBe "newUser"
    user.apiKey.apiKey.length shouldBe 16
  }

  it should "not signUp user when it already exists" in {
    userService.signUp(SignUpUserRequest("newUser", "password")).right.get shouldBe an[SuccessfulUserSignUpResponse]
    userService.signUp(SignUpUserRequest("newUser", "password")).left.get shouldBe an[UnsuccessfulUserSignUpResponse]
    val user = userService.find("newUser", "password").get
    user.userId shouldBe UserId(2)
    user.login shouldBe "newUser"
  }

  it should "find users by API key" in {
    userService.signUp(SignUpUserRequest("newUser", "password"))
    val userByLoginAndPassword = userService.find("newUser", "password").get
    val apiKey = userByLoginAndPassword.apiKey
    val userByApiKey = userService.findByApiKey(apiKey).get
    userByLoginAndPassword shouldBe userByApiKey
  }
}
