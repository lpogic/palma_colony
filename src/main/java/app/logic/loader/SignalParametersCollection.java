package app.logic.loader;


import app.logic.Signal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SignalParametersCollection extends HashSet<SignalParameters> {

    public SignalParametersCollection(int initialCapacity) {
        super(initialCapacity);
    }

    public SignalParametersCollection() {
    }

    public SignalParametersCollection(Collection<? extends SignalParameters> c) {
        super(c);
    }


    public SignalParameters getFirst(Predicate<SignalParameters> predicate){
        for (SignalParameters it : this){
            if(predicate.test(it))return it;
        }
        return null;
    }

    public SignalParameters getFirst(){
        return getFirst(e->true);
    }

    public SignalParametersCollection getBy(Predicate<SignalParameters> predicate){
        return stream().filter(predicate).collect(Collectors.toCollection(SignalParametersCollection::new));
    }

    public List<Signal> mapSignal(){
        return stream().map(SignalParameters::getSignal).collect(Collectors.toCollection(ArrayList::new));
    }

    public SignalParameters serveSignalById(SignalParameters signalParameters){
        SignalParameters first = getFirst(signalParameters::equals);
        if(first != null)return first;
        add(signalParameters);
        return signalParameters;
    }

    public SignalParametersCollection getByName(String name){
        return getBy(e->e.getName().equalsIgnoreCase(name));
    }

    public SignalParameters getFirstByName(String name){
        return getFirst(e->e.getName().equalsIgnoreCase(name));
    }

    public SignalParameters getFirstById(String id){
        return getFirst(e->e.getId().equals(id));
    }

    public SignalParameters getFirstInput(){
        return getFirst(SignalParameters::isInput);
    }

    public SignalParametersCollection getInputs(){
        return getBy(SignalParameters::isInput);
    }

    public SignalParameters getFirstOutput(){
        return getFirst(e->!e.isInput());
    }

    public SignalParametersCollection getOutputs(){
        return getBy(e->!e.isInput());
    }

    public SignalParametersCollection getNotDeviceDriven(){
        return getBy(e->!e.isDeviceDriven());
    }
}
