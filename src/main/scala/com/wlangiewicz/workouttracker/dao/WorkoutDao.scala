package com.wlangiewicz.workouttracker.dao

import com.github.nscala_time.time.Imports._
import com.wlangiewicz.workouttracker.domain._

class WorkoutDao {
  var workouts = Set(
    Workout(UserId(1), WorkoutId(1), "morning run", 10000, 3700, new DateTime(2016, 2, 9, 11, 0, 0, 0)),
    Workout(UserId(1), WorkoutId(2), "evening run", 10000, 3650, new DateTime(2016, 2, 9, 12, 0, 0, 0)),
    Workout(UserId(1), WorkoutId(3), "morning run 2", 10000, 3600, new DateTime(2016, 2, 10, 12, 0, 0, 0)),
    Workout(UserId(1), WorkoutId(4), "evening run 3", 10000, 3550, new DateTime(2016, 2, 15, 12, 0, 0, 0))
  )

  def add(workout: Workout) = {
    workouts = workouts + workout
  }

  // Using String instead of [Int, Int] as Map key - spray-json bug: https://github.com/spray/spray-json/issues/125
  private def sprayBugWorkaround(m: Map[(Int, Int), Set[Workout]]) = m.map(w => (w._1._1.toString + "-" + w._1._2.toString, w._2))

  // Using String instead of [Int, Int] as Map key - spray-json bug: https://github.com/spray/spray-json/issues/125
  def findAllByUserInRangeGroupedWeekly(userId: UserId, rangeStart: DateTime, rangeEnd: DateTime): Map[String, Set[Workout]] = {
    sprayBugWorkaround(findInDateRangeByUser(userId, rangeStart, rangeEnd).groupBy(w => (w.date.getYear, w.date.getWeekOfWeekyear)))
  }

  def findInDateRangeByUser(userId: UserId, rangeStart: DateTime, rangeEnd: DateTime) = {
    workouts.filter(w => w.userId == userId && w.date >= rangeStart && w.date <= rangeEnd)
  }

  // Using String instead of [Int, Int] as Map key - spray-json bug: https://github.com/spray/spray-json/issues/125
  def findAllByUserGroupedWeekly(userId: UserId): Map[String, Set[Workout]] = {
    sprayBugWorkaround(findAllByUser(userId).groupBy(w => (w.date.getYear, w.date.getWeekOfWeekyear)))
  }

  def findAllByUser(userId: UserId): Set[Workout] = workouts.filter(w => w.userId == userId)

  def editWorkout(workout: Workout) = {
    deleteWorkout(workout.workoutId)
    add(workout)
    workout
  }

  def deleteWorkout(workoutId: WorkoutId): Unit = {
    workouts.find(_.workoutId == workoutId).foreach(w => workouts = workouts - w)
  }

  def isOwner(userId: UserId, workoutId: WorkoutId) = {
    workouts.exists(w => w.workoutId == workoutId && w.userId == userId)
  }

  def isValidWorkout(newWorkoutRequest: RecordWorkoutRequest): Boolean = {
    newWorkoutRequest.date > DateTime.now - 10.years // bogus condition for invalid request
  }

  def nextWorkoutId = {
    val currentMaxWorkoutId = workouts.map(_.workoutId.value).max
    WorkoutId(currentMaxWorkoutId + 1)
  }

}
