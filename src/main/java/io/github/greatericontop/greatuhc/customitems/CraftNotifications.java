package io.github.greatericontop.greatuhc.customitems;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftNotifications implements Listener {
    private static final String HOVER_MSG = """
            §eThis is a custom craft in UHC.
            §7Open a crafting table to make this item.
            You can find the recipe using the knowledge
            book (in green, on the left).""";

    private final List<ShapedRecipe> shapedRecipes;
    private final List<ShapelessRecipe> shapelessRecipes;

    public CraftNotifications() {
        shapedRecipes = new ArrayList<>();
        shapelessRecipes = new ArrayList<>();
    }

    public void initializeCraftLists() {
        Bukkit.recipeIterator().forEachRemaining(recipe -> {
            if (recipe instanceof ShapedRecipe shapedRecipe) {
                if (shapedRecipe.getKey().getKey().equals("bloodlust")) {
                    shapedRecipes.add(shapedRecipe);
                }
            }
//            if (recipe instanceof ShapedRecipe shapedRecipe) {
//                if (shapedRecipe.getKey().getNamespace().equals("uhc")) {
//                    shapedRecipes.add(shapedRecipe);
//                }
//            } else if (recipe instanceof ShapelessRecipe shapelessRecipe) {
//                if (shapelessRecipe.getKey().getNamespace().equals("uhc")) {
//                    shapelessRecipes.add(shapelessRecipe);
//                }
//            }
        });
    }

    private boolean _shouldCraft(Map<Material, Integer> inventory, Map<Material, Integer> requiredItems, Material materialPickedUp, int amountPickedUp) {
        for (Map.Entry<Material, Integer> entry : requiredItems.entrySet()) {
            if (entry.getKey() == materialPickedUp) {
                int amountRequired = entry.getValue();
                int beforeAmount = inventory.getOrDefault(entry.getKey(), 0);
                int afterAmount = beforeAmount + amountPickedUp;
                // Must NOT be able to craft before, and be ABLE to craft after
                if (!(beforeAmount < amountRequired && amountRequired <= afterAmount)) {
                    return false;
                }
            } else {
                if (inventory.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                    return false;
                }
            }
        }
        // If the material picked up isn't used in the recipe, then we can craft it before and after.
        // (Otherwise, if it does, return true.)
        return requiredItems.containsKey(materialPickedUp);
    }

    private boolean shouldCraft(Map<Material, Integer> inventory, ShapedRecipe recipe, Material materialPickedUp, int amountPickedUp) {
        Map<Material, Integer> requiredItems = new HashMap<>();
        // :recipe.getIngredientMap(): actually returns a map of {a: _, b: _, c: _, ..., h: _, i: _} of the
        // 9 items you need - NOT what each letter means
        for (ItemStack stack : recipe.getIngredientMap().values()) {
            if (stack == null)  continue;
            Material mat = stack.getType();
            requiredItems.put(mat, requiredItems.getOrDefault(mat, 0) + 1);
        }
        return _shouldCraft(inventory, requiredItems, materialPickedUp, amountPickedUp);
    }

    private boolean shouldCraft(Map<Material, Integer> inventory, ShapelessRecipe recipe, Material materialPickedUp, int amountPickedUp) {
        Map<Material, Integer> requiredItems = new HashMap<>();
        for (ItemStack stack : recipe.getIngredientList()) {
            Material mat = stack.getType();
            requiredItems.put(mat, requiredItems.getOrDefault(mat, 0) + 1);
        }
        return _shouldCraft(inventory, requiredItems, materialPickedUp, amountPickedUp);
    }

    private Map<Material, Integer> generateInventoryMap(PlayerInventory inventory) {
        // Note: There's nothing special about this. This only takes into account the materials.
        //       Yes, there will be some problems if we want custom items, but I really don't care.
        Map<Material, Integer> invMap = new HashMap<>();
        for (ItemStack stack : inventory.getContents()) {
            if (stack != null) {
                Material mat = stack.getType();
                invMap.put(mat, invMap.getOrDefault(mat, 0) + stack.getAmount());
            }
        }
        return invMap;
    }

    @EventHandler()
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player))  return;
        PlayerInventory inventory = player.getInventory();

        // NOTE: The item actually hasn't been added to the inventory yet!
        ItemStack itemPickedUp = event.getItem().getItemStack();
        Material materialPickedUp = itemPickedUp.getType();
        int amountPickedUp = itemPickedUp.getAmount();
        Map<Material, Integer> invMap = generateInventoryMap(inventory);
        for (ShapedRecipe recipe : shapedRecipes) {
            if (shouldCraft(invMap, recipe, materialPickedUp, amountPickedUp)) {
                player.sendMessage(Component.text("§2[info] §7You have the ingredients to craft ")
                        .append(recipe.getResult().displayName())
                        .append(Component.text("§7."))
                );
                player.sendMessage(Component.text("      §8(Hover for more)").hoverEvent(Component.text(HOVER_MSG)));
            }
        }
        for (ShapelessRecipe recipe : shapelessRecipes) {
            if (shouldCraft(invMap, recipe, materialPickedUp, amountPickedUp)) {
                player.sendMessage(Component.text("§2[info] §7You have the ingredients to craft ")
                        .append(recipe.getResult().displayName())
                        .append(Component.text("§7."))
                );
                player.sendMessage(Component.text("      §8(Hover for more)").hoverEvent(Component.text(HOVER_MSG)));
            }
        }
    }

}
