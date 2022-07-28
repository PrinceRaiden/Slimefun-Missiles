package me.princeraiden.missiles.items.machines;

import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;
import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.princeraiden.missiles.blockstorage.*;
import me.princeraiden.missiles.items.missiles.Missile;
import me.princeraiden.missiles.utils.Tuple;
import me.princeraiden.missiles.utils.ui.LaunchComputerUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LaunchComputer extends SlimefunItem implements EnergyNetComponent {
    private final int energyConsumption = 16;

    public LaunchComputer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        new LaunchComputerUI(getId(), item.getDisplayName(), this::onAttemptLaunch);
        System.out.println(getId());
    }

    public void onAttemptLaunch(Location target, Location computerLocation) {
        if (getCharge(computerLocation) < energyConsumption)
            return;

        Tuple<MissileLauncher, Block> launcherTuple = findLauncher(computerLocation.getBlock());
        if (launcherTuple != null) {
            MissileLauncher launcher = launcherTuple.getFirst();
            Block launcherBlock = launcherTuple.getSecond();
            launcher.launchMissile(launcherBlock, target);
        }
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block block, SlimefunItem slimefunItem, Config config) {
                removeCharge(block.getLocation(), energyConsumption);
            }
        });
    }

    private Tuple<MissileLauncher, Block> findLauncher(Block center) {
        Block[] adjacentBlocks = new Block[] {
                center.getRelative(BlockFace.NORTH),
                center.getRelative(BlockFace.SOUTH),
                center.getRelative(BlockFace.EAST),
                center.getRelative(BlockFace.WEST)
        };

        for (Block b : adjacentBlocks) {
            SlimefunItem sfItem = BlockStorage.check(b);
            if (sfItem instanceof MissileLauncher) {
                MissileLauncher launcher = (MissileLauncher) sfItem;
                return new Tuple<>(launcher, b);
            }
        }

        return null;
    }

    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return 128;
    }
}
