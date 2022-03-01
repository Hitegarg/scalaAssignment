import scala.util.Try

object Main extends App {
  val fileName: String = "imdb_movies.csv";
  val movies: List[Movie] = FileReader.getMovieDetailsFromList(fileName)

  print("Please provide the director name : ")
  val directorName: String = scala.io.StdIn.readLine
  println("Please provide the range for start year :")
  val startYear: Int = scala.io.StdIn.readLine.toInt
  println("Please provide the range for end year :")
  val endYear: Int = scala.io.StdIn.readLine.toInt
  val listOfMovie: Try[List[MoviesResult]] = QueriesActions.getMovieTitleByDirectorNameAndYearRange(movies, directorName, startYear, endYear)
  if (listOfMovie.isSuccess)
    listOfMovie.map(_.foreach(println))
  else
    throw new RuntimeException

  print("Please provide the no. of reviews : ")
  val reviewsCount: Int = scala.io.StdIn.readLine.toInt
  try {
    val listOfMovie = QueriesActions.getMovieTitleFilterByReviewsCount(movies, reviewsCount)
    listOfMovie.foreach(m => println(m))
  }
  catch {
    case r: RuntimeException => println(r.getMessage)
    case _ => println("Error!! Please try again")
  }

  print("Please provide the country name : ")
  val newCountry: String = scala.io.StdIn.readLine()
  print("Please provide the minimum votes : ")
  val minVotes: Int = scala.io.StdIn.readLine().toInt
  try {
    val listOfMovie = QueriesActions.getMovieForLongestDurationFilterByCountryAndVotes(movies, newCountry, minVotes)
    listOfMovie.foreach(m => println(m))
  }
  catch {
    case r: RuntimeException => println(r.getMessage)
    case _ => println("Error!! Please try again")
  }
}

