package app.logic.functions;


import app.logic.Signal;

public class Negator implements Function {
    private Signal input;
    private Signal output;

    public Negator(Signal input, Signal output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean execute() {
        if(input.isDefined()){
            output.setState(input.isLow());
            return true;
        }
        else return false;
    }
}
