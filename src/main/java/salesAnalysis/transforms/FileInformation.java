package salesAnalysis.transforms;

import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.values.KV;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileInformation {
    private static final Logger logger = LogManager.getLogger(FileInformation.class);

    public static KV<String, String> get(FileIO.ReadableFile f) {
        try {
            return KV.of(
                    f.getMetadata().resourceId().toString(), f.readFullyAsUTF8String());
        }catch (Exception e) {
            logger
                    .error(String.format("Error in Getting File Information Filename=%s Error=%s",
                            f.getMetadata().resourceId().toString(),
                            e.getMessage()));
        }

        return null;
    }
}
