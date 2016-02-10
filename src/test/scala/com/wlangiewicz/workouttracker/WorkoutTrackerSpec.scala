package com.wlangiewicz.workouttracker

import com.wlangiewicz.workouttracker.domain.UserId
import com.wlangiewicz.workouttracker.services._
import org.scalatest.{FlatSpec, Matchers}

trait WorkoutTrackerSpec extends FlatSpec with Matchers {
  val userService = new UserService
  val workoutService = new WorkoutService
  val reportingService = new ReportingService

  val testingUser = userService.findByUserId(UserId(1)).get
}
