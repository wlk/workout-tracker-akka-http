package com.wlangiewicz.workouttracker.services

import com.wlangiewicz.workouttracker.domain._

class ReportingService {
  // Using String instead of [Int, Int] as Map key - spray-json bug: https://github.com/spray/spray-json/issues/125
  private def sprayBugWorkaround(m: Map[(Int, Int), Report]) = m.map(w => (w._1._1.toString + "-" + w._1._2.toString, w._2))

  def weeklyReport(workouts: Set[Workout]): Map[String, Report] = {
    sprayBugWorkaround(
      workouts.groupBy(w => (w.date.getYear, w.date.getWeekOfWeekyear)).map {
        case (week, weeklyWorkouts) => (week, report(weeklyWorkouts))
      }
    )
  }

  def report(workouts: Set[Workout]): Report = {
    val elements = workouts.size

    val totalDistance = workouts.toSeq.map(_.distanceMeters).sum
    val totalDuration = workouts.toSeq.map(_.durationSeconds).sum
    val averageDistance = totalDistance / elements.toDouble
    val averageDuration = totalDuration / elements.toDouble

    Report(totalDistance, totalDuration, averageDistance, averageDuration, (averageDistance / 1000.0) / (averageDuration / 3600.0))
  }

}
