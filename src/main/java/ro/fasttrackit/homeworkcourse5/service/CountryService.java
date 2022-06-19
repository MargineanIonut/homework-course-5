package ro.fasttrackit.homeworkcourse5.service;

import net.bytebuddy.utility.nullability.AlwaysNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homeworkcourse5.model.Country;
import ro.fasttrackit.homeworkcourse5.repository.CountryRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@Service
public class CountryService {
    private final CountryProviderService countryProviderService;
    private final CountryRepository countryRepository;
    private final List<Country> countryList;

    @Autowired
    ConfigurableEnvironment configurableEnvironment;
    public CountryService(CountryProviderService countryProviderService,
                          CountryRepository countryRepository, List<Country> countryList) {
        this.countryProviderService = countryProviderService;
        this.countryRepository = countryRepository;
        this.countryList = loadCountries();
    }

    public List<Country> loadCountries(){

        List<Country> listOfCountry = new ArrayList<>();

         if (configurableEnvironment.getActiveProfiles().equals("test"))
               listOfCountry = countryProviderService.readCountries();
           else
               listOfCountry = countryRepository.findAll();

        return listOfCountry;
    }


    public List<Country> countries(String includeNeighbour, String excludedNeighbour) {
        countryRepository.saveAll(countryProviderService.readCountries());
        return countryList.stream()
                .filter(country -> country.getNeighbours().contains(includeNeighbour))
                .filter(country -> !country.getNeighbours().contains(excludedNeighbour))
                .toList();
    }

    public List<Country> countries() {
        return countryList;
    }

    public List<String> countryNames() {
        return countryList.stream()
                .map(Country::getName)
                .toList();
    }

    public Optional<String> countryCapital(String countryId) {
        Optional<Country> country = countryList.stream()
                .filter(x -> Objects.equals(x.getId(), Integer.valueOf(countryId)))
                .findFirst();
        return Optional.ofNullable(country.isPresent() ? country.get().getCapital() : "");
    }

    public Long countryPopulation(String countryId) {
        Optional<Country> country = countryList.stream()
                .filter(x -> Objects.equals(x.getId(), Integer.valueOf(countryId)))
                .findFirst();
        return (country.isPresent() ? country.get().getPopulation() : 0L);
    }

    public List<Country> countryContinentList(String continent) {
        return countryList.stream()
                .filter(x -> x.getContinent().equals(continent))
                .toList();
    }

    public List<String> countryNeighboursList(String countryId) {
        List<Country> country = countryList.stream()
                .filter(x -> Objects.equals(x.getId(), Integer.valueOf(countryId)))
                .toList();
        return country.stream()
                .map(Country::getNeighbours)
                .collect(Collectors.toList());
    }

    public List<Country> countryPopulationFilter(String continent, Long minPopulation) {
        return countryList.stream()
                .filter(country -> country.getContinent().equals(continent) &&
                        country.getPopulation() >= minPopulation)
                .toList();
    }


    public Map<String, Long> countryPopulationMap() {
        return countryList.stream()
                .collect(Collectors.toMap(Country::getName,
                        Country::getPopulation));
    }

    public Map<String, List<Country>> continentCountryMap() {
                return countryList.stream()
                        .collect(Collectors.groupingBy(Country::getContinent));
    }
}
