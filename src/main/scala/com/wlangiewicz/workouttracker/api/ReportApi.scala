package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.server.Directives._
import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.dao.{WorkoutDao, _}
import com.wlangiewicz.workouttracker.services.{ReportingService, WorkoutService}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

trait ReportApi extends JsonFormats {
  this: Api =>

  val reportingService: ReportingService
  val workoutDao: WorkoutDao

  val reportApiRoutes =
    pathPrefix("report") {
      authenticateBasic(realm = "Workout API requires Basic Authentication", apiAuthentication) { user =>
        path("all") {
          get {
            complete {
              val allWorkouts = workoutDao.findAllByUser(user.userId)
              reportingService.weeklyReport(allWorkouts)
            }
          }
        }
      }
    }
}
