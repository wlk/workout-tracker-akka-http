package com.wlangiewicz.workouttracker

import com.wlangiewicz.workouttracker.dao.{WorkoutDao, UserDao}
import com.wlangiewicz.workouttracker.domain.UserId
import com.wlangiewicz.workouttracker.services._
import org.scalatest.{FlatSpec, Matchers}

trait WorkoutTrackerSpec extends FlatSpec with Matchers {
  val userDao = new UserDao
  val workoutDao = new WorkoutDao
  val reportingService = new ReportingService

  val testingUser = userDao.findByUserId(UserId(1)).get
}
