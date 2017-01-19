package vanilla

import akka.actor.Actor
import shared.Timer

/**
 * Created by RemcoW on 16-11-2016.
 */
class ActionActor extends Actor {
  // When the ActionActor receives a message containing a Timer it will call the performAction() method on it
  override def receive = {
    case x: Timer =>
      x.performAction()
    // The Timer get send to an Actor in this ActorSystem with the address /user/out
      context.actorSelection("/user/out") ! x
    case _ =>
      println("default")
  }
}
