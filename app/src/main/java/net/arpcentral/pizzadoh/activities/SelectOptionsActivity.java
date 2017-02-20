package net.arpcentral.pizzadoh.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import net.arpcentral.pizzadoh.PizzaDohApp;
import net.arpcentral.pizzadoh.R;
import net.arpcentral.pizzadoh.activities.DetailsActivity;
import net.arpcentral.pizzadoh.models.PizzaType;


import java.util.HashMap;


public class SelectOptionsActivity extends AppCompatActivity {
    static Spinner type_spinner = null;
    static Spinner amount_spinner = null;
    static Switch starter_toggle = null;
    static FloatingActionButton calculate_button = null;
    static TextView using_starter_question = null;

    public final static int AMOUNT = 0;
    public final static String TYPE = "";
    public final static String USE_STARTER = "";
    public final static Float FADED = new Float(.4);
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Pizza Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PizzaDohApp application = (PizzaDohApp) getApplication();
        mTracker = application.getDefaultTracker();

        Log.i("GA", "Analyticing Options screen");
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("View")
                .setAction("View Options")
                .build());
        // Assign all the UI elements
        using_starter_question = (TextView) this.findViewById(R.id.using_starter_question);
        calculate_button = (FloatingActionButton) this.findViewById(R.id.calculate);
        amount_spinner = (Spinner) this.findViewById(R.id.pizza_amount_spinner);
        starter_toggle = (Switch) this.findViewById(R.id.use_a_starter);
        type_spinner = (Spinner) this.findViewById(R.id.pizza_type_spinner);

        // Array Adapter for Pizza Type Spinner
        ArrayAdapter typeSpinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new PizzaType[]{
                new PizzaType(0, "Type of Pizza?", 0),
                new PizzaType(1, "Thin", 3),
                new PizzaType(2, "New York", 4),
                new PizzaType(3, "Pan", 5)
        });

        // Array Adapter for Amount Spinner
        Integer[] amounts = new Integer[]{0, 2, 4, 6, 8, 10, 12, 14, 16};
        ArrayAdapter<Integer> amountSpinnerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, amounts);
        amountSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        amount_spinner.setAdapter(amountSpinnerArrayAdapter);

        typeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        type_spinner.setAdapter(typeSpinnerArrayAdapter);

        // Get the element from the pizza type spinner
        final int iCurrentSelection = type_spinner.getSelectedItemPosition();

        // Now get the item if the valud changes
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    findViewById(R.id.select_amount_spinner_container).setVisibility(View.VISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }

        });

        // Update the amount if chnage
        amount_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    findViewById(R.id.select_if_using_starter_container).setVisibility(View.VISIBLE);
                    findViewById(R.id.calculate).setVisibility(View.VISIBLE);
                    String amount = amount_spinner.getItemAtPosition(amount_spinner.getSelectedItemPosition()).toString();
                    Log.d("amount selected", "turn amount on" + amount);

                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        // if the starter toggle switch gets changed
        starter_toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            if ( starter_toggle.isChecked() ){
                using_starter_question.setAlpha(1);
            }

            else {
                using_starter_question.setAlpha(FADED);
            }
            }
        });

        // Once all options are ready, send to next activity
        calculate_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            // Get values from UI elements
            String type = type_spinner.getItemAtPosition(type_spinner.getSelectedItemPosition()).toString();
            String amount = amount_spinner.getItemAtPosition(amount_spinner.getSelectedItemPosition()).toString();
            Boolean use_starter = starter_toggle.isChecked();

                Log.i("GA", "Analyticing Options screen");
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Click")
                        .setAction("Calculate Measurements")
                        .build());

            Intent intent = new Intent(view.getContext(), DetailsActivity.class);

            HashMap<String,String> batch_values = new HashMap<String, String>();
                batch_values.put("TYPE", type);
                batch_values.put("AMOUNT", amount);
                batch_values.put("USING_STARTER", use_starter.toString());
            intent.putExtra("BATCH", batch_values);
            startActivity(intent);
            }
        });
    }
}