package net.arpcentral.pizzadoh;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


public class SelectOptionsActivity extends AppCompatActivity {
    static Spinner type_spinner = null;
    static Spinner amount_spinner = null;
    static Switch starter_toggle = null;
    static FloatingActionButton calculate_button = null;
    public final static int AMOUNT = 0;
    public final static String TYPE = "";
    public final static String USE_STARTER = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calculate_button = (FloatingActionButton) this.findViewById(R.id.calculate);
        amount_spinner = (Spinner) this.findViewById(R.id.pizza_amount_spinner);
        starter_toggle = (Switch) this.findViewById(R.id.use_a_starter);

        type_spinner = (Spinner) this.findViewById(R.id.pizza_type_spinner);
        ArrayAdapter typeSpinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new PizzaType[]{
                new PizzaType(0, "Type of Pizza?", 0),
                new PizzaType(1, "Thin", 3),
                new PizzaType(2, "New York", 4),
                new PizzaType(3, "Pan", 5)
        });
        Integer[] amounts = new Integer[]{0, 2, 4, 6, 8, 10, 12};
        ArrayAdapter<Integer> amountSpinnerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, amounts);
        amountSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        amount_spinner.setAdapter(amountSpinnerArrayAdapter);

        typeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        type_spinner.setAdapter(typeSpinnerArrayAdapter);

        final int iCurrentSelection = type_spinner.getSelectedItemPosition();

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

        starter_toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


            }
        });

        calculate_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String type = type_spinner.getItemAtPosition(type_spinner.getSelectedItemPosition()).toString();
                String amount = amount_spinner.getItemAtPosition(amount_spinner.getSelectedItemPosition()).toString();
                Boolean use_starter = starter_toggle.isChecked();
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("TYPE", type);
                intent.putExtra("AMOUNT", amount);
                intent.putExtra("USING_STARTER", use_starter);
                Log.d("SELECT OPTIONS ACTIVITY", "getting type " + type + " and amount " + amount + " and using a starter? " +  use_starter);
                startActivity(intent);

            }
        });
    }
}