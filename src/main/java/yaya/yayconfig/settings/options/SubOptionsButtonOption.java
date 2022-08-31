package yaya.yayconfig.settings.options;

import yaya.yayconfig.accessors.ClickableWidgetAccessor;
import yaya.yayconfig.settings.BooleanSetting;
import yaya.yayconfig.settings.options.Option;
import yaya.yayconfig.screens.settings.SettingsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import yaya.yayconfig.settings.options.PerEntryOption;

import java.util.List;

public class SubOptionsButtonOption extends Option
{
	private final PerEntryOption<?> settings;
	private final Enum<?> entry;
	private final Text label;
	private final List<BooleanSetting> requirements;
	
	public SubOptionsButtonOption(String key, PerEntryOption<?> settings, Enum<?> entry, List<BooleanSetting> requirements)
	{
		super(key, null);
		this.settings = settings;
		this.entry = entry;
		this.label = Text.translatable(key).append("...");
		this.requirements = requirements;
	}
	
	@Override
	public ClickableWidget createButton(int x, int y, int width)
	{
		ClickableWidget button = new ButtonWidget(x, y, width, 20,
				label, b -> MinecraftClient.getInstance().setScreen(new SettingsScreen(MinecraftClient.getInstance().currentScreen, settings, entry)));
				((ClickableWidgetAccessor)button).setRequirements(requirements);
		return button;
	}
}
