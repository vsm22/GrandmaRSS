package com.kotlandry.grandma.rss.objects;

import java.io.Serializable;

/**
 * Created by Sergey on 1/19/2018.
 */

public class RssChannel implements IRssChannel, Serializable {

    final String name;
    final String link;

    public RssChannel(String name, String link) {
        this.name = name;
        this.link = link;
    }

    @Override public String getName() { return name; }
    @Override public String getLink() { return link; }

}
