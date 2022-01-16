package me.tofumc.tofumcaddon.items;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;

import me.tofumc.tofumcaddon.TofuMCAddon;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class MobIncapacitator extends SlimefunItem {
    public MobIncapacitator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {

        addItemHandler(onBlockPlace());

    }


    private BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(true) {
            @Override
            public void onPlayerPlace(BlockPlaceEvent event) {
                incapacitate(event, event.getBlock().toString());
            }
        };
    }

    private void incapacitate(BlockPlaceEvent event, String originalBlock) {
        Bukkit.getScheduler().runTaskLater(TofuMCAddon.INSTANCE, () -> {
            Block block = event.getBlock();
            if (block.toString().equals(originalBlock)) {
                for (Entity en : block.getWorld().getEntities()) {
                    if (en instanceof Mob) {
                        Mob mob = (Mob) en;
                        double distance = mob.getLocation().distance(block.getLocation());
                        if (distance <= 5) {
                            if (mob.getMaxHealth() <= 20) {
                                mob.setHealth(1);
                            }
                        }
                    }
                }
                incapacitate(event, originalBlock);
            }
        }, 5 * 20L);
    }
}
