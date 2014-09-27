package cs601.blkqueue;

class Producer<T> implements Runnable {
	protected MessageQueue<T> queue;
	protected MessageSequence<T> sequence;

	Producer(MessageQueue<T> queue, MessageSequence<T> sequence) {
		this.queue = queue;
		this.sequence = sequence;
	}

	@Override
	public void run() {
		try {
			while (sequence.hasNext()) {
				T next = sequence.next();
				queue.put(next);
                //System.out.println("put data :"+next);
			}


			queue.put(sequence.eof());
            //System.out.println("put data :"+sequence.eof());
		}
		catch (InterruptedException ie) {
			ie.printStackTrace(System.err);
		}
	}
}
