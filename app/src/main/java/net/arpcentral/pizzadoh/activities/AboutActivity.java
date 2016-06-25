package net.arpcentral.pizzadoh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.arpcentral.pizzadoh.R;

public class AboutActivity extends AppCompatActivity {
    static FloatingActionButton back_home_button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        back_home_button = (FloatingActionButton) this.findViewById(R.id.back_home_button);
        back_home_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
            startActivity(intent);
            }
        });

    }

}
