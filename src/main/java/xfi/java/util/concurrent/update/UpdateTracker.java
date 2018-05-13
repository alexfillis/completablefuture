package xfi.java.util.concurrent.update;

interface UpdateTracker<T extends Tracked> {
    void received(UpdateMessage updateMessage);

    void transforming(UpdateMessage updateMessage);

    void transformed(UpdateMessage updateMessage, T t);

    void failedToTransform(UpdateMessage updateMessage, Throwable cause);

    void persisting(T update);

    void persisted(T update);

    void failedToPersist(T update, Throwable cause);
}
