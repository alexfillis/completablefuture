package xfi.java.util.concurrent.update.stock;

import xfi.java.util.concurrent.update.Parser;

public class StockParser implements Parser<Stock> {
    @Override
    public Stock parse(String value) {
        return new Stock("999666333");
    }
}
