package yaya.yayconfig.screens.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import yaya.yayconfig.screens.settings.AbstractSettingsScreen;

public class SubCategoryLabel extends ClickableWidget
{
	public SubCategoryLabel(int x, int y, int width, int height, Text message)
	{
		super(x, y, width, height, message);
	}
	
	@Override
	public void appendNarrations(NarrationMessageBuilder builder)
	{
	
	}
	
	@Override
	public Text getMessage()
	{
		return super.getMessage();
	}
	
	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		MinecraftClient client = MinecraftClient.getInstance();
		TextRenderer textRenderer = client.textRenderer;
		Screen screen = client.currentScreen;
		if(screen instanceof AbstractSettingsScreen && !((AbstractSettingsScreen)client.currentScreen).isShowingBG())
		{
			int length = client.textRenderer.getWidth(this.getMessage());
			fill(matrices, x + width / 2 - (length / 2 + 3), y + height - 8 - 9, x + width / 2 + length / 2 + 3, y + height - 8 + 6, -1072689136);
		}
		drawCenteredText(matrices, textRenderer, this.getMessage().copy().setStyle(Style.EMPTY.withUnderline(true)),
				x + width / 2, y + (height - 8) / 2, 16777215 | MathHelper.ceil(this.alpha * 255.0F) << 24);
	}
}
