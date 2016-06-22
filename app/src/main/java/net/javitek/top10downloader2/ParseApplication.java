package net.javitek.top10downloader2;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by Javier on 6/21/2016.
 */
public class ParseApplication {

    private String xmlData;
    private ArrayList<Application> applications;

    public ParseApplication(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public boolean process() {
        boolean status = true;
        Application currentRecord;
        boolean inEntry = false;
        String textValue = "";


        // The parser will go through all the data and look for the start tag and end tag.

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                String tagName = xpp.getName();
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        Log.d("ParseApplications", "Starting tag for " + tagName);
                        if (tagName.equalsIgnoreCase("entry"))
                        {
                            inEntry = true;
                            currentRecord = new Application();
                            break;
                        }
                    case XmlPullParser.END_TAG:
                        Log.d("ParseApplications", "Ending tag for " + tagName);
                        break;

                    default:
                        // Nothing else to do.
                }
                eventType = xpp.next();
            }


        } catch (Exception e) {
            status = false;
            e.printStackTrace();
        }
        return true;
    }
}
