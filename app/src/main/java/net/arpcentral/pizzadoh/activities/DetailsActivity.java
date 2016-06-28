package net.arpcentral.pizzadoh.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Switch;
import android.widget.TextView;

import net.arpcentral.pizzadoh.R;
import net.arpcentral.pizzadoh.models.History;
import net.arpcentral.pizzadoh.models.Ratio;

public class DetailsActivity extends AppCompatActivity {

    final Integer DEFAULT_WATER = 150;
    final Integer DEFAULT_FLOUR = 100;
    public final static Float FADED = new Float(.4);

    static FloatingActionButton reset_button = null;

    static Switch keep_screen_on_toggle = null;
    static TextView keep_screen_on_toggle_question = null;

    static TextView starter_flour_data = null;
    static TextView starter_water_data = null;
    static EditText flour_starter_details_edit = null;
    static EditText water_starter_details_edit = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Secret To Your Perfect Crust");

        Intent intent = getIntent();

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        reset_button = (FloatingActionButton) this.findViewById(R.id.reset_button);
        starter_flour_data = (TextView) this.findViewById(R.id.flour_starter_data);
        starter_water_data = (TextView) this.findViewById(R.id.water_starter_data);
//        keep_screen_on_toggle = (Switch) this.findViewById(R.id.keep_screen_on_toggle);
//        keep_screen_on_toggle_question = (TextView) this.findViewById(R.id.keep_screen_on_toggle_question);

        String type =           intent.getStringExtra("TYPE");
        String amount =         intent.getStringExtra("AMOUNT");
        Boolean use_starter =   intent.getBooleanExtra("USING_STARTER", false);

        int starting_water = DEFAULT_WATER;
        int starting_flour = DEFAULT_FLOUR;
        String starting_water_text = Integer.toString(DEFAULT_WATER);
        String starting_flour_text = Integer.toString(DEFAULT_FLOUR);

        if ( intent.hasExtra("STARTING_WATER") ){
            starting_water = Integer.parseInt(intent.getStringExtra("STARTING_WATER"));
            starting_flour = Integer.parseInt(intent.getStringExtra("STARTING_FLOUR"));
            starting_water_text = intent.getStringExtra("STARTING_WATER");
            starting_flour_text = intent.getStringExtra("STARTING_FLOUR");
        }


        Ratio ratio = new Ratio(type, Integer.parseInt(amount), use_starter);

        final TextView info_title = (TextView)findViewById(R.id.info_title);

        final TextView flour_starter_details = (TextView)findViewById(R.id.flour_starter_data);
        final TextView water_starter_details = (TextView)findViewById(R.id.water_starter_data);

        final EditText flour_starter_details_edit = (EditText) findViewById(R.id.flour_starter_data_edit);
        final EditText water_starter_details_edit = (EditText) findViewById(R.id.water_starter_data_edit);

        RelativeLayout starter_container = (RelativeLayout)findViewById(R.id.starter_container);
        final TextView flour_details = (TextView)findViewById(R.id.flour_details_data);
        final TextView water_details = (TextView)findViewById(R.id.water_details_data);

        final History history = new History(0, "", starting_water, starting_flour, this);
        history.type = type;
        history.quantity = Integer.parseInt(amount);
        history.save();

        //keep_screen_on_toggle_question.setAlpha(FADED);

        // If Using a starter is checked
        if (use_starter){
            flour_starter_details.setText(starting_flour_text);
            water_starter_details.setText(starting_water_text);
            history.starter_water = starting_water;
            history.starter_flour = starting_flour;
        }

        else {
            starter_container.setVisibility(View.GONE);
            history.starter_water = 0;
            history.starter_flour = 0;
        }

        final int adjusted_flour = (int) Math.round(ratio.getAdjustedFlour());
        final int adjusted_water = (int) Math.round(ratio.getAdjustedWater());

        info_title.setText(amount + " " + type + " Pizzas");
        flour_details.setText(Integer.toString(adjusted_flour));
        water_details.setText(Integer.toString(adjusted_water));



        reset_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });

        starter_flour_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("DETAILS", "clicked flour start data!");
                flour_starter_details.setVisibility(View.GONE);
                flour_starter_details_edit.setVisibility(View.VISIBLE);

            }
        });

        starter_water_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d("DETAILS", "clicked flour start data!");
                water_starter_details.setVisibility(View.GONE);
                water_starter_details_edit.setVisibility(View.VISIBLE);

            }
        });

//        keep_screen_on_toggle.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//            boolean handled = false;
//            if ( keep_screen_on_toggle.isChecked() ){
//                keep_screen_on_toggle_question.setAlpha(1);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//                Log.d("DETAILS", "clicked screen on // " + getWindow() );
//            }
//
//            else {
//                keep_screen_on_toggle_question.setAlpha(FADED);
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//                Log.d("DETAILS", "clear screen off");
//            }
//            }
//        });

        water_starter_details_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("EDIT", "editing to value for water " + v.getText());
                    int more_adjustment = DEFAULT_WATER - Integer.parseInt(v.getText().toString());
                    water_details.setText(Integer.toString(adjusted_water + more_adjustment));
                    water_starter_details.setText(v.getText().toString());
                    water_starter_details.setVisibility(View.VISIBLE);
                    water_starter_details_edit.setVisibility(View.GONE);
                    history.starter_water = Integer.parseInt(v.getText().toString());
                    hide_keyboard(flour_starter_details_edit);
                    History.replaceLast(history);
                    handled = true;
                }
                return handled;
            }
        });


        flour_starter_details_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("EDIT", "editing to value for flour " + v.getText());
                    int more_adjustment = DEFAULT_FLOUR - Integer.parseInt(v.getText().toString());
                    flour_details.setText(Integer.toString(adjusted_flour + more_adjustment));
                    flour_starter_details.setText(v.getText().toString());
                    flour_starter_details.setVisibility(View.VISIBLE);
                    flour_starter_details_edit.setVisibility(View.GONE);
                    history.starter_flour = Integer.parseInt(v.getText().toString());
                    History.replaceLast(history);
                    hide_keyboard(flour_starter_details_edit);
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

//        // if the menu item clicked is "About", fire off that activity
//        if (id == R.id.action_about) {
//            Intent intent = new Intent(this, AboutActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//
//        // if the menu item clicked is "About", fire off that activity
//        if (id == R.id.action_history) {
//            Intent intent = new Intent(this, HistoryActivity.class);
//            startActivity(intent);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
