import com.oluwafenyi.outliers.DataPoint;
import com.oluwafenyi.outliers.csvhandlers.CSVReader;
import com.oluwafenyi.outliers.loader.DataLoadingException;
import com.oluwafenyi.outliers.loader.IDataLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CSVReaderTest {
    @Test
    public void testCSVLoading() throws DataLoadingException {
        IDataLoader loader = new CSVReader("src/test/resources/test_data.csv", true);
        List<DataPoint> dataPoints = loader.load();
        Assert.assertEquals(4, dataPoints.size());
        Assert.assertEquals("08/01/1960", dataPoints.get(0).date);
        Assert.assertEquals(30.23445, dataPoints.get(0).price, 0.00001d);
    }
}
