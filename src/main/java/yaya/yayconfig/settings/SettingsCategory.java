package yaya.yayconfig.settings;

import net.minecraft.text.Text;

public interface SettingsCategory
{
	Text getTitle();
	
	SettingsCategory[] getValues();
}
