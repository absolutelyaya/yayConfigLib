package de.yaya.yayconfig.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.Option;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class Settings
{
	public static Class<SettingsCategory> category;
	static final HashMap<SettingsCategory, List<AbstractSetting>> SETTINGS = new HashMap<>();
	
	//Clouds
	public static final BooleanSetting CLOUDS = new BooleanSetting("clouds.enabled", true, true);
	
	//Fog
	public static final BooleanSetting FOG = new BooleanSetting("fog.enabled", true, true);
	public static final PerEntrySetting<Biome.Category> FOG_PER_BIOME = new PerEntrySetting<>("fog.biome", Biome.Category.class,
			List.of(
					new SliderSetting("fog.color.fade-speed", 1.0, 0.1, 2.0, 0.01f, 2, false),
					new SliderSetting("fog.speed", 1.0, 0.1, 2.0, 0.01f, 2, false)
			),
			List.of(Biome.Category.THEEND, Biome.Category.NONE, Biome.Category.NETHER, Biome.Category.UNDERGROUND))
			.setRequirements(List.of(FOG));
	
	//Peset
	public static final ChoiceSetting PRESET = new ChoiceSetting("general.preset", List.of("a", "b", "c", "d"), true)
			.setChangeConsumer(Settings::applyPreset);
	
	public Settings(Class<SettingsCategory> category)
	{
		this.category = category;
	}
	
	public static void applyPreset(String name)
	{
		if(name.equals("custom") || name.equals("§4ERROR"))
			return;
		String[] segments = name.split("\\|");
		for(SettingsCategory cat : category.getEnumConstants())
		{
			if(SETTINGS.get(cat) != null)
			{
				for(AbstractSetting as : SETTINGS.get(cat))
				{
					if(as instanceof BooleanSetting bool)
						bool.defaultValue = SettingsStorage.getBoolean(bool.id);
					else if(as instanceof SliderSetting slider)
						slider.updateDefault(SettingsStorage.getDouble(slider.id));
				}
			}
		}
		if(!Boolean.parseBoolean(segments[1]))
			SettingsManager.load();
	}
	
	public static Option[] getAsOptions(SettingsCategory category)
	{
		if(SETTINGS.get(category) == null)
			return new Option[0];
		List<Option> options = new ArrayList<>();
		for (SettingsOption so : SETTINGS.get(category))
		{
			options.add(so.asOption());
		}
		return options.toArray(Option[]::new);
	}
	
	public static void applyDefaults()
	{
		for(SettingsCategory cat : category.getEnumConstants())
		{
			if(SETTINGS.get(cat) != null)
			{
				for(AbstractSetting as : SETTINGS.get(cat))
				{
					if(as instanceof ChoiceSetting)
					{
						int[] i = ChoiceSetting.deserialize(as.getOption());
						SettingsStorage.setChoice(as.id, i[0], i[1]);
					}
					else if(as instanceof BooleanSetting)
						SettingsStorage.setBoolean(as.id, Boolean.parseBoolean(as.getOption()));
					else if(as instanceof SliderSetting)
						SettingsStorage.setDouble(as.id, Double.parseDouble(as.getOption()));
					else if(as instanceof PerEntrySetting<?>)
						SettingsStorage.setPerEntrySetting(as.getOption());
				}
			}
		}
	}
}
