package net.arpcentral.pizzadoh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
                startActivity(new Intent(view.getContext(),WelcomeActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner)this.findViewById(R.id.pizza_type_spinner);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new PizzaType[]{
                new PizzaType(1,"Thin", 3),
                new PizzaType(2,"New York", 4),
                new PizzaType(3,"Pan", 5)
        });

        PizzaType pt = new PizzaType(1,"Foobar", 3);
        Log.d("myTag", "This is my message");
        Log.d("MyTag", pt.toString());

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }



}
