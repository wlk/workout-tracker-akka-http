package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.HttpHeader
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.Credentials
import com.wlangiewicz.workouttracker.dao.{WorkoutDao, UserDao}
import com.wlangiewicz.workouttracker.domain._
import com.wlangiewicz.workouttracker.dao.WorkoutDao

trait WorkoutApi extends JsonFormats {
  val workoutDao: WorkoutDao
  val userDao: UserDao

  val workoutApiRoutes =
    pathPrefix("workouts") {
      authenticateBasic(realm = "Workout API requires Basic Authentication", apiAuthentication) { user =>
        path("all") {
          get {
            complete {
              val allWorkouts = workoutDao.findAllByUser(user.userId)
              allWorkouts
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
