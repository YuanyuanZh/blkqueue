package cs601.blkqueue;


import java.util.concurrent.atomic.AtomicLong;

public class RingBuffer<T> implements MessageQueue<T> {
    private final AtomicLong w = new AtomicLong(-1);    // just wrote location
    private final AtomicLong r = new AtomicLong(0);        // about to read location
    private int size;
    private T t2;

    private T[] queue1;

    public RingBuffer(int n) {
        this.size = n;
        this.queue1 = (T[]) new Object[size];

    }

    // http://graphics.stanford.edu/~seander/bithacks.html#CountBitsSetParallel
    static boolean isPowerOfTwo(int v) {
        if (v < 0) return false;
        v = v - ((v >> 1) & 0x55555555);                    // reuse input as temporary
        v = (v & 0x33333333) + ((v >> 2) & 0x33333333);     // temp
        int onbits = ((v + (v >> 4) & 0xF0F0F0F) * 0x1010101) >> 24; // count
        // if number of on bits is 1, it's power of two, except for sign bit
        return onbits == 1;
    }

    public void put(T v) throws InterruptedException {
        if (w.intValue() == -1)
        {
            w.incrementAndGet();
        }

        while(((w.intValue()-r.intValue())>=size)||(queue1[w.intValue()%size]!=null)) {
            waitForFreeSlotAt(w.get());

        }


        queue1[w.intValue()%size]=v;
        //System.out.println("put data at"+w.intValue()%size);

        w.incrementAndGet();



    }

    @Override
    public T take() throws InterruptedException {


       while((r.get()>=w.get())||w.intValue()==-1 ){

            waitForDataAt(r.get());
        }

        t2 = queue1[r.intValue()%size];
        queue1[r.intValue()%size]=null;

        r.incrementAndGet();

        return t2;

    }


    // spin wait instead of lock for low latency store
   void waitForFreeSlotAt(final long writeIndex)  {


       }


    // spin wait instead of lock for low latency pickup
   void waitForDataAt(final long readIndex) {



    }
}

