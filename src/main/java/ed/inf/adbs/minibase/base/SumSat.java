package ed.inf.adbs.minibase.base;

/**
 * This class defines the data to be used in SUM aggregation.
 * It has only one field, a int typed sum.
 * For convenience, its toString, hashcode and equals method are
 * overridden. Besides,it also provides some getters and setters.
 */
public class SumSat extends AggregationStat {
    private int sum;

    public SumSat(int sum) {
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SumSat)) return false;

        SumSat sumSat = (SumSat) o;

        return getSum() == sumSat.getSum();
    }

    @Override
    public int hashCode() {
        return getSum();
    }

    @Override
    public String toString() {
        return "SumSat{" +
                "sum=" + sum +
                '}';
    }
}
