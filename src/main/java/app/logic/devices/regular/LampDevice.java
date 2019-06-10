package app.logic.devices.regular;

import app.logic.Signal;
import app.logic.devices.Device;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class LampDevice implements Device {
    private Signal input;
    private ObjectProperty<Paint> fill;

    public LampDevice(Signal input, ObjectProperty<Paint> fill) {
        this.input = input;
        this.fill = fill;
    }

    @Override
    public void dawn() {

    }

    @Override
    public void dusk() {
        fill.setValue(Color.web(input.isHigh() ? "red" : "green"));
    }
}
