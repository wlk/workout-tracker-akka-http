package com.wlangiewicz.workouttracker.api

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContextExecutor

trait Api extends UserApi {
  implicit val system: ActorSystem
  val routes = userApiRoutes

  implicit def executor: ExecutionContextExecutor

}
