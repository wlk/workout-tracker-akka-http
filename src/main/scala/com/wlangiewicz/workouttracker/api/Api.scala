package com.wlangiewicz.workouttracker.api

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import com.wlangiewicz.workouttracker.domain._
import com.wlangiewicz.workouttracker.services._

import scala.concurrent.ExecutionContextExecutor

trait Api extends JsonFormats {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor

  val routes = {
    path("user" / "signup") {
      (post & entity(as[SignUpUserRequest])) { signUpRequest =>
        complete {
          val us = new UserService
          val response = us.signUp(signUpRequest)
          response
        }
      }
    }
  }
}
