package yaya.yayconfig.settings;

import yaya.yayconfig.settings.options.Option;
import yaya.yayconfig.screens.widgets.CyclingButtonWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import yaya.yayconfig.settings.options.SettingsOption;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

//TODO: add tooltips
@Environment(EnvType.CLIENT)
public class ChoiceSetting extends AbstractSetting
{
	private List<String> options;
	Consumer<String> onChange;
	
	public ChoiceSetting(String id, List<String> options, String category, boolean setDefault)
	{
		super(id, category, setDefault);
		this.options = options;
		if(setDefault)
			setDefault();
	}
	
	public ChoiceSetting setChangeConsumer(Consumer<String> consume)
	{
		onChange = consume;
		return this;
	}
	
	public void setValueTo(String value)
	{
		int i = options.indexOf(value);
		if(i != -1)
		{
			SettingsStorage.setChoice(id, i, options.size());
			if(onChange != null)
				onChange.accept(getSelectedOptionName() + "|True");
		}
	}
	
	public void UpdateOptions(List<String> options)
	{
		this.options = options;
		SettingsStorage.setChoice(id, Math.min(SettingsStorage.getChoice(id)[0], options.size()), options.size());
		if(onChange != null)
			onChange.accept(getSelectedOptionName() + "|false");
	}
	
	@Override
	public Text getButtonText()
	{
		return Text.translatable(translationKey, options.get(SettingsStorage.getChoice(id)[0]));
	}
	
	public String getOption() {
		return 0 + "/" + options.size();
	}
	
	public String getSelectedOptionName()
	{
		return options.get(SettingsStorage.getChoice(id)[0]);
	}
	
	@Override
	public void setDefault()
	{
		SettingsStorage.setChoice(id, 0, options.size());
	}
	
	@Override
	public void setDefault(String prefix)
	{
		SettingsStorage.setChoice(prefix + "." + id, 0, options.size());
	}
	
	@Override
	public String serialize() {
		return "E#" + id + ":" + SettingsStorage.getChoice(id)[0] + "/" + options.size();
	}
	
	@Override
	public String serialize(String prefix) {
		return "E#" + prefix + "." + id + ":" + SettingsStorage.getChoice(id)[0] + "/" + options.size();
	}
	
	public static int[] deserialize(String data)
	{
		String[] parts = data.split("/");
		return new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) };
	}
	
	@Override
	public Option asOption()
	{
		return new YayCycler<>(translationKey, this,
				() -> SettingsStorage.getChoice(id)[0],
				(option, value) ->
				{
					SettingsStorage.setChoice(id, value, options.size());
					if(onChange != null)
						onChange.accept(getSelectedOptionName() + "|True");
				},
				this::choiceBuilder, requirements);
	}
	
	public CyclingButtonWidget.Builder<Integer> choiceBuilder()
	{
		return (new CyclingButtonWidget.Builder<Integer>(value -> Text.of(options.get(value % options.size())))).values(getValues());
	}
	
	private List<Integer> getValues()
	{
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < options.size(); i++)
			result.add(i);
		return result;
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new ChoiceSetting(prefix + "." + id, options, id, setDefault);
	}
}
