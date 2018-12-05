package ar.net.mgardos;

public interface SmartFridge {
    void turnOn(SmartFridgeFactory factory);
    void open();
    void close();
}
