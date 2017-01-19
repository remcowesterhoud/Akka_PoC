package vanilla

import akka.actor.{ActorSystem, Props}
import shared.{Calc, Timer}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by RemcoW on 16-11-2016.
 */
object Main extends App {
  // An ActorSystem is created for the actors to live in
  val system = ActorSystem("Akka_PoC")
  // The "in" Actor is added to the system
  val inRef = system.actorOf(Props[InActor], name = "in")
  // The "out" Actor is added to the system
  val outRef = system.actorOf(Props[OutActor], name = "out")

  // Amount of elements to be send through the stream
  var requestAmount: Int = 5

  val timers = ArrayBuffer[Timer]()
  for (i <- 1 to requestAmount) {
    val timer = new Timer(i)
    timers.append(timer)
  }
  for (timer <- timers) {
    timer.startClock()
  }
  // The list of timers is send to the "in" Actor in a message
  inRef ! timers
  Thread.sleep(100)
  Calc.calculateThroughput(timers)
  System.exit(0)
}
