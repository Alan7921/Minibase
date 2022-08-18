package ed.inf.adbs.minibase.base;

/**
 * This class defines the data to be used in AVG aggregation.
 * It has two fields, a int typed sum, and a int typed count.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getters and setters.
 */
public class AvgStat extends AggregationStat {
    private int sum;
    private int count;

    public AvgStat(int sum, int count) {
        this.sum = sum;
        this.count = count;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvgStat)) return false;

        AvgStat avgStat = (AvgStat) o;

        if (getSum() != avgStat.getSum()) return false;
        return getCount() == avgStat.getCount();
    }

    @Override
    public int hashCode() {
        int result = getSum();
        result = 31 * result + getCount();
        return result;
    }
}
