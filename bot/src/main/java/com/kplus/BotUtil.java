package com.kplus;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

public class BotUtil {
	public static void doubleClick(int x, int y, Region region) throws FindFailed{
		Location loc = new Location(x,y);
		Mouse.move(loc);
		App.pause(2);
		Mouse.click(loc, "D" , 1000);
		Mouse.click(loc, "D" , 1000);
	}

	public static void singleClick(int x, int y, Region region) throws FindFailed{
		Location loc = new Location(x,y);
		Mouse.move(loc);
		App.pause(2);
		Mouse.click(loc, "L" , 1000);
		//Mouse.click(loc, "D" , 1000);
	}

	public static void dragShoot(int x, int y ,int width ,int height , Region region) throws FindFailed{
		Location loc = new Location(x,y);
		Mouse.move(loc);
		Location newLoc = new Location(x + (width/ 3)  ,y + (height /3));
		region.dragDrop(loc, newLoc);
	}



	public static boolean isImageFound(Region region, String imgName) {
		return isImageFound(region,imgName,null);
	}

	public static boolean isImageFound(Region region, String imgName, String action) {
		try{
			Pattern pic = new Pattern(imgName).exact();
			Match matchedImage = region.find(pic);

			switch(action) {
			case "click" :
				singleClick(matchedImage.getTarget().getX(),matchedImage.getTarget().getY(),region);	
				break; // optional

			case "dbclick" :
				doubleClick(matchedImage.getTarget().getX(),matchedImage.getTarget().getY(),region);	
				break; // optional

			case "dragdrop" :
				dragShoot(matchedImage.getTarget().getX(),matchedImage.getTarget().getY(),region.getW(),region.getH(),region);	
				break; // optional

			case "highlight" :
				matchedImage.highlight(2);
				break; // optional
				// for null case
			default : // Optional
				// Statements
			}

			return true;
		}catch(FindFailed ex1){
			//System.out.println( "找不到返回鍵");
			return false;
		}
	}
}
