package org.homi.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceManager {
	private static ExecutorService es = Executors.newCachedThreadPool();
	
	private ExecutorServiceManager() {
		
	}
	
	public static ExecutorService getExecutorService() {
		return es;
	}
}
