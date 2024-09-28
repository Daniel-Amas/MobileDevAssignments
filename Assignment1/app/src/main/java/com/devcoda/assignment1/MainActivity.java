package com.devcoda.assignment1;

import static com.devcoda.assignment1.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText etMortgageAmount, etTenure, etInterestRate;
    Button btnCalculate;
    TextView tvEMIResult, tvMoreInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etMortgageAmount = findViewById(R.id.etMortgageAmount);
        etTenure = findViewById(R.id.etTenure);
        etInterestRate = findViewById(R.id.etInterestRate);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvEMIResult = findViewById(R.id.tvEMIResult);
        tvMoreInfo = findViewById(R.id.tvMoreInfo);

        //Call the calculate function on click of btnCalculate
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEMI();
            }
        });

        //Launch a new activity to show how to use the app on click of tvMoreInfo
        tvMoreInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                calculateEMI();
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }

        });
    }

    /**
     * function to calculate the EMI
     */
    private void calculateEMI() {
        double principal = Double.parseDouble(etMortgageAmount.getText().toString());
        double annualInterestRate = Double.parseDouble(etInterestRate.getText().toString());
        int tenureYears = Integer.parseInt(etTenure.getText().toString());

        // Convert annual interest rate to monthly and tenure to months
        double monthlyInterestRate = annualInterestRate / (12 * 100);  // converting to percentage
        int tenureMonths = tenureYears * 12;

        // EMI formula
        double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths))
                / (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

        // Display the result
        tvEMIResult.setText(String.format("Your EMI is: %.2f", emi));
    }

    //TODO: Read lecture slides to learn the proper naming conventions
    //TODO: Assignment report in MD
}