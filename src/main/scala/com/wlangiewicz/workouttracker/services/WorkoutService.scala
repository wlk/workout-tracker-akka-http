package com.wlangiewicz.workouttracker.services

import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.api._
import com.wlangiewicz.workouttracker.domain._

class WorkoutService {
  val workouts = scala.collection.mutable.Set(
    Workout(UserId(1), WorkoutId(1), "morning run", 10000, 3700, DateTime.now - 1.day - 1.hour),
    Workout(UserId(1), WorkoutId(2), "evening run", 10000, 3650, DateTime.now - 1.day),
    Workout(UserId(1), WorkoutId(3), "morning run 2", 10000, 3600, DateTime.now - 1.hour),
    Workout(UserId(1), WorkoutId(4), "evening run 3", 10000, 3550, DateTime.now)
  )

  def findAllByUserInRangeGroupedWeekly(userId: UserId, rangeStart: DateTime, rangeEnd: DateTime) = {
    findInDateRangeByUser(userId, rangeStart, rangeEnd).groupBy(_.date.getWeekOfWeekyear)
  }

  def findInDateRangeByUser(userId: UserId, rangeStart: DateTime, rangeEnd: DateTime) = {
    workouts.filter(w => w.userId == userId && w.date >= rangeStart && w.date <= rangeEnd).toSet
  }

  def findAllByUserGroupedWeekly(userId: UserId): Map[Int, Set[Workout]] = {
    findAllByUser(userId).groupBy(_.date.getWeekOfWeekyear)
  }

  def findAllByUser(userId: UserId): Set[Workout] = workouts.filter(w => w.userId == userId).toSet

  def editWorkout(workout: Workout) = {
    deleteWorkout(workout.workoutId)
    workouts.add(workout)
  }

  def deleteWorkout(workoutId: WorkoutId): Unit = {
    workouts.find(_.workoutId == workoutId).foreach(workouts.remove)
  }

  def recordNewWorkout(newWorkoutRequest: RecordWorkoutRequest): RecordWorkoutResponse = {
    if (!isValidRequest(newWorkoutRequest)) {
      UnsuccessfulRecordWorkoutResponse()
    } else {
      val workoutId = nextWorkoutId
      workouts
        .add(Workout(newWorkoutRequest.userId, workoutId, newWorkoutRequest.name, newWorkoutRequest
          .distanceMeters, newWorkoutRequest.durationSeconds, newWorkoutRequest.date))
      SuccessfulRecordWorkoutResponse()
    }
  }

  def isValidRequest(newWorkoutRequest: RecordWorkoutRequest): Boolean = {
    newWorkoutRequest.date > DateTime.now - 100.years // bogus condition for invalid request
  }

  private def nextWorkoutId = {
    val currentMaxWorkoutId = workouts.map(_.workoutId.value).max
    WorkoutId(currentMaxWorkoutId + 1)
  }

}
