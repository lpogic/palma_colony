package app.logic.functions;

import app.logic.Signal;
import app.logic.State;

public class BistableSwitch implements Function {
    private Signal input;
    private Signal output;
    private State lastState;

    public BistableSwitch(Signal input, Signal output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean execute() {
        if(input.isDefined()){
            output.setState(input.isHigh() && lastState == State.LOW);
            lastState = input.getState();
            return true;
        }else return false;
    }
}
