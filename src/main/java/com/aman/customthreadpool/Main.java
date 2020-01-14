package com.aman.customthreadpool;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int SIZE=3;
        SampleTask t1=new SampleTask(1);
        SampleTask t2=new SampleTask(2);
        SampleTask t3=new SampleTask(3);
        SampleTask t4=new SampleTask(4);
        MyThreadPool t=new MyThreadPool(SIZE);
        t.execute(t1);
        t.execute(t2);
        t.execute(t3);
        t.execute(t4);
        t.shutdown();
        while(!t.isTerminated()){
        }
    }
}
