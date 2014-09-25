package cs601.blkqueue;

import java.util.LinkedList;
import java.util.Queue;


public class SynchronizedBlockingQueue<T> implements MessageQueue<T> {

    private int size;
    private T t;

    protected LinkedList<T> queueList= new LinkedList<T>();

	public SynchronizedBlockingQueue(int size) {
        this.size=size;

	}


	public void put(T o) throws InterruptedException {

        synchronized (this) {
            if (queueList.size() == size) {
                wait();
            }

            queueList.addLast(o);

            notifyAll();
        }

	}

	@Override
	public T take() throws InterruptedException {

        synchronized (this) {

            if (queueList.isEmpty()) {
                wait();
            }
            t=queueList.removeFirst();
            notifyAll();

            return t;

        }
	}
}
