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

}


