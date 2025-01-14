package com.bergerkiller.generated.org.bukkit;

import com.bergerkiller.mountiplex.reflection.declarations.Template;

/**
 * Instance wrapper handle for type <b>org.bukkit.World</b>.
 * To access members without creating a handle type, use the static {@link #T} member.
 * New handles can be created from raw instances using {@link #createHandle(Object)}.
 */
@Template.InstanceType("org.bukkit.World")
public abstract class WorldHandle extends Template.Handle {
    /** @See {@link WorldClass} */
    public static final WorldClass T = Template.Class.create(WorldClass.class, com.bergerkiller.bukkit.common.Common.TEMPLATE_RESOLVER);
    /* ============================================================================== */

    public static WorldHandle createHandle(Object handleInstance) {
        return T.createHandle(handleInstance);
    }

    /* ============================================================================== */

    public abstract int getMinHeight();
    /**
     * Stores class members for <b>org.bukkit.World</b>.
     * Methods, fields, and constructors can be used without using Handle Objects.
     */
    public static final class WorldClass extends Template.Class<WorldHandle> {
        @Template.Optional
        public final Template.Method<Void> playSound = new Template.Method<Void>();
        public final Template.Method<Integer> getMinHeight = new Template.Method<Integer>();

    }

}

