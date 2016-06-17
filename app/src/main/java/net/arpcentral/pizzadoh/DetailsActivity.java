package net.arpcentral.pizzadoh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    static FloatingActionButton reset_button = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DETAIL ACTIVITY", "REACHED");
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);





        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My title");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        reset_button = (FloatingActionButton) this.findViewById(R.id.reset_button);
        String type =       intent.getStringExtra("TYPE");
        String amount =       intent.getStringExtra("AMOUNT");
        Boolean use_starter = intent.getBooleanExtra("USING_STARTER", false);
        Ratio ratio = new Ratio(type, Integer.parseInt(amount), use_starter);
        Log.d("DETAIL ACTIVITY", "Ratio is flour: " + ratio.flour + " water: " + ratio.water + " and are you using a starter? " + use_starter);

        if (use_starter){
            TextView flour_starter_details = (TextView)findViewById(R.id.flour_starter_data);
            flour_starter_details.setText("100");
            TextView water_starter_details = (TextView)findViewById(R.id.water_starter_data);
            water_starter_details.setText("150");
        }

        else {
            RelativeLayout starter_container = (RelativeLayout)findViewById(R.id.starter_container);
            starter_container.setVisibility(View.GONE);

        }

        int adjusted_flour = (int) Math.round(ratio.getAdjustedFlour());
        int adjusted_water = (int) Math.round(ratio.getAdjustedWater());

        TextView flour_details = (TextView)findViewById(R.id.flour_details_data);
        flour_details.setText(Integer.toString(adjusted_flour));
        TextView water_details = (TextView)findViewById(R.id.water_details_data);
        flour_details.setText(Integer.toString(adjusted_water));


        reset_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
                startActivity(intent);

            }
        });

    }

}
