package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import com.wlangiewicz.workouttracker.domain._
import com.wlangiewicz.workouttracker.services.{WorkoutService, UserService}

trait WorkoutApi extends JsonFormats {
  val workoutService: WorkoutService

  val workoutApiRoutes =
    pathPrefix("workouts") {
      path("all") {
        get {
          complete {
            val allWorkouts = workoutService.findAllByUser(UserId(1))
            allWorkouts
          }
        }
      }
    }
}
