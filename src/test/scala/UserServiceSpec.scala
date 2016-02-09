import org.scalatest.{FlatSpec, Matchers}
import services.UserService

class UserServiceSpec extends FlatSpec with Matchers {

  "UserService" should "not find users by login and password pair if it's incorrect" in {
    val us = new UserService
    us.find("test", "incorrect") shouldBe None
  }
}
