package cs601.blkqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class MessageQueueAdaptor<T> implements MessageQueue<T> {

    private int size;


    private T t1;

    protected ArrayBlockingQueue<T> queue;

	MessageQueueAdaptor(int size) {

    //this.size=size;
    queue =new ArrayBlockingQueue<T>(size);

    }

	@Override
	public void put(T o) throws InterruptedException
    {
        queue.put(o);
	}

	@Override
	public T take() throws InterruptedException {


        return queue.take();
	}
}
