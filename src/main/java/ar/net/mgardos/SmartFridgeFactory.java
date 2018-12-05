package ar.net.mgardos;

public interface SmartFridgeFactory {
    FridgeDoor createDoor();
    SmartFridgeInspector createInspector();
}
