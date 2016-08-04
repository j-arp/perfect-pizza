package net.arpcentral.pizzadoh.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.telecom.Call;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.arpcentral.pizzadoh.R;
import net.arpcentral.pizzadoh.models.Step;
import net.arpcentral.pizzadoh.models.Step;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class StepsActivity extends AppCompatActivity {
String TAG = "Steps Activity";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.steps_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton reset_button = (FloatingActionButton) findViewById(R.id.go_back_button);
        reset_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        final HashMap<String, String> batch_values = (HashMap<String, String>)intent.getSerializableExtra("BATCH");
        FloatingActionButton go_back_button = (FloatingActionButton) this.findViewById(R.id.go_back_button);

        go_back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("BATCH", batch_values);
                startActivity(intent);
            }
        });


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_steps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        private static final String ARG_STEP_CATEGORY = "Weapons";



        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Step.Item item) {
            Log.d("PLACEHOLDER", "item as image of " + item.getImg());
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_STEP_NAME, item.getName());
            args.putString(ARG_STEP_DIRECTIONS, item.getDirections());
            args.putString(ARG_STEP_IMG, item.getImg());
            args.putString(ARG_STEP_CATEGORY, item.getCategory());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

            TextView nameTextView = (TextView) rootView.findViewById(R.id.step_name);
            nameTextView.setText(getArguments().getString(ARG_STEP_NAME));

            TextView directionsTextView = (TextView) rootView.findViewById(R.id.step_directions);
            directionsTextView.setText(getArguments().getString(ARG_STEP_DIRECTIONS));


            ImageView step_image = (ImageView) rootView.findViewById(R.id.step_img);
            Log.d(TAG, "step image view is " + step_image);

            int img_id = getResources().getIdentifier(getArguments().getString(ARG_STEP_IMG), "drawable", getContext().getPackageName() ); //"net.arpcentral.pizzadoh"
            if (img_id != 0){
                Log.d(TAG, "image id is " + img_id);
                step_image.setImageResource(img_id);
            }

            else{
                step_image.setVisibility(View.GONE);
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
            String json = readJsonData();
            Step steps = new Step(json);

            final ArrayList<Step.Item> prep = steps.getByKey("Preparation");
            final ArrayList<Step.Item> dough = steps.getByKey("Dough");
            final ArrayList<Step.Item> step_items = steps.getAll();

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner c

            Log.d("STEP ACT" , "Steps are" +  step_items);
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


}
