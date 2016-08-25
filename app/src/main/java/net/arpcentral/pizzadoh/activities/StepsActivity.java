package net.arpcentral.pizzadoh.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import net.arpcentral.pizzadoh.R;
import net.arpcentral.pizzadoh.models.Step;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class StepsActivity extends AppCompatActivity {
String TAG = "Steps Activity";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onBackPressed() {
        Log.d(TAG, "back arrow >>>>>>>>>>>>>>");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final HashMap<String, String> batch_values = (HashMap<String, String>)intent.getSerializableExtra("BATCH");
        int step_index = (int) intent.getIntExtra("INDEX", 0);
        int step_number = (int) intent.getIntExtra("STEP", 1);
        Log.d(TAG, "go to setp " + step_number);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.steps_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(step_index);

        FloatingActionButton reset_button = (FloatingActionButton) findViewById(R.id.go_back_button);
        reset_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton go_back_button = (FloatingActionButton) this.findViewById(R.id.go_back_button);
        go_back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("BATCH", batch_values);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_steps, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            Intent current_intent = getIntent();
            HashMap<String, String> old_batch = (HashMap<String, String>)current_intent.getSerializableExtra("BATCH");

            HashMap<String,String> batch_values = new HashMap<String, String>();
                batch_values.put("TYPE", old_batch.get("TYPE"));
                batch_values.put("AMOUNT", old_batch.get("AMOUNT"));
                batch_values.put("USING_STARTER", old_batch.get("USING_STARTER"));

            Log.d(TAG, "back arrow >>>>>>>>>>> with " + batch_values);
            intent.putExtra("BATCH", batch_values);
            intent.putExtra("TEST", "TEST");
            startActivity(intent);
            return true;
        }
        return false;
    }
    public void playVideo (View view) {
        // Log.d("STEPS ACT", "going to videow aand view is " + view.getContentDescription());
        // Log.d(TAG, "url to yt: " + view.getContentDescription().toString());
        Uri uriUrl = Uri.parse(view.getContentDescription().toString());
        startActivity(new Intent(Intent.ACTION_VIEW, uriUrl));
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        String TAG = "Placeholder Fragment";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_STEP_NAME = "The Name";
        private static final String ARG_STEP_DIRECTIONS = "I am the caption";
        private static final String ARG_STEP_IMG = "??????";
        private static final String ARG_STEP_URL = "";
        private static final String ARG_STEP_CATEGORY = "Weapons";



        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Step.Item item) {
            // Log.d("PLACEHOLDER", "item as image of " + item.getImg());
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_STEP_NAME, item.getName());
            args.putString(ARG_STEP_DIRECTIONS, item.getDirections());
            args.putString(ARG_STEP_IMG, item.getImg());
            args.putString(ARG_STEP_URL, item.getUrl());
            args.putString(ARG_STEP_CATEGORY, item.getCategory());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Log.d(TAG, "onCreateView " + "");
            View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

            TextView nameTextView = (TextView) rootView.findViewById(R.id.step_name);
            nameTextView.setText(getArguments().getString(ARG_STEP_NAME));

            TextView directionsTextView = (TextView) rootView.findViewById(R.id.step_directions);
            directionsTextView.setText(getArguments().getString(ARG_STEP_DIRECTIONS));

            Button urlButton = (Button) rootView.findViewById(R.id.step_url);
            urlButton.setContentDescription(getArguments().getString(ARG_STEP_URL));
            urlButton.setText("Watch Video");

            ImageView step_image = (ImageView) rootView.findViewById(R.id.step_img);
            //Log.d(TAG, "step image view is " + step_image);

            int img_id = getResources().getIdentifier(getArguments().getString(ARG_STEP_IMG), "drawable", getContext().getPackageName() ); //"net.arpcentral.pizzadoh"

            if (img_id != 0){
                // Log.d(TAG, "image id is " + img_id);
                step_image.setImageResource(img_id);
            }

            else{
                step_image.setVisibility(View.GONE);
            }

            if ( getArguments().getString(ARG_STEP_URL).length() == 0){
                //Log.d(TAG, "remove button");
                urlButton.setVisibility(View.GONE);
            }
            else {
                //Log.d(TAG, "show button " + getArguments().getString(ARG_STEP_URL));
            }

            this.getActivity().setTitle(getArguments().getString(ARG_STEP_CATEGORY));

            return rootView;
        }
    }

    /**
     *
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            Intent intent = getIntent();
            final HashMap<String, String> batch_values = (HashMap<String, String>)intent.getSerializableExtra("BATCH");
            String json = readJsonData();
            Step steps = new Step(json);

            final ArrayList<Step.Item> prep = steps.getByKey("Preparation");
            final ArrayList<Step.Item> dough = steps.getByKey("Dough");
            final ArrayList<Step.Item> step_items = steps.getAll();

            Log.d("STEP ACT" , "Get fragment for step " +  position);
            // put_notification(getIntent(),batch_values, position);
            setTitle(step_items.get(position).getCategory());
            return PlaceholderFragment.newInstance(step_items.get(position));
        }

        @Override
        public int getCount() {
            String json = readJsonData();
            Step resources = new Step(json);
            final ArrayList<Step.Item> step_items = resources.getAll();
            return step_items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Step steps = new Step(readJsonData());
            return steps.step_keys().get(position);
        }
    }


    class getImageTask extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... url){
            Log.d("IMAGE", "getting image from " + url);
            try {
                InputStream is = (InputStream) new URL(url[0]).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                Log.d("IMAGE", "returning d " + d);
                return d;
            } catch (Exception e) {
                Log.d("IMAGE", "Failed!" + e.toString());
                return null;
            }
        }

        protected void onPostExecute(String url) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    public void put_notification(Intent intent, HashMap<String,String> batch_values, int step_number){
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_mood_black_24dp)
                        .setContentTitle("Perfect Pizza")
                        .setContentInfo("Go back to step " + step_number)
                ;

        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Builds the notification and issues it.
        Intent resultIntent = new Intent(this, StepsActivity.class);
        resultIntent.putExtra("BATCH", batch_values);
        resultIntent.putExtra("STEP", step_number);
        resultIntent.putExtra("INDEX", step_number - 1);


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
    }

    private String readJsonData() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("steps.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
