package io.github.greatericontop.greatuhc.customitems;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemSugarCookieListener implements Listener {

    @EventHandler()
    public void onEat(PlayerItemConsumeEvent event) {
        ItemMeta im = event.getItem().getItemMeta();
        if (im == null)  return;
        if (im.getLore() == null)  return;
        if (im.getLore().get(0).equals("Sugary")) {
            Player player = event.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 400, 3));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        }
    }

}
