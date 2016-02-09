class WorkoutServiceSpec extends WorkoutTrackerSpec {

  "WorkoutService" should "find all workouts by given user" in {

    workoutService.findAllByUser(testingUser)
  }
}
