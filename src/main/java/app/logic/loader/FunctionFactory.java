package app.logic.loader;


import app.logic.functions.*;
import app.logic.loader.exceptions.LXMLParsingException;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

public class FunctionFactory {

    public Function makeFunction(StartElement element, SignalParametersCollection signals)throws LXMLParsingException {
        switch(element.getAttributeByName(QName.valueOf("class")).getValue().toLowerCase()){
            case "and":
                return makeLogicAndGate(element,signals);
            case "or":
                return makeLogicOrGate(element,signals);
            case "ton":
                return makeOnDelayTimer(element,signals);
            case "fed":
                return makeFallingEdgeDetector(element,signals);
            case "red":
                return makeRisingEdgeDetector(element,signals);
            case "not":
                return makeNegator(element,signals);
            case "biswitch":
                return makeBistableSwitch(element,signals);
            default:
                return null;
        }
    }

    public LogicAndGate makeLogicAndGate(StartElement element, SignalParametersCollection signals) throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.isEmpty())throw new LXMLParsingException("Logic And Gate need to has at least one input");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() != 1)throw new LXMLParsingException("Logic And Gate need to has exactly one output");

        return new LogicAndGate(inputs.mapSignal(),outputs.getFirst().getSignal());
    }

    public LogicOrGate makeLogicOrGate(StartElement element, SignalParametersCollection signals) throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.isEmpty())throw new LXMLParsingException("Logic Or Gate need to has at least one input");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() != 1)throw new LXMLParsingException("Logic Or Gate need to has exactly one output");

        return new LogicOrGate(inputs.mapSignal(),outputs.getFirst().getSignal());
    }

    public OnDelayTimer makeOnDelayTimer(StartElement element, SignalParametersCollection signals) throws LXMLParsingException{
        SignalParametersCollection trigger = signals.getInputs().getByName("trigger");
        if(trigger.size() != 1)throw new LXMLParsingException("On Delay Timer need to has exactly one input named 'trigger'");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() != 1)throw new LXMLParsingException("On Delay Timer need to has exactly one output");

        long periodTime = 1000;
        Attribute period = element.getAttributeByName(QName.valueOf("period"));
        if(period != null) {
            try {
                periodTime = Long.parseLong(period.getValue());
            } catch (NumberFormatException nfe) {
                throw new LXMLParsingException("Wrong format of period attribute");
            }
        }

        return new OnDelayTimer(trigger.getFirst().getSignal(), outputs.getFirst().getSignal(), periodTime);
    }

    public FallingEdgeDetector makeFallingEdgeDetector(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.size() != 1)throw new LXMLParsingException("Falling Edge Detector need to has exactly one input");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() != 1)throw new LXMLParsingException("Falling Edge Detector need to has exactly one output");

        return new FallingEdgeDetector(inputs.getFirst().getSignal(),outputs.getFirst().getSignal());
    }

    public RisingEdgeDetector makeRisingEdgeDetector(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.size() != 1)throw new LXMLParsingException("Rising Edge Detector need to has exactly one input");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() != 1)throw new LXMLParsingException("Rising Edge Detector need to has exactly one output");

        return new RisingEdgeDetector(inputs.getFirst().getSignal(),outputs.getFirst().getSignal());
    }

    public Negator makeNegator(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.size() != 1)throw new LXMLParsingException("Negator need to has exactly one input");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() != 1)throw new LXMLParsingException("Negator need to has exactly one output");

        return new Negator(inputs.getFirst().getSignal(),outputs.getFirst().getSignal());
    }

    public BistableSwitch makeBistableSwitch(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.size() != 1)throw new LXMLParsingException("Bistable Switch need to has exactly one input");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() != 1)throw new LXMLParsingException("Bistable Switch need to has exactly one output");

        return new BistableSwitch(inputs.getFirst().getSignal(),outputs.getFirst().getSignal());
    }
}
