package org.idmap.mapping_services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.idmap.core.IdMapper;
import org.idmap.core.IdMapping;
import org.idmap.core.IdMappingImpl;

public class BridgeDb implements IdMapper {
    
    
    final SortedSet<String> unmatched_ids;
    final URL url;
    
    public static final boolean DEBUG = true;

    private BridgeDb(final URL url) {
        unmatched_ids = new TreeSet<String>();
        this.url = url;
    }
    
    public SortedSet<String> getUnmatchedIds() {
        return  unmatched_ids;
    }
    
    public long getTime() {
        return 0;
    }
    
    public SortedMap<String, IdMapping> run( final Collection<String> query_ids,
                                             final String source_type,
                                             final String target_type,
                                             final String source_species,
                                             final String target_species ) {
     
        return null;
    }
    
    public static void main(final String[] args) throws IOException {
        
        String url = "http://webservice.bridgedb.org:8185/batch"; 
        
        final Collection<String> ids = new ArrayList<String>();
        
        ids.add("ENSMUSG00000063455");
        ids.add("ENSMUSG00000073823");
       // for (int i = 0; i < 10000; i++) {
       //     ids.add("ENSMUSG00000037031");
       // }
        ids.add("ENSMUSG00000037031");
        long t0 = System.currentTimeMillis();
        List<String> res = BridgeDb.runQuery(ids, "Mouse", "xrefs", "En", url);
        
        System.out.println("t=" + (System.currentTimeMillis() - t0)+"ms");
       
        for (String l : res) {
            System.out.println(l);
        }
        
        parseResponse(res, null, "", "Ag", null, null);
    }
    
    private final static void parseResponse(final Object input,
            final Set<String> in_types, final String target_species,
            final String target_type,
            final Map<String, IdMappingImpl> matched_ids,
            final Set<String> unmatched_ids) throws IOException {
        
        final List<String> res = (List<String>) input;
        for (final String s : res) {
            final String[] s1 = s.split("\t");
            if ( s1.length != 3 ) {
                throw new IOException("illegal format: " + s);
            }
            IdMappingImpl idmap = new IdMappingImpl();
            idmap.setTargetSpecies(target_species);
            idmap.setTargetType(target_type);
            idmap.setSourceType(s1[1]);
            idmap.addSourceId(s1[0]);
           // System.out.println(s1[0]);
           
            final String[] s2 = s1[2].split(",");
           
            for (String s2_str : s2) {
                if ( s2_str != null && !s2_str.toLowerCase().equals("n/a")) {
                   // System.out.println(s2_str);
                    final String[] s3 = s2_str.split(":", 2);
                    if ( s3.length != 2 ) {
                        throw new IOException("illegal format: " + s);
                    }
                    if ( s3[0].equals(target_type) ) {
                      //  System.out.println(s3[0] + " => " + s3[1]);
                        idmap.addTargetId(s3[1]);
                       
                    }
                }
            }
            System.out.println(idmap);
            matched_ids.put(s1[0], idmap);
           
        }
        
    }

    private static final List<String> post(final String url_str,
                                    final String species,
                                    final String target,
                                    final String database,
                                    final String query)
            throws IOException {
        final URL url = new URL(url_str + "/" + species + "/" + target + "/" + database);
        if (DEBUG) {
            //System.out.println(url.toString());
        }
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        //conn.setRequestProperty("Content-Type", "application/json");
       
        final OutputStream os = conn.getOutputStream();
        os.write(query.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
           
                throw new IOException("HTTP error code : "
                        + conn.getResponseCode());
            
        }

        final BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        final List<String> res = new ArrayList<String>();
        String line;
        
        while ((line = br.readLine()) != null) {
            res.add(line);
        }

        br.close();
        conn.disconnect();
        os.close();

        return res;
    }

    public final static List<String> runQuery(final Collection<String> ids,
                                        final String species,
                                        final String target,
                                        final String database,
                                        final String url_str)
            throws IOException {
        final String query = makeQuery(ids);
       // System.out.println("url=" + url_str);
       // System.out.println("query=" + query);
        return post( url_str,
                 species,
                target,
                database,
                query );
    }

    private static final String makeQuery(final Collection<String> ids
           ) {
        final StringBuilder sb = new StringBuilder();
      
        sb.append(listToString(ids));
     
        return sb.toString();
    }

    private final static StringBuilder listToString(final Collection<String> l) {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (final String s : l) {
            if (first) {
                first = false;
            }
            else {
                sb.append("\n");
            }
            sb.append(s);
        }
        return sb;
    }

    @Override
    public Set<String> getMatchedIds() {
        // TODO Auto-generated method stub
        return null;
    }
}
