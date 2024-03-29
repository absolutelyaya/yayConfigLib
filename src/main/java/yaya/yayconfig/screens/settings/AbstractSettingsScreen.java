package yaya.yayconfig.screens.settings;

import yaya.yayconfig.settings.SettingsManager;
import yaya.yayconfig.screens.widgets.YayCheckbox;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.text.Text;

@DontObfuscate
public abstract class AbstractSettingsScreen extends Screen
{
	protected static boolean paused;
	protected static boolean showBG = true;
	
	protected Screen parent;
	protected YayCheckbox bgCheckBox;
	protected YayCheckbox pauseCheckBox;
	
	protected AbstractSettingsScreen(Text title, Screen parent)
	{
		super(title);
		this.parent = parent;
	}
	
	@Override
	protected void init()
	{
		super.init();
		if(client != null)
			paused = client.isPaused();
		if(client != null && client.world != null)
		{
			bgCheckBox = this.addDrawableChild(new YayCheckbox(this.width / 2 + 35, this.height - 28, 200, 20,
					Text.translatable("screen.yayconfig.options.showbg"), showBG, !showBG));
			pauseCheckBox = this.addDrawableChild(new YayCheckbox(this.width / 2 - 125, this.height - 28, 200, 20,
					Text.translatable("screen.yayconfig.options.gamepaused"), paused, !showBG));
		}
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		if(client != null)
		{
			if(showBG || client.world == null)
				renderBackground(matrices);
			else
			{
				int length = client.textRenderer.getWidth(this.title);
				fill(matrices, this.width / 2 - (length / 2 + 3), 18 - 6, this.width / 2 + length / 2 + 3, 18 + 7, -1072689136);
			}
			OptionsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
			if(client.world != null)
			{
				if(client.getCurrentServerEntry() != null)
					pauseCheckBox.active = client.getCurrentServerEntry().isLocal();
				if(pauseCheckBox.isChecked() != paused)
					paused = pauseCheckBox.isChecked();
				if(bgCheckBox.isChecked() != showBG)
				{
					showBG = bgCheckBox.isChecked();
					bgCheckBox.setShowBackground(!showBG);
					pauseCheckBox.setShowBackground(!showBG);
				}
			}
		}
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public boolean shouldPause()
	{
		return paused;
	}
	
	@Override
	public void removed()
	{
		super.removed();
		SettingsManager.save();
	}
	
	public boolean isShowingBG()
	{
		return showBG;
	}
}
