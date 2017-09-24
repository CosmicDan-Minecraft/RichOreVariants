package com.cosmicdan.richorevariants.common;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings({"CanBeFinal", "HardcodedLineSeparator"})
@UtilityClass
@Log4j2(topic = "RichOreVariants/ModConfig")
@Config(modid = ModConstants.MODID, name = "CosmicDan/" + ModConstants.MODNAME, category = "")
public final class ModConfig {
	@Config.LangKey("richorevariants.config.Multipliers")
	public static final Multipliers MULTIPLIERS = new Multipliers();

	public static class Multipliers {
		@Config.LangKey("richorevariants.config.Multipliers.hardness")
		@Config.Comment("Hardness and blast resistance multiplier (relative to the base ore block).")
		public float hardness = 10.0f;

		@Config.LangKey("richorevariants.config.Multipliers.drop")
		@Config.Comment("How many to drop of the original ore block (i.e. Redstone Ore, not Redstone Dust)")
		public int drop = 10;
	}

	@UtilityClass
	@Mod.EventBusSubscriber
	private static final class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(ModConstants.MODID)) {
				ConfigManager.sync(ModConstants.MODID, Config.Type.INSTANCE);
				log.info(event.getConfigID());
			}
		}
	}
}
