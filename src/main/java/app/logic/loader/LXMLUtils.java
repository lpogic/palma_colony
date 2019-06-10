package app.logic.loader;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

public class LXMLUtils {

    public static boolean attributeEquals(StartElement element, String attribute, String value){
        Attribute attr = element.getAttributeByName(QName.valueOf(attribute));
        if(attr == null)return false;
        return attr.getValue().equalsIgnoreCase(value);
    }
}
