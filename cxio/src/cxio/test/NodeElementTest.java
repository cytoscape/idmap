package cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cxio.NodesElement;

public class NodeElementTest {

    @Test
    public void test() {
        final NodesElement n0 = new NodesElement("0");
        final NodesElement n1 = new NodesElement("0");
        final NodesElement n2 = new NodesElement("1");
        assertTrue(n0.equals(n1));
        assertTrue(n0.equals(n0));
        assertTrue(n1.equals(n0));
        assertFalse(n2.equals(n1));
        assertFalse(n1.equals(n2));
        assertTrue(n0.getId().equals("0"));
    }

}
