package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.wlangiewicz.workouttracker.domain.{Workout, RecordWorkoutRequest}
import com.github.nscala_time.time.Imports._

// possibly false report by Intellij

class WorkoutApiSpec extends ApiSpec {
  "WorkoutApi" should "reject users without Basic Authentication" in {
    Get("/workouts/all") ~> routes ~> check {
      status shouldBe Unauthorized
    }
  }

  it should "reject users with wrong credentials" in {
    Get("/workouts/all") ~> invalidCredentials ~> routes ~> check {
      status shouldBe Unauthorized
    }
  }

  it should "allow requests from authenticated users" in {
    Get("/workouts/all") ~> validCredentials ~> routes ~> check {
      status shouldBe OK
    }
  }

  it should "allow creating workouts" in {
    val request = RecordWorkoutRequest(testingUser.userId, "testing workout", 10000, 3600, new DateTime(2016, 2, 15, 12, 0, 0, 0))
    Post("/workouts/new", request) ~> validCredentials ~> routes ~> check {
      status shouldBe OK

      Get("/workouts/all") ~> validCredentials ~> routes ~> check {
        val workouts = responseAs[List[Workout]]
        workouts.map(_.name) should contain("testing workout")
        workouts.map(_.workoutId) should contain theSameElementsAs workouts.map(_.workoutId).distinct
      }
    }
  }

  it should "not allow creating invalid workouts" in {
    val request = RecordWorkoutRequest(testingUser.userId, "testing workout", 10000, 3600, new DateTime(1970, 2, 15, 12, 0, 0, 0)) // invalid year
    Post("/workouts/new", request) ~> validCredentials ~> routes ~> check {
      status shouldBe BadRequest
    }
  }

}
