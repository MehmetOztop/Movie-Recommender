/**
 *
 * @author uzaycetin
 */
public class StopWatch {

    private final long start;

    public StopWatch() {
        start = System.currentTimeMillis();
    }

    public double elapsedTime() {
        return (System.currentTimeMillis() - start) / 1000.0;
    }
    
    public double elapsedTimeInMilSec() {
        return (System.currentTimeMillis() - start);
    }
    
}