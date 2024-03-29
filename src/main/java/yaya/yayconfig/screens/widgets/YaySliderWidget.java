package yaya.yayconfig.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.OrderedText;
import yaya.yayconfig.accessors.ClickableWidgetAccessor;
import yaya.yayconfig.settings.BooleanSetting;
import yaya.yayconfig.settings.options.DoubleOption;
import yaya.yayconfig.settings.SettingsStorage;
import yaya.yayconfig.settings.SliderSetting;
import yaya.yayconfig.settings.YaySlider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.Supplier;

public class YaySliderWidget extends SliderWidget implements ClickableWidgetAccessor
{
	private final DoubleOption option;
	private final SliderSetting setting, softMin, softMax;
	private final Supplier<Double> defaultSupplier;
	
	private float popupOpacity;
	private Text popupMessage;
	
	public YaySliderWidget(int x, int y, int width, int height, DoubleOption option, List<OrderedText> tooltips, SliderSetting setting)
	{
		super(x, y, width, height, setting.getButtonText(), option.getRatio(SettingsStorage.getDouble(option.getId())));
		this.option = option;
		this.setting = setting;
		this.softMin = setting.getSoftMin();
		this.softMax = setting.getSoftMax();
		this.defaultSupplier = setting.getDefaultSupplier();
	}
	
	@Override
	protected void applyValue()
	{
		double val = this.option.getValue(this.value);
		if(softMin != null)
		{
			double min = SettingsStorage.getDouble(softMin.id) + ((YaySlider) option).getStep();
			if(val < min - ((YaySlider) option).getStep())
				updatePopup(Text.translatable("popup.yayconfig.restricted-slider", Text.translatable(softMin.translationKey)), true);
			val = Double.max(min, val);
		}
		if(softMax != null)
		{
			double max = SettingsStorage.getDouble(softMax.id) - ((YaySlider) option).getStep();
			if(val > max + ((YaySlider) option).getStep())
				updatePopup(Text.translatable("popup.yayconfig.restricted-slider", Text.translatable(softMax.translationKey)), true);
			val = Double.min(max, val);
		}
		this.value = (val - option.getMin()) / (option.getMax() - option.getMin());
		this.option.set(val);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		boolean b = super.mouseClicked(mouseX, mouseY, button);
		if(clicked(mouseX, mouseY) && button == 1)
			reset();
		return b;
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if(keyCode == GLFW.GLFW_KEY_R)
			reset();
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	@Override
	protected void updateMessage()
	{
		setMessage(setting.getButtonText());
	}
	
	void reset()
	{
		this.value = (defaultSupplier.get() - option.getMin()) / (option.getMax() - option.getMin());
		applyValue();
		updateMessage();
		onRelease(0, 0);
		updatePopup(Text.translatable("popup.yayconfig.reset-slider"), false);
	}
	
	void updatePopup(Text popup, boolean force)
	{
		if(popupOpacity <= 1.9f || force || popup.equals(popupMessage))
		{
			popupOpacity = 2f;
			popupMessage = popup;
		}
	}
	
	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.renderButton(matrices, mouseX, mouseY, delta);
		if(popupOpacity > 0f)
		{
			TextRenderer tr = MinecraftClient.getInstance().textRenderer;
			int length = tr.getWidth(popupMessage);
			int x = this.x + this.width / 2;
			int y = this.y - this.height / 2;
			fill(matrices, x - length / 2 - 3, y - 3, x + length / 2 + 3, y + 10,
					MathHelper.ceil(Math.min(popupOpacity + 0.025f, 1f) / 2f * 255f) << 24);
			ClickableWidget.drawCenteredText(matrices, tr,
					popupMessage, x, y, 0xffffff | MathHelper.ceil(Math.min(popupOpacity + 0.025f, 1f) * 255f) << 24);
			popupOpacity -= delta / 10f;
		}
	}
	
	@Override
	protected void renderBackground(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY)
	{
		RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
		int i = (active && visible ? (this.isHovered() ? 2 : 1) * 20 : 0);
		this.drawTexture(matrices, this.x + (int)(this.value * (double)(this.width - 8)), this.y, 0, 46 + i, 4, 20);
		this.drawTexture(matrices, this.x + (int)(this.value * (double)(this.width - 8)) + 4, this.y, 196, 46 + i, 4, 20);
	}
	
	@Override
	public void setRequirements(List<BooleanSetting> requirements)
	{
		//TODO
	}
}
