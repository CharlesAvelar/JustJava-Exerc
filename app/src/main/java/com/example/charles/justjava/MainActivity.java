package com.example.charles.justjava;

import com.example.charles.justjava.R;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charles.justjava.R;

import java.text.NumberFormat;

/**
 * Este aplicativo exibe um formulário para pedir café .
 */
public class MainActivity extends Activity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Este método é chamado quando o botão é clicado fim .
     */
    public void submitOrder(View view) {
        EditText personName = (EditText) findViewById(R.id.name);
        String name = personName.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        RadioButton medium = (RadioButton) findViewById(R.id.radio_medium);
        boolean isMedium = medium.isChecked();
        RadioButton large = (RadioButton) findViewById(R.id.radio_large);
        boolean isLarge = large.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate, isMedium, isLarge);

        composeEmail(name, createOrderSummary(price, hasWhippedCream, hasChocolate, name));
    }

    /**
     * Calcula o preço da ordem .
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate, boolean isMedium, boolean isLarge) {
        int basePrice = 5;
        if (hasWhippedCream) basePrice += 1;
        if (hasChocolate) basePrice += 2;
        if (isMedium) basePrice += 2;
        if (isLarge) basePrice += 4;
        return quantity * basePrice;
    }

    /**
     * @param price      da ordem
     * @param hasWhippedCream   se é ou não o usuário deseja a cobertura chantilly
     * @return  retorna resumo do pedido
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        return "Name: " + name + "\nAdd whipped cream? " + hasWhippedCream + "\nAdd chocolate? " + hasChocolate + "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!";
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(getApplicationContext(), "You cannot have more than 100 coffees", Toast.LENGTH_LONG).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     *
     Este método é chamado quando o botão de subtração é clicado.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(getApplicationContext(), "You cannot have less than 1 coffee", Toast.LENGTH_LONG).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * Este método mostra o valor a quantidade indicada na tela.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void composeEmail(String name, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}