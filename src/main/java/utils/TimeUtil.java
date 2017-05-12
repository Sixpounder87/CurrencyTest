package utils;

public final class TimeUtil {

	public static void sleepTimeoutSec(int timeout) {
		sleepTimeoutMillis(timeout * 1000);
	}

	public static void sleepTimeoutMillis(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
