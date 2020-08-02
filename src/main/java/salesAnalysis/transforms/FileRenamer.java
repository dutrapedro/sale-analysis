package salesAnalysis.transforms;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileRenamer extends SimpleFunction<String, String> {
    private static final Logger logger = LogManager.getLogger(FileRenamer.class);
    private String path;

    public FileRenamer(String path) {
        this.path = path;
    }

    @Override
    public String apply(String filename) {
        String from = String.format("%s/%s", path, filename);
        String to = String.format("%s/%s.processed", path, filename);

        Path source = Paths.get(from);
        Path newdir = Paths.get(to);

        try {
            return Files.move(source, newdir, StandardCopyOption.REPLACE_EXISTING).toString();
        } catch (IOException e) {
            logger
                    .error(String.format("Error in Renaming Input File From=%s To=%s Error=%s",
                            from,
                            to,
                            e.getMessage()));
        }
        return "";
    }
}
