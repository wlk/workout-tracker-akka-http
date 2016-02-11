package com.wlangiewicz.workouttracker.api

import akka.actor.ActorSystem
import akka.http.scaladsl.server.{Route, RouteConcatenation}

import scala.concurrent.ExecutionContextExecutor

trait Api extends RouteConcatenation with UserApi with WorkoutApi {
  implicit val system: ActorSystem

  val routes =
    Route.seal {
      userApiRoutes ~ workoutApiRoutes
    }

  implicit def executor: ExecutionContextExecutor

}
