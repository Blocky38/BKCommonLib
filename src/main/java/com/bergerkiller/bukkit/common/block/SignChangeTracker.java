package com.bergerkiller.bukkit.common.block;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import com.bergerkiller.bukkit.common.utils.BlockUtil;
import com.bergerkiller.bukkit.common.utils.MaterialUtil;
import com.bergerkiller.generated.net.minecraft.world.level.block.entity.TileEntitySignHandle;

/**
 * Efficiently detects when the text contents of a Sign change, when the
 * sign's backing block entity unloads and re-loads, and when the sign
 * block is destroyed in the world.
 *
 * Detecting will actively load the chunk the sign is in.
 */
public class SignChangeTracker {
    private final Block block;
    private Sign state;
    private TileEntitySignHandle tileEntity;
    private Object[] lastRawLines;

    protected SignChangeTracker(Block block) {
        this.block = block;
        this.state = null;
        this.tileEntity = null;
        this.lastRawLines = null;
    }

    protected SignChangeTracker(Block block, Sign state) {
        this.block = block;
        this.state = state;
        this.tileEntity = TileEntitySignHandle.fromBukkit(state);
        this.lastRawLines = this.tileEntity.getRawLines().clone();
    }

    /**
     * Returns true if the sign was broken/removed from the World. If true, then
     * {@link #getSign()} will return null
     *
     * @return True if removed
     */
    public boolean isRemoved() {
        return this.state == null;
    }

    /**
     * Gets the Block where the sign is at
     *
     * @return Sign Block
     */
    public Block getBlock() {
        return this.block;
    }

    /**
     * Gets a live-updated Sign instance
     *
     * @return Sign
     */
    public Sign getSign() {
        return this.state;
    }

    /**
     * Checks the sign state to see if changes have occurred. If the sign Block Entity
     * reloaded, or the sign text contents changed, true is returned. If there were
     * no changes then false is returned.<br>
     * <br>
     * If the sign was removed entirely, then true is also returned, which should be
     * checked with {@link #isRemoved()}. When removed, the {@link #getSign()} will
     * return null.
     *
     * @return True if changes occurred, or the sign was removed
     */
    public boolean update() {
        TileEntitySignHandle tileEntity = this.tileEntity;

        // Check world to see if a tile entity now exists at this Block.
        // Reading tile entities is slow, so avoid doing that if we can.
        if (tileEntity == null) {
            return tryLoadFromWorld(); // If found, initializes and returns true
        }

        // Ask the TileEntity we already have whether it was removed from the World
        // If it was, we must re-set and re-check for the sign.
        if (tileEntity.isRemoved()) {
            if (tryLoadFromWorld()) {
                return true; // Backing TileEntity instance changed, so probably changed
            } else {
                this.state = null;
                this.tileEntity = null;
                this.lastRawLines = null;
                return true; // Sign is gone
            }
        }

        // Check for sign lines that change. For this, we check the internal IChatBaseComponent contents
        Object[] oldRawLines = this.lastRawLines;
        Object[] newRawLines = tileEntity.getRawLines();
        int numLines = newRawLines.length;
        if (oldRawLines.length != numLines) {
            this.lastRawLines = newRawLines.clone();
            return true; // Never happens, really
        } else {
            int line = 0;
            while (line < numLines) {
                Object newLine = newRawLines[line];
                if (oldRawLines[line] != newLine) {
                    oldRawLines[line] = newLine;

                    // Detected a change. Re-create the Sign state with the updated lines,
                    // and return true to indicate the change.
                    while (++line < numLines) {
                        oldRawLines[line] = newRawLines[line];
                    }
                    this.state = this.tileEntity.toBukkit();
                    return true;
                }

                line++;
            }
            return false;
        }
    }

    private boolean tryLoadFromWorld() {
        Block block = this.block;
        Sign state;
        TileEntitySignHandle tileEntity;
        if (MaterialUtil.ISSIGN.get(block) &&
            (state = BlockUtil.getSign(block)) != null &&
            !(tileEntity = TileEntitySignHandle.fromBukkit(state)).isRemoved())
        {
            this.state = state;
            this.tileEntity = tileEntity;
            this.lastRawLines = tileEntity.getRawLines().clone();
            return true; // Changed!
        }

        return false;
    }

    @Override
    public String toString() {
        return "SignChangeTracker{block=" + this.block + ", sign=" + this.getSign() + "}";
    }

    /**
     * Tracks the changes done to a Sign
     *
     * @param sign The sign to track
     * @return Sign change tracker
     */
    public static SignChangeTracker track(Sign sign) {
        try {
            return new SignChangeTracker(sign.getBlock(), sign);
        } catch (NullPointerException ex) {
            if (sign == null) {
                throw new IllegalArgumentException("Sign is null");
            } else {
                throw ex;
            }
        }
    }

    /**
     * Tracks the changes done to a Sign
     *
     * @param sign The sign to track
     * @return Sign change tracker
     */
    public static SignChangeTracker track(Block signBlock) {
        Sign state = BlockUtil.getSign(signBlock);
        return (state == null) ? new SignChangeTracker(signBlock) : new SignChangeTracker(signBlock, state);
    }
}
