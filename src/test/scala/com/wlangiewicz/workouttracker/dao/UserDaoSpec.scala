package com.wlangiewicz.workouttracker.dao

import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.domain._
import org.scalatest.BeforeAndAfter

class UserDaoSpec extends WorkoutTrackerSpec with BeforeAndAfter {

  before {
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
}
