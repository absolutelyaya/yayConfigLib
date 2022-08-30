package yaya.yayconfig.mojangOptions.widgets;

import java.util.List;

import yaya.yayconfig.accessors.ClickableWidgetAccessor;
import yaya.yayconfig.mojangOptions.DoubleOption;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.OptionSliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.OrderableTooltip;
import net.minecraft.text.OrderedText;
import yaya.yayconfig.settings.BooleanSetting;

@Environment(EnvType.CLIENT)
public class DoubleOptionSliderWidget extends OptionSliderWidget implements OrderableTooltip, ClickableWidgetAccessor
{
	private final DoubleOption option;
	private final List<OrderedText> orderedTooltip;
	
	public DoubleOptionSliderWidget(GameOptions gameOptions, int x, int y, int width, int height, DoubleOption option, List<OrderedText> orderedTooltip) {
		super(gameOptions, x, y, width, height, (double)((float)option.getRatio(option.get(gameOptions))));
		this.option = option;
		this.orderedTooltip = orderedTooltip;
		this.updateMessage();
	}
	
	protected void applyValue() {
		this.option.set(this.options, this.option.getValue(this.value));
		this.options.write();
	}
	
	protected void updateMessage() {
		this.setMessage(this.option.getDisplayString(this.options));
	}
	
	public List<OrderedText> getOrderedTooltip() {
		return this.orderedTooltip;
	}
	
	@Override
	public void setRequirements(List<BooleanSetting> requirements)
	{
	
	}
}
