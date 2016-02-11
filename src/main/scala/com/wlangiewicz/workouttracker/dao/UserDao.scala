package com.wlangiewicz.workouttracker.dao

import com.wlangiewicz.workouttracker.domain._

class UserDao {
  val users = scala.collection.mutable.Set(
    User(UserId(1), "user", "password", ApiKey("key"))
  )

  def add(user: User) = {
    users.add(user)
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
