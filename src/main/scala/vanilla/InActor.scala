package vanilla

import akka.actor.{Actor, Props}
import shared.Timer

/**
  * Created by RemcoW on 16-11-2016.
  */
class InActor extends Actor {

  val a1, a2, a3, a4, a5 = context.actorOf(Props[ActionActor])
  var next = a1

  override def receive = {
    case x: Timer =>
      next match {
        case `a1` =>
          next ! x
          next = a2
        case `a2` =>
          next ! x
          next = a3
        case `a3` =>
          next ! x
          next = a4
        case `a4` =>
          next ! x
          next = a5
        case `a5` =>
          next ! x
          next = a1
      }
    case _ =>
      println("Something went wrong")
  }
}