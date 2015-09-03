package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.cxio.aspects.datamodels.CyVisualPropertiesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class CyVisualPropertiesFragmentReader extends AbstractFragmentReader {

    public static CyVisualPropertiesFragmentReader createInstance() {
        return new CyVisualPropertiesFragmentReader();
    }

    private CyVisualPropertiesFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return CyVisualPropertiesElement.NAME;
    }

    @Override
    public final AspectElement readElement(final ObjectNode o) throws IOException {
        CyVisualPropertiesElement vpe;
        if (o.has(CyVisualPropertiesElement.APPLIES_TO) && (o.has(CyVisualPropertiesElement.VIEW))) {
            vpe = new CyVisualPropertiesElement(ParserUtils.getTextValueRequired(o, CyVisualPropertiesElement.PROPERTIES_OF),
                                                ParserUtils.getAsStringList(o, CyVisualPropertiesElement.APPLIES_TO),
                                                ParserUtils.getTextValue(o, CyVisualPropertiesElement.VIEW));
        }
        else if (o.has(CyVisualPropertiesElement.APPLIES_TO)) {
            vpe = new CyVisualPropertiesElement(ParserUtils.getTextValueRequired(o, CyVisualPropertiesElement.PROPERTIES_OF), ParserUtils.getAsStringList(o, CyVisualPropertiesElement.APPLIES_TO));
        }
        else {
            vpe = new CyVisualPropertiesElement(ParserUtils.getTextValueRequired(o, CyVisualPropertiesElement.PROPERTIES_OF));
        }
        if (o.has(CyVisualPropertiesElement.PROPERTIES)) {
            final Iterator<Entry<String, JsonNode>> it = o.get(CyVisualPropertiesElement.PROPERTIES).fields();
            if (it != null) {
                while (it.hasNext()) {
                    final Entry<String, JsonNode> kv = it.next();
                    vpe.putProperty(kv.getKey(), kv.getValue().asText());
                }
                return vpe;
            }
        }
        return null;
    }

}
