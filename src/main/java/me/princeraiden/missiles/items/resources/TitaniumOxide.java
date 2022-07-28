package me.princeraiden.missiles.items.resources;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import me.princeraiden.missiles.Missiles;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

public class TitaniumOxide implements GEOResource {

    private final NamespacedKey key;
    private final ItemStack item;

    public TitaniumOxide(Missiles missiles, ItemStack item) {
        this.key = new NamespacedKey(missiles, "titanium_oxide");
        this.item = item;
    }

    @Override
    public int getDefaultSupply(World.Environment environment, Biome biome) {
        return 20;
    }

    @Override
    public int getMaxDeviation() {
        return 8;
    }

    @Override
    public String getName() {
        return "Titanium Oxide";
    }

    @Override
    public ItemStack getItem() {
        return item.clone();
    }

    @Override
    public boolean isObtainableFromGEOMiner() {
        return true;
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }
}
