package com.cosmicdan.richorevariants.common.block;

import com.cosmicdan.richorevariants.common.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;

import java.util.Random;

public class BlockRichOre extends BlockOre {
	private final Block originalOreBlock;

	public BlockRichOre(Block originalOreBlock, MapColor color) {
		super(color);
		this.originalOreBlock = originalOreBlock;

	}

	public Block getBaseBlock() {
		return originalOreBlock;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(originalOreBlock);
	}

	@Override
	public int quantityDropped(Random random) {
		return quantityDroppedWithBonus(0, random);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		// maybe add a fortune bonus option?
		return ModConfig.MULTIPLIERS.drop;
	}

	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
		return 0;
	}
}
