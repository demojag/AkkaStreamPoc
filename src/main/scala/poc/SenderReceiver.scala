package poc

import akka.actor.ActorSystem
import akka.stream.{ClosedShape, ActorMaterializer}
import akka.stream.scaladsl._
import GraphDSL.Implicits._

import scala.xml.Elem

object SenderReceiver extends App {


  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val jsonList = List[SourceJson] (SourceJson("{\n  \"orderNumber\": \"23421231\",\n  \"customerName\": \"Jon Doe\",\n  \"itemPurchased\": \"Very beautiful item\"\n  }"))

  val source = Source[SourceJson](jsonList)
  val sinkEcho = Sink.foreach[Elem](x=>println(x))

  val sinkLog = Sink.foreach[String](x=>println("Order processed with order number : " + x))

  source.runForeach(i => println(i))(materializer)


  val g = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>

    val bcast = b.add(Broadcast[Order](2))
    source.map(x=> JsonParser.parseJson(x.value)) ~> bcast.in
    bcast.out(0) ~> Flow[Order].map(x=> OrderXmlMapper.mapOrder(x)) ~> sinkEcho
    bcast.out(1) ~> Flow[Order].map(_.orderNumber) ~> sinkLog
    ClosedShape
  })
  g.run()

}