package yaya.yayconfig.settings;

import yaya.yayconfig.settings.options.Option;
import yaya.yayconfig.screens.widgets.CyclingButtonWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import yaya.yayconfig.settings.options.SettingsOption;

@Environment(EnvType.CLIENT)
public class BooleanSetting extends AbstractSetting
{
	public boolean defaultValue;
	private Text onText, offText;
	
	public BooleanSetting(String id, boolean defaultValue, String category, boolean setDefault)
	{
		super(id, category, setDefault);
		this.defaultValue = defaultValue;
		if(setDefault)
			setDefault();
	}
	
	public BooleanSetting(String id, boolean defaultValue, String category, String on, String off, boolean setDefault)
	{
		this(id, defaultValue, category, setDefault);
		onText = Text.translatable(on);
		offText = Text.translatable(off);
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
		if(onText != null && offText != null)
			return new YayCycler<>(translationKey, this,
					() -> SettingsStorage.getBoolean(id), (ignored, value) -> SettingsStorage.setBoolean(id, value),
					() -> CyclingButtonWidget.onOffBuilder(onText, offText), requirements);
		else
			return new YayCycler<>(translationKey, this,
					() -> SettingsStorage.getBoolean(id), (ignored, value) -> SettingsStorage.setBoolean(id, value),
					CyclingButtonWidget::onOffBuilder, requirements);
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new BooleanSetting(prefix + "." + id, defaultValue, id, setDefault);
	}
}
