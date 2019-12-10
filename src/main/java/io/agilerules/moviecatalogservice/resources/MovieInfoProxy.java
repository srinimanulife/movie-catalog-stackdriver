package io.agilerules.moviecatalogservice.resources;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.agilerules.moviecatalogservice.models.Movie;

@FeignClient(name = "movie-info", url = "${MOVIE_INFO_URI:http://localhost}:8082")//http://movie-info
//@FeignClient(name = "movie-info", url = "${MOVIE_INFO_SERVICE_HOST:http://localhost}:8082")
//@FeignClient(name = "movie-info-service")//Kubernetes Service Name
	public interface MovieInfoProxy {
		@GetMapping("/movies/{movieid}")
		public Movie retrieveMovieInfoValue(@PathVariable("movieid") String movieid);
	}

