package net.arpcentral.pizzadoh;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import net.arpcentral.pizzadoh.asynctasks.FileFetcher;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.List;


public class GalleryActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //FileFetcher runner = new FileFetcher();

        if (ContextCompat.checkSelfPermission(GalleryActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERM", "Need to ask permmission");
            ActivityCompat.requestPermissions(GalleryActivity.this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("PERM", "request code is: " + requestCode);
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERM", "Granted internet perms");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}





/*


        private class GetFileListTask extends AsyncTask<Void, Void, Void> {
            // The list of objects we find in the S3 bucket
            private List<S3ObjectSummary> s3ObjList;
            // A dialog to let the user know we are retrieving the files
            private ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(DownloadSelectionActivity.this,
                        getString(R.string.refreshing),
                        getString(R.string.please_wait));
            }

            @Override
            protected Void doInBackground(Void... inputs) {
                // Queries files in the bucket from S3.
                s3ObjList = s3.listObjects(Constants.BUCKET_NAME).getObjectSummaries();
                transferRecordMaps.clear();
                for (S3ObjectSummary summary : s3ObjList) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("key", summary.getKey());
                    transferRecordMaps.add(map);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                dialog.dismiss();
                simpleAdapter.notifyDataSetChanged();
            }
        }



*/