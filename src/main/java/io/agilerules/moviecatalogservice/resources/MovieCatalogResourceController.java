package io.agilerules.moviecatalogservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import io.agilerules.moviecatalogservice.models.CatalogItem;
import io.agilerules.moviecatalogservice.models.Movie;
import io.agilerules.moviecatalogservice.models.Rating;
import io.agilerules.moviecatalogservice.models.UserRating;
import io.agilerules.ratingsdataservice.util.environment.InstanceInformationService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResourceController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
	private InstanceInformationService containerMetaDataService;
    
    @Autowired
	private MovieInfoProxy movieInfoProxy;
    
    @Autowired
   	private MovieRatingProxy movieRatingProxy;
    

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

      //  UserRating userRating = restTemplate.getForObject("http://movie-rating-service/ratingsdata/user/" + userId, UserRating.class);
    //	UserRating userRating = restTemplate.getForObject("http://moviecatalogservice.fzvceifgg3.us-east-1.elasticbeanstalk.com:8083/ratingsdata/user/" + userId, UserRating.class);
    //	UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/user/" + userId, UserRating.class);
    //	UserRating userRating = restTemplate.getForObject(ratingsHost + "/ratingsdata/user/" + userId, UserRating.class);
    	UserRating userRating = 	movieRatingProxy.retrieveMovieRatingValue(userId);

        return userRating.getRatings().stream()
                .map(rating -> {
                   // Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                  //  Movie movie = restTemplate.getForObject("http://moviecatalogservice.fzvceifgg3.us-east-1.elasticbeanstalk.com:8082/movies/" + rating.getMovieId(), Movie.class);
                //    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            //    	 Movie movie = restTemplate.getForObject(moviesHost + "/movies/" + rating.getMovieId(), Movie.class);
                	 
                	 Movie movie = movieInfoProxy.retrieveMovieInfoValue(rating.getMovieId());
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating(), containerMetaDataService.retrieveInstanceInfo());
                })
                .collect(Collectors.toList());

    }
}

/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/