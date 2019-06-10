package app.logic.functions;


import app.logic.Signal;
import app.logic.State;

public class FallingEdgeDetector implements Function {
    private Signal input;
    private Signal output;
    private State lastState;

    public FallingEdgeDetector(Signal input, Signal output) {
        this.input = input;
        this.output = output;
        lastState = State.UNDEFINED;
    }

    @Override
    public boolean execute() {
        if(input.isDefined()) {
            output.setState(lastState == State.HIGH && input.isLow());
            lastState = input.getState();
            return true;
        } else return false;
    }
}
