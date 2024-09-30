# **NY Sales Tax Calculator**

The NY Sales Tax Calculator is an Android app designed to help users calculate sales tax based on item type and location within New York State and New York City. The app allows for input of an item name and price, selection of item type, and a county-specific tax calculation.
Features

    County-based Sales Tax Calculation: Automatically applies the correct tax rate based on the selected county.
    Clothing/Footwear Tax Exemption: Automatically handles tax exemptions for clothing or footwear items under $110.
    Restaurant Tax Calculation: Includes the NYC-specific 8.875% sales tax rate for restaurant meals or prepared food.
    Item List Management: Optionally add multiple items to the list and calculate the total tax.
    Custom Keypad: Integrated numeric keypad for easier price input.
    Dynamic Calculation: You can calculate tax for individual items without adding them to the list.

## Screenshots
![Screenshot 2024-09-30 164821](https://github.com/user-attachments/assets/3ee294ba-04fe-45d1-904e-85ad553cef39)
![Screenshot 2024-09-30 165208](https://github.com/user-attachments/assets/6e86c72b-b924-4952-94d5-838d815e9b86)
![Screenshot 2024-09-30 165139](https://github.com/user-attachments/assets/3e290d81-a981-4242-b5bc-958528f77168)
![Screenshot 2024-09-30 165037](https://github.com/user-attachments/assets/8696f7ec-dc7e-4ecd-ae6c-595f67563bf7)

## Technologies Use

    Kotlin: Primary language used for development.
    Android SDK: Framework for Android app development.
    RecyclerView: Used for displaying a list of items dynamically.
    Material Design Components: Used for the UI, including TextInputLayouts, Buttons, and Spinners.

## How to Install
Download APK

    Visit the Releases page.
    Download the latest APK.
    Install the APK on your Android device.
        You may need to enable "Install from unknown sources" in your Android settings.

## Build from Source

    Clone the repository to your local machine:

    bash

    git clone https://github.com/username/repo.git

    Open the project in Android Studio.
    Build and run the app on an emulator or connected Android device.

## How to Use

    Select a County: Choose a county from the dropdown.
    Choose an Item Type: Select from "Clothing/Footwear" or "Restaurant."
    Enter Item Name (Optional): Input an item name (max 12 characters).
    Enter Item Price: Enter the price (up to 5 decimal points).
    Add Item (Optional): Add the item to the list if calculating multiple items.
    Calculate Total: Press "Calculate" to display the total before and after tax.

## Development

### If you want to contribute or make changes:

    Fork this repository.
    Create a new branch (git checkout -b feature-branch).
    Make your changes and commit (git commit -am 'Add new feature').
    Push to your branch (git push origin feature-branch).
    Open a Pull Request.

### If you have any questions or feedback, feel free to contact me at:

    Email: ej0393@outlook.com
    GitHub: ej0306
