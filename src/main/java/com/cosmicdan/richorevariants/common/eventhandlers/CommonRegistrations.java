package com.cosmicdan.richorevariants.common.eventhandlers;

import com.cosmicdan.cosmiclib.holders.BlockAndMeta;
import com.cosmicdan.richorevariants.common.ModConfig;
import com.cosmicdan.richorevariants.common.ModConstants;
import com.cosmicdan.richorevariants.common.block.BlockRichOre;
import com.cosmicdan.richorevariants.common.item.ItemBlockRichOre;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;

@Log4j2(topic = "RichOreVariants/CommonRegistrations")
@Mod.EventBusSubscriber
public final class CommonRegistrations {
	@Getter private static final Collection<Block> BLOCKS = new HashSet<>(50);
	@Getter private static final Collection<Item> ITEMS = new HashSet<>(50);

	private CommonRegistrations() {}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerItems(RegistryEvent.Register<Item> event) {
		// we register Blocks the old-fashioned way in the Item event because we need to scan the oredict which is
		// only fully populated after other mods do their own Item registrations
		final String[] oreNames = OreDictionary.getOreNames();
		for (final String oreName : oreNames) {
			if (isValidOreCandidate(oreName)) {
				final BlockAndMeta oreBlockCandidate = getDesiredBaseOre(oreName);
				if (null != oreBlockCandidate) {
					final BlockRichOre richOreBlock = BlockRichOre.builder(oreBlockCandidate);
					if (null != richOreBlock) {
						final ResourceLocation baseBlockRegName = oreBlockCandidate.block.getRegistryName();
						if (null == baseBlockRegName) {
							log.info("Error adding Rich Ore Variant for {} - registry name not found", oreBlockCandidate);
						} else {
							final String blockDomain = baseBlockRegName.getResourceDomain();
							final String blockPath = baseBlockRegName.getResourcePath();
							final int blockMeta = oreBlockCandidate.meta;
							richOreBlock.setRegistryName(ModConstants.MODID, oreName);
							ForgeRegistries.BLOCKS.register(richOreBlock);
							BLOCKS.add(richOreBlock);
							log.info("Added Rich Ore Variant for {} ({})", oreName, blockDomain + ':' + blockPath + '@' + blockMeta);
						}
					}
				}
			}
		}

		// now we register items for real
		for (final Block block : BLOCKS) {
			if (block instanceof BlockRichOre) {
				final ItemBlockRichOre richOreItemBlock = new ItemBlockRichOre(block);
				//noinspection ConstantConditions
				richOreItemBlock.setRegistryName(block.getRegistryName());
				event.getRegistry().register(richOreItemBlock);
				//ITEMS.add(richOreItemBlock);
			}
		}
	}

	private static boolean isValidOreCandidate(String oreName) {
		boolean isValid = false;
		// TODO: Check against configurable blacklist/whitelist
		if (oreName.startsWith("ore"))
			isValid = true;
		return isValid;
	}

	@Nullable
	private static BlockAndMeta getDesiredBaseOre(String oreName) {
		BlockAndMeta desiredBaseOre = null;
		final NonNullList<ItemStack> oreEntries = OreDictionary.getOres(oreName, false);
		if (!oreEntries.isEmpty()) {
			BlockAndMeta oreBlockCandidate = null;
			if (0 < ModConfig.PreferredMods.modids.length) {
				// there are preferred modid's set in config...
				foundPreferred:
				for (final ItemStack oreEntry : oreEntries) {

					// ...so loop over the modids for all entries in this oredict list
					final Block oreEntryBlock = Block.getBlockFromItem(oreEntry.getItem());
					final ResourceLocation oreEntryRegistry = oreEntryBlock.getRegistryName();
					if (null == oreEntryRegistry)
						continue; // bad oredict entry, skip it
					for (final String preferredId : ModConfig.PreferredMods.modids) {
						// loop over the preferred modids, check if any match this block
						if (preferredId.equals(oreEntryRegistry.getResourceDomain())) {
							// found a match!
							oreBlockCandidate = new BlockAndMeta(oreEntryBlock, oreEntry.getMetadata());
							//oreEntryBlock.state
							break foundPreferred;
						}
					}
				}
			}

			if (null == oreBlockCandidate) {
				// a preferred entry was not found or no modid preferences are set at all - just use the first entry
				oreBlockCandidate = new BlockAndMeta(Block.getBlockFromItem(oreEntries.get(0).getItem()), oreEntries.get(0).getMetadata());
			}

			if (oreBlockCandidate.block.equals(Blocks.AIR)) {
				// can this happen?
				log.info("Error adding Rich Ore Variant for {} - item has no block counterpart. Bad ore registration?", oreEntries.get(0).getItem());
				//noinspection AssignmentToNull
				desiredBaseOre = null;
			} else {
				desiredBaseOre = oreBlockCandidate;
			}
		}
		return desiredBaseOre;
	}
}
