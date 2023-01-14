package io.github.greatericontop.greatuhc.customitems;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CraftLimiter implements Listener {

    private final Map<UUID, Integer> crafts_LightApple = new HashMap<>();
    private final Map<UUID, Integer> crafts_sharpBook = new HashMap<>();
    private final Map<UUID, Integer> crafts_corn = new HashMap<>();

    public void clearCrafts() {
        crafts_LightApple.clear();
        crafts_sharpBook.clear();
        crafts_corn.clear();
    }

    private void handleLimitedCraft(CraftItemEvent event, Map<UUID, Integer> craftMap, int craftLimit) {
        UUID uuid = event.getWhoClicked().getUniqueId();
        int craftsAlready = craftMap.getOrDefault(uuid, 0);
        if (craftsAlready >= craftLimit) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(String.format("§cYou already crafted the limit of §e%d §cof this item.", craftLimit));
        } else {
            craftMap.put(uuid, craftsAlready + 1);
            event.getWhoClicked().sendMessage(String.format("§7You have crafted §e%d§7/§e%d §7of this item.", craftsAlready + 1, craftLimit));
        }
    }

    @EventHandler()
    public void onCraft(CraftItemEvent event) {
        if (!(event.getRecipe() instanceof ShapedRecipe)) {
            return;
        }
        ShapedRecipe recipe = (ShapedRecipe) event.getRecipe();
        NamespacedKey key = recipe.getKey();
        if (!key.getNamespace().equals("uhc")) {
            return;
        }
        UUID uuid = event.getWhoClicked().getUniqueId();

        if (key.getKey().equals("light_apple")) {
            handleLimitedCraft(event, crafts_LightApple, 2);
        } else if (key.getKey().equals("sharp_book")) {
            handleLimitedCraft(event, crafts_sharpBook, 4);
        } else if (key.getKey().equals("corn")) {
            handleLimitedCraft(event, crafts_corn, 1);
        }
    }

}
