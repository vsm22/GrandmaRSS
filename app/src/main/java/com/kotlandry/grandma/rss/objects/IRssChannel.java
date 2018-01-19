package com.kotlandry.grandma.rss.objects;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

/**
 * Created by Sergey on 1/12/2018.
 */

public interface IRssChannel extends Serializable {
     String getName();
     String getLink();
}
