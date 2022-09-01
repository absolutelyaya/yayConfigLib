package yaya.yayconfig.settings;

import yaya.yayconfig.settings.options.Option;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import yaya.yayconfig.settings.options.SettingsOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class Settings
{
	protected static boolean usePresets;
	public static Class<? extends SettingsCategory> category;
	protected static final HashMap<SettingsCategory, List<AbstractSetting>> SETTINGS = new HashMap<>();
	
	//Peset
	public static final ChoiceSetting PRESET = new ChoiceSetting("general.preset", List.of("a", "b", "c", "d"), "", true)
			.setChangeConsumer(Settings::applyPreset);
	
	public Settings(Class<? extends SettingsCategory> category, boolean usePresets)
	{
		Settings.category = category;
		Settings.usePresets = usePresets;
	}
	
	public static void applyPreset(String name)
	{
		if(name.equals("custom") || name.equals("§4ERROR") || category == null)
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
