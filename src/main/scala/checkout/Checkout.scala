package checkout

import model.{Discount, ItemPricingRule}

case class Checkout(pricingRules: Map[String, ItemPricingRule]) {
  var basket: Map[String, Int] = Map()
  var  b2:Map[String, Int] =Map()

  def scanToAdd(item: String): Map[String, Int] = {
    basket += (item -> (basket.getOrElse(item, 0) + 1))
    applesBananas
    basket
  }


  def total(): BigDecimal = {
    basket.toList.map { item =>
      val itemPricingRule = pricingRules.get(item._1)
      println(pricingRules.get(item._1))

      calculateBasketItemPrice(itemPricingRule.get, item._2)
    }.sum
  }

  def applesBananas()  = {

    basket match {
      case x if x.keySet("Apple") || x.keySet("Banana") => {
        b2= basket.filter( x => x._1 == "Apple" || x._1 == "Banana")
        b2.toList.map{ item =>
          val singlePrice = pricingRules.get(item._1).get.unitPrice
          println(  (singlePrice, item))

        }
      }
      case _ => basket
    }
    //    if(basket.keySet("Apple") || basket.keySet("Banana")){
    //     b2 = basket.filter( x => x._1 == "Apple" || x._1 == "Banana")
    ////      println(     basket.filter( x => x._1 == "Apple" || x._1 == "Banana"))
    ////      println(basket.filter( x => x._1 == "Apple" || x._1 == "Banana").min._2)
    //
    ////      b2.toList.map{ item =>
    ////        val singlePrice = pricingRules.get(item._1)
    ////        calculateBasketItemPrice(singlePrice.get,  item._2)
    ////
    ////      }.sum.bigDecimal
    //
    //    }
    // basket.filter( x => x._1 == "Apple" || x._1 == "Banana")
  }

  //  def checkItems(item1:String, item2:String):Map[String, Int] = {
  //  //  basket.keySet.contains("Apple")
  //    if(basket.keySet.contains(item1) && basket.keySet.contains(item2)){
  //      (basket.get(item1), basket.get(item2)) match {
  //        case (Some(x), Some(y)) if x < y => basket += (item1 -> (x - 1)); basket
  //        case (Some(y), Some(x)) if y < x => basket += (item2 -> (y - 1)); basket
  //        case _ => basket
  //      }
  //    }
  //    println(basket)
  //  basket
  //  }

  private def calculateBasketItemPrice(itemPricingRule: ItemPricingRule, totalQuantity: Int): BigDecimal = {
    itemPricingRule.discount
      .map(
        discount =>
          totalBasketItemWithPriceDiscount(totalQuantity, discount) +
            totalBasketWithoutDiscount(itemPricingRule, totalQuantity, discount)
      )
      .getOrElse(totalQuantity * itemPricingRule.unitPrice)
  }

  private def totalBasketWithoutDiscount(itemPricingRule: ItemPricingRule, totalQuantity: Int, discount: Discount): BigDecimal = {
    totalQuantity % discount.minimumQuantity * itemPricingRule.unitPrice
  }

  private def totalBasketItemWithPriceDiscount(totalQuantity: Int, discount: Discount): BigDecimal = {

    totalQuantity / discount.minimumQuantity * discount.specialPrice
  }
}