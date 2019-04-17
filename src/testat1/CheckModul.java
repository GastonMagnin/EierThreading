package testat1;

import java.nio.BufferUnderflowException;

public class CheckModul implements Runnable {
	CheckRingBuffer crb;
	public CheckModul(CheckRingBuffer crb) {
		this.crb = crb;
	}
	@Override
	public void run() {
		//TODO  condition
		while(true) {
			try {
			crb.checkEgg();
				Thread.sleep(50);
			} catch (InterruptedException|BufferUnderflowException e) {
			}
		}
	}

}
