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
import android.widget.ToggleButton;


public class SelectOptionsActivity extends AppCompatActivity {
    static Spinner type_spinner = null;
    static Spinner amount_spinner = null;
    static ToggleButton starter_toggle = null;
    static Button details_button = null;
    public final static int AMOUNT = 0;
    public final static String TYPE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        details_button = (Button) this.findViewById(R.id.details_button);
        amount_spinner = (Spinner) this.findViewById(R.id.pizza_amount_spinner);

        type_spinner = (Spinner) this.findViewById(R.id.pizza_type_spinner);
        ArrayAdapter typeSpinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new PizzaType[]{
                new PizzaType(0, "Type of Pizza?", 0),
                new PizzaType(1, "Thin", 3),
                new PizzaType(2, "New York", 4),
                new PizzaType(3, "Pan", 5)
        });
        Integer[] amounts = new Integer[]{2, 4, 6, 8, 10, 12};
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
                    int ratio = 0;
                    switch (i) {
                        case 1:
                            ratio = 65;
                            break;
                        case 2:
                            ratio = 67;
                            break;
                        case 3:
                            ratio = 70;

                        default:
                            ratio = 0;
                            break;
                    }
                    Log.d("type selected", "turn amount on" + amount_spinner);
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
                    findViewById(R.id.continue_button_container).setVisibility(View.VISIBLE);
                    int amount = (i + 1) * 2;
                    Log.d("amount selected", "turn amount on" + amount);

                }
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        details_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String type = type_spinner.getItemAtPosition(type_spinner.getSelectedItemPosition()).toString();
                String amount = amount_spinner.getItemAtPosition(amount_spinner.getSelectedItemPosition()).toString();
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("TYPE", type);
                intent.putExtra("AMOUNT", amount);
                Log.d("select", "getting type " + type + " and amount " + amount );
                startActivity(intent);

            }
        });
    }
}