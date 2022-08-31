package yaya.yayconfig.settings.options;

import yaya.yayconfig.settings.AbstractSetting;
import yaya.yayconfig.settings.BooleanSetting;
import yaya.yayconfig.settings.SliderSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;

@Environment(EnvType.CLIENT)
public interface SettingsOption
{
	Option asOption();
	
	<T extends AbstractSetting>T setRequirements(List<BooleanSetting> bools);
	
	SliderSetting setSoftMax(SliderSetting max);
	
	SliderSetting setSoftMin(SliderSetting min);
	
	SettingsOption addIDPrefix(String prefix);
}
