import domain.{RecordWorkoutRequest, WorkoutId, UserId, Workout}

class WorkoutServiceSpec extends WorkoutTrackerSpec {

  "WorkoutService" should "find all workouts by given user" in {

    workoutService.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(1)) shouldBe
      Some(Workout(UserId(1), WorkoutId(1), "morning run", 10000,  3700))

    workoutService.findAllByUser(testingUser.userId).size shouldBe 4
  }

  it should "record new workouts" in {
    workoutService.findAllByUser(testingUser.userId).size shouldBe 4

    val newWorkoutRequest = RecordWorkoutRequest(testingUser.userId, "testing my new gps watch", 1000, 600)

    workoutService.recordNewWorkout(newWorkoutRequest)

    workoutService.findAllByUser(testingUser.userId).size shouldBe 5

    val newWorkout = workoutService.findAllByUser(testingUser.userId).find(_.workoutId == WorkoutId(5)).get

    newWorkout.name shouldBe "testing my new gps watch"
    newWorkout.workoutId shouldBe WorkoutId(5)
  }
}
