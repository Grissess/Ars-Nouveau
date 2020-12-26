package com.hollingsworth.arsnouveau.client.renderer.item;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.spell.ISpellTier;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpellBookModel extends AnimatedGeoModel<SpellBook> {

    ResourceLocation T1 =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier1.geo.json");
    ResourceLocation T2 =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier2.geo.json");
    ResourceLocation T3_CLOSED =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier3closed.geo.json");
    ResourceLocation T3_OPEN =  new ResourceLocation(ArsNouveau.MODID , "geo/spellbook_tier3closed.geo.json");
    @Override
    public ResourceLocation getModelLocation(SpellBook book) {

        if(book.tier == ISpellTier.Tier.ONE)
            return T1;
        if(book.tier == ISpellTier.Tier.TWO)
            return T2;

        return T3_CLOSED;
    }

    @Override
    public ResourceLocation getTextureLocation(SpellBook book) {
        return new ResourceLocation(ArsNouveau.MODID, "textures/items/spellbook.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SpellBook book) {
        return new ResourceLocation(ArsNouveau.MODID , "animations/spellbook_animations.json");
    }
}
