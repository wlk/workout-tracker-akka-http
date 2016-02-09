import api.{SuccessfulUserSignUpResponse, UnsuccessfulUserSignUpResponse}
import domain.{SignUpUserRequest, User, UserId}
import org.scalatest.BeforeAndAfter

class UserServiceSpec extends WorkoutTrackerSpec with BeforeAndAfter {

  before {
    def cleanupUserService() = {
      userService.users.clear()
      userService.users.add(User(UserId(1), "user", "password"))
    }

    cleanupUserService()
  }

  "UserService" should "not find users by login and password pair if it's incorrect" in {
    userService.find("test", "incorrect") shouldBe None
  }

  it should "find users if login and password are correct" in {
    userService.find("user", "password") shouldBe Some(User(UserId(1), "user", "password"))
  }

  it should "signUp user when it doesn't exist" in {
    userService.signUp(SignUpUserRequest("newUser", "password")) shouldBe SuccessfulUserSignUpResponse()
    userService.find("newUser", "password") shouldBe Some(User(UserId(2), "newUser", "password"))
  }

  it should "not signUp user when it already exists" in {
    userService.signUp(SignUpUserRequest("newUser", "password")) shouldBe SuccessfulUserSignUpResponse()
    userService.signUp(SignUpUserRequest("newUser", "password")) shouldBe UnsuccessfulUserSignUpResponse()
    userService.find("newUser", "password") shouldBe Some(User(UserId(2), "newUser", "password"))
  }
}
