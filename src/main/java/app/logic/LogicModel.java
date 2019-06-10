package app.logic;

import app.logic.devices.Device;
import app.logic.functions.Function;

import java.util.*;


public class LogicModel implements Runnable{
    private Collection<Device> devices;
    private Collection<Signal> modelDrivenSignals;
    private Collection<Function> functions;

    public LogicModel(Collection<Device> devices,
                      Collection<Signal> modelDrivenSignals, Collection<Function> functions) {
        this.devices = devices;
        this.modelDrivenSignals = modelDrivenSignals;
        this.functions = functions;
    }

    @Override
    public void run()throws RuntimeException{
        Deque<Function> deque = new ArrayDeque<>(functions);
        int watchdog = 0;

        modelDrivenSignals.forEach(Signal::reset);
        synchronized (this) {
            devices.forEach(Device::dawn);
        }

        while(!deque.isEmpty()){
            if(watchdog >= deque.size())
                throw new RuntimeException("Run method stuck in interminable cycle");

            Function fun = deque.removeFirst();
            if(fun.execute()) {
                watchdog = 0;
            }else {
                deque.addLast(fun);
                ++watchdog;
            }
        }

        synchronized (this) {
            devices.forEach(Device::dusk);
        }
    }

    public void sortFunctions()throws RuntimeException{
        Deque<Function> unsorted = new ArrayDeque<>(functions);
        List<Function> sorted = new ArrayList<>(functions.size());
        int watchdog = 0;

        modelDrivenSignals.forEach(Signal::reset);

        while(!unsorted.isEmpty()){
            if(watchdog >= unsorted.size())
                throw new RuntimeException("Sort method stuck in interminable cycle");

            Function fun = unsorted.removeFirst();
            if(fun.execute()) {
                sorted.add(fun);
                watchdog = 0;
            }else {
                unsorted.addLast(fun);
                ++watchdog;
            }
        }

        functions = sorted;
    }
}
