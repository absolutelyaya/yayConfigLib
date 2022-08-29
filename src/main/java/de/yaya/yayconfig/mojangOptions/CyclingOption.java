package de.yaya.yayconfig.mojangOptions;

import com.google.common.collect.ImmutableList;
import java.util.function.Function;
import java.util.function.Supplier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;

@Environment(EnvType.CLIENT)
public class CyclingOption<T> extends Option
{
	private final Setter<T> setter;
	private final Function<GameOptions, T> getter;
	private final Supplier<CyclingButtonWidget.Builder<T>> buttonBuilderFactory;
	private Function<MinecraftClient, SimpleOption.TooltipFactory<T>> tooltips = (client) -> (value) -> ImmutableList.of();
	
	private CyclingOption(String key, Function<GameOptions, T> getter, Setter<T> setter, Supplier<CyclingButtonWidget.Builder<T>> buttonBuilderFactory) {
		super(key);
		this.getter = getter;
		this.setter = setter;
		this.buttonBuilderFactory = buttonBuilderFactory;
	}
	
	public CyclingOption<T> tooltip(Function<MinecraftClient, SimpleOption.TooltipFactory<T>> tooltips) {
		this.tooltips = tooltips;
		return this;
	}
	
	public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
		SimpleOption.TooltipFactory<T> tooltipFactory = this.tooltips.apply(MinecraftClient.getInstance());
		return (this.buttonBuilderFactory.get()).tooltip(tooltipFactory).initially(this.getter.apply(options)).build(x, y, width, 20, this.getDisplayPrefix(), (button, value) -> {
			this.setter.accept(options, this, value);
			options.write();
		});
	}
	
	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface Setter<T> {
		void accept(GameOptions gameOptions, Option option, T value);
	}
}
