/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberOfCoffe = 0 , priceOfCoffe = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    /**
     * This Method Calculates Total Order Price
     *
     * Return the Total Order Price
     */

    private int calculatePrice()
    {
        return numberOfCoffe * priceOfCoffe;
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        int totalOrderPrice = calculatePrice();
        CheckBox whippedCream = findViewById(R.id.whippedCreamCheckBox);
        CheckBox chocolate = findViewById(R.id.chocolateCheckBox);
        if(numberOfCoffe > 0) {
            if (whippedCream.isChecked())
                totalOrderPrice = totalOrderPrice + 3 * numberOfCoffe;
            if (chocolate.isChecked())
                totalOrderPrice = totalOrderPrice + 5 * numberOfCoffe;
            displayPrice(totalOrderPrice);
        }
        else
        {
            Toast.makeText(this, "Please Specify Order Quantity First", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(number + "");
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        EditText v =  findViewById(R.id.nameEditText);
        String name = v.getText().toString();
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("Total: " + NumberFormat.getCurrencyInstance().format(number) + "\nThank you " + name);
    }

    /**
     * This method increases numberOfCoffe by one
     */
    public void increment(View view)  {
        if(numberOfCoffe <= 50)
        numberOfCoffe++;
        display(numberOfCoffe);
    }

    /**
     * This method decreases numberOfCoffe by one
     */
    public void decrement(View view) {
        if(numberOfCoffe != 0) {
            numberOfCoffe--;
            display(numberOfCoffe);
        }
    }

    /**
     * This method gives 1 coffe as sample for free
     */
    public void freeCoffe(View view)
    {
        CheckBox whippedCream = findViewById(R.id.whippedCreamCheckBox);
        CheckBox chocolate = findViewById(R.id.chocolateCheckBox);

        if(whippedCream.isChecked() || chocolate.isChecked())
            Toast.makeText(this,"Sorry, Free Coffe Comes Without Toppings",Toast.LENGTH_LONG).show();
        else {
            TextView freeCoffePrice = (TextView) findViewById(R.id.price_text_view);
            freeCoffePrice.setText("Free Coffe ^^");
            TextView sampleQuantity = (TextView) findViewById(R.id.quantity_text_view);
            sampleQuantity.setText("" + 1);
            numberOfCoffe = 1;
        }
    }

    /**
     *  This Method Sends Order Details To Customer By Email
     */

    public void sendDetailsByEmail(View view)
    {
        EditText e = findViewById(R.id.nameEditText);
        String name = "Name: " + e.getText().toString();

        String coffeQuantity = "Quantity: " + numberOfCoffe;

        int totalPrice = calculatePrice();

        CheckBox whippedCream = findViewById(R.id.whippedCreamCheckBox);
        CheckBox chocolate = findViewById(R.id.chocolateCheckBox);
        String toppings = "Toppings: ";
        if(whippedCream.isChecked()) {
                toppings = toppings + "Whipped Cream,";
                totalPrice = totalPrice + numberOfCoffe * 3;
        }
        if(chocolate.isChecked()) {
                toppings = toppings + " Chocolate";
                totalPrice = totalPrice + numberOfCoffe * 5;
        }

        String all = name + "\n" + coffeQuantity + "\n" + toppings + "\nTotal Price: $" + totalPrice + "\nThank You!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "ahmedreda@fci.helwan.edu.eg");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order Summary");
        intent.putExtra(Intent.EXTRA_TEXT,all);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}