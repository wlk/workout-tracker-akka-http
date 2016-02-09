import com.github.nscala_time.time.Imports._
import domain.{WorkoutId, UserId, Workout}

class ReportingServiceSpec extends WorkoutTrackerSpec {

  val beWithinTolerance = be >= 0 and be <= 10


  "ReportingService" should "return weekly summary of given date range" in {
    val workouts = Set(
      Workout(UserId(1), WorkoutId(1), "morning run", 10000, 3700, new DateTime(2016, 2, 9, 11, 0, 0, 0)),
      Workout(UserId(1), WorkoutId(2), "evening run", 10000, 3650, new DateTime(2016, 2, 9, 12, 0, 0, 0)),
      Workout(UserId(1), WorkoutId(3), "morning run 2", 10000, 3600, new DateTime(2016, 2, 10, 12, 0, 0, 0)),
      Workout(UserId(1), WorkoutId(4), "evening run 3", 10000, 3550, new DateTime(2016, 2, 11, 12, 0, 0, 0)),
      Workout(UserId(1), WorkoutId(5), "evening run 3", 10000, 3550, new DateTime(2016, 2, 12, 14, 0, 0, 0)),
      Workout(UserId(1), WorkoutId(6), "evening run 3", 10000, 3550, new DateTime(2016, 2, 13, 11, 0, 0, 0)),
      Workout(UserId(1), WorkoutId(7), "evening run 3", 10000, 3550, new DateTime(2016, 2, 14, 12, 0, 0, 0)),
      Workout(UserId(1), WorkoutId(8), "evening run 3", 10000, 3550, new DateTime(2016, 2, 15, 9, 0, 0, 0))
    )

    val report = reportingService.report(workouts)
    report.totalDistanceMeters shouldBe 80000
    report.totalDurationSeconds shouldBe 28700
    report.averageDistance shouldBe 10000.0
    report.averageDuration shouldBe 3587.5
    report.averageSpeed shouldBe ( 10.0 +- 0.05)
  }

}
