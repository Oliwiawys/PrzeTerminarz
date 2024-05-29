# PrzeTerminarz

PrzeTerminarz is a mobile application designed to help users track and manage the expiration dates of various products in the categories: Food, Medicine, and Cosmetics. The app aims to reduce product wastage by tracking expiration dates and facilitating the use of products before they expire.

## Features

### 1. List Screen

- **Product List Display**: Shows a list of entered products with filtering options by category and product status (valid/expired).
- **List Elements**: Each list item includes the product name, expiration date, category, and quantity (if specified).
- **Sorting**: The list is automatically sorted by product expiration dates, from shortest to longest.
- **Summary**: The screen provides a summary of the total number of displayed items.
- **Item Selection**: Selecting a list item allows for viewing and editing the product details if the product is still valid; otherwise, a message indicates that the product cannot be edited.
- **Long Press Deletion**: Long pressing an item shows an alert asking for confirmation to delete the item from the list (if the product is still valid). Upon confirmation, the item is removed, and the summary is updated.
- **Add New Item**: A button to add a new product is available on the screen.
- **RecyclerView Implementation**: The list is implemented using the RecyclerView graphical component.

### 2. Add/Edit Product Screen

- **Screen Activation**: Triggered by clicking the add button or by editing an existing list entry.
- **Data Entry/Editing**: Allows setting or changing the product data and includes a save button for the entered changes.
- **Category Selection**: Choose the category using a dropdown list.
- **Date Picker**: Enter the expiration date using a date picker control.
- **Data Validation**: Validates the entered data (date cannot be in the past, name cannot be empty, if quantity is entered, it must be a numerical value and the unit must be specified).

### Data Persistence

Data saving in persistent storage is outside the scope of this project. Changes made will reset upon each app closure.

## Sample Data

A set of sample data is loaded each time the application is launched to showcase all functionalities.

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Oliwiawys/PrzeTerminarz
2. Open the project in your preferred IDE (e.g., Android Studio).
3. Build and run the application on an emulator or a physical device.

## Usage

1. **Adding a Product**:
- Click the add button.
- Fill in the product details.
- Save the product.

2. **Editing a Product**:
- Select a valid product from the list.
- Edit the details and save.

3. **Deleting a Product**:
- Long press a valid product.
- Confirm deletion.
