package app.logic.loader;


import app.logic.LogicModel;
import app.logic.devices.Device;
import app.logic.functions.Function;
import app.logic.loader.exceptions.LXMLParsingException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LXMLLoader {
    private URL url;
    private DeviceFactory deviceFactory;

    public LXMLLoader() {
    }

    public LXMLLoader(URL url) {
        this.url = url;
    }

    public void setDeviceFactory(DeviceFactory deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    public static LogicModel load(URL url)throws Exception{
        return new LXMLLoader(url).load();
    }

    public LogicModel load()throws Exception{
        return load(url.openStream());
    }

    public LogicModel load(InputStream stream) throws Exception {

        SignalParametersCollection localSignals = new SignalParametersCollection();
        SignalParametersCollection globalSignals = new SignalParametersCollection();
        List<Device> devices = new ArrayList<>();
        List<Function> functions = new ArrayList<>();

        Stack<StartElement> elements = new Stack<>();

        SignalParametersFactory signalParametersFactory = new SignalParametersFactory();
        if(deviceFactory == null)deviceFactory = new DeviceFactory();
        FunctionFactory functionFactory = new FunctionFactory();


        XMLEventReader eventReader = XMLInputFactory.newInstance().
                createXMLEventReader(stream);

        while(eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();

            switch (event.getEventType()) {

                case XMLStreamConstants.START_ELEMENT:
                    elements.push(event.asStartElement());
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    StartElement element = elements.pop();
                    try {
                        switch (element.getName().getLocalPart().toLowerCase()) {
                            case "input":
                            case "output":
                            case "signal":
                                SignalParameters signal = signalParametersFactory.makeSignalParameters(element);
                                signal.weave(globalSignals.getFirstById(signal.getId()));
                                localSignals.add(signal);
                                globalSignals.add(signal);
                                break;
                            case "device":
                                devices.add(deviceFactory.makeDevice(element, localSignals));
                                localSignals = new SignalParametersCollection();
                                break;
                            case "function":
                                functions.add(functionFactory.makeFunction(element, localSignals));
                                localSignals = new SignalParametersCollection();
                        }
                    }catch(LXMLParsingException lxmle){
                        lxmle.addSuppressed(new LXMLParsingException("Malformed LXML at line " + element.getLocation().getLineNumber()));
                        throw lxmle;
                    }
                    break;
            }
        }

        return new LogicModel(devices,globalSignals.getNotDeviceDriven().mapSignal(),functions);
    }


}
