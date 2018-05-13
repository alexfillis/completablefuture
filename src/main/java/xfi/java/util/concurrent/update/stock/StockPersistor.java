package xfi.java.util.concurrent.update.stock;

import xfi.java.util.concurrent.update.Persistor;

import javax.sql.DataSource;

public class StockPersistor implements Persistor<Stock> {
    public StockPersistor(DataSource dataSource) {

    }

    @Override
    public void persist(Stock thing) {

    }
}
