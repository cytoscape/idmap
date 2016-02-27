package org.cxio.test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.DatamodelsUtil;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.CxioUtil;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AttributesTest {

    @Test
    public void test() throws IOException {
        
      
        List<String> a0 = DatamodelsUtil.parseStringToStringList( null, ATTRIBUTE_DATA_TYPE.STRING);
        
       
        assertTrue(a0 == null);
        
        List<String> a1 = DatamodelsUtil.parseStringToStringList( "[]", ATTRIBUTE_DATA_TYPE.STRING);
        
        assertTrue(a1.size() == 0);
        
        List<String> a2 = DatamodelsUtil.parseStringToStringList( "[\"a\"]", ATTRIBUTE_DATA_TYPE.STRING);
        
        assertTrue(a2.get(0).equals("a"));
        
        
        List<String> a3 = DatamodelsUtil.parseStringToStringList( "[\"a\", \"b,]\"]", ATTRIBUTE_DATA_TYPE.STRING);
        
        assertTrue(a3.get(0).equals("a"));
        assertTrue(a3.get(1).equals("b,]"));
        
        
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append('"');
        sb.append('a');
        sb.append('"');
        sb.append(',');
        sb.append('"');
        sb.append('b');
        sb.append('\\');
        sb.append('"');
        sb.append('b');
        sb.append('"');
        sb.append(']');
        List<String> a4 = DatamodelsUtil.parseStringToStringList( sb.toString(), ATTRIBUTE_DATA_TYPE.STRING);
     
        List<String> a5 = DatamodelsUtil.parseStringToStringList( CxioUtil.stringListToJson(a4), ATTRIBUTE_DATA_TYPE.STRING);
    
        assertTrue(a5.get(0).equals("a"));
        
        StringBuilder sb2 = new StringBuilder();
      
        sb2.append('b');
        sb2.append('"');
        sb2.append('b');
        
        assertTrue(a5.get(1).equals(sb2.toString()));
        
        
        final HiddenAttributesElement a = HiddenAttributesElement.createInstanceWithSingleValue(0L, "name", null, ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(a.isSingleValue() == true);
        assertTrue(a.getValue() == null);
        assertTrue(CxioUtil.getAttributeValuesAsString(a).equals("null"));

        final HiddenAttributesElement aa = HiddenAttributesElement.createInstanceWithSingleValue(0L, "name", "null", ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(aa.isSingleValue() == true);
        assertTrue(aa.getValue() == null);
        assertTrue(CxioUtil.getAttributeValuesAsString(aa).equals("null"));

        final HiddenAttributesElement aaa = HiddenAttributesElement.createInstanceWithSingleValue(0L, "name", "\"null\"", ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(aaa.isSingleValue() == true);
        assertTrue(aaa.getValue() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(aaa).equals("\"null\""));

        final String b_s = "\"v\"";
        final HiddenAttributesElement b = HiddenAttributesElement.createInstanceWithSingleValue(0L, "name", b_s, ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(b.isSingleValue() == true);
        assertTrue(b.getValue().equals("v"));
        assertTrue(CxioUtil.getAttributeValuesAsString(b).equals(b_s));

        final HiddenAttributesElement bb = HiddenAttributesElement.createInstanceWithSingleValue(0L, "name", "\"true\"", ATTRIBUTE_DATA_TYPE.BOOLEAN);

        assertTrue(bb.isSingleValue() == true);
        assertTrue(bb.getValue().equals("true"));
        assertTrue(CxioUtil.getAttributeValuesAsString(bb).equals("\"true\""));

       final HiddenAttributesElement c = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", null, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(c.isSingleValue() == false);
        assertTrue(c.getValues() == null);
        assertTrue(CxioUtil.getAttributeValuesAsString(c).equals("null"));

        final HiddenAttributesElement d = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", "[]", ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(d.isSingleValue() == false);
        assertTrue(d.getValues() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(d).equals("[]"));

        final String e_s = "[\"a\"]";
        final HiddenAttributesElement e = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", e_s, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(e.isSingleValue() == false);
        assertTrue(e.getValues() != null);
        
       
        assertTrue(CxioUtil.getAttributeValuesAsString(e).equals(e_s));

        final String f_s = "[\"a\",null,\"\",null,\"b \"]";
        final HiddenAttributesElement f = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", f_s, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(f.isSingleValue() == false);
        assertTrue(f.getValues() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(f).equals(f_s));
        

        final String f_s2 = " [  \"a\"  ,      null   ,  \"\"   , null ,  \"b \"    ]   ";
        final HiddenAttributesElement g = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", f_s2, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(g.isSingleValue() == false);
        assertTrue(g.getValues() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(g).equals(f_s));

        final String g_s = "[\"1.3\",null,null,\"1.88\"]";
        final HiddenAttributesElement h = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", g_s, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        assertTrue(h.isSingleValue() == false);
        assertTrue(h.getValues() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(h).equals(g_s));

        final String h_s = "[\"\"]";
        final HiddenAttributesElement hh = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", h_s, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(f.isSingleValue() == false);
        assertTrue(f.getValues() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(hh).equals(h_s));
        
        final String i_s = "[\"a,b\",\"c,d\"]";
        final HiddenAttributesElement hi = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", i_s, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(hi.isSingleValue() == false);
        assertTrue(hi.getValues() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(hi).equals(i_s));
        
        final String i_s2 = "[\"a,,b\",\"'c',d\"]";
        final HiddenAttributesElement hi2 = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", i_s2, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(hi2.isSingleValue() == false);
        assertTrue(hi2.getValues() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(hi2).equals(i_s2));
        
        final String i_s3 = "[\"AA\\\"AA\"]";
        final HiddenAttributesElement hi3 = HiddenAttributesElement.createInstanceWithMultipleValues(0L, "name", i_s3, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(hi3.isSingleValue() == false);
        assertTrue(hi3.getValues() != null);
        assertTrue(CxioUtil.getAttributeValuesAsString(hi3).equals(i_s3));
        

    }

    
    
    @Test
    public void test2() throws IOException {

        final NetworkAttributesElement a = NetworkAttributesElement.createInstanceWithMultipleValues(148L, "__Annotations", "[ \"\" ]", ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(a.getValues().get(0).equals(""));

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(a);

        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstanceWithAllAvailableWriters(out1, false, false);

        w1.start();
        w1.writeAspectElements(l1);
        w1.end(true, "");
        // System.out.println(out1.toString());

        assertTrue(out1
                .toString()
                .equals("[{\"numberVerification\":[{\"longNumber\":281474976710655}]},{\"networkAttributes\":[{\"s\":148,\"n\":\"__Annotations\",\"v\":[\"\"],\"d\":\"list_of_string\"}]},{\"status\":[{\"error\":\"\",\"success\":true}]}]"));
    }

    @Test
    public void test3() throws IOException {

        final String t0 = "[" + TestUtil.NUMBER_VERIFICATION + ",{\"networkAttributes\":[{\"s\":148,\"n\":\"__Annotations\",\"v\":[\"\"],\"d\":\"list_of_string\"}]}]";

        final CxReader p = CxReader.createInstance(t0, CxioUtil.getAllAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + NetworkAttributesElement.ASPECT_NAME + " aspect", r0.containsKey(NetworkAttributesElement.ASPECT_NAME));

        final NetworkAttributesElement e = (NetworkAttributesElement) r0.get(NetworkAttributesElement.ASPECT_NAME).get(0);
        e.getValues().get(0).equals("");
        final OutputStream out1 = new ByteArrayOutputStream();
        final CxWriter w1 = CxWriter.createInstanceWithAllAvailableWriters(out1, false, false);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(e);

        w1.start();
        w1.writeAspectElements(l1);
        w1.end(true, "");
        // System.out.println(out1.toString());

    }

}
