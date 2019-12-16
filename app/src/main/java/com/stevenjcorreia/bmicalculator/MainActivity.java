package com.stevenjcorreia.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView resultValue;
    private EditText imperialHeightFeetValue, imperialHeightInchesValue, metricHeightValue, weightValue;
    private Switch imperialMetricSwitch;
    private LinearLayout imperialHeightContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();

        addTextListeners();
    }

    private void addTextListeners() {
        imperialHeightFeetValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (weightValue.getText().toString().isEmpty())
                    return;

                if (imperialHeightFeetValue.getText().toString().isEmpty() && imperialHeightInchesValue.getText().toString().isEmpty())
                    return;

                boolean isMetric = !imperialMetricSwitch.isChecked();

                resultValue.setText(calculateBMI(isMetric));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imperialHeightInchesValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (weightValue.getText().toString().isEmpty())
                    return;

                if (imperialHeightFeetValue.getText().toString().isEmpty() && imperialHeightInchesValue.getText().toString().isEmpty())
                    return;

                boolean isMetric = !imperialMetricSwitch.isChecked();

                resultValue.setText(calculateBMI(isMetric));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imperialMetricSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearValues(!isChecked);
                if (isChecked) {
                    imperialMetricSwitch.setText(getResources().getString(R.string.switch_on));
                    weightValue.setHint(getResources().getString(R.string.imperial_weight_placeholder));

                    imperialHeightContainer.setVisibility(View.VISIBLE);
                    metricHeightValue.setVisibility(View.GONE);
                } else {
                    imperialMetricSwitch.setText(getResources().getString(R.string.switch_off));
                    weightValue.setHint(getResources().getString(R.string.metric_weight_placeholder));

                    imperialHeightContainer.setVisibility(View.GONE);
                    metricHeightValue.setVisibility(View.VISIBLE);
                }
            }
        });

        metricHeightValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (weightValue.getText().toString().isEmpty())
                    return;

                if (metricHeightValue.getText().toString().isEmpty())
                    return;

                boolean isMetric = !imperialMetricSwitch.isChecked();

                resultValue.setText(calculateBMI(isMetric));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        weightValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (weightValue.getText().toString().isEmpty())
                    return;

                boolean isMetric = !imperialMetricSwitch.isChecked();

                if (isMetric) {
                    if (metricHeightValue.getText().toString().isEmpty())
                        return;
                } else {
                    if (imperialHeightFeetValue.getText().toString().isEmpty() && imperialHeightInchesValue.getText().toString().isEmpty())
                        return;
                }

                resultValue.setText(calculateBMI(isMetric));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void clearValues(boolean isMetric) {
        resultValue.setText(getResources().getString(R.string.initial_bmi));
        weightValue.getText().clear();

        if (isMetric) {
            metricHeightValue.getText().clear();
        }
        else {
            imperialHeightFeetValue.getText().clear();
            imperialHeightInchesValue.getText().clear();
        }
    }

    private String calculateBMI(boolean isMetric) {
        int feet = isMetric ? 0 : Integer.parseInt(imperialHeightFeetValue.getText().toString());
        int inches = isMetric ? 0 : feet * 12 + Integer.parseInt(imperialHeightInchesValue.getText().toString());
        int metric_height = isMetric ? Integer.parseInt(metricHeightValue.getText().toString()) : 0;
        double weight = Double.parseDouble(weightValue.getText().toString());

        double result = isMetric ? weight / (metric_height * metric_height / 10000.) : weight * 0.4535924 / ((inches * 2.54f) * (inches * 2.54f) / 10000.);

        return getCategory(result) + " - " + String.format(Locale.US, "%.2f", result);
    }

    private String getCategory(double BMI) {
        if (BMI < 18.5) {
            return getResources().getString(R.string.underweight);
        } else if (BMI >= 18.5 && BMI <= 24.9) {
            return getResources().getString(R.string.normal);
        } else if (BMI >= 25 && BMI <= 29.9) {
            return getResources().getString(R.string.overweight);
        } else {
            return getResources().getString(R.string.obese);
        }
    }

    private void initializeComponents() {
        resultValue = findViewById(R.id.resultValue);

        imperialHeightFeetValue = findViewById(R.id.imperialHeightFeetValue);
        imperialHeightInchesValue = findViewById(R.id.imperialHeightInchesValue);
        metricHeightValue = findViewById(R.id.metricHeightValue);
        weightValue = findViewById(R.id.weightValue);

        imperialMetricSwitch = findViewById(R.id.imperialMetricSwitch);

        imperialHeightContainer = findViewById(R.id.imperialHeightContainer);
    }
}
