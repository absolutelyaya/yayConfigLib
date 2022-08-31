package yaya.yayconfig.screens.settings;

import net.minecraft.screen.ScreenTexts;
import yaya.yayconfig.settings.options.Option;
import yaya.yayconfig.screens.widgets.ButtonListWidget;
import yaya.yayconfig.settings.options.PerEntryOption;
import yaya.yayconfig.settings.Settings;
import yaya.yayconfig.screens.widgets.YayButtonList;
import yaya.yayconfig.settings.SettingsCategory;
import yaya.yayconfig.utilities.TranslationUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SettingsScreen extends AbstractSettingsScreen
{
	private final SettingsCategory category;
	private final PerEntryOption<?> settings;
	
	private ButtonListWidget list;
	private boolean empty;
	private Enum<?> entry;
	
	public SettingsScreen(Screen parent, SettingsCategory category)
	{
		super(category.getTitle(), parent);
		this.category = category;
		this.settings = null;
	}
	
	public SettingsScreen(Screen parent, PerEntryOption<?> settings, Enum<?> entry)
	{
		super(Text.translatable(TranslationUtil.getTranslationKey("screen",
				"fog.biome." + entry.name()).toLowerCase().replace("_", "-")), parent);
		this.category = null;
		this.settings = settings;
		this.entry = entry;
	}
	
	@Override
	protected void init()
	{
		super.init();
		if(client == null)
			return;
		this.list = new YayButtonList(this.client, this.width, this.height, 40, this.height - 80, 25);
		List<Option> options = new ArrayList<>();
		if(category != null)
		{
			var list = new ArrayList<>(List.of(Settings.getAsOptions(category)));
			list.removeIf(e -> e.getClass().equals(PerEntryOption.class));
			options.addAll(list);
			for (Option option : Settings.getAsOptions(category))
			{
				if(option.getClass().equals(PerEntryOption.class))
				{
					options.addAll(List.of(((PerEntryOption<?>)option).getEntries()));
				}
			}
		}
		else
			options.addAll(List.of(settings.getOptions(entry)));
		this.list.addAll(options);
		this.addSelectableChild(this.list);
		this.list.setRenderBackground(false);
		this.list.setRenderHorizontalShadows(false);
		
		empty = options.size() == 0;
		
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 100,
				this.height - (client != null && client.world != null ? 56 : 26), 200, 20,
				ScreenTexts.DONE,button -> this.client.setScreen(this.parent)));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);
		list.render(matrices, mouseX, mouseY, delta);
		
		if(empty && client != null)
		{
			int x = width / 2;
			int y = height / 2;
			int length = client.textRenderer.getWidth(Text.translatable("screen.yayconfig.options.empty"));
			if(!showBG && client.world != null)
				fill(matrices, x - (length / 2 + 3), y - 7, x + length / 2 + 3, y + 7, -1072689136);
			drawCenteredText(matrices, client.textRenderer, Text.translatable("screen.yayconfig.options.empty"), x, y - 4, 0xFFFFFFFF);
		}
	}
}
