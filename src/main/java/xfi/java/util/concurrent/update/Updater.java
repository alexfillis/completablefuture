package xfi.java.util.concurrent.update;

interface Updater {
    void receive(UpdateMessage updateMessage);
}
