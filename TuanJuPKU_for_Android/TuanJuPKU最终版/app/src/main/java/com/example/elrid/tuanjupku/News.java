package com.example.elrid.tuanjupku;

/**
 * Created by Elrid on 5/31/15.
 */
public class News {
    public String newsTitle;
    public String newsAbstract;
    public String newsURL;
    public String iconURL;
    public String theDate;
    public int newsID;

    public News(String s1, String s2, String s3, String s4, String s5, int id ) {
        newsTitle = s1;
        newsAbstract = s2;
        newsURL = s3;
        iconURL = s4;
        theDate = s5;
        newsID = id;
    }
}
