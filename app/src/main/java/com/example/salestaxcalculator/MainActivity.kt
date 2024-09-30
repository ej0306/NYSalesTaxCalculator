package com.example.salestaxcalculator

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var countySpinner: Spinner
    private lateinit var itemPriceEditText: EditText
    private lateinit var itemNameEditText: EditText // New EditText for item name
    private lateinit var itemTypeSpinner: Spinner
    private lateinit var resultTextView: TextView
    private lateinit var calculateButton: Button
    private lateinit var addItemButton: Button
    private lateinit var clearButton: Button
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter // Adapter for RecyclerView
    private lateinit var counties: List<County>
    private lateinit var itemTypes: List<ItemType>

    // List to store item data (Item objects containing name and price)
    private val itemList = mutableListOf<Item>()

    // NYC Tax Rate
    private val nycTaxRate = 0.08875
    // Clothing/Footwear Tax Exemption Threshold
    private val clothingExemptionThreshold = 110.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        countySpinner = findViewById(R.id.countySpinner)
        itemPriceEditText = findViewById(R.id.itemPrice)
        itemNameEditText = findViewById(R.id.itemName) // Initialize item name EditText
        itemTypeSpinner = findViewById(R.id.itemTypeSpinner)
        resultTextView = findViewById(R.id.result)
        calculateButton = findViewById(R.id.calculateButton)
        addItemButton = findViewById(R.id.addItemButton)
        clearButton = findViewById(R.id.clearButton)
        itemsRecyclerView = findViewById(R.id.itemsRecyclerView)

        // Limit the item name to 12 characters
        itemNameEditText.filters = arrayOf(InputFilter.LengthFilter(12))

        // Add TextWatcher to limit the price to 5 decimal places
        itemPriceEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty()) {
                    val input = s.toString()
                    val regex = """^\d+(\.\d{0,5})?$""".toRegex()  // Allow up to 5 decimal places

                    if (!regex.matches(input)) {
                        itemPriceEditText.error = "Max 5 decimal places allowed"
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Get the county and item type data from the repositories
        counties = CountyRepository.getCounties()
        itemTypes = ItemTypeRepository.getItemTypes()

        // Set up the county spinner adapter
        val countyNames = counties.map { it.name }
        val countyAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            countyNames
        )
        countyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countySpinner.adapter = countyAdapter

        // Set up the item type spinner
        val itemTypeNames = itemTypes.map { it.name }
        val itemTypeAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            itemTypeNames
        )
        itemTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        itemTypeSpinner.adapter = itemTypeAdapter

        // Set up the RecyclerView
        itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(itemList)
        itemsRecyclerView.adapter = itemAdapter

        // Set up the "Add Item" button listener
        addItemButton.setOnClickListener {
            addItem()
        }

        // Set up the calculate button listener
        calculateButton.setOnClickListener {
            calculateTotal()
        }

        // Set up the "Clear" button listener
        clearButton.setOnClickListener {
            clearAll()
        }
    }

    // Adds the item name and price to the list and updates RecyclerView dynamically
    private fun addItem() {
        val itemPrice = itemPriceEditText.text.toString().toDoubleOrNull()
        val itemName = itemNameEditText.text.toString()

        if (itemPrice != null && itemName.isNotEmpty()) {
            itemList.add(Item(itemName, itemPrice)) // Add both name and price to the list
            itemAdapter.notifyDataSetChanged() // Notify RecyclerView to update
            itemPriceEditText.text.clear() // Clear the price input
            itemNameEditText.text.clear() // Clear the name input
            Toast.makeText(this, "Item added: $itemName - $%.2f".format(itemPrice), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please enter a valid name and price", Toast.LENGTH_SHORT).show()
        }
    }

    // Calculates the total including tax for all items
    private fun calculateTotal() {
        // Get the entered item price (even if not added to the list)
        val currentItemPrice = itemPriceEditText.text.toString().toDoubleOrNull()

        // If the user didn't enter a price, show an error message
        if (currentItemPrice == null && itemList.isEmpty()) {
            Toast.makeText(this, "Please enter a valid item price or add items to the list", Toast.LENGTH_SHORT).show()
            return
        }

        // Calculate the total before tax from the list of items
        var totalBeforeTax = itemList.sumOf { it.price }

        // Include the current item price in the total if it's not null
        if (currentItemPrice != null) {
            totalBeforeTax += currentItemPrice
        }

        var totalWithTax = totalBeforeTax
        val selectedItemType = itemTypeSpinner.selectedItem.toString()

        // Get the selected county's tax rate
        val selectedCountyPosition = countySpinner.selectedItemPosition
        val selectedCounty = counties[selectedCountyPosition]
        val selectedCountyTaxRate = selectedCounty.taxRate / 100.0  // Assuming taxRate is in percentage

        // Apply different tax logic based on item type
        if (selectedItemType == "Clothing/Footwear") {
            if (totalBeforeTax < clothingExemptionThreshold) {
                totalWithTax = totalBeforeTax // No tax if under threshold
                resultTextView.text = "Total Before Tax: $%.2f\nTotal (Tax-Exempt): $%.2f".format(totalBeforeTax, totalWithTax)
            } else {
                val taxAmount = totalBeforeTax * selectedCountyTaxRate
                totalWithTax = totalBeforeTax + taxAmount
                resultTextView.text = "Total Before Tax: $%.2f\nTotal with Tax: $%.2f (Tax: $%.2f)".format(totalBeforeTax, totalWithTax, taxAmount)
            }
        } else if (selectedItemType == "Restaurant") {
            val taxAmount = totalBeforeTax * selectedCountyTaxRate
            totalWithTax = totalBeforeTax + taxAmount
            resultTextView.text = "Total Before Tax: $%.2f\nTotal with Tax: $%.2f (Tax: $%.2f)".format(totalBeforeTax, totalWithTax, taxAmount)
        }
    }

    // Clears all input fields, the list of items, and the RecyclerView
    private fun clearAll() {
        itemList.clear() // Clear the list of items
        itemAdapter.notifyDataSetChanged() // Notify the adapter to update RecyclerView
        resultTextView.text = "Total: $0.00" // Reset the result TextView
        itemPriceEditText.text.clear() // Clear the item price input
        itemNameEditText.text.clear() // Clear the item name input
        countySpinner.setSelection(0) // Reset the county spinner
        itemTypeSpinner.setSelection(0) // Reset the item type spinner
        Toast.makeText(this, "All data cleared", Toast.LENGTH_SHORT).show() // Confirmation message
    }
}
