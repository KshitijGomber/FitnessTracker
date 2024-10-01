package model;

import model.WeightRecord;
import model.WeightTracker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightTrackerTest {

    @Test
    public void testWeightRecordConstructorAndGetters() {
        WeightRecord record = new WeightRecord("2023-01-01", 150.0);
        assertEquals("2023-01-01", record.getDate());
        assertEquals(150.0, record.getWeight());
    }

    @Test
    public void testLogAndGetWeightRecords() {
        WeightTracker tracker = new WeightTracker();
        tracker.logWeight("2023-01-01", 150.0);
        tracker.logWeight("2023-02-01", 148.0);
        List<WeightRecord> records = tracker.getWeightRecords();
        assertEquals(2, records.size());
        WeightRecord firstRecord = records.get(0);
        assertEquals("2023-01-01", firstRecord.getDate());
        assertEquals(150.0, firstRecord.getWeight());
        WeightRecord secondRecord = records.get(1);
        assertEquals("2023-02-01", secondRecord.getDate());
        assertEquals(148.0, secondRecord.getWeight());
    }
}
