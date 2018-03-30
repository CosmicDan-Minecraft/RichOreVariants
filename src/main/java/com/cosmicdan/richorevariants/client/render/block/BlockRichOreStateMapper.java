package com.cosmicdan.richorevariants.client.render.block;

import com.cosmicdan.richorevariants.common.block.BlockRichOre;
import lombok.extern.log4j.Log4j2;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@Log4j2
public class BlockRichOreStateMapper extends StateMapperBase {
	@ParametersAreNonnullByDefault
	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		Block blockToModel = state.getBlock();
		IBlockState blockToModelState = state;
		if (state.getBlock() instanceof BlockRichOre) {
			blockToModel = ((BlockRichOre) state.getBlock()).getBaseBlock().block;
			//noinspection deprecation
			blockToModelState = blockToModel.getStateFromMeta(((BlockRichOre) state.getBlock()).getBaseBlock().meta);
		}

		return new ModelResourceLocation(Block.REGISTRY.getNameForObject(blockToModel), this.getPropertyString(blockToModelState.getProperties()));
	}
}
