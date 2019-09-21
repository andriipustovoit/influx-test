package com.safeguard;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{

    public static final long NANOSECONDS_IN_YEAR = 31556952000000000L;
    private Random random = new Random();
    public static void main( String[] args ) throws IOException, InterruptedException {
        App app = new App();
        app.run();
    }


    private void runOnline() throws IOException, InterruptedException {
        EchoClient echoClient = new EchoClient();
        while(true) {
            echoClient.sendEcho(String.format("items,field1=%s,field2=%s value=%d", nextField(1), nextField(2), 1));
            System.out.printf("[%s] Sent packet%n", Instant.now());
            Thread.sleep(1000);
        }
    }

    private void run() throws IOException, InterruptedException {
        EchoClient echoClient = new EchoClient();
        long time = Instant.now().minus(365, ChronoUnit.DAYS).toEpochMilli() * 1000_000;
        long amount = 46_671_763;
        long timeInterval = NANOSECONDS_IN_YEAR / amount;
        System.out.printf("Interval between records: %d%n", timeInterval);
        for(int i = 0; i < amount; i++) {
            echoClient.sendEcho(String.format("items,field1=%s,field2=%s,field3=%s,field4=%s,field5=%s  value=%d %d",
                    nextField(1),
                    nextField(2),
                    nextField(3),
                    nextField(4),
                    nextField(5),
                    1, time));
            time += timeInterval;
            if (i % 100 == 0) {
                System.out.printf("Sent %d packets%n", i);
                Thread.sleep(5);
            }
        }
        System.out.printf("Sent %d packets.", amount);
    }

    private String nextField(int i) {
        return String.format("field%d_%d", i, random.nextInt(5));
    }
}
