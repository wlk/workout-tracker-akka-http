package com.wlangiewicz.workouttracker.services

import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.domain.{WorkoutId, SuccessfulRecordWorkoutResponse, RecordWorkoutRequest}
import org.scalatest.BeforeAndAfter

class WorkoutServiceSpec extends WorkoutTrackerSpec with BeforeAndAfter {
  "WorkoutService" should "record new workouts" in {
    workoutDao.findAllByUser(testingUser.userId).size shouldBe 4

    val newWorkoutRequest = RecordWorkoutRequest(testingUser.userId, "testing my new gps watch", 1000, 600, DateTime.now)

    workoutService.recordNewWorkout(newWorkoutRequest).right.get shouldBe an[SuccessfulRecordWorkoutResponse]

    workoutDao.findAllByUser(testingUser.userId).size shouldBe 5

    val newWorkout = workoutDao.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(5)).get

    newWorkout.name shouldBe "testing my new gps watch"
    newWorkout.workoutId shouldBe WorkoutId(5)
  }

  it should "delete workouts" in {
    workoutService.deleteWorkout(testingUser, WorkoutId(1))
    val workouts = workoutDao.findAllByUser(testingUser.userId)
    workouts.map(_.workoutId) should not contain WorkoutId(1)
  }
}
