package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._ // possibly false report by Intellij
import akka.http.scaladsl.model.headers.BasicHttpCredentials
import akka.http.scaladsl.model.ContentTypes._

class WorkoutApiSpec extends ApiSpec {
  "WorkoutApi" should "reject users without Basic Authentication" in {
    Get("/workouts/all") ~> routes ~> check {
      status shouldBe Unauthorized
    }
  }

  it should "reject users with wrong credentials" in {
    Get("/workouts/all") ~> addCredentials(BasicHttpCredentials("wrongKey", "")) ~> routes ~> check {
      status shouldBe Unauthorized
    }
  }

  it should "allow requests from authenticated users" in {
    Get("/workouts/all") ~> addCredentials(BasicHttpCredentials("key", "")) ~> routes ~> check {
      status shouldBe OK
    }
  }

}
