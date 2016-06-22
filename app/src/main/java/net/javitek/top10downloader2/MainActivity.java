package net.javitek.top10downloader2;

import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //private TextView xmlTextView;
    private String mFileContents;
    private Button btnParse;
    private ListView listApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xmlTextView = (TextView) findViewById(R.id.xmlTextView);
        btnParse = (Button) findViewById(R.id.btnParse);
        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseApplication parseApplication = new ParseApplication(mFileContents);
                parseApplication.process();
                // Array adapter to add the result text into the text view.
                // Parameters are Context, layout, list of the array.
                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(
                        MainActivity.this, R.layout.list_item, parseApplication.getApplications());
                listApps.setAdapter(arrayAdapter);
            }
        });

        listApps = (ListView) findViewById(R.id.xmlListView);
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
        //downloadData.execute("http://feeds.feedburner.com/crunchyroll/rss/anime?format=xml");
    }




    // The 3 parameters of the AsyncTask are: String - Download location, Void - Used for
    // progress bar. This time we will not use it, String - Actual result or response that comes back.
    private class DownloadData extends AsyncTask<String, Void, String> {



        // Use Code->Generate->Override Methods (Or Ctrl-O)
        @Override
        protected String doInBackground(String... params) { //The ... implies you can pass as many parameters as needed.
            mFileContents = downloadXMLFile(params[0]);
            if (mFileContents == null)
            {
                Log.d("DownloadData", "Error downloading");
            }

            return mFileContents;
        }

        // Ctrl-O
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData", "Result was: " + result);

            //xmlTextView.setText(result);
        }

        private String downloadXMLFile(String urlPath) {
            StringBuilder tempBuffer = new StringBuilder();

            try
            {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownloadData", "The response code was " + response);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500];
                while (true)
                {
                    charRead = isr.read(inputBuffer);
                    if (charRead <= 0)
                    {
                        break;
                    }
                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
                }

                return tempBuffer.toString();

            }
            catch (IOException e)
            {
                Log.d("DownloadData", "IO Exception reading data: " + e.getMessage());
            }
            catch (SecurityException e)
            {
                Log.d("DownloadData", "Security exception. Needs permission? " + e.getMessage());
            }

            return null;
        }
    }







}
