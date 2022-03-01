package Actions

import Models.{Movie, MoviesResult}

import scala.util.{Failure, Success, Try}

object QueriesActions {

  def getMovieTitleByDirectorNameAndYearRange(moviesList: List[Movie], directorName: String, startRange: Int, endRange: Int): Try[List[MoviesResult]] = {
    val movieTitles: List[MoviesResult] = for {
      movieRow <- moviesList if (movieRow.director == directorName && movieRow.publishedYear.toInt >= startRange && movieRow.publishedYear.toInt <= endRange)
    } yield convertToMovie(movieRow)
    if (movieTitles.isEmpty) {
      Failure(new RuntimeException("No Records found"))
    } else Success(movieTitles)
  }

  def getMovieTitleFilterByReviewsCount(moviesList: List[Movie], userReview: Int): List[MoviesResult] = {
    val movieList = for {
      movieRow <- moviesList if (movieRow.userReviews != "" && movieRow.language != "" && movieRow.userReviews.toInt > userReview && movieRow.language.contains("English"))
    } yield convertToMovie(movieRow)
    if (movieList.isEmpty)
      throw new RuntimeException("No Records found")
    implicit val sortListBasedOnUserReviews:Ordering[MoviesResult] = Ordering.fromLessThan((a,b)=>a.userReviews.toInt > b.userReviews.toInt)
    movieList.sorted
  }

  def getMovieForLongestDurationFilterByCountryAndVotes(moviesList: List[Movie], country: String, noOfVotes: Int): List[MoviesResult] = {
    val movieList = for {
      movieRow <- moviesList if (movieRow.country != "" && movieRow.votes != "" && movieRow.country == country && movieRow.votes.toInt >= noOfVotes)
    } yield convertToMovie(movieRow)
    if (movieList.isEmpty)
      throw new RuntimeException("No Records found")
    implicit val sortListBasedOnDuration:Ordering[MoviesResult] = Ordering.fromLessThan((a,b)=>a.duration.toInt > b.duration.toInt)
    movieList.sorted
  }

  def convertToMovie (movie: Movie): MoviesResult = {
    new MoviesResult(movie.title,movie.publishedYear, movie.budget, movie.userReviews, movie.country,movie.genre, movie.duration)
  }

}