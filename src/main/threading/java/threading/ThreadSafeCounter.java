package java.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeCounter implements Runnable {

	static int counter = 1;
	static ReentrantLock safeCounter = new ReentrantLock(true);
	
	static void incrementCounter () {
		safeCounter.lock();
		counter++;
		safeCounter.unlock();
		try {
			System.out.println(Thread.currentThread().getName() + " : " + counter);
			
		} catch (Exception e) {
			// TODO: handle exception	
		}
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		while (counter<10000) {
			incrementCounter();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		Runnable counterPool[]= new Runnable[10];
		
		for(int i = 0 ; i < 10 ; i++) {
			counterPool[i] = new ThreadSafeCounter();
			executorService.execute(counterPool[i]);
		}
		executorService.shutdown();
		while(!executorService.isTerminated()) { }
		System.out.println("Finished Working");
		
		/*ThreadSafeCounter counter = new ThreadSafeCounter();
		Thread t1 = new Thread(counter);
		Thread t2 = new Thread(counter);
		Thread t3 = new Thread(counter);
		
		t1.start();
		t2.start();
		t3.start();*/
	}

}
