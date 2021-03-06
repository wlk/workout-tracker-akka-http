package com.wlangiewicz.workouttracker.dao

import com.wlangiewicz.workouttracker.domain._

class UserDao {
  var users = Set(
    User(UserId(1), "user", "password", ApiKey("key")),
    User(UserId(2), "userWithoutWorkouts", "password", ApiKey("aosijdf2309g"))
  )

  def add(user: User) = {
    users = users + user
  }

  def findByApiKey(apiKey: ApiKey) = {
    users.find(u => u.apiKey == apiKey)
  }

  def apiKeyForUser(login: String, password: String): Option[ApiKey] = {
    find(login, password).map(_.apiKey)
  }

  def find(login: String, password: String): Option[User] = {
    users.find(u => u.login == login && u.password == password)
  }

  def findByUserId(userId: UserId): Option[User] = {
    users.find(u => u.userId == userId)
  }

  def loginExists(login: String) = users.exists(_.login == login)

  def nextUserId = {
    val currentMaxUserId = users.map(_.userId.value).max
    UserId(currentMaxUserId + 1)
  }
}
