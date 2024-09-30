package com.example.salestaxcalculator

object ItemTypeRepository {
    fun getItemTypes(): List<ItemType> {
        return listOf(
            ItemType(name = "Clothing/Footwear", taxRate = 0.08875, taxExemptionThreshold = 110.0),
            ItemType(name = "Restaurant", taxRate = 0.08875)
        )
    }
}
