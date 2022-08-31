package yaya.yayconfig.settings.options;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Function;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;
import yaya.yayconfig.screens.widgets.YaySliderWidget;
import yaya.yayconfig.settings.AbstractSetting;
import yaya.yayconfig.settings.SettingsStorage;
import yaya.yayconfig.settings.SliderSetting;

@Environment(EnvType.CLIENT)
public class DoubleOption extends Option
{
	protected final float step;
	protected final double min;
	protected double max;
	private final Function<MinecraftClient, List<OrderedText>> tooltipsGetter;
	
	public DoubleOption(String key, AbstractSetting setting, double min, double max, float step, Function<MinecraftClient, List<OrderedText>> tooltipsGetter) {
		super(key, setting);
		this.min = min;
		this.max = max;
		this.step = step;
		this.tooltipsGetter = tooltipsGetter;
	}
	
	public DoubleOption(String key, AbstractSetting setting, double min, double max, float step) {
		this(key, setting, min, max, step, (client) -> ImmutableList.of());
	}
	
	public ClickableWidget createButton(int x, int y, int width)
	{
		SliderSetting option = (SliderSetting)setting;
		List<OrderedText> list = this.tooltipsGetter.apply(MinecraftClient.getInstance());
		return new YaySliderWidget(x, y, width, 20, this, list, option);
	}
	
	public double getRatio(double value) {
		return MathHelper.clamp((this.adjust(value) - this.min) / (this.max - this.min), 0.0, 1.0);
	}
	
	public double getValue(double ratio) {
		return this.adjust(MathHelper.lerp(MathHelper.clamp(ratio, 0.0, 1.0), this.min, this.max));
	}
	
	private double adjust(double value) {
		if (this.step > 0.0F) {
			value = this.step * (float)Math.round(value / (double)this.step);
		}
		
		return MathHelper.clamp(value, this.min, this.max);
	}
	
	public double getMin() {
		return this.min;
	}
	
	public double getMax() {
		return this.max;
	}
	
	//public void setMax(float max) {
	//	this.max = max;
	//}
	
	public void set(double value) {
		SettingsStorage.setDouble(setting.id, value);
	}
	
	//public double get() {
	//	return SettingsStorage.getDouble(setting.id);
	//}
	//
	//public Text getDisplayString() {
	//	return this.displayStringGetter.apply(this);
	//}
}
