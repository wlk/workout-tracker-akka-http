import domain.UserId
import org.scalatest.{FlatSpec, Matchers}
import services.{UserService, WorkoutService}

trait WorkoutTrackerSpec extends FlatSpec with Matchers {
  val userService = new UserService
  val workoutService = new WorkoutService

  val testingUser = userService.findByUserId(UserId(1)).get
}
