package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import com.wlangiewicz.workouttracker.dao.UserDao
import com.wlangiewicz.workouttracker.domain._
import com.wlangiewicz.workouttracker.services.UserService

trait UserApi extends JsonFormats {
  val userDao: UserDao
  val userService: UserService

  val userApiRoutes =
    pathPrefix("user") {
      path("signup") {
        (post & entity(as[SignUpUserRequest])) { signUpRequest =>
          complete {
            val response = userService.signUp(signUpRequest)
            response
          }
        }
      } ~
        path("login") {
          (post & entity(as[LoginUserRequest])) { loginRequest =>
            complete {
              val maybeApiKey = userDao.apiKeyForUser(loginRequest.login, loginRequest.password)
              maybeApiKey match {
                case Some(key) => key
                case None => new RuntimeException("user not found")
              }
            }
          }
        }
    }
}
