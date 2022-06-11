package ro.fasttrackit.homeworkcourse5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> countries(@RequestParam(required = false) String includeNeighbour,
                                                   @RequestParam(required = false) String excludedNeighbour){
        return new ResponseEntity<>(countryService.countries(includeNeighbour,excludedNeighbour), HttpStatus.OK);
    }

    @GetMapping("/countries/names")
    public ResponseEntity<List<String>> countryNames(){
        return new ResponseEntity<>(countryService.countryNames(), HttpStatus.OK);
    }

    @GetMapping("/countries/{countryId}/capital")
    public ResponseEntity<Optional<String>> countryCapital(@PathVariable("countryId") String id){
        return new ResponseEntity<>(countryService.countryCapital(id), HttpStatus.OK);
    }

    @GetMapping("/countries/{countryId}/population")
    public ResponseEntity<Long> countryPopulation(@PathVariable("countryId") String id){
        return new ResponseEntity<>(countryService.countryPopulation(id), HttpStatus.OK);
    }

    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<Country>> countryContinentList(@PathVariable("continent") String continent){
        return new ResponseEntity<>(countryService.countryContinentList(continent), HttpStatus.OK);
    }

    @GetMapping("/countries/{countryId}/neighbours")
    public ResponseEntity<List<String>> countryNeighbourList(@PathVariable("countryId") String countryId){
        return new ResponseEntity<>(countryService.countryNeighboursList(countryId), HttpStatus.OK);
    }

    @GetMapping("/continents/{continent}/countries")
    public ResponseEntity<List<Country>> coountryPopulationFilter(@PathVariable("continent") String continent, @RequestParam(required = false) Long minPopulation){
        return new ResponseEntity<>(countryService.countryPopulationFilter(continent,minPopulation), HttpStatus.OK);
    }

    @GetMapping("countries/population")
    public ResponseEntity<Map<String,Long>> coountryPopulationMap(){
        return new ResponseEntity<>(countryService.countryPopulationMap(), HttpStatus.OK);
    }

    @GetMapping("continents/countries")
    public ResponseEntity<Map<String,List<Country>>> continentCountryMap(){
        return new ResponseEntity<>(countryService.continentCountryMap(), HttpStatus.OK);
    }
}
