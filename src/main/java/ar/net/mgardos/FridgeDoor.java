package ar.net.mgardos;

public class FridgeDoor {
    private boolean opened = Boolean.FALSE;

    public FridgeDoor() {}


    public void open() {
        opened = Boolean.TRUE;
    }

    public void close() {
        opened = Boolean.FALSE;
    }

    public boolean isOpened() {
        return opened;
    }
}
