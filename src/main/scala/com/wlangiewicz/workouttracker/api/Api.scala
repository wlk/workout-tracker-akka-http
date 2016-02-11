package com.wlangiewicz.workouttracker.api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

import scala.concurrent.ExecutionContextExecutor

trait Api extends RouteConcatenation with UserApi with WorkoutApi {
  implicit val system: ActorSystem

  implicit def exceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case _: ApiException =>
        extractUri { uri =>
          complete(HttpResponse(BadRequest, entity = "invalid request"))
        }
    }

  val routes =
    Route.seal {
      userApiRoutes ~ workoutApiRoutes
    }

  implicit def executor: ExecutionContextExecutor

}

class ApiException(val message: String) extends RuntimeException(message)
