package xfi.java.util.concurrent.update;

import xfi.java.util.concurrent.update.index.Index;
import xfi.java.util.concurrent.update.index.IndexParser;
import xfi.java.util.concurrent.update.index.IndexPersistor;
import xfi.java.util.concurrent.update.stock.Stock;
import xfi.java.util.concurrent.update.stock.StockParser;
import xfi.java.util.concurrent.update.stock.StockPersistor;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = null;
        ExecutorService persistingExecutor = Executors.newSingleThreadExecutor();

        UpdateTracker<Stock> stockUpdateTracker = new LoggingUpdateTracker<>();
        Parser<Stock> stockParser = new StockParser();
        ExecutorService stockParsingExecutor = Executors.newSingleThreadExecutor();
        Persistor<Stock> stockPersistor = new StockPersistor(dataSource);
        Updater stockUpdater = new AsyncUpdater<>(stockUpdateTracker, stockParser, stockParsingExecutor, stockPersistor, persistingExecutor);

        UpdateTracker<Index> indexUpdateTracker = new LoggingUpdateTracker<>();
        Parser<Index> indexParser = new IndexParser();
        ExecutorService indexParsingExecutor = Executors.newSingleThreadExecutor();
        Persistor<Index> indexPersistor = new IndexPersistor(dataSource);
        Updater indexUpdater = new AsyncUpdater<>(indexUpdateTracker, indexParser, indexParsingExecutor, indexPersistor, persistingExecutor);



        UpdateMessage updateMessage1 = new UpdateMessage("ABC123", "");
        stockUpdater.receive(updateMessage1);

        UpdateMessage updateMessage2 = new UpdateMessage("XYZ456", "");
        indexUpdater.receive(updateMessage2);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        persistingExecutor.shutdown();
        try {
            persistingExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        indexParsingExecutor.shutdown();
        try {
            indexParsingExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stockParsingExecutor.shutdown();
        try {
            stockParsingExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
