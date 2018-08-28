package com.kplus;

import java.util.Scanner;

import org.sikuli.script.App;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

public class BotStarter {

	public static void main(String[] args) throws FindFailed {


		System.out.println("=========================================================");
		System.out.println("||               MosterStrike Alpha v0.1               ||");
		System.out.println("||                by SmjWateR@k-pluslab                ||");
		System.out.println("||                Not for distribution                 ||");
		System.out.println("=========================================================");
		BackGroundThread bgthread = new BackGroundThread();
		bgthread.run();
		//debug();
		// TODO Auto-generated method stub

	}

	public static void debug(){
		ImagePath.add(BackGroundThread.class.getCanonicalName() + "/images");
		Screen s = new Screen();
		Scanner sc = new Scanner(System.in);
		ScreenImage gameImage = s.userCapture();
		Match foundScreen;
		try {
			foundScreen = s.find(gameImage.getFile());


			int x = 0;
			int y = 0;
			int height = 0;
			int width = 0;
			x = foundScreen.getTarget().getX();
			y = foundScreen.getTarget().getY();
			width = gameImage.w;
			height = gameImage.h;

			foundScreen.highlight(1);
			Region region = new Region(x,y,width,height);

			try{
				Pattern pic =  new Pattern("back.png").exact();
				Match heropower = s.find(pic);
				System.out.println( "back.png");
				pressButton(heropower.getX(),heropower.getY(),region);
			}catch(FindFailed e){
				//System.out.println("Not MissionLevel");
			}
		} catch (FindFailed e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void pressButton(int x, int y, Region region) throws FindFailed{
		Location loc = new Location(x,y);
		Mouse.move(loc);
		App.pause(1);
		System.out.println("Move");

		//Mouse.down(Button.LEFT);
		System.out.println("Down");
		//App.pause((float)0.5);
		//Mouse.up(Button.LEFT);
		Mouse.click(loc, "D" , 1000);
		System.out.println("Up");

	}

	public static void pressBackBtn(Match s , Region region ){
		try{
			Pattern pic = new Pattern("back.png").similar((float)0.7);
			Match back = s.find(pic);
			//System.out.println( "關卡結束");
			pressButton(back.getTarget().getX(),back.getTarget().getY(),region);
			System.out.println( "已選擇返回");
		}catch(FindFailed ex1){
			System.out.println( "找不到返回鍵");
		}
	}

}


