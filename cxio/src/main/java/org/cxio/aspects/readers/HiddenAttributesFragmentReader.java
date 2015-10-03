package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement;
import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class HiddenAttributesFragmentReader extends AbstractFragmentReader {

    public static HiddenAttributesFragmentReader createInstance() {
        return new HiddenAttributesFragmentReader();
    }

    private HiddenAttributesFragmentReader() {
        super();
    }

    @Override
    public String getAspectName() {
        return HiddenAttributesElement.NAME;
    }

    @Override
    public AspectElement readElement(final ObjectNode o) throws IOException {
        ATTRIBUTE_DATA_TYPE type = ATTRIBUTE_DATA_TYPE.STRING;
        if (o.has(AbstractAttributesAspectElement.ATTR_DATA_TYPE)) {
            type = AbstractAttributesAspectElement.toDataType(ParserUtils.getTextValueRequired(o, AbstractAttributesAspectElement.ATTR_DATA_TYPE));
        }
        if (ParserUtils.isArray(o, AbstractAttributesAspectElement.ATTR_VALUES)) {
            return new HiddenAttributesElement(ParserUtils.getTextValue(o, AbstractAttributesAspectElement.ATTR_SUBNETWORK),
                                               ParserUtils.getTextValueRequired(o, AbstractAttributesAspectElement.ATTR_NAME),
                                               ParserUtils.getAsStringList(o, AbstractAttributesAspectElement.ATTR_VALUES),
                                               AbstractAttributesAspectElement.toList(type));
        }

        return new HiddenAttributesElement(ParserUtils.getTextValue(o, AbstractAttributesAspectElement.ATTR_SUBNETWORK),
                                           ParserUtils.getTextValueRequired(o, AbstractAttributesAspectElement.ATTR_NAME),
                                           ParserUtils.getTextValue(o, AbstractAttributesAspectElement.ATTR_VALUES),
                                           type);
    }

}
