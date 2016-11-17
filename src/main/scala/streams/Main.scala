package streams

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import shared.{Calc, Timer}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext

/**
  * Created by RemcoW on 9-11-2016.
  */
object Main extends App {
  implicit val system = ActorSystem("Stream_PoC")
  implicit val materializer = ActorMaterializer()
  val executorService = Executors.newCachedThreadPool()
  implicit val executionContext = ExecutionContext.fromExecutorService(executorService)

  // Amount of elements to be send through the stream
  var requestAmount: Int = 1000
  val timers = ArrayBuffer[Timer]()

  for (i <- 1 to requestAmount) {
    val timer = new Timer(i)
    timers.append(timer)
  }
  val graph = StreamManager.createGraph(timers)
  for (timer <- timers) {
    timer.startClock()
  }
  graph.run()
  Thread.sleep(1000)
  Calc.calculateThroughput(timers)
  System.exit(0)
}

