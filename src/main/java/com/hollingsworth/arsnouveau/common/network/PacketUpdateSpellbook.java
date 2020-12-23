package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.util.StackUtil;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class PacketUpdateSpellbook{

   String spellRecipe;
   int cast_slot;
   String spellName;

    public PacketUpdateSpellbook(){}

    public PacketUpdateSpellbook(String spellRecipe, int cast_slot, String spellName){
        this.spellRecipe = spellRecipe;
        this.cast_slot = cast_slot;
        this.spellName = spellName;
    }

    //Decoder
    public PacketUpdateSpellbook(PacketBuffer buf){
        spellRecipe = buf.readString(32767);
        cast_slot = buf.readInt();
        spellName = buf.readString(32767);
    }

    //Encoder
    public void toBytes(PacketBuffer buf){
        buf.writeString(spellRecipe);
        buf.writeInt(cast_slot);
        buf.writeString(spellName);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            if(ctx.get().getSender() != null){
                ItemStack stack = StackUtil.getHeldSpellbook(ctx.get().getSender());
                if(stack != null && stack.getItem() instanceof SpellBook && spellRecipe != null){
                    CompoundNBT tag = stack.hasTag() ? stack.getTag() : new CompoundNBT();
                    SpellBook item = (SpellBook) stack.getItem();
                    boolean isNext = SpellBook.getMode(tag) < cast_slot;
                    if(isNext)
                        item.playNextPage(stack);
                    else
                        item.playBackPage(stack);
                    SpellBook.setRecipe(tag, spellRecipe, cast_slot);
                    SpellBook.setSpellName(tag, spellName, cast_slot);
                    SpellBook.setMode(tag, cast_slot);
                    stack.setTag(tag);

                    Networking.INSTANCE.send(PacketDistributor.PLAYER.with(()->ctx.get().getSender()), new PacketUpdateBookGUI(tag));

                }
            }
        });
        ctx.get().setPacketHandled(true);

    }
}
