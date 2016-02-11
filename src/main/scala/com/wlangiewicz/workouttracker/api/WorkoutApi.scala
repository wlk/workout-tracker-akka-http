package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.Credentials
import com.wlangiewicz.workouttracker.dao._
import com.wlangiewicz.workouttracker.domain._
import com.wlangiewicz.workouttracker.dao.WorkoutDao
import com.wlangiewicz.workouttracker.services.WorkoutService

trait WorkoutApi extends JsonFormats {
  val workoutDao: WorkoutDao
  val userDao: UserDao
  val workoutService: WorkoutService

  val workoutApiRoutes =
    pathPrefix("workouts") {
      authenticateBasic(realm = "Workout API requires Basic Authentication", apiAuthentication) { user =>
        path("all") {
          get {
            complete {
              workoutDao.findAllByUser(user.userId)
            }
          }
        } ~
          path("new") {
            (post & entity(as[RecordWorkoutRequest])) { newWorkoutRequest =>
              complete {
                workoutService.recordNewWorkout(newWorkoutRequest)
              }
            }
          }
      }
    }

  def apiAuthentication(credentials: Credentials): Option[User] =
    credentials match {
      case p@Credentials.Provided(id) => userDao.findByApiKey(ApiKey(id))
      case Credentials.Missing => None
      case _ => None
    }
}
