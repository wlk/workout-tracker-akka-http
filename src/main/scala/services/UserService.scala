package services

import api._
import domain.User

class UserService {
  private val users = scala.collection.mutable.Set(
    User("user", "password")
  )

  def find(login: String, password: String): Option[User] = {
    users.find(u => u.login == login && u.password == password)
  }

  def signUp(login: String, password: String): UserSignUpResponse = {
    val newUser = User(login, password)

    if (isSignedUp(newUser)) {
      UnsuccessfulUserSignUpResponse()
    } else {
      users.add(User(login, password))
      SuccessfulUserSignUpResponse()
    }

  }

  def isSignedUp(user: User) = users.contains(user)
}
