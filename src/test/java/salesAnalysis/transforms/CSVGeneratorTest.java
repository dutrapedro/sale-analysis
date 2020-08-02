package salesAnalysis.transforms;

import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import salesAnalysis.Report;
import salesAnalysis.TestUtils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.apache.beam.sdk.values.TypeDescriptors.kvs;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;

public class CSVGeneratorTest {
    @Rule
    public TestPipeline pipeline;
    public PCollection<Report> input;
    PCollection<String> output;


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
                .apply("Input", MapElements.via(new ContentParser()));

        output = input.apply("Output", MapElements.via(new CSVGenerator("src/test/data/out")));
    }

    @After
    public void setupAfter() {
        File file = new File("src/test/data/out/flatfile.csv");
        file.delete();
    }

    @Test
    public void testCSVGeneratorGeneratedCorrectCSV() throws IOException {
        pipeline.run();

        Reader in = new StringReader(TestUtils.getFileContent("src/test/data/out/flatfile.csv"));
        CSVParser parser = new CSVParser(in,
                CSVFormat.DEFAULT.withHeader(
                        "customers_quantity",
                        "sellers_quantity",
                        "best_sale_id",
                        "worst_seller_name"
                ));
        List<CSVRecord> rows = parser.getRecords();

        assertEquals("2", rows.get(1).get("customers_quantity"));
        assertEquals("2", rows.get(1).get("sellers_quantity"));
        assertEquals("10", rows.get(1).get("best_sale_id"));
        assertEquals("Paulo", rows.get(1).get("worst_seller_name"));
    }

    @Test
    public void testCSVGeneratorReturnsAReportName() {
        PAssert
                .that(output)
                .containsInAnyOrder("data/in/flatfile");

        pipeline.run();
    }
}
