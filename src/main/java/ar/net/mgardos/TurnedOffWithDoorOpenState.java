package ar.net.mgardos;

public class TurnedOffWithDoorOpenState implements SmartFridgeState {
    private boolean turnedOn = Boolean.FALSE;

    @Override
    public boolean isTurnedOn() {
        return turnedOn;
    }

    @Override
    public void turnOn(SmartFridge fridge) {
        turnedOn = Boolean.TRUE;
        fridge.open();
    }
}
