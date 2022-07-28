package me.princeraiden.missiles.effects;

import me.princeraiden.missiles.utils.MissileEffectUtils;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Map;

public class BlindnessMissileEffect implements MissileEffect {

    private int radius;

    public BlindnessMissileEffect(int radius) {
        this.radius = radius;
    }

    @Override
    public void handle(Location origin) {
        Map<LivingEntity, Double> nearbyEntities = MissileEffectUtils.getNearbyLivingEntities(origin, radius);

        for (Map.Entry<LivingEntity, Double> entry : nearbyEntities.entrySet()) {
            LivingEntity entity = entry.getKey();
            double distanceSquared = entry.getValue();
            int duration = MissileEffectUtils.getPotionDuration(radius, distanceSquared);
            int amplifier = MissileEffectUtils.getPotionAmplifier(duration);
            PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier);
            entity.addPotionEffect(effect);
        }
    }

    @Override
    public int getRadius() {
        return radius;
    }
}
