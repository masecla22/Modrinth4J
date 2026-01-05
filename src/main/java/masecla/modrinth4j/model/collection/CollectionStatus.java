package masecla.modrinth4j.model.collection;

import com.google.gson.annotations.SerializedName;

/**
 * A status decides the visibility of a collection in search, URLs, and the whole site itself.
 */
public enum CollectionStatus {
    /** Collection is displayed on search, and accessible by URL */
    @SerializedName("listed")
    LISTED,
    
    /** Collection is not displayed on search, but accessible by URL */
    @SerializedName("unlisted")
    UNLISTED,
    
    /** Collection is private and only visible to the owner */
    @SerializedName("private")
    PRIVATE,
    
    /** Collection is disabled/rejected */
    @SerializedName("rejected")
    REJECTED,
    
    /** Unknown status */
    @SerializedName("unknown")
    UNKNOWN;
    
    /**
     * Returns if the collection is hidden (cannot be viewed)
     * 
     * @return true if the collection is hidden
     */
    public boolean isHidden() {
        return this == REJECTED || this == PRIVATE;
    }
    
    /**
     * Returns if the collection is searchable
     * 
     * @return true if the collection can be found in search
     */
    public boolean isSearchable() {
        return this == LISTED;
    }
}
