package salesAnalysis;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.Watch;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PDone;
import org.joda.time.Duration;
import org.w3c.dom.Text;

import java.io.IOException;

import static org.apache.beam.sdk.io.Compression.GZIP;
import static org.apache.beam.sdk.transforms.Watch.Growth.never;
import static org.apache.beam.sdk.values.TypeDescriptors.kvs;
import static org.apache.beam.sdk.values.TypeDescriptors.strings;


public class SalesAnalysis {
    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();
        String homePath = System.getenv("HOME");
        String folderToWatch = String.format("%s/data/in/*", homePath);

        pipeline
                .apply(FileIO.match()
                        .filepattern(folderToWatch)
                        .continuously(Duration.standardSeconds(30), never()))
                .apply(FileIO.readMatches())
                .apply(MapElements
                        .into(kvs(strings(), strings()))
                        .via(Test::apply))
                .apply("Pedro", MapElements.via(new ContentParser()));
        PDone.in(pipeline);

        pipeline.run().waitUntilFinish();
    }
}
