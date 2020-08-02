package salesAnalysis.transforms;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import salesAnalysis.Report;
import salesAnalysis.TestUtils;

import java.io.IOException;
import java.util.List;

import static org.apache.beam.sdk.values.TypeDescriptors.kvs;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;

public class ContentParserTest {
    @Rule
    public transient TestPipeline pipeline;

    @Before
    public void setup() {
        pipeline = TestPipeline.create().enableAbandonedNodeEnforcement(false);
    }

    @Test
    public void testContentParserReturn() throws IOException {
        PCollection<KV<String, String>> input = pipeline
                .apply(FileIO.match()
                        .filepattern("src/test/data/in/*"))
                .apply(FileIO.readMatches())
                .apply(MapElements
                        .into(kvs(strings(), strings()))
                        .via(FileInformation::get));

        PCollection<Report> output =
                input.apply("Output", MapElements.via(new ContentParser()));

        PAssert
                .that(output)
                .containsInAnyOrder(new Report(""));

        pipeline.run();
    }
}
