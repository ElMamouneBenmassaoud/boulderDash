package g58112.atlg3.boulderDash.util;

public interface Observable {
    void register(Observer observer);
    void unregister(Observer observer);
}
