package vanilla

import akka.actor.Actor
import shared.Timer

/**
  * Created by RemcoW on 16-11-2016.
  */
class ActionActor extends Actor {
  override def receive = {
    case x: Timer =>
      x.performAction()
      context.actorSelection("/user/out") ! x
    case _ =>
      println("default")
  }
}
