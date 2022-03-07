package ca.nait.dmit.batch;

import jakarta.batch.api.BatchProperty;
import jakarta.batch.api.chunk.AbstractItemReader;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.nio.file.Paths;

@Named
public class EnforcementZoneCentreItemReader extends AbstractItemReader {

    @Inject
    private JobContext _jobContext;

    private BufferedReader _reader;

    @Inject
    @BatchProperty(name = "input_file")
    private String inputFile;

    private int _itemCount = 0;

    @Inject
    @BatchProperty(name = "max_results")
    private int _maxResults;


    @Override
    public void open(Serializable checkpoint) throws Exception {
        super.open(checkpoint);

        _reader = new BufferedReader(new FileReader(Paths.get(inputFile).toFile()));
        // Read the first line to skip the header row
        _reader.readLine();

        _itemCount = 0;
    }

    @Override
    public Object readItem() throws Exception {
        try {
            String line = _reader.readLine();
            _itemCount += 1;
            if (_maxResults == 0 || _itemCount <= _maxResults) {
                return line;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
