package ca.nait.dmit.domain;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class manages AlbertaCovid19Case data.
 *
 * @author Sam Wu
 * @version 2022.01.19
 */
public class AlbertaCovid19CaseManager {

    private static AlbertaCovid19CaseManager instance;

    private AlbertaCovid19CaseManager() throws IOException {
        albertaCovid19CaseList = loadCsvData();
    }

    public static AlbertaCovid19CaseManager getInstance() throws IOException {
        // https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples#thread-safe-singleton
        if(instance == null){
            synchronized (AlbertaCovid19CaseManager.class) {
                if(instance == null){
                    instance = new AlbertaCovid19CaseManager();
                }
            }
        }
        return instance;
    }

    @Getter
    private List<AlbertaCovid19Case> albertaCovid19CaseList;

    private List<AlbertaCovid19Case> loadCsvData() throws IOException {
       List<AlbertaCovid19Case> dataList = new ArrayList<>();

       try (var reader = new BufferedReader(new InputStreamReader(
           getClass().getResourceAsStream("/data/covid-19-alberta-statistics-data.csv")))) {

           final var delimiter = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
           String line;
           // Skip the first line as it contains column headings
           reader.readLine();

           var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           // Read one line at time from the input stream
           while ( (line = reader.readLine()) != null) {
               String[] values = line.split(delimiter, -1); // The -1 limit allows for any number of fields and not discard trailing empty fields
               // Column order of fields:
               // 0 - "ID"
               // 1 - "Date reported"
               // 2 - "Alberta Health Services Zone"
               // 3 - "Gender"
               // 4 - "Age group"
               // 5 - "Case status"
               // 6 - "Case type"

               // Create an object from each row in the file
               AlbertaCovid19Case lineData = new AlbertaCovid19Case();
               lineData.setId(Integer.parseInt(values[0].replaceAll("\"","")));
               lineData.setDateReported(LocalDate.parse(values[1], dateFormatter));
               lineData.setAhsZone(values[2].replaceAll("\"",""));
               lineData.setGender(values[3].replaceAll("\"",""));
               lineData.setAgeGroup(values[4].replaceAll("\"",""));
               lineData.setCaseStatus(values[5].replaceAll("\"",""));
               lineData.setCaseType(values[6].replaceAll("\"",""));

               // Add lineData to dataList
               dataList.add(lineData);
            }
        }
        AlbertaCovid19Case lineData = new AlbertaCovid19Case();
        dataList.add(lineData);
        return dataList;
    }

    public Optional<AlbertaCovid19Case> findById(int id) {
        return albertaCovid19CaseList
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }

    public long countTotalActiveCases() {
        return albertaCovid19CaseList
                .stream()
                .filter(item -> item.getCaseStatus().equalsIgnoreCase("Active"))
                .count();
    }

    public long countActiveCasesByAhsZone(String ahsZone) {
        return albertaCovid19CaseList
                .stream()
//                .filter(item -> item.getCaseStatus().equalsIgnoreCase("Active")
//                    && item.getAhsZone().equalsIgnoreCase(ahsZone)
//                )
                .filter(item -> item.getCaseStatus().equalsIgnoreCase("Active"))
                .filter(item -> item.getAhsZone().equalsIgnoreCase(ahsZone) )
                .count();
    }

    public List<String> distinctAhsZones() {
        return albertaCovid19CaseList
                .stream()
                .map(item -> item.getAhsZone())
                .distinct()
                .filter(item -> item.isEmpty() == false)
                .sorted()
                .collect(Collectors.toList());

    }
}
