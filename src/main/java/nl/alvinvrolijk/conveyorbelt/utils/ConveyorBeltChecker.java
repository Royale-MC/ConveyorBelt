package nl.alvinvrolijk.conveyorbelt.utils;

import nl.alvinvrolijk.conveyorbelt.ConveyorBelt;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ConveyorBeltChecker {

    public ConveyorBeltChecker() {
        if (new Config(ConveyorBelt.instance, false).get().getBoolean("enabled")) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(ConveyorBelt.instance, () -> {
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if(entity.getType().equals(EntityType.PLAYER)){
                            continue;
                        }
                        int speed = 0;
                        Vector direction = null;
                        Block block = entity.getLocation().getBlock();

                        if (block.getType().equals(Material.REPEATER)) {
                            Repeater repeater = (Repeater) block.getBlockData();

                            int rate;
                            rate = repeater.getDelay();

                            if (repeater.isPowered()) {
                                rate = rate * 3;
                            }

                            speed = speed + rate;
                            direction = faceToForce(repeater.getFacing().getOppositeFace());
                        }

                        if (block.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.PURPUR_STAIRS)) {
                            Stairs stairs = (Stairs) block.getLocation().subtract(0, 1, 0).getBlock().getBlockData();

                            if (stairs.getHalf().equals(Bisected.Half.BOTTOM)) {
                                direction = faceToForce(stairs.getFacing().getOppositeFace());
                                speed = speed + 2;
                            } else {
                                direction = faceToForce(stairs.getFacing().getOppositeFace());
                                speed = speed + 1;
                            }
                        }
                        if (block.getLocation().getBlock().getType().equals(Material.PURPUR_STAIRS)) {
                            Stairs stairs = (Stairs) block.getLocation().getBlock().getBlockData();

                            if (stairs.getHalf().equals(Bisected.Half.BOTTOM)) {
                                direction = faceToForce(stairs.getFacing().getOppositeFace());
                                speed = speed + 2;
                            } else {
                                direction = faceToForce(stairs.getFacing().getOppositeFace());
                                speed = speed + 1;
                            }
                        }
                        if (block.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.RED_NETHER_BRICK_STAIRS)) {
                            Stairs stairs = (Stairs) block.getLocation().subtract(0, 1, 0).getBlock().getBlockData();

                            if (stairs.getHalf().equals(Bisected.Half.BOTTOM)) {
                                direction = faceToForce(stairs.getFacing());
                                speed = speed + 2;
                            } else {
                                direction = faceToForce(stairs.getFacing());
                                speed = speed + 1;
                            }
                        }
                        if (block.getLocation().getBlock().getType().equals(Material.RED_NETHER_BRICK_STAIRS)) {
                            Stairs stairs = (Stairs) block.getLocation().getBlock().getBlockData();

                            if (stairs.getHalf().equals(Bisected.Half.BOTTOM)) {
                                direction = faceToForce(stairs.getFacing());
                                speed = speed + 2;
                            } else {
                                direction = faceToForce(stairs.getFacing());
                                speed = speed + 1;
                            }
                        }

                        if (speed != 0 && direction != null) {
                            if (entity.isOnGround() && (!(entity instanceof Player) || !((Player) entity).isSneaking()))
                                entity.setVelocity(direction.multiply(30 * speed));
                        }
                    }
                }
            }, 10L, 1L);
        }
    }

    /**
     * Converts the provided face to a vector that is pointing in that direction
     *
     * @param face BlockFace
     * @return Vector with the direction
     */
    private static Vector faceToForce(BlockFace face) {
        Vector out = new Vector(0, 0, 0);
        if (face == BlockFace.NORTH)
            out.setZ(-0.01);
        if (face == BlockFace.SOUTH)
            out.setZ(0.01);
        if (face == BlockFace.EAST)
            out.setX(0.01);
        if (face == BlockFace.WEST)
            out.setX(-0.01);
        if (face == BlockFace.UP)
            out.setY(0.01);
        if (face == BlockFace.DOWN)
            out.setY(-0.01);
        return out;
    }
}