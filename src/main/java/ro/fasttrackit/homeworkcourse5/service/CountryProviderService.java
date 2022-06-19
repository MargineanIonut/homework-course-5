package ro.fasttrackit.homeworkcourse5.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homeworkcourse5.model.Country;
import ro.fasttrackit.homeworkcourse5.repository.CountryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class CountryProviderService {

    private static final String COUNTRY_DATA = "C:\\ThisIsTheBestIcouldAtTheTime\\FasttrackFolderOrganizer\\Curs5\\tema\\homework-course-5\\src\\main\\resources\\countries.txt";

    private final CountryRepository countryRepository;

    public CountryProviderService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> readCountries() {
        try {
            return Files.lines(Path.of(COUNTRY_DATA))
                    .map(line -> {
                        String[] splittedLine = line.split("\\|");
                        String[] tokens = new String[7];
                        tokens[0] = UUID.randomUUID().toString();
                        tokens[1] = splittedLine[0];
                        tokens[2] = splittedLine[1];
                        tokens[3] = splittedLine[2];
                        tokens[4] = splittedLine[3];
                        tokens[5] = splittedLine[4];
                        tokens[6] = splittedLine.length != 6 ? "This country does't have neighbours" : splittedLine[5];
                        return new Country(tokens[0], tokens[1], tokens[2], Long.parseLong(tokens[3]), tokens[4], tokens[5], tokens[6]);
                    })
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
