package ro.fasttrackit.homeworkcourse5.model;

import java.util.function.Function;

public record Country(String id, String name, String capital, Long population,
                      String area, String continent, String neighbours) {

}
