package de.yaya.yayconfig.settings;

import net.minecraft.text.TranslatableText;

public interface SettingsCategory
{
	TranslatableText getTitle();
	
	SettingsCategory[] values();
}
