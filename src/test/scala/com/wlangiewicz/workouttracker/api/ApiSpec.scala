package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.wlangiewicz.workouttracker.dao._
import org.scalatest.{FlatSpec, Matchers}

class ApiSpec extends FlatSpec with Matchers with ScalatestRouteTest with JsonFormats with Api {
  override val userDao: UserDao = new UserDao
  override val workoutDao: WorkoutDao = new WorkoutDao

  val validCredentials = addCredentials(BasicHttpCredentials("key", ""))
  val invalidCredentials = addCredentials(BasicHttpCredentials("abc", ""))
}
