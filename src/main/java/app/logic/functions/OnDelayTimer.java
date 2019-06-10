package app.logic.functions;


import app.logic.Signal;

public class OnDelayTimer implements Function {
    private Signal trigger;
    private Signal output;
    private long start;
    private long period;
    private boolean counting;

    public OnDelayTimer(Signal trigger, Signal output, long period) {
        this.trigger = trigger;
        this.output = output;
        this.period = period;
        counting = false;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    @Override
    public boolean execute() {
        if(trigger.isDefined()) {
            if (trigger.isHigh()) {
                if (!counting) {
                    start = System.currentTimeMillis();
                    counting = true;
                }
                output.setState(System.currentTimeMillis() - start > period);
            } else {
                output.setState(false);
                counting = false;
            }
            return true;
        }
        else return false;
    }
}
