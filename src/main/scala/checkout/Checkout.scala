package checkout

import model.{Discount, ItemPricingRule}

case class Checkout(pricingRules: Map[String, ItemPricingRule]) {
  var basket: Map[String, Int] = Map()

  def scanToAdd(item: String): Map[String, Int] = {
    basket += (item -> (basket.getOrElse(item, 0) + 1))
    basket
  }


  def total(): BigDecimal = {
    basket.toList.map { item =>
      val itemPricingRule = pricingRules.get(item._1)
      calculateBasketItemPrice(itemPricingRule.get, item._2)
    }.sum
  }

//
//  def checkItems(): Map[String,Int] = {
//  //  basket.keySet.contains("Apple")
//    if(basket.contains("Apple") && basket.contains("Banana")){
//      (basket.get("Apple"), basket.get("Banana")) match {
//        case ("Apple","Banana") =>
//        case ("Banana", "Apple") =>
//
//      }
//    }
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