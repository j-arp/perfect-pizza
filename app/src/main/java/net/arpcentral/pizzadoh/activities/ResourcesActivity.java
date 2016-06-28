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
import net.arpcentral.pizzadoh.models.ExternalResource;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;


public class ResourcesActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_resources);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton reset_button = (FloatingActionButton) findViewById(R.id.reset_button);
        reset_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WelcomeActivity.class);
                startActivity(intent);
            }
        });




    }


    public void goToUrl (View view) {
        Log.d("RESOURCE ACT", "going to url aand view is " + view.getContentDescription());
        Uri uriUrl = Uri.parse(view.getContentDescription().toString());
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


    private String readJsonData() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("external_resources.json");
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
        getMenuInflater().inflate(R.menu.menu_recources, menu);
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
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_RESOURCE_NAME = "The Name";
        private static final String ARG_RESOURCE_CAPTION = "I am the caption";
        private static final String ARG_RESOURCE_IMG = "??????";
        private static final String ARG_RESOURCE_URL = "www.somthing.com";
        private static final String ARG_RESOURCE_CATEGORY = "Weapons";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(ExternalResource.Item item) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_RESOURCE_NAME, item.getName());
            args.putString(ARG_RESOURCE_CAPTION, item.getCaption());
            args.putString(ARG_RESOURCE_URL, item.getUrl());
            args.putString(ARG_RESOURCE_IMG, item.getImg());
            args.putString(ARG_RESOURCE_CATEGORY, item.getCategory());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_resources, container, false);

            TextView nameTextView = (TextView) rootView.findViewById(R.id.resource_name);
            nameTextView.setText(getArguments().getString(ARG_RESOURCE_NAME));

            TextView captionTextView = (TextView) rootView.findViewById(R.id.resource_caption);
            captionTextView.setText(getArguments().getString(ARG_RESOURCE_CAPTION));

            Button urlButton = (Button) rootView.findViewById(R.id.resouce_url);
            urlButton.setContentDescription(getArguments().getString(ARG_RESOURCE_URL)  );

            ImageView resource_image = (ImageView) rootView.findViewById(R.id.resource_img);
            int img_id = getResources().getIdentifier(getArguments().getString(ARG_RESOURCE_IMG), "drawable", getContext().getPackageName() ); //"net.arpcentral.pizzadoh"
            resource_image.setImageResource(img_id);

            this.getActivity().setTitle(getArguments().getString(ARG_RESOURCE_CATEGORY));

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
            ExternalResource resources = new ExternalResource(json);

            final ArrayList<ExternalResource.Item> equipment = resources.getByKey("Equipment");
            final ArrayList<ExternalResource.Item> ingredients = resources.getByKey("Ingredients");
            final ArrayList<ExternalResource.Item> resource_items = resources.getAll();

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner c
            Log.d("RESOURCE ACT", "getting position of " + position);
            Log.d("RESOURCE ACT" , "resources is" +  resource_items);
            setTitle(resource_items.get(position).getCategory());
            return PlaceholderFragment.newInstance(resource_items.get(position));
        }

        @Override
        public int getCount() {
            String json = readJsonData();
            ExternalResource resources = new ExternalResource(json);
            final ArrayList<ExternalResource.Item> resource_items = resources.getAll();
           return resource_items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ExternalResource.keys()[position];
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
