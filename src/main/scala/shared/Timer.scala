package shared

/**
 * Created by RemcoW on 9-11-2016.
 */
class Timer(i: Int) {
  var startTime: Long = 0L
  var endTime: Long = 0L

  def getI: Int = {
    i
  }

  def startClock(): Timer = {
    startTime = System.nanoTime()
    this
  }

  def stopClock(): Timer = {
    endTime = System.nanoTime()
    this
  }

  def performAction(): Timer = {
    println(s"Performed Timer $i on ${Thread.currentThread()}")
    this
  }
}
