package com.aman.customthreadpool;

public class TestClass {
    public static void main(String[] args) throws InterruptedException {
        SampleTask t1=new SampleTask(1);
        SampleTask t2=new SampleTask(2);
        SampleTask t3=new SampleTask(3);
        MyThreadPool t=new MyThreadPool(2);
        t.execute(t1);
        t.execute(t2);
        t.execute(t3);
        t.shutdown();
        while(!t.isTerminated()){

        }
        System.out.println(t.isTerminated());
    }
}
