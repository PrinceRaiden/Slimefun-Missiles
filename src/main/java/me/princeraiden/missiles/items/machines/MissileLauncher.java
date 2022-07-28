package me.princeraiden.missiles.items.machines;

import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockDispenseHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.princeraiden.missiles.Missiles;
import me.princeraiden.missiles.items.missiles.Missile;
import me.princeraiden.missiles.utils.MissileAnimationRunnable;
import me.princeraiden.missiles.utils.MissilesHeads;
import me.princeraiden.missiles.utils.MissilesItems;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MissileLauncher extends SlimefunItem {

    private Missiles addon;

    public MissileLauncher(Missiles addon, ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addon = addon;
    }

    public void launchMissile(Block block, Location target) {
        if (block.getState() instanceof Dropper) {
            Dropper dropper = (Dropper) block.getState();
            Inventory inv = dropper.getInventory();

            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = inv.getItem(i);
                SlimefunItem sfItem = SlimefunItem.getByItem(item);

                if (sfItem instanceof Missile) {
                    Missile missile = (Missile) sfItem;
                    displayMissileAnimation(block, target, missile);
                    break;
                }
            }
        }
    }

    public void displayMissileAnimation(Block origin, Location target, Missile missile) {
        MissileAnimationRunnable runnable = new MissileAnimationRunnable(addon, origin, target, missile);
        int runnableId = addon.getServer().getScheduler().scheduleSyncRepeatingTask(addon, runnable, 0, 1);
        runnable.setId(runnableId);
    }
}
