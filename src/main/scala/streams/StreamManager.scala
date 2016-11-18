package streams

import akka.NotUsed
import akka.stream._
import akka.stream.scaladsl.{Balance, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}
import GraphDSL.Implicits._
import shared.Timer

import scala.collection.mutable.ArrayBuffer


/**
 * Created by RemcoW on 9-11-2016.
 */
object StreamManager {
  def createGraph(timerList: ArrayBuffer[Timer]) = {
    val out = Sink.foreach[Timer](x => x.stopclock())

    RunnableGraph.fromGraph(GraphDSL.create(out) { implicit b => out =>
      val in = b.add(Source(timerList.toList))
      val f1 = Flow[Timer].map(x => {
        x.performAction()
      })
      val balanced = b.add(balancer(f1).async)

      in ~> balanced ~> out

      ClosedShape
    })
  }

  private def balancer(worker: Flow[Timer, Timer, Any]): Flow[Timer, Timer, NotUsed] = {
    Flow.fromGraph(GraphDSL.create() { implicit builder =>
      val bal = builder.add(Balance[Timer](5, waitForAllDownstreams = false))
      val merge = builder.add(Merge[Timer](5))
      (1 to 5).foreach { _ => bal ~> worker.async ~> merge}
      FlowShape(bal.in, merge.out)
    })
  }
}
