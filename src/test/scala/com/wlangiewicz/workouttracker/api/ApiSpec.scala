package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.dao._
import com.wlangiewicz.workouttracker.services.UserService
import org.scalatest.{FlatSpec, Matchers}

class ApiSpec extends WorkoutTrackerSpec with ScalatestRouteTest with JsonFormats with Api {
  val validCredentials = addCredentials(BasicHttpCredentials("key", ""))
  val invalidCredentials = addCredentials(BasicHttpCredentials("abc", ""))
}
