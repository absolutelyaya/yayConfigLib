package yaya.yayconfig.settings;

import yaya.yayconfig.settings.options.SettingsOption;
import yaya.yayconfig.utilities.TranslationUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class AbstractSetting implements SettingsOption
{
	public String id, translationKey, subCategory;
	
	protected final boolean setDefault;
	protected List<BooleanSetting> requirements = new ArrayList<>();
	
	public AbstractSetting(String id, String subCategory, boolean setDefault)
	{
		this.id = id;
		this.translationKey = TranslationUtil.getTranslationKey("setting", id);
		this.setDefault = setDefault;
		this.subCategory = subCategory;
	}
	
	public abstract Text getButtonText();
	
	public abstract String serialize();
	
	public abstract String serialize(String prefix);
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractSetting>T setRequirements(List<BooleanSetting> bools)
	{
		for(BooleanSetting bool : bools)
		{
			if(bool != this)
				requirements.add(bool);
		}
		return (T)this;
	}
	
	@Override
	public SliderSetting setSoftMax(SliderSetting max)
	{
		return null;
	}
	
	@Override
	public SliderSetting setSoftMin(SliderSetting min)
	{
		return null;
	}
	
	public abstract String getOption();
	
	public abstract void setDefault();
	
	public abstract void setDefault(String prefix);
}
