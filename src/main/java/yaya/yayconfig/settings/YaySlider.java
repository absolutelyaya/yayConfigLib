package yaya.yayconfig.settings;

import com.google.common.collect.ImmutableList;
import yaya.yayconfig.accessors.ClickableWidgetAccessor;
import yaya.yayconfig.settings.options.DoubleOption;
import yaya.yayconfig.screens.widgets.YaySliderWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.*;

public class YaySlider extends DoubleOption
{
	private final Function<MinecraftClient, List<OrderedText>> tooltipsGetter;
	private final List<BooleanSetting> requirements;
	
	public YaySlider(String key, AbstractSetting setting, double min, double max, float step, List<BooleanSetting> requirements)
	{
		super(key, setting, min, max, step);
		this.requirements = requirements;
		this.tooltipsGetter = client -> ImmutableList.of();
	}
	
	@Override
	public ClickableWidget createButton(int x, int y, int width)
	{
		List<OrderedText> list = tooltipsGetter.apply(MinecraftClient.getInstance());
		ClickableWidget slider = new YaySliderWidget(x, y, width, 20, this, list, (SliderSetting)setting);
		((ClickableWidgetAccessor)slider).setRequirements(requirements);
		return slider;
	}
	
	public double getStep()
	{
		return step;
	}
}
