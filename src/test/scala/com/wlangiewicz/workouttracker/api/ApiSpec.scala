package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.dao._
import com.wlangiewicz.workouttracker.domain.UserId
import com.wlangiewicz.workouttracker.services.UserService
import org.scalatest.{FlatSpec, Matchers}

class ApiSpec extends WorkoutTrackerSpec with ScalatestRouteTest with JsonFormats with Api {
  val validCredentials = addCredentials(BasicHttpCredentials(userDao.findByUserId(UserId(1)).get.apiKey.apiKey, ""))
  val validCredentialsUserWithoutWorkouts = addCredentials(BasicHttpCredentials(userDao.findByUserId(UserId(2)).get.apiKey.apiKey, ""))

  val invalidCredentials = addCredentials(BasicHttpCredentials("this is incorrect api key", ""))
}
