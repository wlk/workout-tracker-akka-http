package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.model.StatusCodes._
import com.wlangiewicz.workouttracker.domain._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

class ReportApiSpec extends ApiSpec {

  it should "return report for all workouts" in {
    Get("/report/all") ~> validCredentials ~> routes ~> check {
      status shouldBe OK
      val report = responseAs[Report]
      report.totalDistanceMeters shouldBe 40000
    }
  }

  it should "not return report if user has no workouts" in {
    Get("/report/all") ~> validCredentialsUserWithoutWorkouts ~> routes ~> check {
      status shouldBe OK
      val weeklyReport = responseAs[Report]
      weeklyReport.totalDistanceMeters shouldBe 0
    }
  }

  it should "return weekly report for all workouts" in {
    Get("/report/weekly") ~> validCredentials ~> routes ~> check {
      status shouldBe OK
      val report = responseAs[Map[String, Report]]
      report("2016-6").totalDistanceMeters shouldBe 30000
      report("2016-7").totalDistanceMeters shouldBe 10000
    }
  }

  it should "not return weekly report if user has no workouts" in {
    Get("/report/weekly") ~> validCredentialsUserWithoutWorkouts ~> routes ~> check {
      status shouldBe OK
      val report = responseAs[Map[String, Report]]
      report.isEmpty shouldBe true
    }
  }

  it should "return report for workouts in given range" in {
    Get("/report/range/1355354589/1465354589") ~> validCredentials ~> routes ~> check {
      status shouldBe OK
      val report = responseAs[Report]
      report.totalDistanceMeters shouldBe 40000
    }
  }

  it should "return weekly report for workouts in given range" in {
    Get("/report/range/1/2") ~> validCredentials ~> routes ~> check {
      status shouldBe OK
      val report = responseAs[Report]
      report.totalDistanceMeters shouldBe 0
    }
  }
}
