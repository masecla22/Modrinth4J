package masecla.modrinth4j.model.team;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a permission mask for a team member.
 */
@Data
@NoArgsConstructor
public class ModrinthPermissionMask {
    /**
     * Represents a permission type.
     */
    @Getter
    @AllArgsConstructor
    public static enum PermissionType {
        /** Whether the member can upload versions to the project */
        UPLOAD_VERSION(0),
        /** Whether the member can delete versions from the project */
        DELETE_VERSION(1),
        /** Whether the member can edit the project details */
        EDIT_DETAILS(2),
        /** Whether the member can edit the project body */
        EDIT_BODY(3),
        /** Whether the member can manage invites */
        MANAGE_INVITES(4),
        /** Whether the member can remove other members */
        REMOVE_MEMBER(5),
        /** Whether the member can edit other members */
        EDIT_MEMBER(6),
        /** Whether the member can delete the project */
        DELETE_PROJECT(7),
        /** Whether the member can view analytics */
        VIEW_ANALYTICS(8),
        /** Whether the member can view revenue */
        VIEW_REVENUE(9);

        /** The bit significance of the permission */
        private int bitSignificance;
    }

    /** The permissions the member has */
    private Set<PermissionType> permissions = new HashSet<>();

    /**
     * Checks if the mask contains a permission.
     * 
     * @param permission - The permission to check for
     * @return boolean - Whether the mask contains the permission
     */
    public boolean hasPermission(PermissionType permission) {
        return permissions.contains(permission);
    }

    /**
     * Adds a permission to the mask.
     * 
     * @param permission - The permission to add
     */
    public void addPermission(PermissionType permission) {
        permissions.add(permission);
    }

    /**
     * Removes a permission from the mask.
     * 
     * @param permission - The permission to remove
     */
    public void removePermission(PermissionType permission) {
        permissions.remove(permission);
    }

    /**
     * Sets a permission to a value.
     * 
     * @param permission - The permission to set
     * @param value      - The value to set the permission to
     */
    public void setPermission(PermissionType permission, boolean value) {
        if (value)
            addPermission(permission);
        else
            removePermission(permission);
    }

    /**
     * Builds a mask from the permissions.
     * 
     * @return int - The mask
     */
    public int getMask() {
        int mask = 0;
        for (PermissionType permission : permissions) {
            mask |= 1 << permission.bitSignificance;
        }
        return mask;
    }

    /**
     * Sets the mask from an integer.
     * 
     * @param mask - The mask to set
     */
    public void setMask(int mask) {
        permissions.clear();
        for (PermissionType permission : PermissionType.values()) {
            if ((mask & (1 << permission.bitSignificance)) != 0) {
                permissions.add(permission);
            }
        }
    }

    /**
     * Builds a {@link ModrinthPermissionMask} from a mask.
     * 
     * @param mask - The mask to build from
     * @return ModrinthPermissionMask - The mask
     */
    public static ModrinthPermissionMask fromMask(int mask) {
        ModrinthPermissionMask permissionMask = new ModrinthPermissionMask();
        permissionMask.setMask(mask);
        return permissionMask;
    }

    /**
     * Builds a {@link ModrinthPermissionMask} with all permissions.
     * 
     * @return ModrinthPermissionMask - The mask
     */
    public static ModrinthPermissionMask all() {
        ModrinthPermissionMask permissionMask = new ModrinthPermissionMask();
        for (PermissionType permission : PermissionType.values()) {
            permissionMask.addPermission(permission);
        }
        return permissionMask;
    }

    /**
     * This is a {@link TypeAdapter} for {@link ModrinthPermissionMask}. Used to
     * build the bitmask
     * from the JSON.
     */
    public static class ModrinthPermissionMaskAdapter extends TypeAdapter<ModrinthPermissionMask> {
        /**
         * Writes the mask to the JSON.
         */
        @Override
        public void write(com.google.gson.stream.JsonWriter out, ModrinthPermissionMask value)
                throws java.io.IOException {
            if (value != null)
                out.value(value.getMask());
            else
                out.nullValue();
        }

        /**
         * Reads the mask from the JSON.
         */
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
