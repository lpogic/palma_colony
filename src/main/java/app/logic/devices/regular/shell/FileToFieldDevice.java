package app.logic.devices.regular.shell;

import app.logic.Signal;
import javafx.beans.property.StringProperty;

public class FileToFieldDevice extends ShellCmdDevice {
    private StringProperty target;

    public FileToFieldDevice(Signal read, Signal responseReady, String fileName, StringProperty target) {
        super(read,responseReady);
        this.target = target;
        setCommand("type " + fileName);
    }

    @Override
    void responseIsReady() {
        target.set(getResponse());
        super.responseIsReady();
    }
}