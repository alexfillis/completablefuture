package xfi.java.util.concurrent.update;

public interface Persistor<T> {
    void persist(T thing);
}
