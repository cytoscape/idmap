package org.idmap.core;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A basic implementation for an IdMapping.
 * 
 * @author cmzmasek
 *
 */
public final class IdMappingImpl implements IdMapping {
    
    private final SortedSet<String> source_ids;
    private  String source_type;
    private  String source_species;
    private final SortedSet<String> target_ids;
    private  String target_type;
    private  String target_species;
    private  String relationship_type;
    
    public IdMappingImpl() {
        source_ids = new TreeSet<String>();
        target_ids = new TreeSet<String>();
    }
    
    public final Set<String> getSourceIds() {
        return  source_ids ;
    }
    
    public final Set<String> getTargetIds() {
        return  target_ids ;
    }
    
    public final void addSourceId( final String source_id ) {
        source_ids.add( source_id ) ;
    }
    
    public final void addTargetId( final String target_id ) {
        target_ids.add( target_id ) ;
    }

    public String getSourceType() {
        return source_type;
    }

    public void setSourceType(String source_type) {
        this.source_type = source_type;
    }

    public String getSourceSpecies() {
        return source_species;
    }

    public void setSourceSpecies(String source_species) {
        this.source_species = source_species;
    }

    public String getTargetType() {
        return target_type;
    }

    public void setTargetType(String target_type) {
        this.target_type = target_type;
    }

    public String getTargetSpecies() {
        return target_species;
    }

    public void setTargetSpecies(String target_species) {
        this.target_species = target_species;
    }

    public String getRelationshipType() {
        return relationship_type;
    }

    public void setRelationshipType(String relationship_type) {
        this.relationship_type = relationship_type;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("source ids    : " + setToSB( getSourceIds() ) + "\n");
        sb.append("target ids    : " + setToSB( getTargetIds() ) + "\n");
        sb.append("source id type: " + getSourceType() + "\n");
        sb.append("target id type: " + getTargetType() + "\n");
        sb.append("source species: " + getSourceSpecies() + "\n");
        sb.append("target species: " + getTargetSpecies() );
        if ( getRelationshipType() != null ) {
            sb.append("\n");
            sb.append("type          : " + getRelationshipType() + "\n");
        }
        return sb.toString();
    }
    
    private static StringBuilder setToSB(final Set<String> set) {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String str : set) {
            if (first) {
                first = false;
            }
            else {
                sb.append(", ");
            }
            sb.append(str);
        }
        return sb;
    }

}