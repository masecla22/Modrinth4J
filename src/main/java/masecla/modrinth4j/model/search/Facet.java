package masecla.modrinth4j.model.search;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import masecla.modrinth4j.model.project.ProjectType;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Facet {

    public static enum FacetType {
        CATEGORIES,
        VERSIONS,
        LICENSE,
        PROJECT_TYPE;
    }

    private FacetType type;
    private String value;

    @Override
    public String toString() {
        return type.toString().toLowerCase() + ":" + value;
    }

    public static Facet category(String value) {
        return new Facet(FacetType.CATEGORIES, value);
    }

    public static Facet version(String value) {
        return new Facet(FacetType.VERSIONS, value);
    }

    public static Facet license(String value) {
        return new Facet(FacetType.LICENSE, value);
    }

    public static Facet projectType(ProjectType type) {
        return new Facet(FacetType.PROJECT_TYPE, type.name().toLowerCase());
    }
}
