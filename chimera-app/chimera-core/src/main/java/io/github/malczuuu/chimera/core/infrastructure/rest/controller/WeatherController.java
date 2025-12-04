package io.github.malczuuu.chimera.core.infrastructure.rest.controller;

import io.github.malczuuu.chimera.core.common.model.Content;
import io.github.malczuuu.chimera.core.common.model.weather.WeatherModel;
import io.github.malczuuu.chimera.core.domain.weather.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/weather")
public class WeatherController {

  private final WeatherService weatherService;

  public WeatherController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Content<WeatherModel>> getWeather(
      @RequestParam(name = "city") String city, HttpServletRequest request) {
    Content<WeatherModel> weatherContent = weatherService.getWeather(city);
    return ResponseEntity.ok(weatherContent);
  }
}
