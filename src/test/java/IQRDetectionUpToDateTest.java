import com.oluwafenyi.outliers.DataPoint;
import com.oluwafenyi.outliers.OutlierDetector;
import com.oluwafenyi.outliers.csvhandlers.CSVReader;
import com.oluwafenyi.outliers.loader.DataLoadingException;
import com.oluwafenyi.outliers.loader.IDataLoader;
import com.oluwafenyi.outliers.strategy.IQROutlierDetectionStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class IQRDetectionUpToDateTest {
    static List<DataPoint> data;
    OutlierDetector detector;

    @BeforeClass
    public static void init() throws DataLoadingException {
        IDataLoader csvLoader = new CSVReader("src/test/resources/data.csv", true);
        data = csvLoader.load();
    }

    @Before
    public void setUp() {
        detector = new OutlierDetector(new IQROutlierDetectionStrategy());
    }

    @Test
    public void getOutliersUpToPointInDataSetTest() {
        detector.addDataPoints(data);
        LocalDate date = LocalDate.of(1960, 2, 2);
        List<DataPoint> outliers = detector.getOutliersUpToDate(date);
        Assert.assertEquals(outliers.size(), 1);
        Assert.assertEquals(outliers.get(0).price, 110.9257881d, 0.0001);
    }

    @Test
    public void getOutliersUpToPointInDataSetTwoResultsTest() {
        detector.addDataPoints(data);
        LocalDate date = LocalDate.of(1960, 7, 12);
        List<DataPoint> outliers = detector.getOutliersUpToDate(date);
        Assert.assertEquals(outliers.size(), 2);
        Assert.assertEquals(outliers.get(0).price, 110.9257881d, 0.0001);
        Assert.assertEquals(outliers.get(1).price, 124.855201d, 0.0001);
    }

    @Test
    public void removeOutliersUpToPointInDataSetTest() {
        detector.addDataPoints(data);
        LocalDate date = LocalDate.of(1960, 7, 12);
        List<DataPoint> outliers = detector.getOutliersUpToDate(date);
        List<DataPoint> cleanData = detector.removeOutliersUptoDate(outliers, date);
        List<DataPoint> cleanDataFiltered = cleanData.stream().filter(dataPoint -> dataPoint.date.isAfter(date)).collect(Collectors.toList());
        Assert.assertEquals(cleanDataFiltered.size(), 0);
    }
}
