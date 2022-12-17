package masecla.modrinth4j.model.team;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModrinthPermissionMask {
    @Getter
    @AllArgsConstructor
    public static enum PermissionType {
        UPLOAD_VERSION(0),
        DELETE_VERSION(1),
        EDIT_DETAILS(2),
        EDIT_BODY(3),
        MANAGE_INVITES(4),
        REMOVE_MEMBER(5),
        EDIT_MEMBER(6),
        DELETE_PROJECT(7),
        VIEW_ANALYTICS(8),
        VIEW_REVENUE(9);

        private int bitSignificance;
    }

    private Set<PermissionType> permissions = new HashSet<>();

    public boolean hasPermission(PermissionType permission) {
        return permissions.contains(permission);
    }

    public void addPermission(PermissionType permission) {
        permissions.add(permission);
    }

    public void removePermission(PermissionType permission) {
        permissions.remove(permission);
    }

    public void setPermission(PermissionType permission, boolean value) {
        if (value)
            addPermission(permission);
        else
            removePermission(permission);
    }

    public int getMask() {
        int mask = 0;
        for (PermissionType permission : permissions) {
            mask |= 1 << permission.bitSignificance;
        }
        return mask;
    }

    public void setMask(int mask) {
        permissions.clear();
        for (PermissionType permission : PermissionType.values()) {
            if ((mask & (1 << permission.bitSignificance)) != 0) {
                permissions.add(permission);
            }
        }
    }

    public static ModrinthPermissionMask fromMask(int mask) {
        ModrinthPermissionMask permissionMask = new ModrinthPermissionMask();
        permissionMask.setMask(mask);
        return permissionMask;
    }

    public static class ModrinthPermissionMaskAdapter extends TypeAdapter<ModrinthPermissionMask> {
        @Override
        public void write(com.google.gson.stream.JsonWriter out, ModrinthPermissionMask value)
                throws java.io.IOException {
            out.value(value.getMask());
        }

        @Override
        public ModrinthPermissionMask read(com.google.gson.stream.JsonReader in) throws java.io.IOException {
            JsonToken token = in.peek();
            if (token == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            if (token == JsonToken.NUMBER) {
                return fromMask(in.nextInt());
            }
            return null;
        }
    }

}
