package yaya.yayconfig.settings.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import yaya.yayconfig.settings.AbstractSetting;

@Environment(EnvType.CLIENT)
public abstract class Option
{
	private final String key;
	protected final AbstractSetting setting;
	
	public Option(String key, AbstractSetting setting)
	{
		this.key = key;
		this.setting = setting;
	}
	
	public abstract ClickableWidget createButton(int x, int y, int width);
	
	protected Text getDisplayPrefix()
	{
		return Text.translatable(key);
	}
	
	public String getId()
	{
		return setting.id;
	}
}
