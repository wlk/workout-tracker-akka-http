package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.dao.{WorkoutDao, _}
import com.wlangiewicz.workouttracker.domain._
import com.wlangiewicz.workouttracker.services.WorkoutService
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

trait WorkoutApi extends JsonFormats {
  this: Api =>
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
                workoutService.recordNewWorkout(user.userId, newWorkoutRequest) match {
                  case Left(e)         => BadRequest -> s"invalid request: $newWorkoutRequest"
                  case Right(response) => response
                }
              }
            }
          } ~
          path("delete") {
            (delete & entity(as[DeleteWorkoutRequest])) { deleteRequest =>
              complete {
                workoutService.deleteWorkout(user, deleteRequest.workoutId)
              }
            }
          } ~
          path("update") {
            (post & entity(as[UpdateWorkoutRequest])) { updateRequest =>
              complete {
                workoutService.updateWorkout(user, updateRequest.workout)
              }
            }
          } ~
          path("range" / IntNumber / IntNumber) { (rangeStart, rangeEnd) =>
            get {
              complete {
                workoutDao.findInDateRangeByUser(user.userId, new DateTime(rangeStart.toLong * 1000), new DateTime(rangeEnd.toLong * 1000))
              }
            }
          } ~
          path("grouped") {
            get {
              complete {
                workoutDao.findAllByUserGroupedWeekly(user.userId)
              }
            }
          } ~
          path("grouped" / IntNumber / IntNumber) { (rangeStart, rangeEnd) =>
            get {
              complete {
                workoutDao.findAllByUserInRangeGroupedWeekly(user.userId, new DateTime(rangeStart.toLong * 1000), new DateTime(rangeEnd.toLong * 1000))
              }
            }
          }
      }
    }
}
