package com.wlangiewicz.workouttracker.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.RouteConcatenation

import scala.concurrent.ExecutionContextExecutor

trait Api extends RouteConcatenation with UserApi with WorkoutApi {
  implicit val system: ActorSystem
  val routes = userApiRoutes ~ workoutApiRoutes

  implicit def executor: ExecutionContextExecutor

}
