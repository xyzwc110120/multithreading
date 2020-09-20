package priv.cyx.java.multithreading03_creat_fork_join;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 使用 Fork / Join 框架
 */
public class CreateForkJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建 ForkJoin 连接池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 计算耗时
        Instant start = Instant.now();
        ForkJoinThread thread = new ForkJoinThread(0, 1000000000);
        forkJoinPool.invoke(thread);
        Instant end = Instant.now();

        System.out.println("消耗时间：" + Duration.between(start, end) + "，计算结果：" + thread.get());


        // 使用单线程计算，对比耗时
        long sum = 0;
        Instant begin = Instant.now();
        for (long i = 0; i <= 1000000000; i++) {
            sum += i;
        }
        Instant finish = Instant.now();
        System.out.println("消耗时间：" + Duration.between(begin, finish) + "，计算结果：" + sum);
    }
}


/**
 * 可以通过继承 RecursiveTask<V> 或 RecursiveAction 抽象类来实现 ForkJoin
 * RecursiveTask<V> 与 RecursiveAction 的关系就像 Callable<T> 和 Runnable，前者有返回值，后者没有
 */
class ForkJoinThread extends RecursiveTask<Long> {
    // 定义一个最大值和最小值，求最小值加到最大值之和
    private long min;
    private long max;

    // 定义一个拆分的最小临界值，若拆分的粒度小于该值，那就不再拆分
    private static final long BORDER = 1000000;

    public ForkJoinThread(long min, long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    protected Long compute() {
        // 获得拆封后的粒度
        long size = max - min;

        if (size <= BORDER) {       // 若拆分粒度小于等于临界值，则不再拆分，计算求和
            long sum = 0L;
            for (long i = min; i <= max; i++) {
                sum += i;
            }
            return sum;
        } else {                    // 若拆分力度大于临界值，则继续拆分
            long middle = (max + min) / 2;      // 算出最大值和最小值的中间值，进行拆分
            // 向左拆分
            ForkJoinThread leftFork = new ForkJoinThread(min, middle);
            leftFork.fork();
            // 向右拆分
            ForkJoinThread rightFork = new ForkJoinThread(middle + 1, max);
            rightFork.fork();
            // 返回拆分后合并的值
            return leftFork.join() + rightFork.join();
        }
    }
}
