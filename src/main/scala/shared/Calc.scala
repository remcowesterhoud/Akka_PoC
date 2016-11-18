package shared

import scala.collection.mutable.ArrayBuffer

/**
  * Created by RemcoW on 16-11-2016.
  */
object Calc {
  def calculateThroughput(timers: ArrayBuffer[Timer]) = {
    var totalTimeInMs = 0.00
    println()
    for (timer <- timers) {
      val took = (timer.endTime - timer.startTime) / 1000000.00
      println(s"Timer ${timer.getI} took ${took}ms")
      totalTimeInMs += took
    }
    println("-------------------------------------------------")
    println(s"Amount of requests: ${timers.size}")
    println(s"Total time: ${totalTimeInMs}ms")
    println(s"Average: ${totalTimeInMs / timers.size}ms")
    println(s"Throughput: ${(timers.size / totalTimeInMs) * 1000 * 5} requests/s")
    println("-------------------------------------------------")
    println(s"Difference ${timers(0).getI} and ${timers(timers.size - 1).getI}: ${((timers(timers.size - 1).endTime - timers(timers.size - 1).startTime) - (timers(0).endTime - timers(0).startTime)) / 1000000.00}ms")
  }
}
