package me.princeraiden.missiles.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import org.bukkit.Material;

public class MissilesItems {

    // Categories
    public static final CustomItemStack MAIN_ITEM_GROUP = new CustomItemStack(Material.GUNPOWDER, "&5Missiles");
    public static final CustomItemStack RESOURCES_ITEM_GROUP = new CustomItemStack(MissilesHeads.PLUTONIUM_239.getItemStack(), "&cResources");
    public static final CustomItemStack MACHINES_ITEM_GROUP = new CustomItemStack(MissilesHeads.COMPUTER.getItemStack(), "&cMachines");
    public static final CustomItemStack MISSILES_ITEM_GROUP = new CustomItemStack(MissilesHeads.MISSILE.getItemStack(), "&cMissiles");

    // Resources
    public static final SlimefunItemStack ROCKET_FUEL = new SlimefunItemStack(
            "ROCKET_FUEL", SlimefunItems.FUEL_BUCKET, "&aRocket Fuel"
    );

    public static final SlimefunItemStack NITROGLYCERIN = new SlimefunItemStack(
            "NITROGLYCERIN", MissilesHeads.NITROGLYCERIN.getBase64(), "&cNitroglycerin",
            "",
            "&eA very delicate explosive powder...",
            "&eHandle with extreme caution!"
    );

    public static final SlimefunItemStack PLUTONIUM_239 = new SlimefunItemStack(
            "PLUTONIUM_239", MissilesHeads.PLUTONIUM_239.getBase64(), "&7Plutonium-239",
            "",
            "&eAn isotope of Plutonium that is",
            "&eMore radioactive",
            "",
            LoreBuilder.radioactive(Radioactivity.VERY_DEADLY)
    );

    public static final SlimefunItemStack GASOLINE = new SlimefunItemStack(
            "GASOLINE", MissilesHeads.GASOLINE.getBase64(), "&cGasoline"
    );

    public static final SlimefunItemStack NAPALM = new SlimefunItemStack(
            "NAPALM", Material.GLOWSTONE_DUST, "&cNapalm Powder",
            "",
            "&eA sticky fiery substance"
    );

    public static final SlimefunItemStack COBALT_59 = new SlimefunItemStack(
            "COBALT_59", MissilesHeads.COBALT_59.getBase64(), "&5Cobalt-59",
            "",
            LoreBuilder.radioactive(Radioactivity.VERY_DEADLY)
    );

    public static final SlimefunItemStack TITANIUM_OXIDE = new SlimefunItemStack(
            "TITANIUM_OXIDE", Material.GUNPOWDER, "&3Titanium Oxide"
    );

    public static final SlimefunItemStack TITANIUM_INGOT = new SlimefunItemStack(
            "TITANIUM_INGOT", Material.IRON_INGOT, "&3Titanium"
    );

    public static final SlimefunItemStack TITANIUM_ALLOY = new SlimefunItemStack(
            "TITANIUM_ALLOY", Material.COPPER_INGOT, "&3Treated Titanium"
    );

    public static final SlimefunItemStack ROCKET_CHAMBER_WALL = new SlimefunItemStack(
            "ROCKET_CHAMBER_WALL", Material.NETHERITE_BLOCK, "&aRocket Chamber Wall"
    );

    // Machines
    public static final SlimefunItemStack MISSILE_LAUNCHER = new SlimefunItemStack(
            "MISSILE_LAUNCHER", Material.DROPPER, "&cMissile Launcher",
            "",
            "&aLaunches missiles. Right click the",
            "&aEnd rod to enable."
    );

    public static final SlimefunItemStack LAUNCH_COMPUTER = new SlimefunItemStack(
            "LAUNCH_COMPUTER", MissilesHeads.COMPUTER.getBase64(), "&cLaunch Computer",
            "",
            "&aPlace next to a missile launcher",
            "",
            LoreBuilder.powerBuffer(128),
            LoreBuilder.powerPerSecond(16)
    );

    // Missiles
    public static final SlimefunItemStack MISSILE_1KT = new SlimefunItemStack(
            "MISSILE_1KT", MissilesHeads.MISSILE.getBase64(), "&c1 Kiloton Missile"
    );

    public static final SlimefunItemStack MISSILE_5KT = new SlimefunItemStack(
            "MISSILE_5KT", MissilesHeads.MISSILE.getBase64(), "&c5 Kiloton Missile"
    );

    public static final SlimefunItemStack MISSILE_15KT_NUCLEAR = new SlimefunItemStack(
            "MISSILE_15KT", MissilesHeads.MISSILE.getBase64(), "&c15 Kiloton Nuclear Missile"
    );

    public static final SlimefunItemStack MISSILE_50KT_NUCLEAR = new SlimefunItemStack(
            "MISSILE_50KT", MissilesHeads.MISSILE.getBase64(), "&c50 Kiloton Nuclear Missile"
    );

    public static final SlimefunItemStack MISSILE_NUCLEAR = new SlimefunItemStack(
            "MISSILE_NUCLEAR", MissilesHeads.MISSILE.getBase64(), "&cNuclear Missile"
    );

    public static final SlimefunItemStack MISSILE_COBALT = new SlimefunItemStack(
            "MISSILE_COBALT", MissilesHeads.MISSILE.getBase64(), "&cRadioactive Cobalt Missile"
    );

    public static final SlimefunItemStack MISSILE_NAPALM = new SlimefunItemStack(
            "MISSILE_NAPALM", MissilesHeads.MISSILE.getBase64(), "&cNapalm Missile"
    );
}
