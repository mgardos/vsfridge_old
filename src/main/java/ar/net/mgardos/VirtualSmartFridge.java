package ar.net.mgardos;

import java.util.ArrayList;
import java.util.List;

public class VirtualSmartFridge implements SmartFridge {
    private FridgeDoor door;
    private SmartFridgeInspector inspector;
    private SmartFridgeState state;
    private List<Product> unidentifiedProducts;

    public VirtualSmartFridge(SmartFridgeState fs) {
        state = fs;
        unidentifiedProducts = new ArrayList<Product>(0);
    }

    @Override
    public void turnOn(SmartFridgeFactory factory) {
        if (!state.isTurnedOn()) {
            door = factory.createDoor();
            inspector = factory.createInspector();
        }
        state.turnOn(this);
    }

    @Override
    public void open() {
        if (state.isTurnedOn() && !door.isOpened()) {
            door.open();
        }
    }

    @Override
    public void close() {
        if (door.isOpened()) {
            door.close();
        }

        if (!door.isOpened()) {
            List<Snapshot> snapshots = inspector.survey(unidentifiedProducts);
            if (snapshots.isEmpty()) {
                unidentifiedProducts.clear();
            }
        }
    }

    public Boolean hasUnidentifiedProducts() {
        return !unidentifiedProducts.isEmpty();
    }
}
