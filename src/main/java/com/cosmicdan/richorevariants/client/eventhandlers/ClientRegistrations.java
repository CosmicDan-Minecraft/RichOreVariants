package com.cosmicdan.richorevariants.client.eventhandlers;

import com.cosmicdan.richorevariants.client.render.block.BlockRichOreStateMapper;
import com.cosmicdan.richorevariants.client.util.PropertyStringMapper;
import com.cosmicdan.richorevariants.common.block.BlockRichOre;
import com.cosmicdan.richorevariants.common.eventhandlers.CommonRegistrations;
import lombok.extern.log4j.Log4j2;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Log4j2
@Mod.EventBusSubscriber(Side.CLIENT)
public final class ClientRegistrations {
	private ClientRegistrations() {}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		final BlockRichOreStateMapper richOreBlockStateMapper = new BlockRichOreStateMapper();
		for (final Block block : CommonRegistrations.getBLOCKS()) {
			if (block instanceof BlockRichOre) {
				final BlockRichOre richOreBlock = (BlockRichOre) block;
				//noinspection ConstantConditions
				final String resourceDomain = richOreBlock.getBaseBlock().block.getRegistryName().getResourceDomain();
				final String resourcePath = richOreBlock.getBaseBlock().block.getRegistryName().getResourcePath();
				//noinspection deprecation
				final IBlockState blockState = richOreBlock.getBaseBlock().block.getStateFromMeta(richOreBlock.getBaseBlock().meta);

				// item model
				final ModelResourceLocation itemModel = new ModelResourceLocation(resourceDomain + ':' + resourcePath, PropertyStringMapper.INSTANCE.getPropertyString(blockState.getProperties()));

				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(richOreBlock), 0, itemModel);
				// block (world) model
				ModelLoader.setCustomStateMapper(richOreBlock, richOreBlockStateMapper);
			}
		}
	}
}
