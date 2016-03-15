package com.wlangiewicz.workouttracker.api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.Credentials
import com.wlangiewicz.workouttracker.domain.{ApiKey, User}

import scala.concurrent.ExecutionContextExecutor

trait Api extends RouteConcatenation with UserApi with WorkoutApi with ReportApi {
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
      userApiRoutes ~ workoutApiRoutes ~ reportApiRoutes
    }

  implicit def executor: ExecutionContextExecutor

  def apiAuthentication(credentials: Credentials): Option[User] =
    credentials match {
      case p @ Credentials.Provided(id) => userDao.findByApiKey(ApiKey(id))
      case Credentials.Missing          => None
      case _                            => None
    }

}

class ApiException(val message: String) extends RuntimeException(message)

