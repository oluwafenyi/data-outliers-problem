import com.oluwafenyi.outliers.OutlierDetector;
import com.oluwafenyi.outliers.strategy.IQROutlierDetectionStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IQROutlierDetectionTest {
    OutlierDetector detector;

    @Before
    public void setUp() {
        this.detector= new OutlierDetector(new IQROutlierDetectionStrategy());
    }

    @Test
    public void skeletonTest() {
        Assert.assertTrue(true);
    }
}
