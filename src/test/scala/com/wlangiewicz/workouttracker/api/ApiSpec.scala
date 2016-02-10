package com.wlangiewicz.workouttracker.api

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, FlatSpec}

trait ApiSpec extends FlatSpec with Matchers with ScalatestRouteTest with JsonFormats with Api {

}
