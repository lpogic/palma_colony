package app.logic.devices.regular.shell;

import app.logic.Signal;
import javafx.beans.property.ReadOnlyStringProperty;

public class FieldToFileDevice extends ShellCmdDevice {
    private String fileName;
    private ReadOnlyStringProperty source;

    public FieldToFileDevice(Signal write, Signal responseReady, String fileName, ReadOnlyStringProperty source) {
        super(write,responseReady);
        this.fileName = fileName;
        this.source = source;
    }

    @Override
    protected void sendCommand() {
        setCommand("echo " + source.get() + " > " + fileName);
        super.sendCommand();
    }
}
