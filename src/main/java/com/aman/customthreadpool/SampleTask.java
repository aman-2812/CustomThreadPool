package com.aman.customthreadpool;

public class SampleTask implements Runnable{

    private int taskNumber;

    SampleTask(int taskNumber) {
        this.taskNumber=taskNumber;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("Task running:"+taskNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
