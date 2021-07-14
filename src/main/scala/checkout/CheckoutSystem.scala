package checkout

import model.{Discount, ItemPricingRule}

object CheckoutSystem extends App {

  val itemRules = Map(
    "Apple" -> ItemPricingRule(unitPrice = BigDecimal("0.60"), discount = Some(Discount(minimumQuantity = 2, specialPrice = BigDecimal("0.60")))),
    "Orange" -> ItemPricingRule(unitPrice = BigDecimal("0.25"), discount = Some(Discount(minimumQuantity = 3, specialPrice = BigDecimal("0.50")))),
    "Banana" -> ItemPricingRule(unitPrice = BigDecimal("0.20"), discount = Some(Discount(minimumQuantity = 2, specialPrice = BigDecimal("0.20")))

    ))

  val basketRules = Map(

  )

  val basket = Seq("Apple", "Banana", "Orange")
  val checkout = Checkout(itemRules)

  basket.foreach(checkout.scanToAdd)
// basket.foreach(checkout.applesBananas)

  println(checkout.total())
}
