package com.cosmicdan.richorevariants.common.item;

import com.cosmicdan.richorevariants.common.ModConstants;
import com.cosmicdan.richorevariants.common.block.BlockRichOre;
import lombok.extern.log4j.Log4j2;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

@Log4j2(topic = "RichOreVariants/ItemBlockRichOre")
public class ItemBlockRichOre extends ItemBlock {

	public ItemBlockRichOre(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (this.block instanceof BlockRichOre) {
			Block baseBlock = ((BlockRichOre)this.block).getBaseBlock();
			String baseOreBlockName = baseBlock.getLocalizedName();
			return I18n.translateToLocal(ModConstants.MODID + ".richoreprefix") + " " + baseOreBlockName;
		}
		// shouldn't happen
		return super.getItemStackDisplayName(stack);
	}
}
