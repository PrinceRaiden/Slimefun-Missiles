package me.princeraiden.missiles.utils.ui;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.princeraiden.missiles.blockstorage.LaunchComputerXStorageItem;
import me.princeraiden.missiles.blockstorage.LaunchComputerYStorageItem;
import me.princeraiden.missiles.blockstorage.LaunchComputerZStorageItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LaunchComputerUI {

    private static final int[] BORDER = {
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

    private boolean armed;
    private AttemptLaunchCallback onAttemptLaunch;
    private int x;
    private int y;
    private int z;

    public LaunchComputerUI(String id, String displayName, AttemptLaunchCallback onAttemptLaunch) {
        this.onAttemptLaunch = onAttemptLaunch;

        new BlockMenuPreset(id, displayName) {
            @Override
            public void init() {
                addBorder(this);
            }

            @Override
            public void newInstance(BlockMenu menu, Block b) {
                Config config = BlockStorage.getLocationInfo(b.getLocation());
                String xString = config.getString(LaunchComputerXStorageItem.getKey());
                String yString = config.getString(LaunchComputerYStorageItem.getKey());
                String zString = config.getString(LaunchComputerZStorageItem.getKey());
                x = xString == null ? x : Integer.parseInt(xString);
                y = yString == null ? y : Integer.parseInt(yString);
                z = zString == null ? z : Integer.parseInt(zString);
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

    private void addBorder(BlockMenuPreset preset) {
        for (int i : BORDER) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
    }

    private void fill(BlockMenu menu, int initialSlot, boolean negative, CoordinateType type, Block blockLoc) {
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
                    (Player p, int slot, ItemStack item, ClickAction action) -> onMenuClickCoordinateButton(menu, action.isShiftClicked() ? finalIncrement * 10 : finalIncrement, type, blockLoc));
            increment *= 100;
        }
    }

    private boolean onMenuClickCoordinateButton(BlockMenu preset, int increment, CoordinateType type, Block blockLoc) {
        switch (type) {
            case X:
                x += increment;
                BlockStorage.addBlockInfo(blockLoc, LaunchComputerXStorageItem.getKey(), String.valueOf(x));
                break;
            case Y:
                y += increment;
                if (y < minCruiseAlt) {
                    y = minCruiseAlt;
                } else if (y > maxCruiseAlt) {
                    y = maxCruiseAlt;
                }

                BlockStorage.addBlockInfo(blockLoc, LaunchComputerYStorageItem.getKey(), String.valueOf(y));
                break;
            case Z:
                z += increment;
                BlockStorage.addBlockInfo(blockLoc, LaunchComputerZStorageItem.getKey(), String.valueOf(z));
                break;
        }
        updateCoordinateDisplay(preset, type);
        return false;
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

    private void updateCoordinateDisplay(BlockMenu menu, CoordinateType type) {
        String typeString = "";
        String[] lore = new String[0];
        int slot = 0;
        int coordinate = 0;

        switch (type) {
            case X:
                slot = xDisplaySlot;
                coordinate = x;
                typeString = "X";
                break;
            case Y:
                slot = yDisplaySlot;
                coordinate = y;
                typeString = "Cruise Altitude";
                lore = new String[3];
                lore[0] = "";
                lore[1] = "&eMin: " + minCruiseAlt;
                lore[2] = "&eMax: " + maxCruiseAlt;
                break;
            case Z:
                slot = zDisplaySlot;
                coordinate = z;
                typeString = "Z";
                break;
        }

        menu.replaceExistingItem(slot, new CustomItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, "&c" + typeString + ": &e" + coordinate, lore));
    }

    private void addLaunchButton(BlockMenu menu, int slot, Block b) {
        Material material = armed ? Material.RED_STAINED_GLASS : Material.GRAY_STAINED_GLASS;
        String name = (armed ? "&c" : "&7") + "Launch";
        CustomItemStack itemStack = new CustomItemStack(material, name);
        menu.replaceExistingItem(slot, itemStack);
        menu.addMenuClickHandler(slot, (Player p, int s, ItemStack item, ClickAction action) -> {
            if (!armed)
                return false;

            Location target = new Location(b.getWorld(), x, y, z);
            onAttemptLaunch.run(target, b.getLocation());
            return false;
        });
    }

    private void updateBlockMenu(BlockMenu menu, Block b) {
        fill(menu, 10, true, CoordinateType.X, b);
        updateCoordinateDisplay(menu, CoordinateType.X);
        fill(menu, 14, false, CoordinateType.X, b);

        fill(menu, 19, true, CoordinateType.Y, b);
        updateCoordinateDisplay(menu, CoordinateType.Y);
        fill(menu, 23, false, CoordinateType.Y, b);

        fill(menu, 28, true, CoordinateType.Z, b);
        updateCoordinateDisplay(menu, CoordinateType.Z);
        fill(menu, 32, false, CoordinateType.Z, b);

        addArmedIndicator(menu, 37, b);
        addArmedIndicator(menu, 38, b);
        addArmedIndicator(menu, 39, b);
        addLaunchButton(menu, 42, b);
    }

    private enum CoordinateType {
        X, Y, Z
    }
}
