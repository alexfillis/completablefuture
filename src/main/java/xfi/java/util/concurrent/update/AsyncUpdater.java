package xfi.java.util.concurrent.update;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class AsyncUpdater<T extends Tracked> implements Updater {
    private final UpdateTracker<T> updateTracker;
    private final Parser<T> parser;
    private final ExecutorService parsingExecutor;
    private final Persistor<T> persistor;
    private final ExecutorService persistingExecutor;

    AsyncUpdater(UpdateTracker<T> updateTracker, Parser<T> parser, ExecutorService parsingExecutor, Persistor<T> persistor, ExecutorService persistingExecutor) {
        this.updateTracker = updateTracker;
        this.parser = parser;
        this.parsingExecutor = parsingExecutor;
        this.persistor = persistor;
        this.persistingExecutor = persistingExecutor;
    }

    @Override
    public void receive(UpdateMessage updateMessage) {
        CompletableFuture
                .runAsync(() -> updateTracker.received(updateMessage), parsingExecutor)
                .thenRun(() -> updateTracker.transforming(updateMessage))
                .thenApply(aVoid -> parser.parse(updateMessage.getBody()))
                .thenApply(t -> {
                    updateTracker.transformed(updateMessage, t);
                    return t;
                })
                .exceptionally(cause -> {
                    updateTracker.failedToTransform(updateMessage, cause);
                    return null;
                })
                .thenApplyAsync(t -> {
                    updateTracker.persisting(t);
                    return t;
                }, persistingExecutor)
                .thenApply(t -> {
                    persistor.persist(t);
                    return t;
                })
                .handle((t, cause) -> {
                    if (t != null) {
                        updateTracker.persisted(t);
                    } else {
                        updateTracker.failedToPersist(t, cause);
                    }
                    return null;
                });
    }
}
