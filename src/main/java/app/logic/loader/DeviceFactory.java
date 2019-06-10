package app.logic.loader;


import app.logic.devices.Device;
import app.logic.devices.event.EventButtonDevice;
import app.logic.devices.event.EventDevice;
import app.logic.devices.event.EventFileDevice;
import app.logic.devices.regular.ButtonDevice;
import app.logic.devices.regular.FileDevice;
import app.logic.devices.regular.LampDevice;
import app.logic.devices.regular.shell.FieldToFileDevice;
import app.logic.devices.regular.shell.FileToFieldDevice;
import app.logic.loader.exceptions.LXMLParsingException;
import javafx.scene.Scene;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Circle;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

public class DeviceFactory {
    private Scene scene;

    public DeviceFactory() {
    }

    public DeviceFactory(Scene scene) {
        this.scene = scene;
    }

    Device makeDevice(StartElement element, SignalParametersCollection signals)throws LXMLParsingException {
        if(LXMLUtils.attributeEquals(element,"control","event")){
            signals.forEach(e->e.setDeviceDriven(true));
            return makeEventDevice(element,signals);
        } else {
            return _makeDevice(element,signals);
        }
    }

    private EventDevice makeEventDevice(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        if(LXMLUtils.attributeEquals(element,"type","file")){
            return makeEventFileDevice(element,signals);
        } else {
            switch(element.getAttributeByName(QName.valueOf("class")).getValue().toLowerCase()){
                case "button":
                    return makeEventButtonDevice(element,signals);
                default:
                    throw new LXMLParsingException("Unrecognized event controlled device");
            }
        }
    }

    private Device _makeDevice(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        if(LXMLUtils.attributeEquals(element,"type","file")){
            switch(element.getAttributeByName(QName.valueOf("class")).getValue().toLowerCase()){
                case "file->field":
                    return makeFileToFieldDevice(element,signals);
                case "field->file":
                    return makeFieldToFileDevice(element,signals);
                default:
                    throw new LXMLParsingException("Unrecognized device");
            }
        } else {
            switch(element.getAttributeByName(QName.valueOf("class")).getValue().toLowerCase()){
                case "button":
                    return makeButtonDevice(element,signals);
                case "lamp":
                    return makeLampDevice(element,signals);
                default:
                    throw new LXMLParsingException("Unrecognized device");
            }
        }
    }

    private EventFileDevice makeEventFileDevice(StartElement element, SignalParametersCollection signals){
        return new EventFileDevice();
    }

    private EventButtonDevice makeEventButtonDevice(StartElement element, SignalParametersCollection signals){
        return new EventButtonDevice();
    }

    private ButtonDevice makeButtonDevice(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() != 1)throw new LXMLParsingException("Button need to has exactly one output");

        Attribute idAttribute = element.getAttributeByName(QName.valueOf("id"));
        if(idAttribute == null)throw new LXMLParsingException("Button need to has an id attribute");
        String id = idAttribute.getValue();
        ToggleButton button = (ToggleButton) scene.lookup("#" + id);
        if(button == null)throw new LXMLParsingException("Button '" + id + "' doesnt found on the scene");
        return new ButtonDevice(outputs.getFirst().getSignal(),button.selectedProperty());
    }

    private LampDevice makeLampDevice(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.size() != 1)throw new LXMLParsingException("Lamp need to has exactly one input");

        Attribute idAttribute = element.getAttributeByName(QName.valueOf("id"));
        if(idAttribute == null)throw new LXMLParsingException("Lamp need to has an id attribute");
        String id = idAttribute.getValue();
        Circle circle = (Circle) scene.lookup("#" + id);
        if(circle == null)throw new LXMLParsingException("Lamp '" + id + "' doesnt found on the scene");
        return new LampDevice(inputs.getFirst().getSignal(),circle.fillProperty());
    }

    private FileToFieldDevice makeFileToFieldDevice(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.size() != 1)throw new LXMLParsingException("FileToField need to has exactly one input");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() > 1)throw new LXMLParsingException("FileToField need to has at most one output");
        SignalParameters output = outputs.getFirstOutput();
        if(output == null)output = new SignalParameters();
        output.setDeviceDriven(true);

        Attribute fileAttribute = element.getAttributeByName(QName.valueOf("file"));
        if(fileAttribute == null)throw new LXMLParsingException("FileToField need to has an file attribute");
        String file = fileAttribute.getValue();

        Attribute fieldAttribute = element.getAttributeByName(QName.valueOf("field"));
        if(fieldAttribute == null)throw new LXMLParsingException("FileToField need to has an field attribute");
        String field = fieldAttribute.getValue();
        TextInputControl textInputControl = (TextInputControl) scene.lookup("#" + field);

        return new FileToFieldDevice(inputs.getFirst().getSignal(),
                output.getSignal(),file,textInputControl.textProperty());
    }

    private FieldToFileDevice makeFieldToFileDevice(StartElement element, SignalParametersCollection signals)throws LXMLParsingException{
        SignalParametersCollection inputs = signals.getInputs();
        if(inputs.size() != 1)throw new LXMLParsingException("FieldToFile need to has exactly one input");

        SignalParametersCollection outputs = signals.getOutputs();
        if(outputs.size() > 1)throw new LXMLParsingException("FieldToFile need to has at most one output");
        SignalParameters output = outputs.getFirstOutput();
        if(output == null)output = new SignalParameters();
        output.setDeviceDriven(true);

        Attribute fileAttribute = element.getAttributeByName(QName.valueOf("file"));
        if(fileAttribute == null)throw new LXMLParsingException("FieldToFile need to has an file attribute");
        String file = fileAttribute.getValue();

        Attribute fieldAttribute = element.getAttributeByName(QName.valueOf("field"));
        if(fieldAttribute == null)throw new LXMLParsingException("FieldToFile need to has an field attribute");
        String field = fieldAttribute.getValue();
        TextInputControl textInputControl = (TextInputControl) scene.lookup("#" + field);

        return new FieldToFileDevice(inputs.getFirst().getSignal(),
                output.getSignal(),file,textInputControl.textProperty());
    }
}
