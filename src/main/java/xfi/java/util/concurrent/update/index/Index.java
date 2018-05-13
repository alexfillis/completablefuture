package xfi.java.util.concurrent.update.index;

import xfi.java.util.concurrent.update.Tracked;

public class Index implements Tracked {
    private String value;

    Index(String value) {
        this.value = value;
    }

    @Override
    public String getTrackingId() {
        return "Hello$$$444";
    }
}
