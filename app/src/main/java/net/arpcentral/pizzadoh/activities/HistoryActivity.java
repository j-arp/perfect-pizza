package net.arpcentral.pizzadoh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.arpcentral.pizzadoh.R;
import net.arpcentral.pizzadoh.activities.DetailsActivity;
import net.arpcentral.pizzadoh.activities.WelcomeActivity;
import net.arpcentral.pizzadoh.models.History;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    static FloatingActionButton clear_history_button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Past Activity");



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView history_list = (ListView) findViewById(R.id.history_list);
        final ArrayList<History> all_history = History.fetch(this);
        final ArrayList<History> no_history = new ArrayList<History>();
        clear_history_button = (FloatingActionButton) this.findViewById(R.id.clear_history_button);

        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.history_list_item, R.id.history_list_item_text, all_history);
        final ArrayAdapter clear_adapter = new ArrayAdapter(this, R.layout.history_list_item, R.id.history_list_item_text,no_history);
        history_list.setAdapter(adapter);

        clear_history_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("HISTORY ACT", "clicked clear all");
                History.clear(view.getContext());
                history_list.setAdapter(clear_adapter);
            }
        });

        history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 1
                History selectedHistory = all_history.get(position);


                String type = selectedHistory.type;
                String amount = Integer.toString(selectedHistory.quantity);
                Boolean use_starter = selectedHistory.using_starter();

                Intent intent = new Intent(view.getContext(), DetailsActivity.class);

                intent.putExtra("TYPE", type);
                intent.putExtra("AMOUNT", amount);
                intent.putExtra("USING_STARTER", use_starter);

                intent.putExtra("STARTING_FLOUR", Integer.toString(selectedHistory.starter_flour));
                intent.putExtra("STARTING_WATER", Integer.toString(selectedHistory.starter_water));
                Log.d("HISTORY ACT", "clicked on histoury: " + selectedHistory);
                startActivity(intent);
                // 2
                // Intent detailIntent = new Intent(context, RecipeDetailActivity.class);

                // 3
                // detailIntent.putExtra("title", selectedRecipe.title);
                // detailIntent.putExtra("url", selectedRecipe.instructionUrl);

                // 4
                // startActivity(detailIntent);
            }

        });

    }
}
