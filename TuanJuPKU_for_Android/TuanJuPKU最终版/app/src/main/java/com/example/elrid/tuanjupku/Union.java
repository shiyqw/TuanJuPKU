package com.example.elrid.tuanjupku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elrid on 5/31/15.
 */
public class Union {
    public String unionName;
    public String iconURL;
    public int unionID;
    public List<News> newsList;

    public Union(String s1, String s2, int id) {
        unionName = s1;
        iconURL = s2;
        unionID = id;
        newsList = new ArrayList<News>();
    }
}
