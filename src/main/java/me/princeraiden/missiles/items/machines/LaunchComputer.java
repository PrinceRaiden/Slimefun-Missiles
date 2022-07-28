package me.princeraiden.missiles.items.machines;

import io.github.thebusybiscuit.slimefun4.api.events.AndroidMineEvent;
import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.princeraiden.missiles.blockstorage.*;
import me.princeraiden.missiles.items.missiles.Missile;
import me.princeraiden.missiles.utils.Tuple;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LaunchComputer extends SlimefunItem {

    private final int[] border = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 18, 27, 36, 45,
            17, 26, 35, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53,
            40, 41, 43
    };
    private final int xDisplaySlot = 13;
    private final int yDisplaySlot = 22;
    private final int zDisplaySlot = 31;
    private final int maxCruiseAlt = 300;
    private final int minCruiseAlt = -64;

    private int launchX = 0;
    private int cruiseAlt = 200;
    private int launchZ = 0;
    private Location blockLocation;
    private boolean armed;

    public LaunchComputer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        new BlockMenuPreset(getId(), ChatUtils.removeColorCodes(item.getItemMeta().getDisplayName())) {

            @Override
            public void init() {
                createMenu(this);
            }

            @Override
            public void newInstance(BlockMenu menu, Block b) {
                Config config = BlockStorage.getLocationInfo(b.getLocation());
                String xString = config.getString(LaunchComputerXStorageItem.getKey());
                String yString = config.getString(LaunchComputerYStorageItem.getKey());
                String zString = config.getString(LaunchComputerZStorageItem.getKey());
                launchX = xString == null ? launchZ : Integer.parseInt(xString);
                cruiseAlt = yString == null ? cruiseAlt : Integer.parseInt(yString);
                launchZ = zString == null ? launchX : Integer.parseInt(zString);

                blockLocation = b.getLocation();
                updateBlockMenu(menu, b);
            }

            @Override
            public boolean canOpen(Block block, Player player) {
                return true;
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow itemTransportFlow) {
                return new int[0];
            }
        };
    }


    private void createMenu(BlockMenuPreset preset) {
        for (int i : border) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
    }

    private void fill(BlockMenu menu, int initialSlot, boolean negative, CoordinateType type) {
        Material material = negative ? Material.RED_STAINED_GLASS_PANE : Material.LIME_STAINED_GLASS_PANE;
        String colorCode = negative ? "&c" : "&a";
        int increment = negative ? -1 : 1;

        String typeString = "";
        switch (type) {
            case X:
                typeString = "X";
                break;
            case Y:
                typeString = "Cruise Altitude";
                break;
            case Z:
                typeString = "Z";
                break;
        }

        for (int i = negative ? 0 : 2; (negative ? i < 3 : i >= 0); i += negative ? 1 : -1) {
            int finalIncrement = increment;
            String name = colorCode + increment + " " + typeString;
            List<String> lore = List.of(
                    "",
                    "&eShift click to increment by " + increment * 10
            );

            menu.replaceExistingItem(initialSlot + i, new CustomItemStack(material, name, lore));
            menu.addMenuClickHandler(initialSlot + i,
                    (Player p, int slot, ItemStack item, ClickAction action) -> onMenuClickCoordinateButton(menu, action.isShiftClicked() ? finalIncrement * 10 : finalIncrement, type));
            increment *= 100;
        }
    }

    private boolean onMenuClickCoordinateButton(BlockMenu preset, int increment, CoordinateType type) {
        switch (type) {
            case X:
                launchX += increment;
                BlockStorage.addBlockInfo(blockLocation, LaunchComputerXStorageItem.getKey(), String.valueOf(launchX));
                break;
            case Y:
                cruiseAlt += increment;
                if (cruiseAlt < minCruiseAlt) {
                    cruiseAlt = minCruiseAlt;
                } else if (cruiseAlt > maxCruiseAlt) {
                    cruiseAlt = maxCruiseAlt;
                }

                BlockStorage.addBlockInfo(blockLocation, LaunchComputerYStorageItem.getKey(), String.valueOf(cruiseAlt));
                break;
            case Z:
                launchZ += increment;
                BlockStorage.addBlockInfo(blockLocation, LaunchComputerZStorageItem.getKey(), String.valueOf(launchZ));
                break;
        }
        updateCoordinateDisplay(preset, type);
        return false;
    }

    private void updateCoordinateDisplay(BlockMenu menu, CoordinateType type) {
        String typeString = "";
        String[] lore = new String[0];
        int slot = 0;
        int coordinate = 0;

        switch (type) {
            case X:
                slot = xDisplaySlot;
                coordinate = launchX;
                typeString = "X";
                break;
            case Y:
                slot = yDisplaySlot;
                coordinate = cruiseAlt;
                typeString = "Cruise Altitude";
                lore = new String[3];
                lore[0] = "";
                lore[1] = "&eMin: " + minCruiseAlt;
                lore[2] = "&eMax: " + maxCruiseAlt;
                break;
            case Z:
                slot = zDisplaySlot;
                coordinate = launchZ;
                typeString = "Z";
                break;
        }

        menu.replaceExistingItem(slot, new CustomItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&c" + typeString + ": &e" + coordinate, lore));
    }

    private void addArmedIndicator(BlockMenu menu, int slot, Block b) {
        Material material = armed ? Material.RED_STAINED_GLASS : Material.LIME_STAINED_GLASS;
        String name = armed ? "&cArmed" : "&aUnarmed";
        CustomItemStack itemStack = new CustomItemStack(material, name);
        menu.replaceExistingItem(slot, itemStack);
        menu.addMenuClickHandler(
                slot,
                (Player p, int s, ItemStack item, ClickAction action) -> {
                    armed = !armed;
                    updateBlockMenu(menu, b);
                    return false;
                }
        );
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

    private void addLaunchButton(BlockMenu menu, int slot, Block b) {
        Material material = armed ? Material.RED_STAINED_GLASS : Material.GRAY_STAINED_GLASS;
        String name = (armed ? "&c" : "&7") + "Launch";
        CustomItemStack itemStack = new CustomItemStack(material, name);
        menu.replaceExistingItem(slot, itemStack);
        menu.addMenuClickHandler(
                slot,
                (Player p, int s, ItemStack item, ClickAction action) -> {
                    if (!armed)
                        return false;

                    Tuple<MissileLauncher, Block> launcherTuple = findLauncher(b);
                    if (launcherTuple != null) {
                        MissileLauncher launcher = launcherTuple.getFirst();
                        Block launcherBlock = launcherTuple.getSecond();
                        launcher.launchMissile(launcherBlock, launchX, cruiseAlt, launchZ);
                    }

                    return false;
                }
        );
    }

    private void updateBlockMenu(BlockMenu menu, Block b) {
        fill(menu, 10, true, CoordinateType.X);
        updateCoordinateDisplay(menu, CoordinateType.X);
        fill(menu, 14, false, CoordinateType.X);

        fill(menu, 19, true, CoordinateType.Y);
        updateCoordinateDisplay(menu, CoordinateType.Y);
        fill(menu, 23, false, CoordinateType.Y);

        fill(menu, 28, true, CoordinateType.Z);
        updateCoordinateDisplay(menu, CoordinateType.Z);
        fill(menu, 32, false, CoordinateType.Z);

        addArmedIndicator(menu, 37, b);
        addArmedIndicator(menu, 38, b);
        addArmedIndicator(menu, 39, b);
        addLaunchButton(menu, 42, b);
    }

    private enum CoordinateType {
        X, Y, Z
    }
}
