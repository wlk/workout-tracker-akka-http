package domain

class Id(value: Int) {
  def next = new Id(value + 1)
}

case class UserId(value: Int) extends Id(value)

case class WorkoutId(value: Int) extends Id(value)

case class User(userId: UserId, login: String, password: String)

case class Workout(userId: UserId, workoutId: WorkoutId, name: String, distanceMeters: Int, durationSeconds: Int)
