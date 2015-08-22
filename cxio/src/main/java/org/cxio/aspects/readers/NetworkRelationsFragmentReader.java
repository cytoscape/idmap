package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.NetworkRelationsElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class NetworkRelationsFragmentReader extends AbstractFragmentReader {

    public static NetworkRelationsFragmentReader createInstance() {
        return new NetworkRelationsFragmentReader();
    }

    private NetworkRelationsFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return NetworkRelationsElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
        return new NetworkRelationsElement(ParserUtils.getTextValueRequired(o, NetworkRelationsElement.PARENT),
                                           ParserUtils.getTextValueRequired(o, NetworkRelationsElement.CHILD),
                                           ParserUtils.getTextValueRequired(o, NetworkRelationsElement.TYPE));
    }
}