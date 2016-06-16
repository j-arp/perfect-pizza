package net.arpcentral.pizzadoh;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String type =       intent.getStringExtra("TYPE");
        String amount =       intent.getStringExtra("AMOUNT");

        Ratio ratio = new Ratio(type, Integer.parseInt(amount));

        Log.d("dets act", "Ratio is flour: " + ratio.flour + " water: " + ratio.water);

        TextView flour_details = (TextView)findViewById(R.id.flour_details_data);
        flour_details.setText(ratio.flour.toString());
        TextView water_details = (TextView)findViewById(R.id.water_details_data);
        water_details.setText(ratio.water.toString());

    }

}
