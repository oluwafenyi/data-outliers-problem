import com.oluwafenyi.outliers.DataPoint;
import com.oluwafenyi.outliers.OutlierDetector;
import com.oluwafenyi.outliers.strategy.IQROutlierDetectionStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class IQROutlierDetectionTest {
    OutlierDetector detector;

    @Before
    public void setUp() {
        this.detector = new OutlierDetector(new IQROutlierDetectionStrategy());
    }

    @Test
    public void removeOutliersTest() {
        List<DataPoint> outliers = new LinkedList<>();
        outliers.add(new DataPoint("20-01-2021", 1));
        outliers.add(new DataPoint("21-01-2021", 2));
        outliers.add(new DataPoint("22-01-2021", 3));

        List<DataPoint> dataPoints = new LinkedList<>();
        dataPoints.add(new DataPoint("20-01-2021", 1));
        dataPoints.add(new DataPoint("21-01-2021", 2));
        dataPoints.add(new DataPoint("22-01-2021", 3));

        this.detector.addDataPoints(dataPoints);
        List<DataPoint> cleanedList = this.detector.removeOutliers(outliers);
        Assert.assertEquals(0, cleanedList.size());
    }

    @Test
    public void removeOutliersNoDataPointTest() {
        List<DataPoint> outliers = new LinkedList<>();
        outliers.add(new DataPoint("20-01-2021", 1));
        outliers.add(new DataPoint("21-01-2021", 2));
        outliers.add(new DataPoint("22-01-2021", 3));

        List<DataPoint> dataPoints = new LinkedList<>();

        this.detector.addDataPoints(dataPoints);
        List<DataPoint> cleanedList = this.detector.removeOutliers(outliers);
        Assert.assertEquals(0, cleanedList.size());
    }

    @Test
    public void removeOutliersNoOutlierTest() {
        List<DataPoint> outliers = new LinkedList<>();

        List<DataPoint> dataPoints = new LinkedList<>();
        dataPoints.add(new DataPoint("20-01-2021", 1));
        dataPoints.add(new DataPoint("21-01-2021", 2));
        dataPoints.add(new DataPoint("22-01-2021", 3));

        this.detector.addDataPoints(dataPoints);
        List<DataPoint> cleanedList = this.detector.removeOutliers(outliers);
        Assert.assertEquals(3, cleanedList.size());
    }

    @Test
    public void removeOutliersNoMatchTest() {
        List<DataPoint> outliers = new LinkedList<>();
        outliers.add(new DataPoint("21-01-2021", 2));

        List<DataPoint> dataPoints = new LinkedList<>();
        dataPoints.add(new DataPoint("22-01-2021", 3));

        this.detector.addDataPoints(dataPoints);
        List<DataPoint> cleanedList = this.detector.removeOutliers(outliers);
        Assert.assertEquals(1, cleanedList.size());
    }

    @Test
    public void getOutliersNoneTest() {
        List<DataPoint> dataPoints = new LinkedList<>();
        dataPoints.add(new DataPoint("20-01-2021", 1));

        this.detector.addDataPoints(dataPoints);
        List<DataPoint> outliers = this.detector.getOutliers();
        Assert.assertEquals(0, outliers.size());
    }

    @Test
    public void getOutliersFourDataPointsTest() {
        List<DataPoint> dataPoints = new LinkedList<>();
        dataPoints.add(new DataPoint("21-01-2021", 1));
        dataPoints.add(new DataPoint("22-01-2021", 1));
        dataPoints.add(new DataPoint("23-01-2021", 1));
        dataPoints.add(new DataPoint("24-01-2021", 1));

        this.detector.addDataPoints(dataPoints);
        List<DataPoint> outliers = this.detector.getOutliers();
        Assert.assertEquals(0, outliers.size());
    }

    @Test
    public void getOutliersCompoundedHistoricalDataTest() {
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("20-01-2021", 1))));
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("21-01-2021", 2))));
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("22-01-2021", 3))));
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("23-01-2021", 4))));
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("24-01-2021", 5))));
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("25-01-2021", 5))));
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("26-01-2021", 5))));
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("27-01-2021", 5))));
        this.detector.addDataPoints(new LinkedList<>(List.of(new DataPoint("25-01-2021", 1000))));
        List<DataPoint> outliers = this.detector.getOutliers();
        Assert.assertEquals(1, outliers.size());
    }
}
