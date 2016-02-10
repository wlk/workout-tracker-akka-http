package com.wlangiewicz.workouttracker.api

import com.wlangiewicz.workouttracker.domain.{SuccessfulUserSignUpResponse, SignUpUserRequest}
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._ // false report by Intellij

class UserApiSpec extends ApiSpec {

  "UserApi" should "allow to signUp" in {
    Post("/user/signup", SignUpUserRequest("newUser", "myPassword")) ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `application/json`
      val response = responseAs[SuccessfulUserSignUpResponse]
      response.apiKey.length shouldBe 16
    }
  }

}
