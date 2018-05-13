package xfi.java.util.concurrent.update.index;

import xfi.java.util.concurrent.update.Parser;

public class IndexParser implements Parser<Index> {
    @Override
    public Index parse(String value) {
        return new Index("XYZ898989");
    }
}
