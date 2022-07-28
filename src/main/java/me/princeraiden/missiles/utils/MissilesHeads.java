package me.princeraiden.missiles.utils;

import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerHead;
import io.github.thebusybiscuit.slimefun4.libraries.dough.skins.PlayerSkin;
import org.bukkit.inventory.ItemStack;

public enum MissilesHeads {

    // TODO change these two heads
    NITROGLYCERIN("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODViZWMxNTc0ZGUzNzNkZmI3NzVhYjU4YjJjMWU0NjIxZDkyYzZkYWFjN2M2YTc0ZDc4ZmI3MGZmZjRkMCJ9fX0="),
    PLUTONIUM_239("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODMyMjg2OWIxMzE2OGJmMmEyMDA4ZDQ0YTliMzY4ZmUxMGRmOWJlZjBkZDliMzVlMTEwNjU4N2Y2YWQ4MDIxNiJ9fX0="),
    COBALT_59("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJmNzUyN2YwZTkxMWFkZGU1MzBiNzBjMmRlYmU4ZjMxZTQ3MzUwMDczZDNkMDdmNzEyYjMyMTQyMzU3NDhlZSJ9fX0="),
    GASOLINE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGI4OWRlNDhhOWI4N2NmZDA3YzcwNGYyYmU1ZTVhOGNjNDVlODA3OWQzOGZhYWVkZjEzYjE1ZDE1YTEwYTcwYyJ9fX0="),
    COMPUTER("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ5ZjE4YzlkODVmOTJmNzJmODY0ZDY3YzEzNjdlOWE0NWRjMTBmMzcxNTQ5YzQ2YTRkNGRkOWU0ZjEzZmY0In19fQ=="),
    MISSILE("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRhMzMyYWJkZTMzM2ExNWE2YzZmY2ZlY2E4M2YwMTU5ZWE5NGI2OGU4ZjI3NGJhZmMwNDg5MmI2ZGJmYyJ9fX0=");

    private String base64;
    MissilesHeads(String base64) {
        this.base64 = base64;
    }

    public ItemStack getItemStack() {
        return PlayerHead.getItemStack(PlayerSkin.fromBase64(this.base64));
    }

    public String getBase64() {
        return this.base64;
    }
}
