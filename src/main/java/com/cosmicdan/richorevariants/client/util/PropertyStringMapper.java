package com.cosmicdan.richorevariants.client.util;

import lombok.extern.log4j.Log4j2;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@link StateMapperBase} used to create property strings (i.e. via #getPropertyString).
 * @author Choonster
 */
@MethodsReturnNonnullByDefault
@Log4j2
public class PropertyStringMapper extends StateMapperBase {
	public static final PropertyStringMapper INSTANCE = new PropertyStringMapper();

	@ParametersAreNonnullByDefault
	@Override
	protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
		return new ModelResourceLocation("minecraft:air");
	}
}
