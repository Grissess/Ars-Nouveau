package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.GlyphLib;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EffectCraft extends AbstractEffect {
    public EffectCraft() {
        super(GlyphLib.EffectCraftID, "Craft");
    }

    private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.crafting");

    @Override
    public void onResolve(RayTraceResult rayTraceResult, World world, @Nullable LivingEntity shooter, List<AbstractAugment> augments, SpellContext spellContext) {
        if(rayTraceResult instanceof EntityRayTraceResult) {
            Entity hit = ((EntityRayTraceResult) rayTraceResult).getEntity();
            if (hit instanceof LivingEntity && isRealPlayer((LivingEntity) hit)) {
                PlayerEntity playerEntity = (PlayerEntity) hit;
                playerEntity.openMenu(new SimpleNamedContainerProvider((id, inventory, player) -> {
                    return new CustomWorkbench(id, inventory, IWorldPosCallable.create(player.getCommandSenderWorld(), player.blockPosition()));
                }, CONTAINER_NAME));
            }
        }
    }

    @Override
    public int getManaCost() {
        return 50;
    }

    public static class CustomWorkbench extends WorkbenchContainer{

        public CustomWorkbench(int id, PlayerInventory playerInventory) {
            super(id, playerInventory);
        }

        public CustomWorkbench(int id, PlayerInventory playerInventory, IWorldPosCallable p_i50090_3_) {
            super(id, playerInventory, p_i50090_3_);
        }

        @Override
        public boolean stillValid(PlayerEntity playerIn) {
            return true;
        }
    }

    @Override
    public Item getCraftingReagent() {
        return Items.CRAFTING_TABLE;
    }

    @Override
    public String getBookDescription() {
        return "Opens the target's crafting menu.";
    }
}
