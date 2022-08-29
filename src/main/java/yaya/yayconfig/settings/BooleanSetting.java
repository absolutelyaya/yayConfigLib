package yaya.yayconfig.settings;

import yaya.yayconfig.mojangOptions.Option;
import yaya.yayconfig.mojangOptions.widgets.CyclingButtonWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class BooleanSetting extends AbstractSetting
{
	public boolean defaultValue;
	
	public BooleanSetting(String id, boolean defaultValue, boolean setDefault)
	{
		super(id, setDefault);
		this.defaultValue = defaultValue;
		if(setDefault)
			setDefault();
	}
	
	public BooleanSetting(String id, boolean defaultValue, String name, boolean setDefault)
	{
		super(id, name, setDefault);
		this.defaultValue = defaultValue;
		if(setDefault)
			setDefault();
	}
	
	public String getOption()
	{
		return Boolean.toString(defaultValue);
	}
	
	@Override
	public void setDefault()
	{
		SettingsStorage.setBoolean(id, defaultValue);
	}
	
	@Override
	public void setDefault(String prefix)
	{
		SettingsStorage.setBoolean(prefix + "." + id, defaultValue);
	}
	
	@Override
	public Text getButtonText()
	{
		return Text.translatable(translationKey, defaultValue);
	}
	
	@Override
	public String serialize()
	{
		return "B#" + id + ":" + SettingsStorage.getBoolean(id);
	}
	
	@Override
	public String serialize(String prefix) {
		return "B#" + prefix + "." + id + ":" + SettingsStorage.getBoolean(prefix + "." + id);
	}
	
	@Override
	public Option asOption()
	{
		return new YayCycler<>(translationKey,
				ignored -> SettingsStorage.getBoolean(id), (ignored, option, value) -> SettingsStorage.setBoolean(id, (boolean)value),
				this::onOffBuilder, requirements);
	}
	
	public CyclingButtonWidget.Builder<Boolean> onOffBuilder()
	{
		return (new CyclingButtonWidget.Builder<>(value -> value ? ScreenTexts.ON : ScreenTexts.OFF));
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new BooleanSetting(prefix + "." + id, defaultValue, id, setDefault);
	}
}
