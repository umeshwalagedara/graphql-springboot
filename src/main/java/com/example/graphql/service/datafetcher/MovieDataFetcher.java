package com.example.graphql.service.datafetcher;

import com.example.graphql.entity.Movie;
import com.example.graphql.repo.MovieRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieDataFetcher implements DataFetcher {

  @Autowired
  MovieRepository movieRepository;

  @Override
  public Movie get(DataFetchingEnvironment dataFetchingEnvironment) {
    int id = dataFetchingEnvironment.getArgument("id");
    return  movieRepository.findById(id).get();
  }

}
