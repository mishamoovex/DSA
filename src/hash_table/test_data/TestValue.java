package hash_table.test_data;

public class TestValue {

    int val;

    public TestValue(int i) {
        this.val = i;
    }

    @Override
    public String toString() {
        return "Value:" + val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestValue testValue = (TestValue) o;
        return val == testValue.val;
    }

    @Override
    public int hashCode() {
        return val % 2;
    }
}

