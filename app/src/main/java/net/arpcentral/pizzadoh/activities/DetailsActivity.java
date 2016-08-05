package net.arpcentral.pizzadoh.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.arpcentral.pizzadoh.R;
import net.arpcentral.pizzadoh.models.History;
import net.arpcentral.pizzadoh.models.Ratio;

import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {
    public final String Tag = "DetailsActivity";
    final Integer DEFAULT_WATER = 150;
    final Integer DEFAULT_FLOUR = 100;
    public final static Float FADED = new Float(.4);

    static FloatingActionButton reset_button = null;
    static FloatingActionButton continue_button = null;

    static TextView starter_flour_text_field = null;
    static TextView starter_water_text_field = null;
    static EditText flour_starter_edit_field = null;
    static EditText water_starter_edit_field = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final String TAG = "Details Activity";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Secret To Your Perfect Crust");

        Intent intent = getIntent();
        Log.d(TAG, "getting intent");

        reset_button = (FloatingActionButton) this.findViewById(R.id.reset_button);
        continue_button = (FloatingActionButton) this.findViewById(R.id.continue_button);
        starter_flour_text_field = (TextView) this.findViewById(R.id.flour_starter_data);
        starter_water_text_field = (TextView) this.findViewById(R.id.water_starter_data);

        final HashMap<String, String> batch_values = (HashMap<String, String>)intent.getSerializableExtra("BATCH");
            String type =           batch_values.get("TYPE");
            String amount =         batch_values.get("AMOUNT");
            Boolean use_starter =   batch_values.get("USING_STARTER").equals("true") ? true : false;

            String starting_water_value = batch_values.get("STARTING_WATER");
            String starting_flour_value = batch_values.get("STARTING_FLOUR");

        Log.d("BATCHMAP", "starter went from " + batch_values.get("USING_STARTER") + " to " + use_starter);
        Log.d("BATCHMAP", "s_flour: " + batch_values.get("STARTING_WATER") + " / s_wat: " + batch_values.get("STARTING_FLOUR"));

        int starting_water = DEFAULT_WATER;
        int starting_flour = DEFAULT_FLOUR;
        String starting_water_text = DEFAULT_WATER.toString();
        String starting_flour_text = DEFAULT_FLOUR.toString();

        // set default starter amounts to defaults
        if (starting_flour_value != null){
            Log.d("DETAILS", "set flour int from text cuz its in batch: " + starting_flour_value);
            starting_flour = Integer.parseInt(starting_flour_value);
            starting_flour_text = starting_flour_value;
        }

        if (starting_water_value != null){
            Log.d("DETAILS", "set water int from text cuz its in batch: " + starting_water_value);
            starting_water = Integer.parseInt(starting_water_value);
            starting_water_text = starting_water_value;
        }

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_mood_black_24dp)
                        .setContentTitle("Perfect Pizza")
                        .setContentInfo("Your measurements are waiting for you")
                ;

        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.



        Intent resultIntent = new Intent(this, DetailsActivity.class);
        resultIntent.putExtra("BATCH", batch_values);

        Log.d(TAG, "getting amount and type: " + batch_values.get("AMOUNT") + " / " + batch_values.get("TYPE"));



        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        mNotificationId,
                        resultIntent,
                        PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_CANCEL_CURRENT
                );


        mBuilder.setContentIntent(resultPendingIntent);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());



        RelativeLayout starter_container = (RelativeLayout)findViewById(R.id.starter_container);

        Ratio ratio = new Ratio(type, Integer.parseInt(amount), use_starter, starting_water, starting_flour);

        final TextView info_title_field = (TextView)findViewById(R.id.info_title);

        final TextView flour_starter_text_field = (TextView)findViewById(R.id.flour_starter_data);
        final TextView water_starter_text_field = (TextView)findViewById(R.id.water_starter_data);

        final EditText flour_starter_edit_field = (EditText) findViewById(R.id.flour_starter_data_edit);
        final EditText water_starter_edit_field = (EditText) findViewById(R.id.water_starter_data_edit);

        final TextView flour_text_field = (TextView)findViewById(R.id.flour_details_data);
        final TextView water_text_field = (TextView)findViewById(R.id.water_details_data);
        Log.d("DETAILS ACT", "construct history with " + starting_water + " / " + starting_flour);

        final History history = new History(1, "", starting_water, starting_flour, this);
            history.setType(type);
            history.setQuantity(amount);
            history.save();

        //keep_screen_on_toggle_question.setAlpha(FADED);

        // If Using a starter is checked
        if (use_starter){
            flour_starter_text_field.setText(starting_flour_text);
            water_starter_text_field.setText(starting_water_text);
            history.setStarterWater(starting_water);
            history.setStarterFlour(starting_flour);
        }

        else {
            starter_container.setVisibility(View.GONE);
            history.setStarterWater(0);
            history.setStarterFlour(0);
        }

        final int adjusted_flour = (int) Math.round(ratio.getAdjustedFlour());
        final int adjusted_water = (int) Math.round(ratio.getAdjustedWater());

        info_title_field.setText(amount + " " + type + " Pizzas");
        flour_text_field.setText(Integer.toString(adjusted_flour));
        water_text_field.setText(Integer.toString(adjusted_water));

        continue_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StepsActivity.class);
                intent.putExtra("BATCH", batch_values);
                startActivity(intent);
            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });


        starter_flour_text_field.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("DETAILS", "clicked flour start data!");
                flour_starter_text_field.setVisibility(View.GONE);
                flour_starter_edit_field.setVisibility(View.VISIBLE);

            }
        });

        starter_water_text_field.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("DETAILS", "clicked flour start data!");
                water_starter_text_field.setVisibility(View.GONE);
                water_starter_edit_field.setVisibility(View.VISIBLE);

            }
        });

        water_starter_edit_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("EDIT", "editing to value for water " + v.getText());
                    int more_adjustment = DEFAULT_WATER - Integer.parseInt(v.getText().toString());
                    water_text_field.setText(Integer.toString(adjusted_water + more_adjustment));
                    water_starter_text_field.setText(v.getText().toString());
                    water_starter_text_field.setVisibility(View.VISIBLE);
                    water_starter_edit_field.setVisibility(View.GONE);
                    history.setStarterWater(v.getText().toString());
                    hide_keyboard(flour_starter_edit_field);
                    History.replaceLast(history);
                    handled = true;
                }
                return handled;
            }
        });


        flour_starter_edit_field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("EDIT", "editing to value for flour " + v.getText());
                    int more_adjustment = DEFAULT_FLOUR - Integer.parseInt(v.getText().toString());
                    flour_text_field.setText(Integer.toString(adjusted_flour + more_adjustment));
                    flour_starter_text_field.setText(v.getText().toString());
                    flour_starter_text_field.setVisibility(View.VISIBLE);
                    flour_starter_edit_field.setVisibility(View.GONE);
                    history.setStarterFlour(v.getText().toString());
                    History.replaceLast(history);
                    hide_keyboard(flour_starter_edit_field);
                    handled = true;
                }
                return handled;
            }
        });


    }
    private void hide_keyboard(EditText source) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(source.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is pres ent.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
