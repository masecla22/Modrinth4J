package masecla.modrinth4j.model.search;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.project.ProjectType;

/**
 * Represents a facet.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Facet {

    /**
     * The type of the facet.
     */
    public static enum FacetType {
        /** The categories facet */
        CATEGORIES,
        /** The versions facet */
        VERSIONS,
        /** The license facet */
        LICENSE,
        /** The project type facet */
        PROJECT_TYPE;
    }

    /** The facet type */
    private FacetType type;

    /** The facet value */
    private String value;

    /**
     * Returns the facet as a string.
     */
    @Override
    public String toString() {
        return type.toString().toLowerCase() + ":" + value;
    }

    /**
     * Creates a new category facet.
     * 
     * @param value The category value.
     * @return The category facet.
     */
    public static Facet category(String value) {
        return new Facet(FacetType.CATEGORIES, value);
    }

    /**
     * Creates a new version facet.
     * 
     * @param value The version value.
     * @return The version facet.
     */
    public static Facet version(String value) {
        return new Facet(FacetType.VERSIONS, value);
    }

    /**
     * Creates a new license facet.
     * 
     * @param value The license value.
     * @return The license facet.
     */
    public static Facet license(String value) {
        return new Facet(FacetType.LICENSE, value);
    }

    /**
     * Creates a new project type facet.
     * 
     * @param type The project type.
     * @return The project type facet.
     */
    public static Facet projectType(ProjectType type) {
        return new Facet(FacetType.PROJECT_TYPE, type.name().toLowerCase());
    }
}
