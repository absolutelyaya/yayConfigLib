package yaya.yayconfig.mojangOptions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public abstract class Option {
	private final Text key;
	
	public Option(String key) {
		this.key = Text.translatable(key);
	}
	
	public abstract ClickableWidget createButton(GameOptions options, int x, int y, int width);
	
	protected Text getDisplayPrefix() {
		return this.key;
	}
	
	protected Text getPixelLabel(int pixel) {
		return Text.translatable("options.pixel_value", this.getDisplayPrefix(), pixel);
	}
	
	protected Text getPercentLabel(double proportion) {
		return Text.translatable("options.percent_value", this.getDisplayPrefix(), (int)(proportion * 100.0));
	}
	
	protected Text getPercentAdditionLabel(int percentage) {
		return Text.translatable("options.percent_add_value", this.getDisplayPrefix(), percentage);
	}
	
	protected Text getGenericLabel(Text value) {
		return Text.translatable("options.generic_value", this.getDisplayPrefix(), value);
	}
	
	protected Text getGenericLabel(int value) {
		return this.getGenericLabel(Text.of(Integer.toString(value)));
	}
}
