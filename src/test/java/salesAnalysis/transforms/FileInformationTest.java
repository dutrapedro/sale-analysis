package salesAnalysis.transforms;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import salesAnalysis.Report;
import salesAnalysis.TestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.apache.beam.sdk.values.TypeDescriptors.kvs;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;

public class FileInformationTest {
    @Rule public transient TestPipeline pipeline;

    @Before
    public void setup() {
        pipeline = TestPipeline.create().enableAbandonedNodeEnforcement(false);
    }

    @Test
    public void testFileInformationReturn() throws IOException {
        String expectedPath = Paths.get("src/test/data/in/flatfile").toFile().getAbsolutePath();

        PCollection<FileIO.ReadableFile> input = pipeline
                .apply(FileIO.match()
                        .filepattern("src/test/data/in/*"))
                .apply(FileIO.readMatches());

        PCollection<KV<String, String>> output = input
                .apply(MapElements
                .into(kvs(strings(), strings()))
                .via(FileInformation::get));

        PAssert
                .that(output)
                .containsInAnyOrder(
                        KV.of(expectedPath, TestUtils.getFileContent(null))
                );

        pipeline.run();
    }
}
