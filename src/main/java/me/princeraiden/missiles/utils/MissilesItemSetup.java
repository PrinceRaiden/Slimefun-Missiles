package me.princeraiden.missiles.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.groups.NestedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.SubItemGroup;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.princeraiden.missiles.Missiles;
import me.princeraiden.missiles.effects.BlindnessMissileEffect;
import me.princeraiden.missiles.effects.RadiationMissileEffect;
import me.princeraiden.missiles.items.machines.LaunchComputer;
import me.princeraiden.missiles.items.machines.MissileLauncher;
import me.princeraiden.missiles.items.missiles.Missile;
import me.princeraiden.missiles.items.resources.TitaniumOxide;
import me.princeraiden.missiles.items.resources.VeryDeadlyRadioactiveItem;
import me.princeraiden.missiles.effects.DisorientationMissileEffect;
import me.princeraiden.missiles.effects.FireMissileEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class MissilesItemSetup {

    public static void setup(Missiles addon) {
        NestedItemGroup mainItemGroup = new NestedItemGroup(new NamespacedKey(addon, "MISSILES"), MissilesItems.MAIN_ITEM_GROUP);
        SubItemGroup resourcesItemGroup = new SubItemGroup(new NamespacedKey(addon, "MISSILES_RESOURCES"), mainItemGroup, MissilesItems.RESOURCES_ITEM_GROUP);
        SubItemGroup machinesItemGroup = new SubItemGroup(new NamespacedKey(addon, "MISSILES_MACHINES"), mainItemGroup, MissilesItems.MACHINES_ITEM_GROUP);
        SubItemGroup missilesItemGroup = new SubItemGroup(new NamespacedKey(addon, "MISSILES_MISSILES"), mainItemGroup, MissilesItems.MISSILES_ITEM_GROUP);

        new SlimefunItem(resourcesItemGroup, MissilesItems.ROCKET_FUEL, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.FUEL_BUCKET, SlimefunItems.FUEL_BUCKET
                }
        ).register(addon);

        new SlimefunItem(resourcesItemGroup, MissilesItems.NITROGLYCERIN, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.REACTOR_COOLANT_CELL
                }
        ).register(addon);

        new VeryDeadlyRadioactiveItem(resourcesItemGroup, MissilesItems.PLUTONIUM_239, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.PLUTONIUM
                }
        ).register(addon);

        new SlimefunItem(resourcesItemGroup, MissilesItems.GASOLINE, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.FUEL_BUCKET
                }
        ).register(addon);

        new SlimefunItem(resourcesItemGroup, MissilesItems.NAPALM, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.GASOLINE, SlimefunItems.PLASTIC_SHEET
                }
        ).register(addon);

        new VeryDeadlyRadioactiveItem(resourcesItemGroup, MissilesItems.COBALT_59, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.COBALT_INGOT, SlimefunItems.BOOSTED_URANIUM
                }
        ).register(addon);

        new SlimefunItem(resourcesItemGroup, MissilesItems.TITANIUM_OXIDE, RecipeType.GEO_MINER, new ItemStack[0]).register(addon);
        new TitaniumOxide(addon, MissilesItems.TITANIUM_OXIDE).register();

        new SlimefunItem(resourcesItemGroup, MissilesItems.TITANIUM_INGOT, RecipeType.HEATED_PRESSURE_CHAMBER,
                new ItemStack[] {
                        MissilesItems.TITANIUM_OXIDE, MissilesItems.TITANIUM_OXIDE
                }
        ).register(addon);

        new SlimefunItem(resourcesItemGroup, MissilesItems.TITANIUM_ALLOY, RecipeType.SMELTERY,
                new ItemStack[] {
                        SlimefunItems.HARDENED_METAL_INGOT, MissilesItems.TITANIUM_INGOT, SlimefunItems.HARDENED_METAL_INGOT,
                        SlimefunItems.STEEL_INGOT, MissilesItems.TITANIUM_INGOT, SlimefunItems.STEEL_INGOT,
                        MissilesItems.TITANIUM_INGOT, MissilesItems.TITANIUM_INGOT, MissilesItems.TITANIUM_INGOT
                }
        ).register(addon);

        new SlimefunItem(resourcesItemGroup, MissilesItems.ROCKET_CHAMBER_WALL, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.TITANIUM_ALLOY, SlimefunItems.COPPER_WIRE, MissilesItems.TITANIUM_ALLOY,
                        MissilesItems.TITANIUM_ALLOY, SlimefunItems.ENERGY_REGULATOR, MissilesItems.TITANIUM_ALLOY,
                        MissilesItems.TITANIUM_ALLOY, MissilesItems.TITANIUM_ALLOY, MissilesItems.TITANIUM_ALLOY
                }
        ).register(addon);

        new MissileLauncher(addon, machinesItemGroup, MissilesItems.MISSILE_LAUNCHER, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.ROCKET_CHAMBER_WALL, null, MissilesItems.ROCKET_CHAMBER_WALL,
                        MissilesItems.ROCKET_CHAMBER_WALL, null, MissilesItems.ROCKET_CHAMBER_WALL,
                        MissilesItems.ROCKET_CHAMBER_WALL, new ItemStack(Material.DISPENSER), MissilesItems.ROCKET_CHAMBER_WALL
                }
        ).register(addon);

        new LaunchComputer(machinesItemGroup, MissilesItems.LAUNCH_COMPUTER, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE, SlimefunItems.COPPER_WIRE,
                        SlimefunItems.ALUMINUM_BRASS_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.ALUMINUM_BRASS_INGOT,
                        SlimefunItems.PLASTIC_SHEET, SlimefunItems.FERROSILICON, SlimefunItems.GOLD_24K_BLOCK
                }
        ).register(addon);

        new Missile(missilesItemGroup, MissilesItems.MISSILE_1KT, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.COPPER_WIRE, MissilesItems.NITROGLYCERIN, SlimefunItems.COPPER_WIRE,
                        MissilesItems.NITROGLYCERIN, MissilesItems.NITROGLYCERIN, MissilesItems.NITROGLYCERIN,
                        MissilesItems.TITANIUM_ALLOY, MissilesItems.ROCKET_FUEL, MissilesItems.TITANIUM_ALLOY
                }, 10, 1, 50,
                new DisorientationMissileEffect(30),
                new FireMissileEffect(5, 0.1)
        ).register(addon);

        new Missile(missilesItemGroup, MissilesItems.MISSILE_5KT, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.TITANIUM_ALLOY, MissilesItems.NITROGLYCERIN, MissilesItems.TITANIUM_ALLOY,
                        MissilesItems.NITROGLYCERIN, MissilesItems.MISSILE_1KT, MissilesItems.NITROGLYCERIN,
                        MissilesItems.TITANIUM_ALLOY, MissilesItems.ROCKET_FUEL, MissilesItems.TITANIUM_ALLOY
                }, 15, 1.5f, 45,
                new DisorientationMissileEffect(30),
                new FireMissileEffect(5, 0.1)
        ).register(addon);

        new Missile(missilesItemGroup, MissilesItems.MISSILE_15KT_NUCLEAR, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.TITANIUM_ALLOY, MissilesItems.PLUTONIUM_239, MissilesItems.TITANIUM_ALLOY,
                        MissilesItems.PLUTONIUM_239, MissilesItems.MISSILE_5KT, MissilesItems.PLUTONIUM_239,
                        MissilesItems.TITANIUM_ALLOY, MissilesItems.ROCKET_FUEL, MissilesItems.TITANIUM_ALLOY
                }, 25, 2, 40,
                new DisorientationMissileEffect(40),
                new BlindnessMissileEffect(60),
                new RadiationMissileEffect(20),
                new FireMissileEffect(15, 0.1)
        ).register(addon);

        new Missile(missilesItemGroup, MissilesItems.MISSILE_50KT_NUCLEAR, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.PLUTONIUM_239, MissilesItems.PLUTONIUM_239, MissilesItems.PLUTONIUM_239,
                        MissilesItems.ROCKET_CHAMBER_WALL, MissilesItems.MISSILE_15KT_NUCLEAR, MissilesItems.ROCKET_CHAMBER_WALL,
                        MissilesItems.ROCKET_FUEL, MissilesItems.ROCKET_FUEL, MissilesItems.ROCKET_FUEL
                }, 35, 3, 30,
                new DisorientationMissileEffect(100),
                new BlindnessMissileEffect(80),
                new RadiationMissileEffect(50),
                new FireMissileEffect(30, 0.2)
        ).register(addon);

        new Missile(missilesItemGroup, MissilesItems.MISSILE_NUCLEAR, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.PLUTONIUM_239, MissilesItems.PLUTONIUM_239, MissilesItems.PLUTONIUM_239,
                    MissilesItems.PLUTONIUM_239, MissilesItems.MISSILE_15KT_NUCLEAR, MissilesItems.PLUTONIUM_239,
                        MissilesItems.ROCKET_CHAMBER_WALL, MissilesItems.ROCKET_FUEL, MissilesItems.ROCKET_CHAMBER_WALL
                }, 50, 3.5f, 25,
                new DisorientationMissileEffect(150),
                new BlindnessMissileEffect(120),
                new RadiationMissileEffect(90),
                new FireMissileEffect(45, 0.2)
        ).register(addon);

        new Missile(missilesItemGroup, MissilesItems.MISSILE_COBALT, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.COBALT_59, MissilesItems.COBALT_59, MissilesItems.COBALT_59,
                        MissilesItems.COBALT_59, MissilesItems.MISSILE_5KT, MissilesItems.COBALT_59,
                        MissilesItems.TITANIUM_ALLOY, MissilesItems.ROCKET_FUEL, MissilesItems.TITANIUM_ALLOY
                }, 15, 3, 10,
                new DisorientationMissileEffect(200),
                new BlindnessMissileEffect(200),
                new RadiationMissileEffect(150),
                new FireMissileEffect(15, 0.3)
        ).register(addon);

        new Missile(missilesItemGroup, MissilesItems.MISSILE_NAPALM, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        MissilesItems.NAPALM, MissilesItems.NAPALM, MissilesItems.NAPALM,
                        MissilesItems.NAPALM, MissilesItems.MISSILE_5KT, MissilesItems.NAPALM,
                        MissilesItems.TITANIUM_ALLOY, MissilesItems.ROCKET_FUEL, MissilesItems.TITANIUM_ALLOY
                }, 10, 3, 10,
                new DisorientationMissileEffect(30), // TODO multiple explosions for larger missiles (nuclear should probably have ~4 explosions of same strength to increase damage radius)
                new BlindnessMissileEffect(20),
                new FireMissileEffect(50, 0.5)
        ).register(addon);
    }
}
