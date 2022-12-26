package masecla.modrinth4j.model.search;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacetCollection {
    private List<List<Facet>> facets;

    /**
     * This method will add a block which will be OR'd. If you want to use AND,
     * call this method multiple times.
     * 
     * From the Modrinth documentation:
     * 
     * Operators
     * In search, the main operators are AND and OR. The others are not supported as
     * of now.
     * 
     * OR
     * All elements in a single array after the first one are considered in a single
     * OR block. For example, the search [["versions:1.16.5", "versions:1.17.1"]]
     * translates to Projects that supports 1.16.5 OR 1.17.1
     * 
     * AND
     * All arrays in the top-level one are considered in a single AND block. For
     * example, the search [["versions:1.16.5"], ["project_type:modpack"]]
     * translates to Projects that support 1.16.5 AND are modpacks
     * 
     * For more information on how to use this, please see the Modrinth
     * documentation here:
     * {@link https://docs.modrinth.com/docs/tutorials/api_search/}
     * 
     * @param facets - The block of facets to add to the collection
     */
    public void addPossibleConditions(Facet... facets) {
        if (this.facets == null)
            this.facets = new ArrayList<>();
        List<Facet> list = new ArrayList<>();
        for (Facet f : facets)
            list.add(f);
        this.facets.add(list);
    }

    public static class FacetAdapter extends TypeAdapter<FacetCollection> {
        @Override
        public void write(JsonWriter out, FacetCollection value) throws java.io.IOException {
            if(value == null){
                out.nullValue();
                return;
            }

            out.beginArray();
            for (List<Facet> list : value.facets) {
                out.beginArray();
                for (Facet f : list) {
                    out.value(f.toString());
                }
                out.endArray();
            }
            out.endArray();
        }

        @Override
        public FacetCollection read(JsonReader in) throws java.io.IOException {
            JsonToken token = in.peek();
            if (!token.equals(JsonToken.BEGIN_ARRAY)) {
                return null;
            }

            FacetCollection collection = new FacetCollection(new ArrayList<>());
            in.beginArray();
            while (in.hasNext()) {
                List<Facet> list = new ArrayList<>();
                in.beginArray();
                while (in.hasNext()) {
                    String[] split = in.nextString().split(":");
                    list.add(new Facet(Facet.FacetType.valueOf(split[0].toUpperCase()), split[1]));
                }
                in.endArray();
                collection.facets.add(list);
            }
            in.endArray();

            return collection;
        }

    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (List<Facet> list : facets) {
            builder.append("[");
            for (Facet f : list) {
                builder.append(f.toString());
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("],");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("]");
        return builder.toString();
    }
}
