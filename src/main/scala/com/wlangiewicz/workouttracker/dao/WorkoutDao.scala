package com.wlangiewicz.workouttracker.dao

import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.domain._

class WorkoutDao {
  val workouts = scala.collection.mutable.Set(
    Workout(UserId(1), WorkoutId(1), "morning run", 10000, 3700, new DateTime(2016, 2, 9, 11, 0, 0, 0)),
    Workout(UserId(1), WorkoutId(2), "evening run", 10000, 3650, new DateTime(2016, 2, 9, 12, 0, 0, 0)),
    Workout(UserId(1), WorkoutId(3), "morning run 2", 10000, 3600, new DateTime(2016, 2, 10, 12, 0, 0, 0)),
    Workout(UserId(1), WorkoutId(4), "evening run 3", 10000, 3550, new DateTime(2016, 2, 15, 12, 0, 0, 0))
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

  def isOwner(userId: UserId, workoutId: WorkoutId) = {
    workouts.exists(w => w.workoutId == workoutId && w.userId == userId)
  }

  def deleteWorkout(workoutId: WorkoutId): Unit = {
    workouts.find(_.workoutId == workoutId).foreach(workouts.remove)
  }

  def recordNewWorkout(newWorkoutRequest: RecordWorkoutRequest): Either[UnsuccessfulRecordWorkoutResponse, SuccessfulRecordWorkoutResponse] = {
    if (!isValidWorkout(newWorkoutRequest)) {
      Left(UnsuccessfulRecordWorkoutResponse())
    } else {
      val workoutId = nextWorkoutId
      workouts
        .add(Workout(newWorkoutRequest.userId, workoutId, newWorkoutRequest.name, newWorkoutRequest
          .distanceMeters, newWorkoutRequest.durationSeconds, newWorkoutRequest.date))
      Right(SuccessfulRecordWorkoutResponse(workoutId))
    }
  }

  def isValidWorkout(newWorkoutRequest: RecordWorkoutRequest): Boolean = {
    newWorkoutRequest.date > DateTime.now - 100.years // bogus condition for invalid request
  }

  private def nextWorkoutId = {
    val currentMaxWorkoutId = workouts.map(_.workoutId.value).max
    WorkoutId(currentMaxWorkoutId + 1)
  }

}
