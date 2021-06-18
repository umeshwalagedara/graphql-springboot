package com.example.graphql.service.datafetcher;

import com.example.graphql.entity.Movie;
import com.example.graphql.repo.MovieRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AllMovieDataFetcher implements DataFetcher<List<Movie>> {

  @Autowired
  MovieRepository movieRepository;

  @Override
  public List<Movie> get(DataFetchingEnvironment dataFetchingEnvironment) {
     List<Movie> movieList = new ArrayList<Movie>();
     movieRepository.findAll().forEach(movieList::add);
     return  movieList;
  }

}
