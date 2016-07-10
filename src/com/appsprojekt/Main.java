package com.appsprojekt;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws IOException {
		Activity.make(TimeUnit.HOURS.toMillis(3), 0.6f, 0.4f);
	}
}
