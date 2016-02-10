package services

import domain.{Report, Workout}

class ReportingService {
  def weeklyReport(workouts: Set[Workout]): Map[Int, Report] = {
    workouts.groupBy(_.date.getWeekOfWeekyear).map {
      case (week, weeklyWorkouts) => (week, report(weeklyWorkouts))
    }
  }

  def report(workouts: Set[Workout]) = {
    val elements = workouts.size

    val totalDistance = workouts.toSeq.map(_.distanceMeters).sum
    val totalDuration = workouts.toSeq.map(_.durationSeconds).sum
    val averageDistance = totalDistance / elements.toDouble
    val averageDuration = totalDuration / elements.toDouble

    Report(totalDistance, totalDuration, averageDistance, averageDuration, (averageDistance / 1000.0) / (averageDuration / 3600.0))
  }

}
