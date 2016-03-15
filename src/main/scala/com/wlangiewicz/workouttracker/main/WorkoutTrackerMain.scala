package com.wlangiewicz.workouttracker.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.wlangiewicz.workouttracker.api.Api
import com.wlangiewicz.workouttracker.dao.{UserDao, WorkoutDao}
import com.wlangiewicz.workouttracker.services.{ReportingService, UserService, WorkoutService}
import scala.concurrent.ExecutionContextExecutor

class WorkoutTrackerApi()(override implicit val system: ActorSystem, override implicit val executor: ExecutionContextExecutor) extends Api {
  val userDao = new UserDao
  val workoutDao = new WorkoutDao
  val userService = new UserService(userDao)
  val workoutService = new WorkoutService(workoutDao)
  val reportingService = new ReportingService
}

object WorkoutTrackerMain extends App {
  implicit val system = ActorSystem("workout-tracker")
  implicit val dispatcher = system.dispatcher

  val api = new WorkoutTrackerApi()

  implicit val materializer = ActorMaterializer()

  Http().bindAndHandle(api.routes, interface = "0.0.0.0", port = 8080)
}
