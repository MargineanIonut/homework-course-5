package ro.fasttrackit.homeworkcourse5.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.homeworkcourse5.model.Country;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private final CountryProviderService countryProviderService;
    private final List<Country> countryList;

    public CountryService(CountryProviderService countryProviderService, List<Country> countryList) {
        this.countryProviderService = countryProviderService;
        this.countryList = countryProviderService.readCountries();
    }

    public List<Country> countries(String includeNeighbour, String excludedNeighbour) {
        return countryList.stream()
                .filter(country -> country.neighbours().contains(includeNeighbour))
                .filter(country -> !country.neighbours().contains(excludedNeighbour))
                .toList();
    }

    public List<String> countryNames() {
        return countryList.stream()
                .map(Country::name)
                .toList();
    }

    public Optional<String> countryCapital(String countryId) {
        Optional<Country> country = countryList.stream()
                .filter(x -> x.id().equals(countryId))
                .findFirst();
        return Optional.ofNullable(country.isPresent() ? country.get().capital() : "");
    }

    public Long countryPopulation(String countryId) {
        Optional<Country> country = countryList.stream()
                .filter(x -> x.id().equals(countryId))
                .findFirst();
        return (country.isPresent() ? country.get().population() : 0L);
    }

    public List<Country> countryContinentList(String continent) {
        return countryList.stream()
                .filter(x -> x.continent().equals(continent))
                .toList();
    }

    public List<String> countryNeighboursList(String countryId) {
        List<Country> country = countryList.stream()
                .filter(x -> x.id().equals(countryId))
                .toList();
        return country.stream()
                .map(Country::neighbours)
                .collect(Collectors.toList());
    }

    public List<Country> countryPopulationFilter(String continent, Long minPopulation) {
        return countryList.stream()
                .filter(country -> country.continent().equals(continent) &&
                        country.population() >= minPopulation)
                .toList();
    }

    public List<Country> countryNeighboursFilter(String includeNeighbour, String excludeNeighbour) {
        return countryList.stream()
                .filter(country -> country.neighbours().contains(includeNeighbour))
                .toList();
    }

    public Map<String, Long> countryPopulationMap() {
        return countryList.stream()
                .collect(Collectors.toMap(Country::name,
                        Country::population));
    }

    public Map<String, List<Country>> continentCountryMap() {
                return countryList.stream()
                        .collect(Collectors.groupingBy(Country::continent));
    }
}
