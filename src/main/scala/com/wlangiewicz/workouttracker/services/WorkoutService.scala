package com.wlangiewicz.workouttracker.services

import com.wlangiewicz.workouttracker.dao.WorkoutDao
import com.wlangiewicz.workouttracker.domain.{Workout, SuccessfulRecordWorkoutResponse, UnsuccessfulRecordWorkoutResponse, RecordWorkoutRequest}

class WorkoutService(workoutDao: WorkoutDao) {

  def recordNewWorkout(newWorkoutRequest: RecordWorkoutRequest): Either[UnsuccessfulRecordWorkoutResponse, SuccessfulRecordWorkoutResponse] = {
    if (!workoutDao.isValidWorkout(newWorkoutRequest)) {
      Left(UnsuccessfulRecordWorkoutResponse())
    } else {
      val workoutId = workoutDao.nextWorkoutId
      workoutDao.add(Workout(newWorkoutRequest.userId, workoutId, newWorkoutRequest.name, newWorkoutRequest.distanceMeters, newWorkoutRequest.durationSeconds, newWorkoutRequest.date))
      Right(SuccessfulRecordWorkoutResponse(workoutId))
    }
  }

}
