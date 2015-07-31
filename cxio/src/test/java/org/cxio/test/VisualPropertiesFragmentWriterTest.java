package org.cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.VisualPropertiesElement;
import org.cxio.aspects.writers.VisualPropertiesFragmentWriter;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.junit.Test;

public class VisualPropertiesFragmentWriterTest {

    @Test
    public void test() throws IOException {
        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final OutputStream out0 = new ByteArrayOutputStream();
        final CxWriter w = CxWriter.createInstance(out0, false);
        w.addAspectFragmentWriter(VisualPropertiesFragmentWriter.createInstance());

        w.start();
        w.writeAspectElements(l0);
        w.end();

        assertEquals("[]", out0.toString());

        final VisualPropertiesElement c1 = new VisualPropertiesElement("nodes:default");

        c1.putProperty("text-opacity", "1.0");
        c1.putProperty("width", "40.0");
        c1.putProperty("background-color", "rgb(204,204,255)");

        final VisualPropertiesElement c2 = new VisualPropertiesElement("nodes:selected");
        c2.putProperty("background-color", "rgb(255,255,0)");

        final VisualPropertiesElement c3 = new VisualPropertiesElement("nodes");
        c3.addAppliesTo("1");
        c3.addAppliesTo("2");
        c3.putProperty("background-color", "rgb(0,0,0)");

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(c1);
        l1.add(c2);
        l1.add(c3);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstance(out1, false);
        w1.addAspectFragmentWriter(VisualPropertiesFragmentWriter.createInstance());

        w1.start();
        w1.writeAspectElements(l1);
        w1.end();

        assertEquals("[{\"visualProperties\":[{\"properties_of\":\"nodes:default\",\"properties\":{\"background-color\":\"rgb(204,204,255)\",\"text-opacity\":\"1.0\",\"width\":\"40.0\"}},{\"properties_of\":\"nodes:selected\",\"properties\":{\"background-color\":\"rgb(255,255,0)\"}},{\"properties_of\":\"nodes\",\"applies_to\":[\"1\",\"2\"],\"properties\":{\"background-color\":\"rgb(0,0,0)\"}}]}]",
                     out1.toString());

    }

}