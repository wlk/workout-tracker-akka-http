package com.wlangiewicz.workouttracker

import com.wlangiewicz.workouttracker.dao.{WorkoutDao, UserDao}
import com.wlangiewicz.workouttracker.domain.{User, UserId}
import com.wlangiewicz.workouttracker.services._
import org.scalatest.{FlatSpec, Matchers}

trait WorkoutTrackerSpec extends FlatSpec with Matchers {
  val userDao = new UserDao
  val workoutDao = new WorkoutDao

  val userService = new UserService(userDao)
  val workoutService = new WorkoutService(workoutDao)

  val reportingService = new ReportingService

  val testingUser = userDao.findByUserId(UserId(1)).get
  val testingUserNoWorkouts = userDao.findByUserId(UserId(2)).get

  def cleanupUserDao() = {
    userDao.users.clear()
    userDao.users.add(User(UserId(1), "user", "password", testingUser.apiKey))
    userDao.users.add(User(UserId(2), "userWithoutWorkouts", "password", testingUserNoWorkouts.apiKey))
  }
}
