package com.kotlandry.grandma.rss;

import com.kotlandry.grandma.rss.objects.IRssItem;

import java.util.List;

/**
 * Created by Sergey on 1/14/2018.
 */

public final class UpdateChannelResult {

    private String result;
    private Exception ex;

    public String getResult() {  return result;  }
    public void setResult(String result) { this.result = result;  }
    public Exception getExeption() { return ex; }
    public void setExeption(Exception ex) { this.ex = ex; }

    public UpdateChannelResult(String result) {
        this.result = result;
        ex = null;
    }

    public UpdateChannelResult(Exception ex) {
        this.ex = ex;
        result = null;
    }
}
