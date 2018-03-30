package com.cosmicdan.richorevariants.common.item;

import com.cosmicdan.richorevariants.common.ModConstants;
import com.cosmicdan.richorevariants.common.block.BlockRichOre;
import lombok.extern.log4j.Log4j2;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@Log4j2(topic = "RichOreVariants/ItemBlockRichOre")
public class ItemBlockRichOre extends ItemBlock {
	public ItemBlockRichOre(Block block) {
		super(block);
	}

	@SuppressWarnings("deprecation")
	@ParametersAreNonnullByDefault
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		final String displayName;
		if (this.block instanceof BlockRichOre) {
			// Is there a better way to do this...?
			final Item baseItem = Item.getItemFromBlock(((BlockRichOre)this.block).getBaseBlock().block);
			final ItemStack baseItemStack = new ItemStack(baseItem, 1, ((BlockRichOre)this.block).getBaseBlock().meta);
			displayName = I18n.translateToLocal(ModConstants.MODID + ".richoreprefix") + ' ' + baseItemStack.getDisplayName();
		} else {
			// shouldn't happen
			displayName = super.getItemStackDisplayName(stack);
		}
		return displayName;
	}
}
