package me.princeraiden.missiles;

import me.princeraiden.missiles.items.machines.MissileLauncher;
import me.princeraiden.missiles.listeners.MissileUnloadListener;
import me.princeraiden.missiles.utils.MissilesItemSetup;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missiles extends JavaPlugin implements SlimefunAddon {

    private MissileUnloadListener missileUnloadListener;

    @Override
    public void onEnable() {
        // Read something from your config.yml
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }

        MissilesItemSetup.setup(this);
        registerListeners();
    }

    private void registerListeners() {
        missileUnloadListener = new MissileUnloadListener();
        getServer().getPluginManager().registerEvents(missileUnloadListener, this);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/PrinceRaiden/Slimefun-Missiles/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public MissileUnloadListener getMissileUnloadListener() {
        return missileUnloadListener;
    }
}
