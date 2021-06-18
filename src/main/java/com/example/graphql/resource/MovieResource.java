package com.example.graphql.resource;

import com.example.graphql.service.MovieGraphQlService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/movies")
@RestController
public class MovieResource {

  @Autowired
  MovieGraphQlService movieGraphQlService;

  @PostMapping
  public ResponseEntity<Object> getAllMovies(@RequestBody String query){
    ExecutionResult result = movieGraphQlService.getMovieGraphQl().execute(query);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }


}
