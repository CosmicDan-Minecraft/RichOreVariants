package com.cosmicdan.richorevariants.common;

import com.cosmicdan.cosmiclib.annotations.ForgeEntryPoint;
import com.cosmicdan.richorevariants.Main;
import com.cosmicdan.richorevariants.common.block.BlockRichOre;
import com.cosmicdan.richorevariants.common.item.ItemBlockRichOre;
import lombok.extern.log4j.Log4j2;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Events for both physical servers and clients. Most things will belong here.
 */
@ForgeEntryPoint
@Log4j2(topic = "RichOreVariants/CommonProxy")
public class CommonProxy {


	/**
	 * Register block/items to GameRegistry, (tile) entities ans assign oredict names
	 */
	public void preInit(FMLPreInitializationEvent event) {}

	/**
	 * Register worldgen, recipes, event handlers and send IMC messages
	 */
	public void init(FMLInitializationEvent event) {
		// we setup our block here because we need to make sure oredictionary block are made
		String[] oreNames = OreDictionary.getOreNames();
		for (String oreName : oreNames) {
			if (oreName.startsWith("ore")) {
				NonNullList<ItemStack> oreEntries = OreDictionary.getOres(oreName, false);
				if (oreEntries.size() > 0) {
					//Item oreItem = ;
					Block oreBlock = Block.getBlockFromItem(oreEntries.get(0).getItem());
					if (oreBlock != Blocks.AIR) {
						BlockRichOre richOreBlock = buildRichOreBlock(oreBlock);
						if (richOreBlock == null) {
							// show error
						} else {
							//log.info("Registering '" + oreBlock.getRegistryName().getResourceDomain() + "/" + oreBlock.getRegistryName().getResourcePath() + "'");
							ResourceLocation baseBlockRegistryName = oreBlock.getRegistryName();
							richOreBlock.setRegistryName("richorevariants", baseBlockRegistryName.getResourcePath());
							ForgeRegistries.BLOCKS.register(richOreBlock);
							ItemBlockRichOre richOreItemBlock = new ItemBlockRichOre(richOreBlock);
							richOreItemBlock.setRegistryName(richOreBlock.getRegistryName());
							ForgeRegistries.ITEMS.register(richOreItemBlock);
							log.info("Registered Rich Ore: " + richOreBlock.getRegistryName());
							Main.PROXY.registerBlockRender(richOreBlock, oreBlock);
						}
					}
				}
			}
		}
	}

	private BlockRichOre buildRichOreBlock(Block oreBlock) {
		String errorMsg = null;
		MapColor oreBlockMapColor = oreBlock.getDefaultState().getMapColor(null, null);
		//if (null == oreBlockMapColor)
		//	oreBlockMapColor = Material.ROCK.getMaterialMapColor();
		BlockRichOre richOre = new BlockRichOre(oreBlock, oreBlockMapColor);

		// find and set hardness
		final float hardness = oreBlock.getBlockHardness(null, null, null);
		if (0f == hardness) {
			logError("Could not get block hardness");
			return null;
		}
		richOre.setHardness(ModConfig.MULTIPLIERS.hardness * hardness);

		// find and set blast resistance
		float resistance = oreBlock.getExplosionResistance(null);
		if (0f == resistance) {
			logError("Could not get block blast resistance");
			return null;
		}
		richOre.setResistance(ModConfig.MULTIPLIERS.hardness * resistance);

		// set other common stuff


		return richOre;
	}

	private void logError(String reason) {

	}

	/**
	 * Other stuff e.g. mod integrations, housework
	 */
	public void postInit(FMLPostInitializationEvent event) {}


	// Side-specific methods here

	public void registerBlockRender(Block newBlock, Block baseBlock) {
	}
}
