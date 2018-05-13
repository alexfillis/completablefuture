package xfi.java.util.concurrent.update.stock;

import xfi.java.util.concurrent.update.Tracked;

public class Stock implements Tracked {
    private String trackingId;

    Stock(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String getTrackingId() {
        return trackingId;
    }
}
