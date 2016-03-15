package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.StatusCodes._
import com.wlangiewicz.workouttracker.domain._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

class ReportApiSpec extends ApiSpec {

  it should "return report for all workouts" in {
    Get("/report/all") ~> validCredentials ~> routes ~> check {
      status shouldBe OK
      val weeklyReport = responseAs[Map[String, Report]]
      weeklyReport("2016-6").totalDistanceMeters shouldBe 30000
      weeklyReport("2016-7").totalDistanceMeters shouldBe 10000
    }
  }

  ignore should "not return report if user has no workouts" in {

  }

  ignore should "return weekly report for all workouts" in {

  }

  ignore should "not return weekly report if user has no workouts" in {

  }

  ignore should "return report for workouts in given range" in {

  }

  ignore should "return weekly report for workouts in given range" in {

  }
}
