package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.ritual.RitualContext;
import com.hollingsworth.arsnouveau.common.block.tile.RitualTile;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualDig extends AbstractRitual {

    public RitualDig(){
        super();
    }

    public RitualDig(RitualTile tile, RitualContext context) {
        super(tile, context);
    }

    @Override
    public void tick() {
        World world = tile.getLevel();
        if(world.getGameTime() % 20 == 0 && !world.isClientSide){
            BlockPos pos = tile.getBlockPos().north().below(getContext().progress);
            if(pos.getY() <= 1){
                onEnd();
                return;
            }
            world.destroyBlock(pos, true);
            world.destroyBlock(pos.south().south(), true);
            world.destroyBlock(pos.south().east(), true);
            world.destroyBlock(pos.south().west(), true);
            getContext().progress++;
        }
    }

    @Override
    public String getID() {
        return RitualLib.DIG;
    }
}
