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

  // This method will return a RunnableGraph. A RunnableGraph is an Akka Stream that has no open input or outputs.
  def createGraph(timerList: ArrayBuffer[Timer]) = {
    // A Sink is defined. This particular Sink will call the stopClock() method of any element it receives.
    val out = Sink.foreach[Timer](x => x.stopClock())

    // We create a RunnableGraph using the Graph domain-specific language.
    RunnableGraph.fromGraph(GraphDSL.create(out) { implicit b => out =>
      // We define a Source. This particular Source takes a list and will pass the elements of this list through the Stream.
      val in = b.add(Source(timerList.toList))
      // We define a Flow. This particular Flow will call the performAction() method of any element it receives and return it as a Future.
      val f1 = Flow[Timer].mapAsyncUnordered(5)(x => Future {
        x.performAction()
      })

      // Using the GraphDSL we can chain the Stream together using the '~>' syntax. This syntax makes it easy to see how the elements flow through the Stream.
      in ~> f1 ~> out

      // The graph is returned as a ClosedShape. This means it has no open inputs or outputs.
      ClosedShape
    })
  }
}
