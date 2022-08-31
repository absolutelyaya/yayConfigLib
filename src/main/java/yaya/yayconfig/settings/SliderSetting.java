package yaya.yayconfig.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import yaya.yayconfig.settings.options.SettingsOption;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SliderSetting extends AbstractSetting
{
	public double defaultValue;
	
	public final double min, max;
	public final float step;
	public final boolean displayPercent;
	public final int decimals;
	
	private SliderSetting softMin, softMax;
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, boolean setDefault)
	{
		super(id, setDefault);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = true;
		this.decimals = 0;
		if(setDefault)
			setDefault();
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, String name, boolean setDefault)
	{
		super(id, name, setDefault);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = true;
		this.decimals = 0;
		if(setDefault)
			setDefault();
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, int decimals, boolean setDefault)
	{
		super(id, setDefault);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = decimals == 0;
		this.decimals = decimals;
		if(setDefault)
			setDefault();
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, int decimals, String name, boolean setDefault)
	{
		super(id, name, setDefault);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = decimals == 0;
		this.decimals = decimals;
		if(setDefault)
			setDefault();
	}
	
	public void updateDefault(double d)
	{
		defaultValue = d;
	}
	
	public String getOption()
	{
		return Double.toString(defaultValue);
	}
	
	@Override
	public void setDefault()
	{
		SettingsStorage.setDouble(id, defaultValue);
	}
	
	@Override
	public void setDefault(String prefix)
	{
		SettingsStorage.setDouble(prefix + "." + id, defaultValue);
	}
	
	@Override
	public Text getButtonText()
	{
		if(displayPercent)
			return Text.translatable(translationKey).append(": " + (int)(SettingsStorage.getDouble(id) / max * 100) + "%");
		else
			return Text.translatable(translationKey).append(": " + String.format("%." + decimals + "f", (SettingsStorage.getDouble(id))));
	}
	
	@Override
	public String serialize()
	{
		return "D#" + id + ":" + SettingsStorage.getDouble(id);
	}
	
	@Override
	public String serialize(String prefix) {
		return "D#" + prefix + "." + id + ":" + SettingsStorage.getDouble(prefix + "." + id);
	}
	
	@Override
	public YaySlider asOption()
	{
		return new YaySlider(translationKey, this, min, max, step, requirements);
	}
	
	@Override
	public SliderSetting setSoftMin(SliderSetting min)
	{
		this.softMin = min;
		return this;
	}
	
	@Override
	public SliderSetting setSoftMax(SliderSetting max)
	{
		this.softMax = max;
		return this;
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new SliderSetting(prefix + "." + id, defaultValue, min, max, step, decimals, id, setDefault);
	}
	
	public SliderSetting getSoftMax()
	{
		return softMax;
	}
	
	public SliderSetting getSoftMin()
	{
		return softMin;
	}
	
	public Supplier<Double> getDefaultSupplier()
	{
		return () -> defaultValue;
	}
}
