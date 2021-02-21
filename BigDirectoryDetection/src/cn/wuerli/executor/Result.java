package cn.wuerli.executor;

import java.math.BigDecimal;

/**
 * Created By Wu Erli On 2021/2/21 18:24
 */


public class Result implements Comparable<Result>{
    private String fileName;
    private BigDecimal kb;
    private BigDecimal mb;
    private BigDecimal gb;

    private static final BigDecimal divisor = BigDecimal.valueOf(1024);

    // size is bytes of file
    // will be converted to KB, MB, GB
    public Result(String fileName, BigDecimal size) {
        this.fileName = fileName;
        kb = size.divide(divisor);
        mb = kb.divide(divisor);
        gb = mb.divide(divisor);
    }

    public void show() {
//        System.out.println(fileName + ":   " + kb + " KB   |   " + mb + " MB   |   " + gb + "G");
        System.out.printf("%30s:   %10.2fKB   |   %10.2fMB   |   %10.2fG\n", fileName, kb, mb, gb);
//        System.out.printf("%s:\t%10.2fKB\t|\t%10.2fMB\t|\t%10.2fG\n", fileName, kb, mb, gb);
    }

    @Override
    public int compareTo(Result o) {
        return kb.compareTo(o.kb);
    }

    public BigDecimal getGb() {
        return gb;
    }
}
