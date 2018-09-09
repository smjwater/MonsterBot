package com.kplus;

import java.util.Scanner;

import org.sikuli.basics.Debug;
import org.sikuli.basics.Settings;
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
import com.kplus.BotUtil;

public class BackGroundThread implements Runnable{

	private int x = 0;
	private int y = 0;
	private int height = 0;
	private int width = 0;
	private static int waitCycle = 0;

	private ScreenImage startImage;
	private ScreenImage missionTypeImage;
	private ScreenImage missionNameImage;
	private ScreenImage missionLevelImage;
	private ScreenImage menuBtnImage;
	private ScreenImage singlePlayerImage;
	private ScreenImage goImage;

	private boolean chooseStage = false;
	boolean isPlayingNow = true;
	boolean isMenu = false;
	private int mode;

	Match okBtn ;

	public void run() {
		System.out.println(BackGroundThread.class.getCanonicalName());
		ImagePath.add(BackGroundThread.class.getCanonicalName() + "/images");
		//ImagePath.add("/images");
		Screen s = new Screen();
		Scanner sc = new Scanner(System.in);

		mode =2;
		//	mode = Integer.parseInt(sc.nextLine());
		//System.out.println("Press Enter to Capture Game Screen.");
		//sc.nextLine();
		ScreenImage gameImage = s.userCapture();
		try {
			Match foundScreen = s.find(gameImage.getFile());
			x = foundScreen.getTarget().getX();
			y = foundScreen.getTarget().getY();
			width = gameImage.w;
			height = gameImage.h;
			foundScreen.highlight(1);
			Region region = new Region(x,y,width,height);
			//System.out.println("請按ENTER鍵然後選擇冒險圖");
			//sc.nextLine();
			//startImage = s.userCapture();

			System.out.println("請按ENTER鍵然後選擇冒險項目");
			sc.nextLine();
			missionTypeImage = s.userCapture();
			System.out.println("請按ENTER鍵然後選擇關卡");
			sc.nextLine();
			missionNameImage = s.userCapture();
			System.out.println("請按ENTER鍵然後選擇等級");
			sc.nextLine();
			missionLevelImage = s.userCapture();

			System.out.println("請進入遊戲,按ENTER鍵開始");
			sc.nextLine();

			System.out.println("開始自動打怪,不夠體力自動吃珠回體");

			while(true){
				findNextAction(foundScreen , region);
			}

		} catch (FindFailed e) {
			System.out.println("Screen not found!");
			e.printStackTrace();
		}
	}



	private void findNextAction(Match s , Region region){
		int countMove = 0;

		if(!isPlayingNow){
			try{
				Pattern pic = new Pattern("singleplay.png").exact();
				Match imageFound = s.find(pic);
				System.out.println( "已選擇單人遊戲");
				BotUtil.singleClick(imageFound.getTarget().getX(),imageFound.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){}

			try{
				Pattern pic = new Pattern(missionTypeImage).exact();
				Match imageFound = s.find(pic);
				System.out.println( "已選擇訓練冒險");
				BotUtil.singleClick(imageFound.getTarget().getX(),imageFound.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){}


			try{
				Pattern pic = new Pattern(missionNameImage).exact();
				Match isMissionName = s.find(pic);
				System.out.println( "已選擇關卡");
				BotUtil.singleClick(isMissionName.getTarget().getX(),isMissionName.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){
				//System.out.println("Not MissionName");
			}

			try{
				Pattern pic = new Pattern(missionLevelImage).exact();
				Match isMissionLvl = s.find(pic);
				System.out.println( "已選擇關卡等級");
				BotUtil.singleClick(isMissionLvl.getTarget().getX(),isMissionLvl.getTarget().getY(),region);
				App.pause(2);
				countMove++;
			}catch(FindFailed e){
				//System.out.println("Not MissionLevel");
			}


			try{
				Pattern pic = new Pattern("solo.png").exact();
				Match singlePlayer = s.find(pic);
				System.out.println( "已選擇單人");
				BotUtil.singleClick(singlePlayer.getTarget().getX(),singlePlayer.getTarget().getY(),region);
				App.pause(2);
				countMove++;
			}catch(FindFailed e){
				//System.out.println("Not MissionLevel");
			}

			try{
				Pattern pic =  new Pattern("multiplay.png").exact();
				Match multiplay = s.find(pic);
				System.out.println( "進錯了多人介面");
				pressBackBtn(s,region);
			}catch(FindFailed e){
				//System.out.println("Not MissionLevel");
			}

			try{
				Pattern pic  = new Pattern("10.png").exact();
				Match isChoosePartner = s.find(pic);
				System.out.println( "已選擇伙伴 , 準備開始遊戲");
				BotUtil.singleClick(isChoosePartner.getTarget().getX(),isChoosePartner.getTarget().getY(),region);
				App.pause(2);
			}catch(FindFailed e){
				try{
					Pattern pic  = new Pattern("heropower.png").exact();
					Match isChoosePartner = s.find(pic);
					System.out.println( "已選擇伙伴 , 準備開始遊戲");
					BotUtil.singleClick(isChoosePartner.getTarget().getX(),isChoosePartner.getTarget().getY(),region);
				}catch(FindFailed ex){

				}
			}

			try{
				Pattern pic2 = new Pattern("start.png").exact();
				Match isOk = s.find(pic2);
				System.out.println( "出擊");
				BotUtil.singleClick(isOk.getTarget().getX(),isOk.getTarget().getY(),region);
				App.pause(2);
				isPlayingNow = true;
				isMenu = false;
				countMove++;
			}catch(FindFailed ex){

			}
		}




		try{
			Pattern pic = new Pattern("stageEndOK.png").exact();
			Match isOk = s.find(pic);
			okBtn = isOk;
			BotUtil.doubleClick(isOk.getTarget().getX(),isOk.getTarget().getY(),region);
			isPlayingNow = false;
			isMenu = true;
			countMove++;
			System.out.println( "按了OK按鈕");
		}catch(FindFailed e){

		}

		try {
			Pattern pic2 = new Pattern("ok2.png").exact();
			Match isOk2 = s.find(pic2);
			isPlayingNow = false;
			isMenu = true;
			BotUtil.doubleClick(isOk2.getTarget().getX(),isOk2.getTarget().getY(),region);
			System.out.println( "按了OK2按鈕");
			countMove++;
		} catch (FindFailed e1) {
			//System.out.println( "�䤣���OK���s");
		}

		if(isMenu){
			try{
				Pattern pic = new Pattern("newMonster.png").exact();
				Match newMonster = s.find(pic);
				System.out.println( "新怪物");
				BotUtil.doubleClick(newMonster.getTarget().getX(),newMonster.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){

			}


			try{
				Pattern pic = new Pattern("winningBG.png").exact();
				Match winningBG = s.find(pic);
				System.out.println( "特別獎勵");
				BotUtil.doubleClick(winningBG.getTarget().getX(),winningBG.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){

			}


			try{
				Pattern pic = new Pattern("winningBG.png").exact();
				Match winningBG = s.find(pic);
				System.out.println( "獎勵2");
				BotUtil.doubleClick(winningBG.getTarget().getX(),winningBG.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){

			}

		}



		if(isPlayingNow){
			//Is playing
			try{
				Pattern pic	= new Pattern("menu.png").exact();
				Match isPlaying = s.find(pic);

				BotUtil.dragShoot(x, y , width , height , region);
				isPlayingNow = true;
				countMove++;
			}catch(FindFailed e){

			}

		}

		if(countMove == 0){

			waitCycle++;
			if(waitCycle == 4){
				System.out.println("問題發生 ,修復中....");


				try{
					Pattern pic = new Pattern(menuBtnImage).exact();
					Match isPlaying = s.find(pic);
					BotUtil.dragShoot(x, y , width , height , region);
					isPlayingNow = true;
					System.out.println( "遊玩中");
				}catch(FindFailed e){
					isPlayingNow = false;
					System.out.println( "不在遊玩中");


					try{
						Pattern pic = new Pattern("10.png").exact();
						Match isHeroBg = s.find(pic);
						System.out.println( "找到好友列表");
						BotUtil.singleClick(isHeroBg.getTarget().getX(),isHeroBg.getTarget().getY(),region);
						isPlayingNow = false;
						isMenu = true;
					}catch(FindFailed ex){
						System.out.println( "不在好友列表");
						try{
							Pattern pic = new Pattern("back.png").exact();
							Match isHeroBg = s.find(pic);
							BotUtil.singleClick(isHeroBg.getTarget().getX(),isHeroBg.getTarget().getY(),region);
							System.out.println( "已選擇返回");
							isPlayingNow = false;
							isMenu = true;
						}catch(FindFailed ex1){
							System.out.println( "找不到返回鍵");
							System.out.println( "強制點擊");
							Location loc = new Location(region.getTarget().getX(),region.getTarget().getY());
							Mouse.click(loc, "D" , 1000);
						}
					}
				}
				waitCycle = 0;
			}


		}
	}


	public void pressBackBtn(Match s , Region region ){
		try{
			Pattern pic = new Pattern("back.png").exact();
			Match isHeroBg = region.find(pic);
			BotUtil.singleClick(isHeroBg.getTarget().getX(),isHeroBg.getTarget().getY(),region);
			System.out.println( "已選擇返回");
		}catch(FindFailed ex1){
			System.out.println( "找不到返回鍵");
		}
	}


}
