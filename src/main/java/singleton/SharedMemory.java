package singleton;

import data.DataSettings;
import parameter.ParameterSettings;

/**
 * i diamoirazomeni mnimi
 */
public class SharedMemory {
    private DataSettings dataSettings;
    private ParameterSettings parameterSettings;

    private static SharedMemory uniqueInstance = null;

    public static SharedMemory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SharedMemory();
        }
        return uniqueInstance;
    }

    public DataSettings getDataSettings() {
        return dataSettings;
    }

    public void setDataSettings(DataSettings dataSettings) {
        this.dataSettings = dataSettings;
    }

    public ParameterSettings getParameterSettings() {
        return parameterSettings;
    }

    public void setParameterSettings(ParameterSettings parameterSettings) {
        this.parameterSettings = parameterSettings;
    }
}
