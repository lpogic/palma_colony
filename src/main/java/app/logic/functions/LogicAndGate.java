package app.logic.functions;

import app.logic.Signal;
import app.logic.State;

import java.util.Collection;

public class LogicAndGate implements Function {
    private Collection<Signal> inputs;
    private Signal output;

    public LogicAndGate(Collection<Signal> inputs, Signal output) {
        this.output = output;
        this.inputs = inputs;
    }

    @Override
    public boolean execute() {
        boolean defined = true;
        for(Signal it : inputs) {
            if(it.isLow()){
                output.setState(State.LOW);
                return true;
            }
            if (!it.isDefined()) defined = false;
        }
        if(defined){
            output.setState(State.HIGH);
            return true;
        }else return false;
    }
}
