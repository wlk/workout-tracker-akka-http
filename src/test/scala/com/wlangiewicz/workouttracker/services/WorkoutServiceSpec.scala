package com.wlangiewicz.workouttracker.services

import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.api.SuccessfulRecordWorkoutResponse
import com.wlangiewicz.workouttracker.domain._
import org.scalatest.BeforeAndAfter

class WorkoutServiceSpec extends WorkoutTrackerSpec with BeforeAndAfter {

  before {
    def cleanupWorkoutService() = {
      workoutService.workouts.clear()

      val testingWorkouts = scala.collection.mutable.Set(
        Workout(UserId(1), WorkoutId(1), "morning run", 10000, 3700, DateTime.now - 1.day - 1.hour),
        Workout(UserId(1), WorkoutId(2), "evening run", 10000, 3650, DateTime.now - 1.day),
        Workout(UserId(1), WorkoutId(3), "morning run 2", 10000, 3600, DateTime.now - 1.hour),
        Workout(UserId(1), WorkoutId(4), "evening run 3", 10000, 3550, DateTime.now)
      )

      testingWorkouts.foreach(workoutService.workouts.add)
    }

    cleanupWorkoutService()
  }

  "WorkoutService" should "find all workouts by given user" in {

    val workoutFound = workoutService.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(1)).get

    workoutFound.workoutId shouldBe WorkoutId(1)
    workoutFound.userId shouldBe UserId(1)

    workoutService.findAllByUser(testingUser.userId).size shouldBe 4
  }

  it should "record new workouts" in {
    workoutService.findAllByUser(testingUser.userId).size shouldBe 4

    val newWorkoutRequest = RecordWorkoutRequest(testingUser.userId, "testing my new gps watch", 1000, 600, DateTime
      .now)

    workoutService.recordNewWorkout(newWorkoutRequest) shouldBe SuccessfulRecordWorkoutResponse()

    workoutService.findAllByUser(testingUser.userId).size shouldBe 5

    val newWorkout = workoutService.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(5)).get

    newWorkout.name shouldBe "testing my new gps watch"
    newWorkout.workoutId shouldBe WorkoutId(5)
  }

  it should "find workouts in given date range" in {
    val rangeStart = DateTime.now - 10.years
    val rangeEnd = DateTime.now + 10.years

    workoutService.findInDateRangeByUser(testingUser.userId, rangeStart, rangeEnd).size shouldBe 4
  }

  it should "remove workouts when requested" in {
    workoutService.findAllByUser(testingUser.userId).size shouldBe 4
    workoutService.deleteWorkout(WorkoutId(1))
    workoutService.findAllByUser(testingUser.userId).size shouldBe 3
  }

  it should "edit workouts when requested" in {
    val oldWorkout = workoutService.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(1)).get

    workoutService.editWorkout(oldWorkout.copy(distanceMeters = 5555))

    val updatedWorkout = workoutService.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(1)).get

    updatedWorkout.distanceMeters shouldBe 5555

  }
}
