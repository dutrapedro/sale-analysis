package salesAnalysis.transforms;

import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.apache.beam.sdk.values.TypeDescriptors.kvs;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileRenamerTest {
    @Rule
    public TestPipeline pipeline;
    public PCollection<String> input;


    @Before
    public void setup() {
        pipeline = TestPipeline.create().enableAbandonedNodeEnforcement(false);

        input = pipeline
                .apply(FileIO.match()
                        .filepattern("src/test/data/in/*"))
                .apply(FileIO.readMatches())
                .apply(MapElements
                        .into(kvs(strings(), strings()))
                        .via(FileInformation::get))
                .apply("Input", MapElements.via(new ContentParser()))
                .apply("Generate", MapElements.via(new CSVGenerator("src/test/data/out")));

        input.apply("Output", MapElements.via(new FileRenamer("src/test/data/in")));
    }

    @After
    public void afterSetup() {
        File processedFile = new File("src/test/data/in/flatfile.processed");
        if (processedFile.exists()) {
            processedFile.renameTo(new File("src/test/data/in/flatfile"));
        }
    }

    @Test
    public void assertThatFileRenamerRenamesInputFile() {
        pipeline.run();

        File processedFile = new File("src/test/data/in/flatfile.processed");
        File file = new File("src/test/data/in/flatfile");

        assertTrue(processedFile.exists());
        assertFalse(file.exists());
    }
}
