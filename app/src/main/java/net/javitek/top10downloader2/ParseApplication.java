package net.javitek.top10downloader2;

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
}
