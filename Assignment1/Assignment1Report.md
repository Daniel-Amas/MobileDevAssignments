# Report: Android App Development - Layouts, Views, and Intents
## 1. Introduction
This report provides an overview of how layouts, views, and intents are applied in the Android app developed to calculate the Equated Monthly Installment (EMI) based on mortgage details. These concepts are essential in building interactive and user-friendly Android applications.

## 2. Layouts
Layouts are used to define the structure and arrangement of user interface elements within the Android app. For this project, ConstraintLayout is primarily used in both the main activity (activity_main.xml) and the information activity (activity_info.xml).

### 2.1 ConstraintLayout
ConstraintLayout is used in this app because it provides flexibility in positioning UI elements relative to each other and the parent layout. Both the main activity and the info activity leverage ConstraintLayout to create a structured interface.

Main Activity (activity_main.xml):
In the main activity, the layout organizes input fields, a button to calculate EMI, and a text view to display the result:

The input fields (EditText) for mortgage amount, tenure, and interest rate are placed sequentially, with alignment constraints to ensure they remain centered.
The "Calculate EMI" button is placed below the input fields, aligned centrally using constraints.
The TextView to display the EMI result is located below the input fields and updates dynamically after calculation.
This layout ensures that elements are aligned responsively across various screen sizes and orientations​(activity_main).

Info Activity (activity_info.xml):
The information activity layout is also built using ConstraintLayout, with text instructions arranged vertically. A floating action button allows users to exit the info screen:

The instructions (TextView elements) are organized in a step-by-step format, guiding users on how to use the app.
The floating action button is placed at the top right to allow users to close the info screen easily.
This design provides clarity and ensures that users can easily navigate back to the main screen after viewing the instructions​(activity_info).

## 3. Views
Views are the building blocks of the Android user interface. Several view types are used in the app to create a functional mortgage calculator.

### 3.1 EditText
EditText views allow users to input data such as the mortgage amount, tenure, and interest rate. These inputs are crucial for the EMI calculation:
The EditText fields accept numeric input, which is used in the calculation logic in MainActivity.java.

### 3.2 Button
A Button view is used to trigger the calculation of the EMI when clicked:
This button interacts with the calculation logic in the Java file, updating the result view based on the user input.

### 3.3 TextView
TextView is used both for displaying static instructions and showing dynamic results:
In the main activity, a TextView displays the calculated EMI based on user inputs.
In the info activity, multiple TextView elements provide step-by-step instructions for the user.

## 4. Intents
Intents allow navigation between different activities in the app. The app uses explicit intents to switch between the Main Activity and the Info Activity.

### 4.1 Explicit Intent
An explicit intent is triggered when the user clicks on the "How to Use" text view in the main activity, opening the info activity (InfoActivity.java). This provides users with instructions on how to use the app.

### 4.2 Back Navigation
In the info activity, a floating action button (btnCloseInfo) allows users to navigate back to the main activity using another explicit intent:
This ensures smooth navigation between the main and information screens, improving user experience.

## 5. Conclusion
This project effectively demonstrates the use of layouts, views, and intents in Android app development:

Layouts (ConstraintLayout) are used to design responsive and well-structured interfaces.
Views (EditText, Button, TextView) provide interactive elements for input, action, and output.
Intents facilitate seamless navigation between multiple screens, enhancing the app's functionality.
These components are fundamental to creating interactive and user-friendly Android applications.
