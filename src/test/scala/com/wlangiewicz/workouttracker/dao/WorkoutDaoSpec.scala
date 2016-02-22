package com.wlangiewicz.workouttracker.dao

import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.WorkoutTrackerSpec
import com.wlangiewicz.workouttracker.domain._
import org.scalatest.BeforeAndAfter

class WorkoutDaoSpec extends WorkoutTrackerSpec with BeforeAndAfter {

  before {
    def cleanupWorkoutDao() = {
      workoutDao.workouts = Set()

      val testingWorkouts = Set(
        Workout(UserId(1), WorkoutId(1), "morning run", 10000, 3700, new DateTime(2016, 2, 9, 11, 0, 0, 0)),
        Workout(UserId(1), WorkoutId(2), "evening run", 10000, 3650, new DateTime(2016, 2, 9, 12, 0, 0, 0)),
        Workout(UserId(1), WorkoutId(3), "morning run 2", 10000, 3600, new DateTime(2016, 2, 10, 12, 0, 0, 0)),
        Workout(UserId(1), WorkoutId(4), "evening run 3", 10000, 3550, new DateTime(2016, 2, 15, 12, 0, 0, 0))
      )

      testingWorkouts.foreach(workoutDao.add)
    }

    cleanupWorkoutDao()
  }

  "WorkoutDao" should "find all workouts by given user" in {

    val workoutFound = workoutDao.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(1)).get

    workoutFound.workoutId shouldBe WorkoutId(1)
    workoutFound.userId shouldBe UserId(1)

    workoutDao.findAllByUser(testingUser.userId).size shouldBe 4
  }

  it should "find workouts in given date range" in {
    val date = new DateTime(2016, 2, 9, 11, 0, 0, 0)
    val rangeStart = date - 10.years
    val rangeEnd = date + 10.years
    workoutDao.findInDateRangeByUser(testingUser.userId, rangeStart, rangeEnd).size shouldBe 4
  }

  it should "filter out workouts out of date range" in {
    val date = new DateTime(2016, 2, 9, 11, 0, 0, 0)
    val rangeStart = date
    val rangeEnd = date + 1.minute
    workoutDao.findInDateRangeByUser(testingUser.userId, rangeStart, rangeEnd).size shouldBe 1
  }

  it should "remove workouts when requested" in {
    workoutDao.findAllByUser(testingUser.userId).size shouldBe 4
    workoutDao.deleteWorkout(WorkoutId(1))
    workoutDao.findAllByUser(testingUser.userId).size shouldBe 3
  }

  it should "edit workouts when requested" in {
    val oldWorkout = workoutDao.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(1)).get

    workoutDao.editWorkout(oldWorkout.copy(distanceMeters = 5555))

    val updatedWorkout = workoutDao.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(1)).get

    updatedWorkout.distanceMeters shouldBe 5555
  }

  it should "find workouts grouped by week" in {
    val workoutsGrouped = workoutDao.findAllByUserGroupedWeekly(testingUser.userId)
    workoutsGrouped.size shouldBe 2
    workoutsGrouped(6).size shouldBe 3
    workoutsGrouped(6).find(_.workoutId == WorkoutId(2)).get.name shouldBe "evening run"
  }

  it should "find workouts grouped by week for given range" in {
    val date = new DateTime(2016, 2, 9, 11, 0, 0, 0)
    val rangeStart = date
    val rangeEnd = date + 1.day
    val workoutsGrouped = workoutDao.findAllByUserInRangeGroupedWeekly(testingUser.userId, rangeStart, rangeEnd)
    workoutsGrouped.size shouldBe 1
    workoutsGrouped(6).find(_.workoutId == WorkoutId(1)).get.distanceMeters shouldBe 10000
  }
}
