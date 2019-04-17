package testat1;

import java.nio.BufferUnderflowException;

public class CheckModul implements Runnable {
	CheckRingBuffer crb;
	public CheckModul(CheckRingBuffer crb) {
		this.crb = crb;
	}
	@Override
	public void run() {
		while(true) {
			try {
			if(!crb.checkEgg()) {
				System.out.println("Ei aussortiert");
			}
				Thread.sleep(50);
			} catch (BufferUnderflowException e) {
			} catch (InterruptedException e) {
			}
		}
	}

}
