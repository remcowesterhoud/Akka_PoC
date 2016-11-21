package vanilla

import akka.actor.{Actor, Props}
import shared.Timer

import scala.collection.mutable.ArrayBuffer

/**
 * Created by RemcoW on 16-11-2016.
 */
class InActor extends Actor {

  val a1, a2, a3, a4, a5 = context.actorOf(Props[ActionActor])

  override def receive = {
    case x: ArrayBuffer[Timer] =>
      for (i <- x.indices) {
        val next = i % 5 match {
          case 0 => a1
          case 1 => a2
          case 2 => a3
          case 3 => a4
          case 4 => a5
        }
        next ! x(i)
      }
    case _ =>
      println("Something went wrong")
  }
}