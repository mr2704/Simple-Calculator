package com.example.easycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result_textview, Solution_textview;
    MaterialButton buttonC, button_open_bracket, button_closed_Bracket;
    MaterialButton button_Divide, button_Multiply, button_Subtract, button_Addition, button_Equals;
    MaterialButton button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9;
    MaterialButton button_AC, button_Decimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        result_textview = findViewById(R.id.result_textView);
        Solution_textview = findViewById(R.id.Solution_textView);

        // Assign IDs to buttons
        assignId(buttonC, R.id.button_c);
        assignId(button_open_bracket, R.id.button_open_bracket);
        assignId(button_closed_Bracket, R.id.button_closed_bracket);
        assignId(button_Divide, R.id.button_Divide);
        assignId(button_Multiply, R.id.button_Multiply);
        assignId(button_Addition, R.id.button_Addition);
        assignId(button_Subtract, R.id.button_Subtract);
        assignId(button_Equals, R.id.button_Equals);
        assignId(button_0, R.id.button_0);
        assignId(button_1, R.id.button_1);
        assignId(button_2, R.id.button_2);
        assignId(button_3, R.id.button_3);
        assignId(button_4, R.id.button_4);
        assignId(button_5, R.id.button_5);
        assignId(button_6, R.id.button_6);
        assignId(button_7, R.id.button_7);
        assignId(button_8, R.id.button_8);
        assignId(button_9, R.id.button_9);
        assignId(button_Decimal, R.id.button_Decimal);
        assignId(button_AC, R.id.button_AC);

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = Solution_textview.getText().toString();

        if (buttonText.equals("AC")) {
            Solution_textview.setText("");
            result_textview.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            String result = getResult(dataToCalculate);
            result_textview.setText(result);
            return;
        }
        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate += buttonText;
        }

        Solution_textview.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Error")){
            result_textview.setText(finalResult);
        }
    }

    private String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1); // Turn off optimization for compatibility
            Scriptable scope = context.initStandardObjects();
            String finalResult = context.evaluateString(scope, data, "JavaScript", 1, null).toString();
            return finalResult;
        } catch (Exception e) {
            return "Error";
        } finally {
            Context.exit();
        }
    }
}
