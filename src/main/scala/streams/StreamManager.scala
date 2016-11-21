package streams

import java.util.concurrent.Executors

import akka.stream._
import akka.stream.scaladsl.GraphDSL.Implicits._
import akka.stream.scaladsl.{Flow, GraphDSL, RunnableGraph, Sink, Source}
import shared.Timer

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{ExecutionContext, Future}


/**
 * Created by RemcoW on 9-11-2016.
 */
object StreamManager {
  val executorService = Executors.newCachedThreadPool()
  implicit val executionContext = ExecutionContext.fromExecutorService(executorService)

  def createGraph(timerList: ArrayBuffer[Timer]) = {
    val out = Sink.foreach[Timer](x => x.stopClock())

    RunnableGraph.fromGraph(GraphDSL.create(out) { implicit b => out =>
      val in = b.add(Source(timerList.toList))
      val f1 = Flow[Timer].mapAsyncUnordered(5)(x => Future {
        x.performAction()
      })

      in ~> f1 ~> out

      ClosedShape
    })
  }
}
