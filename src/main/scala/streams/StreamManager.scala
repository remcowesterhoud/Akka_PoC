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
    RunnableGraph.fromGraph(GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      val in = Source(timerList.toList)
      val out = Sink.foreach[Timer](x => x.stopclock())

      val bal = builder.add(Balance[Timer](5))
      val merge = builder.add(Merge[Timer](5))

      val f1, f2, f3, f4, f5 = Flow[Timer].map(x => {
        x.performAction()
      }).async

      in ~> bal ~> f1 ~> merge ~> out
      bal ~> f2 ~> merge
      bal ~> f3 ~> merge
      bal ~> f4 ~> merge
      bal ~> f5 ~> merge

      ClosedShape
    })
  }
}
