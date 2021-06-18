package com.example.graphql.service;

import com.example.graphql.entity.Movie;
import com.example.graphql.repo.MovieRepository;
import com.example.graphql.service.datafetcher.AllMovieDataFetcher;
import com.example.graphql.service.datafetcher.MovieDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class MovieGraphQlService {

  @Autowired
  MovieRepository movieRepository;

  @Value("classpath:movies.graphql")
  Resource resource;

  private GraphQL graphQL;

  @Autowired
  private AllMovieDataFetcher allMovieDataFetcher;

  @Autowired
  private MovieDataFetcher movieDataFetcher;

  @PostConstruct
  private void loadSchema() throws IOException {

    // load data to db
    loadDataIntoHSQL();

    File schemaFile = resource.getFile();
    TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);

    RuntimeWiring wiring = buildRuntimeWiring();
    GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    graphQL = GraphQL.newGraphQL(schema).build();

  }

  private RuntimeWiring buildRuntimeWiring() {
    return RuntimeWiring.newRuntimeWiring()
            .type("Query", typeWiring -> typeWiring
                    .dataFetcher("allMovies", allMovieDataFetcher)
                    .dataFetcher("movie", movieDataFetcher))
            .build();
  }

  public GraphQL getMovieGraphQl(){
    return graphQL;
  }

  private void loadDataIntoHSQL() {

    Stream.of(
            new Movie(1, "Lupin", "Kindle Edition",
                    new String[] {
                            "Chloe Aridjis"
                    }, "1"),
            new Movie(2, "Money Heiest", "Orielly",
                    new String[] {
                            "Peter", "Sam"
                    }, "2"),
            new Movie(3, "Java 9 Programming", "Orielly",
                    new String[] {
                            "Venkat", "Ram"
                    }, "4")
    ).forEach(book -> {
      movieRepository.save(book);
    });
  }


}
