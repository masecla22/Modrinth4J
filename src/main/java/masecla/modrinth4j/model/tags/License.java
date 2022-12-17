package masecla.modrinth4j.model.tags;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class License {
    @SerializedName("short")
    private String shortName;
    private String name;
}
