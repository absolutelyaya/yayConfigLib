package yaya.yayconfig.accessors;

import yaya.yayconfig.settings.BooleanSetting;

import java.util.List;

public interface ClickableWidgetAccessor
{
	void setRequirements(List<BooleanSetting> requirements);
}
