package yaya.yayconfig.screens.widgets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

import yaya.yayconfig.settings.options.Option;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ButtonListWidget extends ElementListWidget<ButtonListWidget.ButtonEntry> {
	public ButtonListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
		super(minecraftClient, i, j, k, l, m);
		this.centerListVertically = false;
	}
	
	public int addSingleOptionEntry(Option option)
	{
		return this.addEntry(ButtonListWidget.ButtonEntry.create(this.width, option));
	}
	
	public void addOptionEntry(Option firstOption, @Nullable Option secondOption)
	{
		this.addEntry(ButtonListWidget.ButtonEntry.create(this.width, firstOption, secondOption));
	}
	
	public void addAll(Option[] options) {
		for(int i = 0; i < options.length; i += 2) {
			this.addOptionEntry(options[i], i < options.length - 1 ? options[i + 1] : null);
		}
	}
	
	public int getRowWidth() {
		return 400;
	}
	
	protected int getScrollbarPositionX() {
		return super.getScrollbarPositionX() + 32;
	}
	
	@Environment(EnvType.CLIENT)
	protected static class ButtonEntry extends ElementListWidget.Entry<ButtonEntry> {
		final Map<Option, ClickableWidget> optionsToButtons;
		final List<ClickableWidget> buttons;
		
		private ButtonEntry(Map<Option, ClickableWidget> optionsToButtons) {
			this.optionsToButtons = optionsToButtons;
			this.buttons = ImmutableList.copyOf(optionsToButtons.values());
		}
		
		public static ButtonEntry create(int width, Option option)
		{
			return new ButtonEntry(ImmutableMap.of(option, option.createButton(width / 2 - 155, 0, 310)));
		}
		
		public static ButtonEntry create(int width, Option firstOption, @Nullable Option secondOption)
		{
			ClickableWidget clickableWidget = firstOption.createButton( width / 2 - 155, 0, 150);
			return secondOption == null ? new ButtonEntry(ImmutableMap.of(firstOption, clickableWidget)) :
						   new ButtonEntry(ImmutableMap.of(firstOption, clickableWidget, secondOption,
								   secondOption.createButton( width / 2 - 155 + 160, 0, 150)));
		}
		
		public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			this.buttons.forEach((button) -> {
				button.y = y;
				button.render(matrices, mouseX, mouseY, tickDelta);
			});
		}
		
		public List<? extends Element> children() {
			return this.buttons;
		}
		
		public List<? extends Selectable> selectableChildren() {
			return this.buttons;
		}
	}
}
