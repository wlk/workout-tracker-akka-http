package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.wlangiewicz.workouttracker.domain._
import com.github.nscala_time.time.Imports._

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
      responseAs[List[Workout]].size should be > 1
    }
  }

  it should "not list workouts if user has none" in {
    Get("/workouts/all") ~> validCredentialsUserWithoutWorkouts ~> routes ~> check {
      status shouldBe OK
      responseAs[List[Workout]].size shouldBe 0
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
    val request = RecordWorkoutRequest(testingUser.userId, "testing workout", 10000, 3600, new DateTime(1970, 2, 15, 12, 0, 0, 0)) // year out of allowed range
    Post("/workouts/new", request) ~> validCredentials ~> routes ~> check {
      status shouldBe BadRequest
    }
  }

  it should "allow user to delete it's own workout" in {
    val request = DeleteWorkoutRequest(WorkoutId(1))
    Delete("/workouts/delete", request) ~> validCredentials ~> routes ~> check {

      Get("/workouts/all") ~> validCredentials ~> routes ~> check {
        val workouts = responseAs[List[Workout]]
        workouts.map(_.workoutId) should not contain WorkoutId(1)
      }
    }
  }

  it should "not allow user to delete someone's else workout" in {
    val request = DeleteWorkoutRequest(WorkoutId(2))
    Delete("/workouts/delete", request) ~> validCredentialsUserWithoutWorkouts ~> routes ~> check {
      status shouldBe BadRequest

      Get("/workouts/all") ~> validCredentials ~> routes ~> check {
        val workouts = responseAs[List[Workout]]
        workouts.map(_.workoutId) should contain(WorkoutId(2))
      }
    }
  }

  ignore should "allow user to edit workout he owns" in {
  }

  ignore should "not allow user to edit someone's else workout" in {
  }

}
