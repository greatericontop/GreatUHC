package io.github.greatericontop.greatuhc.game.pregame;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KitSelectorGUIListener implements Listener {
    private static final String INVENTORY_NAME = "§e§k~~ §6UHC Kits §e§k~~";

    private PreGameManager manager;
    public KitSelectorGUIListener(PreGameManager manager) {
        this.manager = manager;
    }

    @EventHandler()
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND)  return;
        ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta im = heldItem.getItemMeta();
        if (im == null)  return;
        if (im.getLore() != null && im.getLore().get(0).equals("Kit Selector")) {
            openKitSelector(event.getPlayer());
        }
    }

    private ItemStack newItemStack(Material mat, String name, String... lore) {
        ItemStack stack = new ItemStack(mat, 1);
        ItemMeta im = stack.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        stack.setItemMeta(im);
        return stack;
    }

    public void openKitSelector(Player player) {
        Inventory gui = Bukkit.createInventory(player, 9, INVENTORY_NAME);
        for (int i = 0; i < 9; i++) {
            gui.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        }
        gui.setItem(0, newItemStack(
                Material.LEATHER_CHESTPLATE, "§eArmorer",
                "§7Leather Helmet (protection 1)",
                "§7Leather Chestplate (protection 1)",
                "§7Leather Leggings (protection 1)",
                "§7Leather Boots (protection 1)"
        ));
        gui.setItem(1, newItemStack(
                Material.STONE_PICKAXE, "§eStone Gear",
                "§7Stone Sword (unbreaking 1)",
                "§7Stone Pickaxe (efficiency 1 unbreaking 1)",
                "§7Stone Axe (efficiency 1 unbreaking 1)",
                "§7Stone Shovel (efficiency 1 unbreaking 1)"
        ));
        gui.setItem(2, newItemStack(
                Material.VINE, "§eEcologist",
                "§764 Lily Pad",
                "§720 Oak Log",
                "§73 Apple",
                "§73 Sugar Cane",
                "§7Stone Pickaxe (efficiency 1)"
        ));
        gui.setItem(3, newItemStack(
                Material.ENCHANTING_TABLE, "§eEnchanter",
                "§76 Leather",
                "§712 Sugar Cane",
                "§730 Bottle o' Enchanting",
                "§7Stone Pickaxe (efficiency 1)"
        ));
        player.openInventory(gui);
    }

    @EventHandler()
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(INVENTORY_NAME)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (!event.getView().getTopInventory().equals(event.getClickedInventory())) {
            // skip if player is not clicking the top inventory with the items in it
            return;
        }
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {
            event.setCancelled(true);
            return;
        }

        switch (event.getSlot()) {
            case 0 -> manager.setPlayerKit(player.getUniqueId(), PreGameManager.Kit.ARMORER);
            case 1 -> manager.setPlayerKit(player.getUniqueId(), PreGameManager.Kit.STONE_GEAR);
            case 2 -> manager.setPlayerKit(player.getUniqueId(), PreGameManager.Kit.ECOLOGIST);
            case 3 -> manager.setPlayerKit(player.getUniqueId(), PreGameManager.Kit.ENCHANTER);
        }
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        event.setCancelled(true);
    }

}