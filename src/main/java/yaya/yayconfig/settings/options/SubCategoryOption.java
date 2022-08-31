package yaya.yayconfig.settings.options;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import yaya.yayconfig.screens.widgets.SubCategoryLabel;
import yaya.yayconfig.utilities.TranslationUtil;

public class SubCategoryOption extends Option
{
	String key;
	
	public SubCategoryOption(String id)
	{
		super(id, null);
		this.key = id;
	}
	
	@Override
	public ClickableWidget createButton(int x, int y, int width)
	{
		return new SubCategoryLabel(x, y, width, 20,
				Text.translatable(TranslationUtil.getTranslationKey("setting.sub-category", key)));
	}
}
