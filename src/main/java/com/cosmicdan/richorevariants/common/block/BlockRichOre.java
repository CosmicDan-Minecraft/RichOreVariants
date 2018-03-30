package com.cosmicdan.richorevariants.common.block;

import com.cosmicdan.cosmiclib.holders.BlockAndMeta;
import com.cosmicdan.richorevariants.common.ModConfig;
import lombok.extern.log4j.Log4j2;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Random;

@Log4j2(topic = "RichOreVariants/BlockRichOre")
public final class BlockRichOre extends BlockOre {
	private final BlockAndMeta baseBlock;

	private BlockRichOre(BlockAndMeta baseBlock, MapColor color) {
		super(color);
		this.baseBlock = baseBlock;
	}

	public BlockAndMeta getBaseBlock() {
		return baseBlock;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(baseBlock.block);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return baseBlock.meta;
	}

	@Override
	public int quantityDropped(@Nullable Random random) {
		return quantityDroppedWithBonus(0, random);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, @Nullable Random random) {
		// maybe add a fortune bonus option?
		return ModConfig.MULTIPLIERS.drop;
	}

	@Override
	public int getExpDrop(@Nullable IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
		return 0;
	}

	@SuppressWarnings({"deprecation", "MethodWithMultipleReturnPoints", "ConstantConditions"})
	public static BlockRichOre builder(BlockAndMeta originalOreBlock) {
		final MapColor oreBlockMapColor = originalOreBlock.block.getMapColor(null, null, null);
		//if (null == oreBlockMapColor) // always false apparently
		//	oreBlockMapColor = Material.ROCK.getMaterialMapColor();
		final BlockRichOre richOre = new BlockRichOre(originalOreBlock, oreBlockMapColor);

		// find and set hardness
		final float hardness = originalOreBlock.block.getStateFromMeta(originalOreBlock.meta).getBlockHardness(null, null);
		if (0.0f == hardness) {
			log.error("Skipping generation of RichOreVariant for {}, reason: {}", originalOreBlock.block, "Could not get block hardness");
			return null;
		}
		richOre.setHardness((float) (ModConfig.MULTIPLIERS.hardness * hardness));
		// we will have to let the game use calculated blast resistance as it's not possible to get resistance from a blockstate
		return richOre;
	}
}
