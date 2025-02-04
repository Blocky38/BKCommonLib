package com.bergerkiller.bukkit.common.internal.logic;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import com.bergerkiller.bukkit.common.Common;
import com.bergerkiller.bukkit.common.conversion.type.HandleConversion;
import com.bergerkiller.bukkit.common.events.PacketReceiveEvent;
import com.bergerkiller.bukkit.common.events.PacketSendEvent;
import com.bergerkiller.bukkit.common.internal.CommonPlugin;
import com.bergerkiller.bukkit.common.protocol.PacketListener;
import com.bergerkiller.bukkit.common.protocol.PacketType;
import com.bergerkiller.generated.net.minecraft.server.level.EntityPlayerHandle;
import com.bergerkiller.generated.net.minecraft.server.level.WorldServerHandle;
import com.bergerkiller.mountiplex.reflection.declarations.Template;

/**
 * Handler for Minecraft 1.9 - 1.13.2. There are no portal create events being used,
 * and the portal travel agent has simple methods to find and create portals at
 * coordinates. On these versions there are also methods to create end platforms.
 * We can use the 'isViewingCredits' method of the player to check whether portal
 * events should be ignored.
 */
class PortalHandler_1_9 extends PortalHandler {
    private final PortalTravelAgentHandle _pta = Template.Class.create(PortalTravelAgentHandle.class, Common.TEMPLATE_RESOLVER);

    public PortalHandler_1_9() {
    }

    @Override
    public void enable(CommonPlugin plugin) {
        // Listener to disable player portal events pre-emptively while players are viewing credits
        // This is required, otherwise other plugins get very confused and teleport players out of the
        // credits screen.
        plugin.register(new Listener() {
            @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
            public void onPortalEvent(PlayerPortalEvent event) {
                if (EntityPlayerHandle.fromBukkit(event.getPlayer()).isViewingCredits()) {
                    event.setCancelled(true);
                }
            }
        });

        // While the player is inside the end portal, it continuously fires a portal enter event.
        // This causes a horrible sound effect to play, because that effect is sent every tick.
        // This is obviously very unwanted while viewing the credits!
        plugin.register(new PacketListener() {
            @Override
            public void onPacketReceive(PacketReceiveEvent event) {
            }

            @Override
            public void onPacketSend(PacketSendEvent event) {
                if (EntityPlayerHandle.fromBukkit(event.getPlayer()).isViewingCredits()) {
                    event.setCancelled(true);
                }
            }
        }, PacketType.OUT_WORLD_EVENT);
    }

    @Override
    public void disable(CommonPlugin plugin) {
    }

    @Override
    public void forceInitialization() {
        _pta.forceInitialization();
    }

    @Override
    public Block findNetherPortal(Block startBlock, int radius) {
        return _pta.findNetherPortal(startBlock, radius);
    }

    @Override
    public Block createNetherPortal(Block startBlock, BlockFace orientation, Entity initiator) {
        int radius = WorldServerHandle.fromBukkit(startBlock.getWorld()).getNetherPortalCreateRadius();
        if (_pta.createNetherPortal(startBlock, radius)) {
            return _pta.findNetherPortal(startBlock, radius);
        } else {
            return null;
        }
    }

    @Override
    public void markNetherPortal(Block netherPortalBlock) {
        // No portals are stored on 1.14 and before
    }

    @Override
    public Block findEndPlatform(World world) {
        return _pta.findOrCreateEndPlatform(world, false);
    }

    @Override
    public Block createEndPlatform(World world, Entity initiator) {
        return _pta.findOrCreateEndPlatform(world, true);
    }

    @Override
    public boolean isMainEndWorld(World world) {
        return _pta.isMainEndWorld(world);
    }

    @Override
    public void showEndCredits(Player player) {
        EntityPlayerHandle ep = EntityPlayerHandle.fromBukkit(player);
        _pta.showEndCredits(HandleConversion.toEntityHandle(player), ep.hasSeenCredits());
        ep.setHasSeenCredits(true);
    }

    @Template.Optional
    @Template.Import("net.minecraft.core.BlockPosition")
    @Template.Import("net.minecraft.world.level.dimension.DimensionManager")
    @Template.Import("net.minecraft.server.level.EntityPlayer")
    @Template.Import("net.minecraft.server.level.WorldServer")
    @Template.Import("net.minecraft.network.protocol.Packet")
    @Template.Import("net.minecraft.network.protocol.game.PacketPlayOutGameStateChange")
    @Template.InstanceType("net.minecraft.server.PortalTravelAgent")
    public static abstract class PortalTravelAgentHandle extends Template.Class<Template.Handle> {

        /* 
         * <SHOW_END_CREDITS>
         * public static void showEndCredits(Object entityPlayerRaw, boolean seenCredits) {
         *     EntityPlayer player = (EntityPlayer) entityPlayerRaw;
         * #if version >= 1.10.2
         *     player.worldChangeInvuln = true;
         * #elseif version >= 1.9.4
         *     #require net.minecraft.server.EntityPlayer protected boolean worldChangeInvuln:ck;
         *     player#worldChangeInvuln = true;
         * #elseif version >= 1.9
         *     #require net.minecraft.server.EntityPlayer protected boolean worldChangeInvuln:cj;
         *     player#worldChangeInvuln = true;
         * #endif
         *     player.world.kill((net.minecraft.world.entity.Entity) player);
         *     if (!player.viewingCredits) {
         *         player.viewingCredits = true;
         *         player.playerConnection.sendPacket((Packet) new PacketPlayOutGameStateChange(4, seenCredits ? 0.0F : 1.0F));
         *     }
         * }
         */
        @Template.Generated("%SHOW_END_CREDITS%")
        public abstract void showEndCredits(Object entityPlayerRaw, boolean seenCredits);

        /*
         * <IS_MAIN_END_WORLD>
         * public static boolean isMainEndWorld(org.bukkit.World world) {
         *     WorldServer world = ((org.bukkit.craftbukkit.CraftWorld) world).getHandle();
         * #if version >= 1.13
         *     return world.dimension == DimensionManager.THE_END;
         * #else
         *     return world.dimension == 1;
         * #endif
         * }
         */
        @Template.Generated("%IS_MAIN_END_WORLD%")
        public abstract boolean isMainEndWorld(World world);

        /*
         * <FIND_NETHER_PORTAL>
         * public static org.bukkit.block.Block findNetherPortal(org.bukkit.block.Block startBlock, int createRadius) {
         *     WorldServer world = ((org.bukkit.craftbukkit.CraftWorld) startBlock.getWorld()).getHandle();
         *     BlockPosition blockposition = new BlockPosition(startBlock.getX(), startBlock.getY(), startBlock.getZ());
         *     PortalTravelAgent agent = new PortalTravelAgent(world);
         *     BlockPosition result = agent.findPortal((double) startBlock.getX(),
         *                                             (double) startBlock.getY(),
         *                                             (double) startBlock.getZ(),
         *                                             createRadius);
         *     if (result == null) {
         *         return null;
         *     }
         *     return startBlock.getWorld().getBlockAt(result.getX(), result.getY(), result.getZ());
         * }
         */
        @Template.Generated("%FIND_NETHER_PORTAL%")
        public abstract Block findNetherPortal(Block startBlock, int createRadius);

        /*
         * <CREATE_NETHER_PORTAL>
         * public static boolean createNetherPortal(org.bukkit.block.Block startBlock, int createRadius) {
         *     WorldServer world = ((org.bukkit.craftbukkit.CraftWorld) startBlock.getWorld()).getHandle();
         *     PortalTravelAgent agent = new PortalTravelAgent(world);
         *     return agent.createPortal((double) startBlock.getX() + 0.5,
         *                               (double) startBlock.getY(),
         *                               (double) startBlock.getZ() + 0.5,
         *                               createRadius);
         * }
         */
        @Template.Generated("%CREATE_NETHER_PORTAL%")
        public abstract boolean createNetherPortal(Block startBlock, int createRadius);

        /*
         * <FIND_OR_CREATE_END_PLATFORM>
         * public static org.bukkit.block.Block findOrCreateEndPlatform(org.bukkit.World bworld, boolean create) {
         *     #require net.minecraft.server.PortalTravelAgent private BlockPosition findEndPortal(BlockPosition portal);
         *     #require net.minecraft.server.PortalTravelAgent private BlockPosition createEndPortal(double x, double y, double z);
         * 
         *     WorldServer world = ((org.bukkit.craftbukkit.CraftWorld) bworld).getHandle();
         *     PortalTravelAgent agent = new PortalTravelAgent(world);
         * 
         * #if version >= 1.13
         *     BlockPosition platformPos = WorldProviderTheEnd.g;
         * #else
         *     World the_end_world = MinecraftServer.getServer().getWorldServer(1);
         *     BlockPosition platformPos = (the_end_world == null) ? null : the_end_world.worldProvider.h();
         *     if (platformPos == null) {
         *         platformPos = new BlockPosition(100, 50, 0);
         *     }
         * #endif
         * 
         *     if (create) {
         *         BlockPosition position = agent#createEndPortal((double)platformPos.getX(),(double)platformPos.getY(),(double)platformPos.getZ());
         *         return bworld.getBlockAt(platformPos.getX(), platformPos.getY()-2, platformPos.getZ());
         *     } else {
         *         BlockPosition position = agent#findEndPortal(platformPos);
         *         if (position == null) {
         *             return null;
         *         } else {
         *             return bworld.getBlockAt(position.getX(), position.getY()-1, position.getZ());
         *         }
         *     }
         * }
         */
        @Template.Generated("%FIND_OR_CREATE_END_PLATFORM%")
        public abstract Block findOrCreateEndPlatform(World world, boolean create);
    }
}
