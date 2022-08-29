package yaya.yayconfig.settings;

import yaya.yayconfig.mojangOptions.Option;
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
