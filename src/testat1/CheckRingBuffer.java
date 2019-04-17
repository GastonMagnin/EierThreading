package testat1;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import uebung1a.*;

public class CheckRingBuffer {
	private final static int BUFFERSIZE = 50;
	Ei[] buffer = new Ei[BUFFERSIZE];
	private AtomicInteger eggsInBuffer = new AtomicInteger(0);
	private AtomicInteger inputPosition = new AtomicInteger(0);
	private AtomicInteger checkPosition = new AtomicInteger(0);
	private AtomicInteger outputPosition = new AtomicInteger(0);
	private ArrayList<String> paths = new ArrayList<String>();
	private Boolean pathsGenerated = false;

	public synchronized void enqueue(Ei input) throws BufferOverflowException {
		synchronized (eggsInBuffer) {
			if (eggsInBuffer.get() >= 50)
				throw new BufferOverflowException();
		}
		synchronized (outputPosition) {
			if (inputPosition.get() >= outputPosition.get() + 50) {
				try {
					outputPosition.wait();
				} catch (InterruptedException e) {
				}
			}

		}
		buffer[inputPosition.get() % 50] = input;
		System.out.println(eggsInBuffer.get());
		eggsInBuffer.incrementAndGet();
		System.out.println(eggsInBuffer.get());
		synchronized (inputPosition) {
			inputPosition.incrementAndGet();
			inputPosition.notifyAll();
		}
		return;
	}

	public boolean checkEgg() throws BufferUnderflowException {
		if (eggsInBuffer.get() <= 0)
			throw new BufferUnderflowException();
		synchronized (inputPosition) {
			if (checkPosition.get() >= inputPosition.get()) {
				try {
					inputPosition.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		if (buffer[checkPosition.get() % 50].getDefekt()) {
			buffer[checkPosition.get() % 50] = null;
			synchronized (checkPosition) {
				checkPosition.getAndIncrement();
				checkPosition.notifyAll();
			}
			System.out.println("checkOut");
			return false;
		}
		synchronized (checkPosition) {
			checkPosition.getAndIncrement();
			checkPosition.notifyAll();
		}
		return true;
	}

	public synchronized Ei dequeue() throws BufferUnderflowException {
		if (eggsInBuffer.get() <= 0)
			throw new BufferUnderflowException();
		synchronized (checkPosition) {
			if (outputPosition.get() >= checkPosition.get()) {
				try {
					checkPosition.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		Ei egg = buffer[outputPosition.get() % 50];
		//If buffer[outputPosition % 50] is null skip 
		if (egg == null) {
			eggsInBuffer.decrementAndGet();
			synchronized (outputPosition) {
				outputPosition.incrementAndGet();
				outputPosition.notifyAll();
			}
			return dequeue();
		}
		eggsInBuffer.decrementAndGet();
		synchronized (outputPosition) {
			outputPosition.getAndIncrement();
			outputPosition.notifyAll();
		}

		return egg;
	}

	public Integer getEggsInBuffer() {
		return eggsInBuffer.get();
	}

	public Integer getInputPosition() {
		return inputPosition.get();
	}

	public Integer getCheckPosition() {
		return checkPosition.get();
	}

	public Integer getOutputPosition() {
		return outputPosition.get();
	}

	public ArrayList<String> getPaths() {
		return paths;
	}

	public Boolean getPathsGenerated() {
		return pathsGenerated;
	}

	public void setPathsGenerated(Boolean pathsGenerated) {
		this.pathsGenerated = pathsGenerated;
	}
}
