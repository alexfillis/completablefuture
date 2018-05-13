package xfi.java.util.concurrent.update;

class LoggingUpdateTracker<T extends Tracked> implements UpdateTracker<T> {
    @Override
    public void received(UpdateMessage updateMessage) {
        System.out.printf("[%s] Received update message with ID of '%s' and body '%s'%n", Thread.currentThread().getName(), updateMessage.getMessageId(), updateMessage.getBody());
    }

    @Override
    public void transforming(UpdateMessage updateMessage) {
        System.out.printf("[%s] Transforming update message with ID of '%s'%n", Thread.currentThread().getName(), updateMessage);
    }

    @Override
    public void transformed(UpdateMessage updateMessage, T t) {
        System.out.printf("[%s] Transformed update message with ID of '%s' to '%s'%n", Thread.currentThread().getName(), updateMessage, t);
    }

    @Override
    public void failedToTransform(UpdateMessage updateMessage, Throwable cause) {
        System.out.printf("[%s] Failed to transform update message with ID of '%s': %s%n", Thread.currentThread().getName(), updateMessage, cause);
    }

    @Override
    public void persisting(T update) {
        System.out.printf("[%s] Initiating persistence for update '%s'%n", Thread.currentThread().getName(), update.getTrackingId());
    }

    @Override
    public void persisted(T update) {
        System.out.printf("[%s] Succeeded in persisting update '%s'%n", Thread.currentThread().getName(), update.getTrackingId());
    }

    @Override
    public void failedToPersist(T update, Throwable cause) {
        System.out.printf("[%s] Failed to persist update '%s'%n", Thread.currentThread().getName(), update.getTrackingId());
    }
}
