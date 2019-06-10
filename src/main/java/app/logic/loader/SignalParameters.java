package app.logic.loader;


import app.logic.Signal;

public class SignalParameters{
    private Signal signal;
    private String id;
    private String name;
    private boolean input;
    private boolean deviceDriven;

    public SignalParameters() {
        deviceDriven = false;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public boolean isDeviceDriven() {
        return deviceDriven;
    }

    public void setDeviceDriven(boolean deviceDriven) {
        this.deviceDriven = deviceDriven;
    }

    public void weave(SignalParameters that){
        if(that == null)return;
        that.name = this.name;
        that.input = this.input;
        this.deviceDriven = that.deviceDriven;
        this.signal = that.signal;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SignalParameters && ((SignalParameters) obj).id.equals(id);
    }

    @Override
    public String toString() {
        return "SignalParameters{" +
                "signal=" + signal +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", input=" + input +
                ", deviceDriven=" + deviceDriven +
                '}';
    }
}
