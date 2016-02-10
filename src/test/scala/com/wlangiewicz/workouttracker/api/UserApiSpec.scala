package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._ // possibly false report by Intellij
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.StatusCodes._
import com.wlangiewicz.workouttracker.domain._

class UserApiSpec extends ApiSpec {

  "UserApi" should "allow to signUp" in {
    Post("/user/signup", SignUpUserRequest("newUser", "myPassword")) ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `application/json`
      val response = responseAs[SuccessfulUserSignUpResponse]
      response.apiKey.apiKey.length shouldBe 16
    }

    Post("/user/signup", SignUpUserRequest("newUser2", "myPassword")) ~> routes ~> check {
      val response = responseAs[SuccessfulUserSignUpResponse]
      val apiKey = response.apiKey

      Post("/user/login", LoginUserRequest("newUser2", "myPassword")) ~> routes ~> check {
        val response = responseAs[ApiKey]
        response shouldBe apiKey
      }
    }
  }

}
