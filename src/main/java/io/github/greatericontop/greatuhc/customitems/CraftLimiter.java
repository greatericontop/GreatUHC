package io.github.greatericontop.greatuhc.customitems;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
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

    private int findNumberInCraftingTable(CraftItemEvent event) {
        // the minimum number of all the items in the grid is the highest possible number of items that can be crafted
        // since each result requires 1 of every item in the grid
        int minNumber = 9999;
        for (ItemStack craftingStack : event.getInventory().getMatrix()) {
            if (craftingStack == null) {
                continue;
            }
            minNumber = Math.min(minNumber, craftingStack.getAmount());
        }
        return minNumber;
    }

    private void handleLimitedCraft(CraftItemEvent event, Map<UUID, Integer> craftMap, int craftLimit) {
        UUID uuid = event.getWhoClicked().getUniqueId();
        int craftsAlready = craftMap.getOrDefault(uuid, 0);
        if (craftsAlready >= craftLimit) { // can't make more
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(String.format("§cYou already crafted the limit of §e%d §cof this item.", craftLimit));
        } else {
            int numberOfCrafts = findNumberInCraftingTable(event);
            if (craftsAlready + numberOfCrafts > craftLimit) { // attempted to make too many
                event.setCancelled(true);
                event.getWhoClicked().sendMessage(String.format("§cYou have too many items in the crafting table. You can only craft §e%d §cmore items. (You tried to make %d.)", craftLimit-craftsAlready, numberOfCrafts));
            } else {
                craftMap.put(uuid, craftsAlready + numberOfCrafts);
                event.getWhoClicked().sendMessage(String.format("§7You have crafted §e%d§7/§e%d §7of this item.", craftsAlready + numberOfCrafts, craftLimit));
            }
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
