package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.common.lib.LibItemNames;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SpellParchment extends ModItem implements IScribeable {
    public SpellParchment() {
        super(LibItemNames.SPELL_PARCHMENT);
    }

    @Override
    public void inventoryTick(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if(!stack.hasTag())
            stack.setTag(new CompoundNBT());
    }

    public static void setSpell(ItemStack stack, String spellRecipe){
        stack.getTag().putString("spell", spellRecipe);
    }

    public static Spell getSpellRecipe(ItemStack stack){
        if(!stack.hasTag())
            return new Spell();
        return Spell.deserialize(stack.getTag().getString("spell"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        if(!stack.hasTag() || stack.getTag().getString("spell").equals(""))
            return;

        StringBuilder tip = new StringBuilder();
        Spell spellsFromTagString = Spell.deserialize(stack.getTag().getString("spell"));
        tooltip.add(new StringTextComponent(spellsFromTagString.getDisplayString()));
    }

    @Override
    public boolean onScribe(World world, BlockPos pos, PlayerEntity player, Hand handIn, ItemStack thisStack) {

        if(!(player.getHeldItem(handIn).getItem() instanceof SpellBook))
            return false;

        if(SpellBook.getMode(player.getHeldItem(handIn).getTag()) == 0){
            PortUtil.sendMessage(player, new StringTextComponent("Set your spell book to a spell."));
            return false;
        }

        SpellParchment.setSpell(thisStack, SpellBook.getRecipeString(player.getHeldItem(handIn).getTag(), SpellBook.getMode(player.getHeldItem(handIn).getTag())));
        PortUtil.sendMessage(player,new StringTextComponent("Spell inscribed."));
        return false;
    }
}
