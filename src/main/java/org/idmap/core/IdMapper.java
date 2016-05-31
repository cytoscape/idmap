package org.idmap.core;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * This interface defines a basic mapping
 * service client.
 * 
 * @author cmzmasek
 *
 */
public interface IdMapper {
   
    /**
     * This returns a set of query identifiers for which
     * a mapping could not have been established
     * with the latest execution of method "run".
     * 
     * @return a set of unmatched identifiers
     */
    public Set<String> getUnmatchedIds();
    
    
    /**
     * This returns a set of query identifiers for which
     * a mapping was established
     * with the latest execution of method "run".
     * 
     * @return a set of matched identifiers
     */
    public Set<String> getMatchedIds();
    
    
    /**
     * This is the main method for accessing and querying mapping services.
     * 
     * @param query_ids the identifiers to be mapped
     * @param source_type the type/source (e.g. "UniProt") of the identifiers to be mapped from
     * @param target_type the type/source  (e.g. "Ensembl")of the identifiers to be mapped to
     * @param source_species the species (e.g. "Homo sapiens") of the identifiers to be mapped from
     * @param target_species the species (e.g. "Homo sapiens") of the identifiers to be mapped to
     * 
     * @return a map of identifiers (for which a mapping exists) to IdMapping objects
     */
    public Map<String, IdMapping> run( final Collection<String> query_ids,
                                       final String source_type,
                                       final String target_type,
                                       final String source_species,
                                       final String target_species );
}