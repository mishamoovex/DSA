package hash_table.test_data;

public class TestKey {

    int val;

    public TestKey(int i) {
        this.val = i;
    }

    @Override
    public String toString() {
        return "Key:" + val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestKey testValue = (TestKey) o;
        return val == testValue.val;
    }

    @Override
    public int hashCode() {
        return val % 2;
    }
}
