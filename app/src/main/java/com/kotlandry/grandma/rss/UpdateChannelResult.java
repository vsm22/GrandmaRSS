package com.kotlandry.grandma.rss;

import com.kotlandry.grandma.rss.objects.IRssItem;

import java.util.List;

/**
 * Created by Sergey on 1/14/2018.
 */

public final class UpdateChannelResult {

    private List<IRssItem> result;
    private Exception ex;

    public List<IRssItem> getResult() {  return result;  }
    public void setResult(List<IRssItem> result) { this.result = result;  }
    public Exception getException() { return ex; }
    public void setExeption(Exception ex) { this.ex = ex; }

    public UpdateChannelResult(List<IRssItem> result) {
        this.result = result;
        ex = null;
    }

    public UpdateChannelResult(Exception ex) {
        this.ex = ex;
        result = null;
    }
}
