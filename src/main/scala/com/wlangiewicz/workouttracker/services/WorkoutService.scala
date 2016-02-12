package com.wlangiewicz.workouttracker.services

import com.wlangiewicz.workouttracker.api.ApiException
import com.wlangiewicz.workouttracker.dao.WorkoutDao
import com.wlangiewicz.workouttracker.domain._

import scala.util.{ Failure, Success, Try }

class WorkoutService(workoutDao: WorkoutDao) {
  def updateWorkout(user: User, updatedWorkout: Workout): Try[Workout] = {
    if(workoutDao.isOwner(user.userId, updatedWorkout.workoutId)){
      Success(workoutDao.editWorkout(updatedWorkout))
    } else {
      Failure(new ApiException("you are not allowed to edit this workout"))
    }
  }


  def deleteWorkout(user: User, workoutId: WorkoutId): Try[WorkoutId] = {
    if (workoutDao.isOwner(user.userId, workoutId)) {
      workoutDao.deleteWorkout(workoutId)
      Success(workoutId)
    } else {
      Failure(new ApiException("you don't own this workout"))
    }
  }

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
