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
@Config(modid = ModConstants.MODID, name = ModConstants.MODNAME, category = "")
public final class ModConfig {
	@Config.LangKey("richorevariants.config.Multipliers")
	public static final Multipliers MULTIPLIERS = new Multipliers();

	public static class Multipliers {
		@Config.LangKey("richorevariants.config.Multipliers.hardness")
		@Config.Comment("Hardness and blast resistance multiplier of the rich variants (relative to the base ore block).")
		@Config.RequiresMcRestart
		@Config.RangeDouble(min = 1.0d, max = 100.0d)
		public double hardness = 10.0d;

		@Config.LangKey("richorevariants.config.Multipliers.drop")
		@Config.Comment("Amount of base ore blocks to drop when rich variant is broken")
		@Config.RequiresMcRestart
		@Config.RangeInt(min = 1, max = 100)
		public int drop = 10;
	}

	@Config.LangKey("richorevariants.config.PreferredMods")
	public static final PreferredMods PreferredMods = new PreferredMods();

	public static class PreferredMods {
		@Config.LangKey("richorevariants.config.PreferredMods.modids")
		@Config.Comment("Specify a list of Mod ID's for rich ores to be based on.\n" +
				"This can override the texture, drops, hardness, etc.")
		public String[] modids = { "minecraft", "thermalfoundation" };

	}

	@UtilityClass
	@Mod.EventBusSubscriber
	private static final class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(ModConstants.MODID)) {
				ConfigManager.sync(ModConstants.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
