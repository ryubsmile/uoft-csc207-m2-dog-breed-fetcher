package dogapi;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private final BreedFetcher fetcher;
    private final Map<String, List<String>> cacheMap;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
        this.cacheMap = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // return statement included so that the starter code can compile and run.
        if (cacheMap.containsKey(breed)) {
            return cacheMap.get(breed);
        } else {
            callsMade++;
            List<String> subBreeds = fetcher.getSubBreeds(breed);
            cacheMap.put(breed, subBreeds);
            return subBreeds;
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}