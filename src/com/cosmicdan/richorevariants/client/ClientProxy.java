package com.cosmicdan.richorevariants.client;

import com.cosmicdan.cosmiclib.annotations.ForgeEntryPoint;
import com.cosmicdan.richorevariants.common.CommonProxy;
import lombok.extern.log4j.Log4j2;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@ForgeEntryPoint
@Log4j2(topic = "RichOreVariants/ClientProxy")
public class ClientProxy extends CommonProxy {
	@Override
	public final void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public final void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public final void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public void registerBlockRender(Block newBlock, Block baseBlock) {
		// set item model
		ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		itemModelMesher.register(Item.getItemFromBlock(newBlock), 0, new ModelResourceLocation(baseBlock.getRegistryName().getResourceDomain() + ":" + baseBlock.getRegistryName().getResourcePath()));
		
		// set world block model...?
		//IBakedModel baseModel = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(baseBlock.getDefaultState());

	}
}
