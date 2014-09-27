package cs601.blkqueue;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class RingBuffer<T> implements MessageQueue<T> {
    private final AtomicLong w = new AtomicLong(-1);    // just wrote location
    private final AtomicLong r = new AtomicLong(0);        // about to read location
    private int size;
    //private boolean wFlag;
    //private boolean rFlag;
    private T t2;
    //private int wSlot;
    //private int rSlot;

    //private ArrayList<T> queue;

    private T[] queue1;

    public RingBuffer(int n) {
        this.size = n;
        //this.queue = new ArrayList<T>();
        this.queue1 = (T[]) new Object[size];
        //System.out.println(size);
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
       /* if(queue1[w.intValue()%size]!=null){
            waitForFreeSlotAt(w.get());


        }*/


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


        //System.out.println("return data is"+t2);


        return t2;

    }


    // spin wait instead of lock for low latency store
   void waitForFreeSlotAt(final long writeIndex)  {

       /*if(((w.intValue()+1)%size<r.intValue()%size)&&(queue1[w.intValue()%size]!=null)){
           /*if(queue1[w.intValue()%size]!=null){

           }
           return;*/

       }





    // spin wait instead of lock for low latency pickup
   void waitForDataAt(final long readIndex) {

      /* if(r.get()<=w.get()){return;}*/


    }
}

/*
	public void put(T v) throws InterruptedException {

        if(queue.size()>=size){
            System.out.println("hello");
            waitForFreeSlotAt(w.get());
            System.out.println("hello again");
        }
        if(w.intValue()==-1)
        {w.incrementAndGet();}
        queue.add(v);
        System.out.println("****"+queue.indexOf(v));
        w.incrementAndGet();
        //Thread.sleep(1);

	}

	@Override
	public T take() throws InterruptedException {

        //Thread.sleep(1);

        System.out.println("@@@@@@@@@@@@");
        System.out.println(r.get());


        if (queue.isEmpty()) {

            waitForDataAt(r.get());
        }

        System.out.println("%%%%%%"+queue.get(0));
        t2 = queue.remove(0);
        System.out.println(t2);

            //queue[r.intValue()%size]=null;
        r.incrementAndGet();

        return t2;
    }



	// spin wait instead of lock for low latency store
	public void waitForFreeSlotAt(final long writeIndex)throws InterruptedException{

       Thread.sleep(10);
       if((w.intValue()-r.intValue()<size)&&(queue.size()<size)){

           System.out.println("I'm black");
           return;

       }


	}

	// spin wait instead of lock for low latency pickup
	public void waitForDataAt(final long readIndex)throws InterruptedException{

        Thread.sleep(10);

        if((w.get()>=readIndex)){
            return;
        }


	}
}*/
