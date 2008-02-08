/**
 * This package is a simple abstraction for providing a lazy-loading type cache.  The idea is that there exists a cache
 * in which objects are stored in a standard key-value scheme.  A client does not put objects into this cache.  A
 * client does not remove objects from this cache.  A client simply retrieves objects from this cache.  When the cache
 * does not have a value associated with the provided key, it knows how to create a new value to associate with that
 * key.  This value is then stored and returned.  The creation of the new value is achieved with the
 * {@link CacheItemLoader} interface.  Also, prior to returning any value, the cache validates it to ensure that it is
 * still valid.  If it is not, it is removed from the cache.  Then, the cache creates a new one, using the same
 * mechanism as it would were a value not present in the first place.  The validation is achieved with the
 * {@link CacheItemValidator} interface.
 *
 * A new {@link Cache} object is created using the {@link CacheFactory}.
 *
 * A default implemenation using just {@code HashMaps} is available in {@link edu.wvu.ferl.cache.impl}.
 *
 * Other implementations can use the default implementation as a reference.  It is also recommended for
 * "implementations" of this api to simply be adapters to other, more full fledged caching mechanisms.
 *
 * <b>Note:</b>  This package really bears no relation to the ferl project other than that it is the mechanism that
 * ferl uses to interact with caches for multiple situations.  Therefore, this package, and implementations of its
 * interfaces could easily be broken out into a separate project.  And probably should be.
 */
package edu.wvu.ferl.cache;