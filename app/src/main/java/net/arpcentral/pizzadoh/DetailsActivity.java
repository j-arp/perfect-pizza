package net.arpcentral.pizzadoh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DetailsActivity extends AppCompatActivity {

    final Integer DEFAULT_WATER = 150;
    final Integer DEFAULT_FLOUR = 100;
    static FloatingActionButton reset_button = null;
    static Switch keep_screen_on_toggle = null;
    static TextView starter_flour_data = null;
    static TextView starter_water_data = null;
    static EditText flour_starter_details_edit = null;
    static EditText water_starter_details_edit = null;
    static TextView keep_screen_on_toggle_question = null;
    public final static Float FADED = new Float(.4);


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
        keep_screen_on_toggle = (Switch) this.findViewById(R.id.keep_screen_on_toggle);
        keep_screen_on_toggle_question = (TextView) this.findViewById(R.id.keep_screen_on_toggle_question);

        String type =       intent.getStringExtra("TYPE");
        String amount =       intent.getStringExtra("AMOUNT");
        Boolean use_starter = intent.getBooleanExtra("USING_STARTER", false);
        Ratio ratio = new Ratio(type, Integer.parseInt(amount), use_starter);
        final TextView flour_starter_details = (TextView)findViewById(R.id.flour_starter_data);
        final TextView water_starter_details = (TextView)findViewById(R.id.water_starter_data);

        final EditText flour_starter_details_edit = (EditText) findViewById(R.id.flour_starter_data_edit);
        final EditText water_starter_details_edit = (EditText) findViewById(R.id.water_starter_data_edit);

        RelativeLayout starter_container = (RelativeLayout)findViewById(R.id.starter_container);
        final TextView flour_details = (TextView)findViewById(R.id.flour_details_data);
        final TextView water_details = (TextView)findViewById(R.id.water_details_data);

        keep_screen_on_toggle_question.setAlpha(FADED);

        // If Using a starter is checked
        if (use_starter){
            flour_starter_details.setText(DEFAULT_FLOUR.toString());
            water_starter_details.setText(DEFAULT_WATER.toString());
        }

        else {
            starter_container.setVisibility(View.GONE);
        }

        final int adjusted_flour = (int) Math.round(ratio.getAdjustedFlour());
        final int adjusted_water = (int) Math.round(ratio.getAdjustedWater());

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
                Log.d("EDIT", "action id is " + actionId);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("EDIT", "editing to value " + v.getText());
                    int more_adjustment = DEFAULT_WATER - Integer.parseInt(v.getText().toString());
                    water_details.setText(Integer.toString(adjusted_water + more_adjustment));
                    water_starter_details.setText(v.getText().toString());
                    water_starter_details.setVisibility(View.VISIBLE);
                    water_starter_details_edit.setVisibility(View.GONE);
                    hide_keyboard(flour_starter_details_edit);
                    handled = true;
                }
                return handled;
            }
        });


        flour_starter_details_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                Log.d("EDIT", "action id is " + actionId);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("EDIT", "editing to value " + v.getText());
                    int more_adjustment = DEFAULT_FLOUR - Integer.parseInt(v.getText().toString());
                    flour_details.setText(Integer.toString(adjusted_flour + more_adjustment));
                    flour_starter_details.setText(v.getText().toString());
                    flour_starter_details.setVisibility(View.VISIBLE);
                    flour_starter_details_edit.setVisibility(View.GONE);
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

}
