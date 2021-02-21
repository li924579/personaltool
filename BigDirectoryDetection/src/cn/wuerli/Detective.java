package cn.wuerli;

import cn.wuerli.executor.FileSizeCounter;
import cn.wuerli.executor.Result;
import sun.nio.ch.ThreadPool;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created By Wu Erli On 2021/2/21 1:17
 */


public class Detective {
    public static void main(String[] args) {
//        String path = "D:\\";
        String path = args[0];
        ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 32, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(128), new ThreadPoolExecutor.CallerRunsPolicy());
        File rootFile = new File(path);
        if (!rootFile.isDirectory()) {
            System.out.println(rootFile.getName() + "   :   " + rootFile.length() / 1024L + "KB");
            return;
        }
        File[] files = rootFile.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No file can be counted!");
            return;
        }
        try {
            Map<String, Future<BigDecimal>> futures = new HashMap<>();
            for (File file : files) {
                futures.put(file.getName(), executor.submit(new FileSizeCounter(file.getPath())));
            }
            List<Result> results = new ArrayList<>();
            for (Map.Entry entry : futures.entrySet()) {
                results.add(new Result((String)entry.getKey(), (BigDecimal) ((Future) entry.getValue()).get()));
            }
            Collections.sort(results);
            for (Result result :results) {
                result.show();
            }
//            System.out.println("Total size is " + results.stream().mapToDouble(x -> x.getGb().doubleValue()).sum() + "G");
            System.out.println("=========================================================================");
            System.out.printf("Total used size is %.2fG\n", results.stream().mapToDouble(x -> x.getGb().doubleValue()).sum());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }
}
