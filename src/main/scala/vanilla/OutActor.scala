package vanilla

import akka.actor.Actor
import shared.Timer

/**
  * Created by RemcoW on 16-11-2016.
  */
class OutActor extends Actor{
  override def receive = {
    case x: Timer =>
      x.stopclock()
    case _ =>
      println("Default")
  }
}
