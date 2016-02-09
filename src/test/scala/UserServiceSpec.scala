import api.{SuccessfulUserSignUpResponse, UnsuccessfulUserSignUpResponse}
import domain.User
import org.scalatest.{FlatSpec, Matchers}
import services.UserService

class UserServiceSpec extends FlatSpec with Matchers {

  "UserService" should "not find users by login and password pair if it's incorrect" in {
    val us = new UserService
    us.find("test", "incorrect") shouldBe None
  }

  it should "find users if login and password are correct" in {
    val us = new UserService
    us.find("user", "password") shouldBe Some(User("user", "password"))
  }

  it should "signUp user when it doesn't exist" in {
    val us = new UserService
    us.signUp("newUser", "password") shouldBe SuccessfulUserSignUpResponse()
    us.find("newUser", "password") shouldBe Some(User("newUser", "password"))
  }

  it should "not signUp user when it already exists" in {
    val us = new UserService
    us.signUp("newUser", "password") shouldBe SuccessfulUserSignUpResponse()
    us.signUp("newUser", "password") shouldBe UnsuccessfulUserSignUpResponse()
    us.find("newUser", "password") shouldBe Some(User("newUser", "password"))
  }
}
