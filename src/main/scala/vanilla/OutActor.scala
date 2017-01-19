package vanilla

import akka.actor.Actor
import shared.Timer

/**
 * Created by RemcoW on 16-11-2016.
 */
class OutActor extends Actor {
  // When the "out" Actor receives a message containing a Timer it will call the stopClock() method on this Timer
  override def receive = {
    case x: Timer =>
      x.stopClock()
    case _ =>
      println("Default")
  }
}
