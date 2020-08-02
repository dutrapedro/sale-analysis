package salesAnalysis;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.joda.time.Duration;
import salesAnalysis.transforms.CSVGenerator;
import salesAnalysis.transforms.ContentParser;
import salesAnalysis.transforms.FileInformation;
import salesAnalysis.transforms.FileRenamer;

import static org.apache.beam.sdk.transforms.Watch.Growth.never;
import static org.apache.beam.sdk.values.TypeDescriptors.kvs;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;


public class EntryPoint {
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();
        String path = String.format("%s/data/in", System.getenv("HOME"));
        String folderToWatch = String.format("%s/*.txt", path);

        pipeline
                .apply("Watch for Files", FileIO.match()
                        .filepattern(folderToWatch)
                        .continuously(Duration.standardSeconds(30), never()))
                .apply("Read Watched File", FileIO.readMatches())
                .apply("Get Info About File", MapElements
                        .into(kvs(strings(), strings()))
                        .via(FileInformation::get))
                .apply("Parse Content", MapElements.via(new ContentParser()))
                .apply("Generate CSV Report", MapElements.via(new CSVGenerator("")))
                .apply("Rename original file", MapElements.via(new FileRenamer(path)));

        pipeline.run();
    }
}
