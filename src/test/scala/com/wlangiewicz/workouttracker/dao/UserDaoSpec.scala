package com.wlangiewicz.workouttracker.dao

import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.domain._
import org.scalatest.BeforeAndAfter

class UserDaoSpec extends WorkoutTrackerSpec with BeforeAndAfter {

  before {
    def cleanupUserDao() = {
      userDao.users.clear()
      userDao.users.add(User(UserId(1), "user", "password", ApiKey(userDao.randomApiKey)))
    }

    cleanupUserDao()
  }

  "UserDao" should "not find users by login and password pair if it's incorrect" in {
    userDao.find("test", "incorrect") shouldBe None
  }

  it should "find users if login and password are correct" in {
    val user = userDao.find("user", "password").get
    user.login shouldBe "user"
    user.userId shouldBe UserId(1)
  }

  it should "signUp user when it doesn't exist" in {
    val response = userDao.signUp(SignUpUserRequest("newUser", "password")).right.get
    response shouldBe an[SuccessfulUserSignUpResponse]
    val user = userDao.find("newUser", "password").get
    user.userId shouldBe UserId(2)
    user.login shouldBe "newUser"
    user.apiKey.apiKey.length shouldBe 16
  }

  it should "not signUp user when it already exists" in {
    userDao.signUp(SignUpUserRequest("newUser", "password")).right.get shouldBe an[SuccessfulUserSignUpResponse]
    userDao.signUp(SignUpUserRequest("newUser", "password")).left.get shouldBe an[UnsuccessfulUserSignUpResponse]
    val user = userDao.find("newUser", "password").get
    user.userId shouldBe UserId(2)
    user.login shouldBe "newUser"
  }

  it should "find users by API key" in {
    userDao.signUp(SignUpUserRequest("newUser", "password"))
    val userByLoginAndPassword = userDao.find("newUser", "password").get
    val apiKey = userByLoginAndPassword.apiKey
    val userByApiKey = userDao.findByApiKey(apiKey).get
    userByLoginAndPassword shouldBe userByApiKey
  }
}
