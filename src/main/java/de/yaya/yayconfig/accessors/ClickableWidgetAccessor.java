package de.yaya.yayconfig.accessors;

import de.yaya.yayconfig.settings.BooleanSetting;

import java.util.List;

public interface ClickableWidgetAccessor
{
	void setRequirements(List<BooleanSetting> requirements);
}
