package yaya.yayconfig.settings.options;

import com.google.common.collect.ImmutableList;
import java.util.function.Function;
import java.util.function.Supplier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.SimpleOption;
import yaya.yayconfig.settings.AbstractSetting;

@Environment(EnvType.CLIENT)
public class CyclingOption<T> extends Option
{
	private final Setter<T> setter;
	private final Supplier<T> getter;
	private final Supplier<CyclingButtonWidget.Builder<T>> buttonBuilderFactory;
	private Function<MinecraftClient, SimpleOption.TooltipFactory<T>> tooltips = (client) -> (value) -> ImmutableList.of();
	
	private CyclingOption(String key, AbstractSetting setting, Supplier<T> getter, Setter<T> setter, Supplier<CyclingButtonWidget.Builder<T>> buttonBuilderFactory) {
		super(key, setting);
		this.getter = getter;
		this.setter = setter;
		this.buttonBuilderFactory = buttonBuilderFactory;
	}
	
	public CyclingOption<T> tooltip(Function<MinecraftClient, SimpleOption.TooltipFactory<T>> tooltips) {
		this.tooltips = tooltips;
		return this;
	}
	
	public ClickableWidget createButton(int x, int y, int width) {
		SimpleOption.TooltipFactory<T> tooltipFactory = this.tooltips.apply(MinecraftClient.getInstance());
		return (this.buttonBuilderFactory.get()).tooltip(tooltipFactory).initially(this.getter.get()).build(x, y, width, 20,
				this.getDisplayPrefix(), (button, value) -> this.setter.accept(this, value));
	}
	
	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface Setter<T>
	{
		void accept(Option option, T value);
	}
}
