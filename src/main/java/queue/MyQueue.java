package queue;

import entity.BookingRequests;
import org.apache.log4j.Logger;

import java.util.LinkedList;

public class MyQueue {

    public static final int NUMBERS_OF_REQUESTS = 15;

    private Logger logger = Logger.getLogger(MyQueue.class);

    private LinkedList list = new LinkedList();
    private int countAdd = 0;
    private int countGet = 0;


    public synchronized void add (BookingRequests bookingRequests) {
        while (list.size() == 5) {
            try {
                logger.info("Ожидание, очередь заполнена" + "\n");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (countAdd < NUMBERS_OF_REQUESTS) {
            list.add(bookingRequests);
            logger.info("Сгенерирован запрос: " + list.getLast() + " "  + Thread.currentThread().getName()  +
                    " Запрос #" + countAdd + "\n");
            countAdd++;
        }

        this.notifyAll();
    }


    public synchronized void get () {
        if (countGet < NUMBERS_OF_REQUESTS) {
            while (list.isEmpty()) {
                try {
                    logger.info("Ожидание, очередь пустая" + "\n");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            logger.info("Полученный запрос: " + list.getFirst() + " " + Thread.currentThread().getName() + "\n");
            list.removeFirst();
            countGet++;
            this.notifyAll();
        }
    }


    public int getCountAdd() {
        return countAdd;
    }

    public int getCountGet() {
        return countGet;
    }

    @Override
    public String toString() {
        return "MyQueue{" +
                ", countAdd=" + countAdd +
                ", countGet=" + countGet +
                '}';
    }
}

