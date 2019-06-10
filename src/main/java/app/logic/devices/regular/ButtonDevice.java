package app.logic.devices.regular;

import app.logic.Signal;
import app.logic.devices.Device;
import javafx.beans.property.ReadOnlyBooleanProperty;

public class ButtonDevice implements Device {
    private Signal output;
    private ReadOnlyBooleanProperty clicked;

    public ButtonDevice(Signal output, ReadOnlyBooleanProperty clicked) {
        this.output = output;
        this.clicked = clicked;
    }

    @Override
    public void dawn() {
        output.setState(clicked.get());

    }

    @Override
    public void dusk() {}
}
