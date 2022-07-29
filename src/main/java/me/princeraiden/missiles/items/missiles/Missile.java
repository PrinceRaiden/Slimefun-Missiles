package me.princeraiden.missiles.items.missiles;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.princeraiden.missiles.effects.MissileEffect;
import org.bukkit.inventory.ItemStack;

public class Missile extends SlimefunItem {

    private int explosionPower;
    private int explosionDiameter;
    private int explosionSpacing;
    /**
     * To find blocks per tick, square speed
     */
    private float speed;
    private float accuracyRadius;
    private MissileEffect[] effects;

    public Missile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                   int explosionPower, float speed, float accuracy, MissileEffect... effects) {
        this(itemGroup, item, recipeType, recipe, explosionPower, 1, 0, speed, accuracy, effects);
    }

    public Missile(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                   int explosionPower, int explosionDiameter, int explosionSpacing, float speed, float accuracy, MissileEffect... effects) {
        super(itemGroup, item, recipeType, recipe);
        this.explosionPower = explosionPower;
        this.explosionDiameter = explosionDiameter;
        this.explosionSpacing = explosionSpacing;
        this.speed = speed;
        this.accuracyRadius = accuracy;
        this.effects = effects;
    }

    public int getExplosionPower() {
        return explosionPower;
    }

    public int getExplosionDiameter() {
        return explosionDiameter;
    }

    public int getExplosionSpacing() {
        return explosionSpacing;
    }

    public float getSpeed() {
        return speed;
    }

    public float getAccuracyRadius() {
        return accuracyRadius;
    }

    public MissileEffect[] getEffects() {
        return effects;
    }
}
