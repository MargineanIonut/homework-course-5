package ro.fasttrackit.homeworkcourse5.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.homeworkcourse5.model.Country;
import ro.fasttrackit.homeworkcourse5.service.CountryService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/allCountries")
    public List<Country> listOfcountries(){
        return countryService.countries();
    }

    @GetMapping("/countries")
    public List<Country> countries(@RequestParam(required = false) String includeNeighbour,
                                   @RequestParam(required = false) String excludedNeighbour){
        return countryService.countries(includeNeighbour,excludedNeighbour);
    }

    @GetMapping("/countries/names")
    public List<String> countryNames(){
        return countryService.countryNames();
    }

    @GetMapping("/countries/{countryId}/capital")
    public Optional<String> countryCapital(@PathVariable("countryId") String id){
        return countryService.countryCapital(id);
    }

    @GetMapping("/countries/{countryId}/population")
    public Long countryPopulation(@PathVariable("countryId") String id){
        return countryService.countryPopulation(id);
    }

    @GetMapping("/countries/{continent}/continent")
    public List<Country> countryContinentList(@PathVariable("continent") String continent){
        return countryService.countryContinentList(continent);
    }

    @GetMapping("/countries/{countryId}/neighbours")
    public List<String> countryNeighbourList(@PathVariable("countryId") String countryId){
        return countryService.countryNeighboursList(countryId);
    }

    @GetMapping("/continents/{continent}/countries")
    public List<Country> coountryPopulationFilter(@PathVariable("continent") String continent, @RequestParam(required = false) Long minPopulation){
        return countryService.countryPopulationFilter(continent,minPopulation);
    }

    @GetMapping("countries/population")
    public Map<String,Long> coountryPopulationMap(){
        return countryService.countryPopulationMap();
    }

    @GetMapping("continents/countries")
    public Map<String,List<Country>> continentCountryMap(){
        return countryService.continentCountryMap();
    }
}
