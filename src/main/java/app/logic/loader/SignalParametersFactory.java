package app.logic.loader;

import app.logic.Signal;
import app.logic.loader.exceptions.LXMLParsingException;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

public class SignalParametersFactory {

    public SignalParameters makeSignalParameters(StartElement element)throws LXMLParsingException{
        Attribute id = element.getAttributeByName(QName.valueOf("id"));
        if(id == null)throw new LXMLParsingException("Signal need to has an id attribute");

        Attribute nameAttribute = element.getAttributeByName(QName.valueOf("name"));
        String name = nameAttribute != null ? nameAttribute.getValue() : "";

        SignalParameters signalParameters = new SignalParameters();
        signalParameters.setSignal(new Signal());
        signalParameters.setId(id.getValue());
        signalParameters.setName(name);
        signalParameters.setInput(element.getName().getLocalPart().equalsIgnoreCase("input"));
        return signalParameters;
    }
}
