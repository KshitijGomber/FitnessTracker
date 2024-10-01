package model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightRecordTest {

    @Test
    public void testWeightRecordConstructorAndGetters() {
        WeightRecord record = new WeightRecord("2023-01-01", 150);
        assertEquals("2023-01-01", record.getDate());
        assertEquals(150, record.getWeight());
    }
}
