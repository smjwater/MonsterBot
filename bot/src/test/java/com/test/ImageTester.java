package com.test;

import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import com.kplus.BackGroundThread;
import com.kplus.BotUtil;

public class ImageTester {
	
	public static void main(String srgs[]) {
		try {
			Screen s = new Screen();
			Region region = screenSetup(s);
			
			Boolean found = BotUtil.isImageFound(s, "singleplay.png", "highlight");
			System.out.println( "Pics Found");
			
		} catch (FindFailed e1) {
			System.out.println( "Pics Not Found");
		}
	}
	
	
	private static Region screenSetup(Screen s) throws FindFailed {
		ScreenImage gameImage = s.userCapture();
		ImagePath.add(BackGroundThread.class.getCanonicalName() + "/images");
		 int x = 0;
		 int y = 0;
		 int height = 0;
		 int width = 0;
		Match foundScreen = s.find(gameImage.getFile());
		x = foundScreen.getTarget().getX();
		y = foundScreen.getTarget().getY();
		width = gameImage.w;
		height = gameImage.h;
		foundScreen.highlight(1);

		return new Region(x,y,width,height);
	}
}
