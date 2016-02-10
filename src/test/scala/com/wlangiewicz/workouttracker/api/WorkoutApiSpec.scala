package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._ // possibly false report by Intellij
import akka.http.scaladsl.model.ContentTypes._

class WorkoutApiSpec extends ApiSpec {
  "WorkoutApi" should "reject unauthorized users" in {
    Get("/workouts/all") ~> routes ~> check {
      status shouldBe Unauthorized
    }
  }

}
