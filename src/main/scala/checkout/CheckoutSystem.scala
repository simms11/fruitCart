package checkout

import model.{Discount, ItemPricingRule}

object CheckoutSystem extends App {

  val rules = Map(
    "Apple" -> ItemPricingRule(unitPrice = BigDecimal("0.6"), discount = Some(Discount(minimumQuantity = 2, specialPrice = BigDecimal("0.6")))),
    "Orange" -> ItemPricingRule(unitPrice = BigDecimal("0.25"), discount = Some(Discount(minimumQuantity = 3, specialPrice = BigDecimal("0.5")))),
    "Banana" -> ItemPricingRule(unitPrice = BigDecimal("0.20"), discount = Some(Discount(minimumQuantity = 1, specialPrice = BigDecimal("0.20")))

    ))

  val basket = Seq("Apple", "Orange", "Banana")
  val checkout = Checkout(rules)

  basket.foreach(checkout.scanToAdd)
  println(checkout.total())
}
