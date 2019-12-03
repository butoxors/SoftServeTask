package Task;

import Task.DriverFactory.DriverType;
import org.testng.annotations.DataProvider;

public class DataProviders {
    @DataProvider(name = "getOptions")
    public Object[] getOptions(){
        return new Object[]{
                DriverType.CHROME,
                DriverType.FIREFOX
        };
    }
}
