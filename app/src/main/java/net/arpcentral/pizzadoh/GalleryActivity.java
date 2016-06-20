package net.arpcentral.pizzadoh;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;


public class GalleryActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }
    final AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(Constants.AWS_ID, Constants.AWS_KEY));

    private class GetFileListTask extends AsyncTask<Void, Void, Void> {
        // The list of objects we find in the S3 bucket
        private List<S3ObjectSummary> s3ObjList;
        // A dialog to let the user know we are retrieving the files
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... inputs) {
            // Queries files in the bucket from S3.
            s3ObjList = s3Client.listObjects(Constants.BUCKET_NAME).getObjectSummaries();
            //log.d("aws", "Aws");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

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