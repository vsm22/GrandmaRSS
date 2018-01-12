package com.kotlandry.grandma.rss.objects;

import java.net.URL;
import java.util.Date;

/**
 * Created by Sergey on 1/12/2018.
 */

public interface IRssItem {

    /**
     *
     * @return article title
     */
     String getTitle();

    /**
     *
     * @return URL link to an article
     */
     URL    getLink();

    /**
     *
     * @return date when article was creqated
     */
     Date   getPubDate();

}
