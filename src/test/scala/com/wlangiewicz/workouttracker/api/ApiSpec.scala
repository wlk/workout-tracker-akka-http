package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.wlangiewicz.workouttracker.services.{WorkoutService, UserService}
import org.scalatest.{FlatSpec, Matchers}

class ApiSpec extends FlatSpec with Matchers with ScalatestRouteTest with JsonFormats with Api {
  override val userService: UserService = new UserService
  override val workoutService: WorkoutService = new WorkoutService
}
