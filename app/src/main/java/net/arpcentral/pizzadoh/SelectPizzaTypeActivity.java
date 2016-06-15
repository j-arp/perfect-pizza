package net.arpcentral.pizzadoh;
import android.app.Application;
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



public class SelectPizzaTypeActivity extends AppCompatActivity {
    static Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_type);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.restart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), WelcomeActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner) this.findViewById(R.id.pizza_type_spinner);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new PizzaType[]{
                new PizzaType(0, "Type of Pizza?", 0),
                new PizzaType(1, "Thin", 3),
                new PizzaType(2, "New York", 4),
                new PizzaType(3, "Pan", 5)
        });


        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        final int iCurrentSelection = spinner.getSelectedItemPosition();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    int ratio = 0;
                    switch (i){
                        case 1:  ratio = 65;
                                break;
                        case 2:  ratio = 67;
                                break;
                        case 3:  ratio = 70;

                        default: ratio = 0;
                                break;
                    }

                    FloatingActionButton fab_c = (FloatingActionButton) findViewById(R.id.continue_to_amount);
                    fab_c.show();

                }


            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }

        });

    }
}