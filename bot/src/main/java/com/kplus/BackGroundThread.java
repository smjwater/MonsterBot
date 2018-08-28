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

public class BackGroundThread implements Runnable{

	private int x = 0;
	private int y = 0;
	private int height = 0;
	private int width = 0;
	private int waitCycle = 0;
	//private ScreenImage missionType;
	private ScreenImage startImage;
	private ScreenImage missionTypeImage;
	private ScreenImage missionNameImage;
	private ScreenImage missionLevelImage;
	private ScreenImage menuBtnImage;
	//private ScreenImage redBg;
	//private ScreenImage heroPowerImage;
	//private ScreenImage backBtnImage;
	private ScreenImage singlePlayerImage;
	private ScreenImage goImage;

	private boolean chooseStage = false;
	boolean isPlayingNow = true;
	boolean isMenu = false;
	private int mode;
	//private boolean autoModeStart;
	Match okBtn ;

	public void run() {
		System.out.println(BackGroundThread.class.getCanonicalName());
		ImagePath.add(BackGroundThread.class.getCanonicalName() + "/images");
		//ImagePath.add("/images");
		Screen s = new Screen();
		Scanner sc = new Scanner(System.in);
		//System.out.println("Please Select Mode");
		//System.out.println("1. �۰�  , 2.���");

		mode =2;
		//	mode = Integer.parseInt(sc.nextLine());
		//System.out.println("Press Enter to Capture Game Screen.");
		//sc.nextLine();
		ScreenImage gameImage = s.userCapture();
		try {
			Match foundScreen = s.find(gameImage.getFile());
			//System.out.println("X: "+foundScreen.getTarget().getX());
			//System.out.println("Y: "+foundScreen.getTarget().getY());
			x = foundScreen.getTarget().getX();
			y = foundScreen.getTarget().getY();
			width = gameImage.w;
			height = gameImage.h;
			//System.out.println("W: "+ gameImage.w);
			//System.out.println("H: "+ gameImage.h);

			foundScreen.highlight(1);
			Region region = new Region(x,y,width,height);



			System.out.println("請按ENTER鍵然後選擇冒險圖");
			sc.nextLine();
			startImage = s.userCapture();


			System.out.println("請按ENTER鍵然後選擇冒險項目");
			sc.nextLine();
			missionTypeImage = s.userCapture();
			System.out.println("請按ENTER鍵然後選擇關卡");
			sc.nextLine();
			missionNameImage = s.userCapture();
			System.out.println("請按ENTER鍵然後選擇等級");
			sc.nextLine();
			missionLevelImage = s.userCapture();
			System.out.println("請按ENTER鍵然後選擇單人");
			sc.nextLine();
			singlePlayerImage = s.userCapture();
			if(mode == 2){
				//				System.out.println("�жi�J����C��,��ENTER��ܦn�ͭ^�����O��");
				//				sc.nextLine();
				//				heroPowerImage = s.userCapture();
				//				System.out.println("��ENTER��ܪ�^���");
				//				sc.nextLine();
				//				backBtnImage = s.userCapture();

				//partnerX = heroPowerImage.x;
				System.out.println("請按ENTER鍵然後選擇出發");
				sc.nextLine();
				goImage = s.userCapture();

				System.out.println("請按ENTER鍵然後選擇MENU的NU字");
				sc.nextLine();
				menuBtnImage = s.userCapture();
				//System.out.println("�жi�J���d,��ENTER��ܹC����MENU 2��");
				//sc.nextLine();
				//menuBtnImage2 = s.userCapture();
				System.out.println("開始自動打怪,不夠體力自動吃珠回體");

			}
			while(true){
				findNextAction(foundScreen , region);
			}
			//s.find(s.userCapture().getFile()).highlight(2);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			System.out.println("Screen not found!");
			e.printStackTrace();
		}
	}


	private void dragShoot(int x, int y ,int width ,int height , Region region) throws FindFailed{
		Location loc = new Location(x,y);
		Mouse.move(loc);
		Location newLoc = new Location(x + (width/ 3)  ,y + (height /3));
		region.dragDrop(loc, newLoc);
		waitCycle = 0;
	}

	private void pressButton(int x, int y, Region region) throws FindFailed{
		Location loc = new Location(x,y);
		Mouse.move(loc);
		App.pause(1);
		//System.out.println("Move");
		Mouse.click(loc, "L" , 1000);

	}

	private void findNextAction(Match s , Region region){
		int countMove = 0;

		if(!isPlayingNow){
			try{
				Pattern pic ;
				if(mode == 1){
					pic = new Pattern("singleplay.png").exact();
					//Match isHeroBg = s.find(pic);
				}else{
					pic = new Pattern(startImage).exact();
				}
				Match imageFound = s.find(pic);
				System.out.println( "已選擇單人遊戲");
				pressButton(imageFound.getTarget().getX(),imageFound.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){}

			try{
				Pattern pic = new Pattern(missionTypeImage).exact();
				Match imageFound = s.find(pic);
				System.out.println( "已選擇訓練冒險");
				pressButton(imageFound.getTarget().getX(),imageFound.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){}


			try{
				Pattern pic = new Pattern(missionNameImage).exact();
				Match isMissionName = s.find(pic);
				System.out.println( "已選擇關卡");
				pressButton(isMissionName.getTarget().getX(),isMissionName.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){
				//System.out.println("Not MissionName");
			}

			try{
				Pattern pic = new Pattern(missionLevelImage).exact();
				Match isMissionLvl = s.find(pic);
				System.out.println( "已選擇關卡等級");
				//isMissionLvl.highlight(2);
				pressButton(isMissionLvl.getTarget().getX(),isMissionLvl.getTarget().getY(),region);
				App.pause(2);
				countMove++;
			}catch(FindFailed e){
				//System.out.println("Not MissionLevel");
			}


			try{
				Pattern pic = new Pattern(singlePlayerImage).exact();
				Match singlePlayer = s.find(pic);
				System.out.println( "已選擇單人");
				//isMissionLvl.highlight(2);
				pressButton(singlePlayer.getTarget().getX(),singlePlayer.getTarget().getY(),region);
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
				pressButton(isChoosePartner.getTarget().getX(),isChoosePartner.getTarget().getY(),region);
				App.pause(2);
			}catch(FindFailed e){
				try{
					Pattern pic  = new Pattern("heropower.png").exact();
					Match isChoosePartner = s.find(pic);
					System.out.println( "已選擇伙伴 , 準備開始遊戲");
					pressButton(isChoosePartner.getTarget().getX(),isChoosePartner.getTarget().getY(),region);
				}catch(FindFailed ex){

				}
			}

			try{
				Pattern pic2 = new Pattern(goImage).exact();
				Match isOk = s.find(pic2);
				System.out.println( "出擊");
				pressButton(isOk.getTarget().getX(),isOk.getTarget().getY(),region);
				App.pause(2);
				isPlayingNow = true;
				isMenu = false;
				countMove++;
			}catch(FindFailed ex){

			}
		}




		try{
			Pattern pic = new Pattern("btnOk.png").exact();
			Match isOk = s.find(pic);
			okBtn = isOk;
			//System.out.println( "�w��ܥ��");
			pressButton(isOk.getTarget().getX(),isOk.getTarget().getY(),region);
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
			doubleClick(isOk2.getTarget().getX(),isOk2.getTarget().getY(),region);
			System.out.println( "按了OK2按鈕");
			countMove++;
		} catch (FindFailed e1) {
			//System.out.println( "�䤣���OK���s");
		}

		if(isMenu){
			try{
				Pattern pic = new Pattern("special.png").exact();
				Match special = s.find(pic);
				System.out.println( "通關介面");
				doubleClick(special.getTarget().getX(),special.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){
				//System.out.println("�䤣��q������");
			}

			try{
				Pattern pic = new Pattern("award.png").exact();
				Match award = s.find(pic);
				System.out.println( "獎勵");
				doubleClick(award.getTarget().getX(),award.getTarget().getY(),region);
				countMove++;
			}catch(FindFailed e){
				//System.out.println( "�䤣����y");
			}
			try{
				Pattern pic = new Pattern("newMonster.png").exact();
				Match newMonster = s.find(pic);
				System.out.println( "新怪物");
				doubleClick(newMonster.getTarget().getX(),newMonster.getTarget().getY(),region);
				countMove++;
				//System.out.println("Not Ok button");
			}catch(FindFailed e){
				//e.printStackTrace();
				//System.out.println( "�䤣��_�I���y");
			}


			try{
				Pattern pic = new Pattern("winningBG.png").exact();
				Match winningBG = s.find(pic);
				System.out.println( "特別獎勵");
				doubleClick(winningBG.getTarget().getX(),winningBG.getTarget().getY(),region);
				countMove++;
				//System.out.println("Not Ok button");
			}catch(FindFailed e){
				//e.printStackTrace();
				//System.out.println( "�䤣��_�I���y");
			}

            try{
                Pattern pic = new Pattern("winningBG2.png").exact();
                Match winningBG2 = s.find(pic);
                System.out.println( "冒險獎勵");
                doubleClick(winningBG2.getTarget().getX(),winningBG2.getTarget().getY(),region);
                countMove++;
                //System.out.println("Not Ok button");
            }catch(FindFailed e){
                //e.printStackTrace();
                //System.out.println( "�䤣��_�I���y");
            }

			try{
				Pattern pic = new Pattern("levelup.png").exact();
				Match levelup = s.find(pic);
				System.out.println( "階級提升");
				doubleClick(levelup.getTarget().getX(),levelup.getTarget().getY(),region);

			}catch(FindFailed e){
				//System.out.println( "�䤣�춥�Ŵ���");
			}

			try{
				Pattern pic = new Pattern("btnOk.png").exact();
				Match isOk = s.find(pic);
				okBtn = isOk;
				pressButton(isOk.getTarget().getX(),isOk.getTarget().getY(),region);
				isPlayingNow = false;
				isMenu = true;
				countMove++;
				System.out.println( "按了OK3按鈕");
			}catch(FindFailed e){

			}
		}



		if(isPlayingNow){
			//Is playing
			try{
				Pattern pic ;
				if(mode == 1){
					pic = new Pattern("menu.png").exact();
					//Match isHeroBg = s.find(pic);
				}else{
					pic = new Pattern(menuBtnImage).exact();
				}
				Match isPlaying = s.find(pic);

				dragShoot(x, y , width , height , region);
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
					dragShoot(x, y , width , height , region);
					isPlayingNow = true;
					System.out.println( "遊玩中");
				}catch(FindFailed e){
					isPlayingNow = false;
					System.out.println( "不在遊玩中");


					try{
						Pattern pic = new Pattern("10.png").exact();
						Match isHeroBg = s.find(pic);
						System.out.println( "找到好友列表");
						pressButton(isHeroBg.getTarget().getX(),isHeroBg.getTarget().getY(),region);
						isPlayingNow = false;
						isMenu = true;
					}catch(FindFailed ex){
						System.out.println( "不在好友列表");
						try{
							Pattern pic = new Pattern("back.png").exact();
							Match isHeroBg = s.find(pic);
							pressButton(isHeroBg.getTarget().getX(),isHeroBg.getTarget().getY(),region);
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
			Match isHeroBg = s.find(pic);
			//System.out.println( "關卡結束");
			pressButton(isHeroBg.getTarget().getX(),isHeroBg.getTarget().getY(),region);
			System.out.println( "已選擇返回");
		}catch(FindFailed ex1){
			System.out.println( "找不到返回鍵");
		}
	}

	private static void doubleClick(int x, int y, Region region) throws FindFailed{
		Location loc = new Location(x,y);
		Mouse.move(loc);
		App.pause(2);
		Mouse.click(loc, "D" , 1000);
		Mouse.click(loc, "D" , 1000);
	}

	private static void singleClick(int x, int y, Region region) throws FindFailed{
		Location loc = new Location(x,y);
		Mouse.move(loc);
		App.pause(2);
		Mouse.click(loc, "L" , 1000);
		//Mouse.click(loc, "D" , 1000);
	}
}
