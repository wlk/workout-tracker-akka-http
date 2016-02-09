package services

import api._
import domain._

class WorkoutService {
  def recordNewWorkout(newWorkoutRequest: RecordWorkoutRequest): RecordWorkoutResponse = {
    if (false) { // TODO - how adding a workout can fail
      UnsuccessfulRecordWorkoutResponse()
    } else {
      val workoutId = nextWorkoutId
      workouts.add(Workout(newWorkoutRequest.userId, workoutId, newWorkoutRequest.name, newWorkoutRequest.distanceMeters, newWorkoutRequest.durationSeconds))
      SuccessfulRecordWorkoutResponse()
    }
  }

  val workouts = scala.collection.mutable.Set(
    Workout(UserId(1), WorkoutId(1), "morning run", 10000,  3700),
    Workout(UserId(1), WorkoutId(2), "evening run", 10000,  3650),
    Workout(UserId(1), WorkoutId(3), "morning run 2", 10000,  3600),
    Workout(UserId(1), WorkoutId(4), "evening run 3", 10000,  3550)
  )

  def findAllByUser(userId: UserId): Set[Workout] = workouts.filter(w => w.userId == userId).toSet

  private def nextWorkoutId = {
    val currentMaxWorkoutId = workouts.map(_.workoutId.value).max
    WorkoutId(currentMaxWorkoutId + 1)
  }

}
