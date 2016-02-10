package com.wlangiewicz.workouttracker.domain

import com.github.nscala_time.time.Imports._

sealed class Id(value: Int) {
  def next = new Id(value + 1)
}

case class UserId(value: Int) extends Id(value)

case class WorkoutId(value: Int) extends Id(value)

case class User(userId: UserId, login: String, password: String, apiKey: String)

case class SignUpUserRequest(login: String, password: String)

case class Workout(userId: UserId, workoutId: WorkoutId, name: String, distanceMeters: Int, durationSeconds: Int, date: DateTime)

case class RecordWorkoutRequest(userId: UserId, name: String, distanceMeters: Int, durationSeconds: Int, date: DateTime)

case class Report(totalDistanceMeters: Int, totalDurationSeconds: Int, averageDistance: Double, averageDuration: Double, averageSpeed: Double)