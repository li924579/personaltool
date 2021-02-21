package cn.wuerli.executor;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * Created By Wu Erli On 2021/2/21 17:26
 */


public class FileSizeCounter implements Callable<BigDecimal> {

    String rootPath;

    public FileSizeCounter(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public BigDecimal call() throws Exception {
        File rootFile = new File(rootPath);
        if (!rootFile.isDirectory()) return BigDecimal.valueOf(rootFile.length());
        return countHelper(rootFile, 0);
    }

    private BigDecimal countHelper(File rootFile, int currDepth) {
        BigDecimal counter = BigDecimal.ZERO;
        File[] files = rootFile.listFiles();
        if (files == null || files.length == 0) return counter;
        for (File file : files) {
            if (file.isDirectory()) counter = counter.add(countHelper(file, currDepth + 1));
            else counter = counter.add(BigDecimal.valueOf(file.length()));
        }
        return counter;
    }
}
