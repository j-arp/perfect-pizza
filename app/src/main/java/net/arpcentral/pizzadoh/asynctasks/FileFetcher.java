package net.arpcentral.pizzadoh.asynctasks;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import net.arpcentral.pizzadoh.Constants;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;


public class FileFetcher extends AsyncTask<Void, Integer, Void> {

    final AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(Constants.AWS_ID, Constants.AWS_KEY));

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
        //s3ObjList = s3Client.listObjects(Constants.BUCKET_NAME).getObjectSummaries();
        //log.d("aws", "Aws");
        ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest().withBucketName(Constants.BUCKET_NAME));
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (S3ObjectSummary summary : objectSummaries) {
            String key = summary.getKey();

            S3ObjectInputStream content = s3Client.getObject(Constants.BUCKET_NAME, key).getObjectContent();
            byte[] bytes = new byte[0];
            try {
                bytes = IOUtils.toByteArray(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
