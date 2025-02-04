package com.bergerkiller.generated.net.minecraft.network.syncher;

import com.bergerkiller.mountiplex.reflection.declarations.Template;
import com.bergerkiller.bukkit.common.wrappers.DataWatcher.Item;
import com.bergerkiller.bukkit.common.wrappers.DataWatcher.Key;
import com.bergerkiller.generated.net.minecraft.world.entity.EntityHandle;
import java.util.List;

/**
 * Instance wrapper handle for type <b>net.minecraft.network.syncher.DataWatcher</b>.
 * To access members without creating a handle type, use the static {@link #T} member.
 * New handles can be created from raw instances using {@link #createHandle(Object)}.
 */
@Template.InstanceType("net.minecraft.network.syncher.DataWatcher")
public abstract class DataWatcherHandle extends Template.Handle {
    /** @See {@link DataWatcherClass} */
    public static final DataWatcherClass T = Template.Class.create(DataWatcherClass.class, com.bergerkiller.bukkit.common.Common.TEMPLATE_RESOLVER);
    /* ============================================================================== */

    public static DataWatcherHandle createHandle(Object handleInstance) {
        return T.createHandle(handleInstance);
    }

    public static final DataWatcherHandle createNew(EntityHandle owner) {
        return T.constr_owner.newInstance(owner);
    }

    /* ============================================================================== */

    public abstract List<Item<?>> unwatchAndReturnAllWatched();
    public abstract List<Item<?>> returnAllWatched();
    public abstract Item<Object> read(Key<?> key);
    public abstract void register(Key<?> key, Object defaultValue);
    public abstract void set(Key<?> key, Object value);
    public abstract Object get(Key<?> key);
    public abstract boolean isChanged();
    public abstract boolean isEmpty();

    public static DataWatcherHandle createNew(org.bukkit.entity.Entity owner) {
        return createHandle(T.constr_owner.raw.newInstance(com.bergerkiller.bukkit.common.conversion.type.HandleConversion.toEntityHandle(owner)));
    }
    public abstract EntityHandle getOwner();
    public abstract void setOwner(EntityHandle value);
    /**
     * Stores class members for <b>net.minecraft.network.syncher.DataWatcher</b>.
     * Methods, fields, and constructors can be used without using Handle Objects.
     */
    public static final class DataWatcherClass extends Template.Class<DataWatcherHandle> {
        public final Template.Constructor.Converted<DataWatcherHandle> constr_owner = new Template.Constructor.Converted<DataWatcherHandle>();

        public final Template.Field.Converted<EntityHandle> owner = new Template.Field.Converted<EntityHandle>();

        public final Template.Method.Converted<List<Item<?>>> unwatchAndReturnAllWatched = new Template.Method.Converted<List<Item<?>>>();
        public final Template.Method.Converted<List<Item<?>>> returnAllWatched = new Template.Method.Converted<List<Item<?>>>();
        public final Template.Method.Converted<Item<Object>> read = new Template.Method.Converted<Item<Object>>();
        public final Template.Method<Void> register = new Template.Method<Void>();
        public final Template.Method<Void> set = new Template.Method<Void>();
        public final Template.Method<Object> get = new Template.Method<Object>();
        public final Template.Method<Boolean> isChanged = new Template.Method<Boolean>();
        public final Template.Method<Boolean> isEmpty = new Template.Method<Boolean>();

    }


    /**
     * Instance wrapper handle for type <b>net.minecraft.network.syncher.DataWatcher.Item</b>.
     * To access members without creating a handle type, use the static {@link #T} member.
     * New handles can be created from raw instances using {@link #createHandle(Object)}.
     */
    @Template.InstanceType("net.minecraft.network.syncher.DataWatcher.Item")
    public abstract static class ItemHandle extends Template.Handle {
        /** @See {@link ItemClass} */
        public static final ItemClass T = Template.Class.create(ItemClass.class, com.bergerkiller.bukkit.common.Common.TEMPLATE_RESOLVER);
        /* ============================================================================== */

        public static ItemHandle createHandle(Object handleInstance) {
            return T.createHandle(handleInstance);
        }

        /* ============================================================================== */

        public abstract Object getValue();
        public abstract void setValue(Object value);

        public DataWatcherHandle.ItemHandle cloneHandle() {
            Object clone;
            if (T.constr_key_value.isAvailable()) {
                Object rawKey = T.key.raw.get(getRaw());
                clone = T.constr_key_value.raw.newInstance(rawKey, this.getValue());
            } else {
                int typeId = T.typeId.getInteger(getRaw());
                int keyId = T.keyId.getInteger(getRaw());
                clone = T.constr_typeId_keyId_value.raw.newInstance(typeId, keyId, this.getValue());
            }
            T.changed.copy(getRaw(), clone);
            return createHandle(clone);
        }
        public abstract boolean isChanged();
        public abstract void setChanged(boolean value);
        /**
         * Stores class members for <b>net.minecraft.network.syncher.DataWatcher.Item</b>.
         * Methods, fields, and constructors can be used without using Handle Objects.
         */
        public static final class ItemClass extends Template.Class<ItemHandle> {
            @Template.Optional
            public final Template.Constructor.Converted<ItemHandle> constr_key_value = new Template.Constructor.Converted<ItemHandle>();
            @Template.Optional
            public final Template.Constructor.Converted<ItemHandle> constr_typeId_keyId_value = new Template.Constructor.Converted<ItemHandle>();

            @Template.Optional
            public final Template.Field.Integer typeId = new Template.Field.Integer();
            @Template.Optional
            public final Template.Field.Integer keyId = new Template.Field.Integer();
            @Template.Optional
            public final Template.Field.Converted<Key<?>> key = new Template.Field.Converted<Key<?>>();
            @Template.Optional
            public final Template.Field<Object> value = new Template.Field<Object>();
            public final Template.Field.Boolean changed = new Template.Field.Boolean();

            public final Template.Method<Object> getValue = new Template.Method<Object>();
            public final Template.Method<Void> setValue = new Template.Method<Void>();

        }

    }

}

