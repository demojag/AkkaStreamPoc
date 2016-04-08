package poc

import scala.xml.Elem
import org.json4s._
import org.json4s.native.JsonMethods._
case class SourceJson(value: String)

case class Order(orderNumber: String, customerName: String, itemPurchased: String)

object OrderXmlMapper {

  def mapOrder(order: Order): Elem = {
    <OrderHeader>
      <Body OrderNumber={order.orderNumber}
            Name={order.customerName}
            Item={order.itemPurchased}/>
    </OrderHeader>

  }
}

object JsonParser {
  implicit val formats = org.json4s.DefaultFormats
  def parseJson(json : String):Order={
    val v = parse(json).extract[Order]
    v
  }
}
