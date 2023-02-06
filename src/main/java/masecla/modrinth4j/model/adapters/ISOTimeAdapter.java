package masecla.modrinth4j.model.adapters;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class ISOTimeAdapter extends TypeAdapter<Instant> {

    @Override
    public Instant read(JsonReader in) throws IOException {
        JsonToken nextToken = in.peek();
        if (nextToken == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String s = in.nextString();
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(s);
        return Instant.from(ta);
    }

    @Override
    public void write(JsonWriter out, Instant value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.value(DateTimeFormatter.ISO_INSTANT.format(value));
    }

}
