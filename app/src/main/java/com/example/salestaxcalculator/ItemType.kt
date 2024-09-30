package com.example.salestaxcalculator

data class ItemType(
    val name: String,
    val taxRate: Double, // Percentage tax rate for this item type (e.g., 8.875%)
    val taxExemptionThreshold: Double = Double.MAX_VALUE // Threshold under which tax does not apply (default: no exemption)
)
