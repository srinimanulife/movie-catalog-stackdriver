package io.agilerules.moviecatalogservice.resources;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.agilerules.moviecatalogservice.models.Movie;
import io.agilerules.moviecatalogservice.models.UserRating;

@FeignClient(name = "movie-rating", url = "${MOVIE_RATING_URI:http://localhost}:8083")//http://movie-rating
//@FeignClient(name = "movie-rating", url = "${MOVIE_RATING_SERVICE_HOST:http://localhost}:8083")
//@FeignClient(name = "movie-rating-service")//Kubernetes Service Name
	public interface MovieRatingProxy {
		@GetMapping("/ratingsdata/user/{userid}")
		public UserRating retrieveMovieRatingValue(@PathVariable("userid") String userid);
	}

