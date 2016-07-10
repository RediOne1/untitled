package com.appsprojekt;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * author:  Adrian Kuta
 * date:    15.06.2016
 */
class Activity {

	private Robot robot;
	private Random random;

	private Activity() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		random = new Random();
	}

	static void make(long time, float mouse, float keyboard) {
		Activity activity = new Activity();
		int intervals = (int) (time / TimeUnit.MINUTES.toMillis(10));
		while (intervals > 0) {
			activity.makeJob(TimeUnit.MINUTES.toMillis(10), mouse, keyboard);
			intervals--;
		}
		activity.makeJob(time % TimeUnit.MINUTES.toMillis(10), mouse, keyboard);
	}

	private void makeJob(long time, float mouse, float keyboard) {
		if (robot == null)
			return;

		mouse += getRandomDiff(20);
		System.out.println("Mouse " + (mouse * 100) + "%");
		keyboard += getRandomDiff(20);
		System.out.println("Keyboard " + (keyboard * 100) + "%");

		long currentTimeMillis = System.currentTimeMillis();
		long mouseEndTime = (long) (time * mouse) + currentTimeMillis;
		long keyboardEndTime = (long) (time * keyboard) + currentTimeMillis;
		long endTime = time + currentTimeMillis;


		long mouseDelay = TimeUnit.MINUTES.toMillis(random.nextInt(3) + 1);
		mouseEndTime += mouseDelay;
		long mouseStartTime = System.currentTimeMillis() + mouseDelay;

		boolean temp = true;
		while ((currentTimeMillis = System.currentTimeMillis()) < endTime) {
			if (mouseStartTime < currentTimeMillis && currentTimeMillis < mouseEndTime) {
				Point mousePoint = MouseInfo.getPointerInfo().getLocation();
				if (temp)
					robot.mouseMove(mousePoint.x + 1, mousePoint.y);
				else
					robot.mouseMove(mousePoint.x - 1, mousePoint.y);
			}
			if (currentTimeMillis < keyboardEndTime) {
				robot.keyPress(KeyEvent.VK_SCROLL_LOCK);
				robot.keyRelease(KeyEvent.VK_SCROLL_LOCK);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			temp = !temp;
		}
	}

	private float getRandomDiff(int maxDiff) {
		float result;

		int temp = random.nextInt(maxDiff / 2 - 5) + 5;
		boolean sign = random.nextBoolean();
		temp *= sign ? 1 : -1;

		result = (float) temp / 100;

		return result;
	}
}
