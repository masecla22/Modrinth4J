package masecla.modrinth4j.model.search;

import lombok.Data;

@Data
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
}
