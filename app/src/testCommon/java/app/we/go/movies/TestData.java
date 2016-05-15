package app.we.go.movies;

/**
 * Created by Aristides Papadopoulos (github:talosdev).
 */
public class TestData {


    public static final String MOVIE_JSON ="{\"adult\":false,\"backdrop_path\":\"/wVTYlkKPKrljJfugXN7UlLNjtuJ.jpg\",\"genre_ids\":[28,12,80],\"id\":206647,\"original_language\":\"en\",\"original_title\":\"Spectre\",\"overview\":\"A cryptic message from Bond’s past sends him on a trail to uncover a sinister organization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.\",\"release_date\":\"2015-10-26\",\"poster_path\":\"/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg\",\"popularity\":57.231904,\"title\":\"Spectre\",\"video\":false,\"vote_average\":6.7,\"vote_count\":453}";

    /**
     * The id of the movie "Spectre"
     */
    public static final long MOVIE_ID = 206647;
    public static final String MOVIE_TITLE = "Spectre";
    public static final String MOVIE_OVERVIEW = "A cryptic message from Bond’s past sends him on a trail to uncover a sinister organization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.";
    public static final String MOVIE_RELEASE_DATE  = "2015-10-26";
    public static final String MOVIE_POSTER_PATH = "/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg";

    public static final String MOVIE_JSON_EMPTY_DATE ="{\"adult\":false,\"backdrop_path\":\"/wVTYlkKPKrljJfugXN7UlLNjtuJ.jpg\",\"genre_ids\":[28,12,80],\"id\":206647,\"original_language\":\"en\",\"original_title\":\"Spectre\",\"overview\":\"A cryptic message from Bond’s past sends him on a trail to uncover a sinister organization. While M battles political forces to keep the secret service alive, Bond peels back the layers of deceit to reveal the terrible truth behind SPECTRE.\",\"release_date\":\"\",\"poster_path\":\"/1n9D32o30XOHMdMWuIT4AaA5ruI.jpg\",\"popularity\":57.231904,\"title\":\"Spectre\",\"video\":false,\"vote_average\":6.7,\"vote_count\":453}";
    public static final long MOVIE_ID_INEXISTENT = 0;


    public class VideoData {
        public static final long MOVIE_ID = 122917L;

        public static final String KEY_1 = "ZSzeFFsKEt4";
        public static final String NAME_1 = "Official Teaser";
        public static final String TYPE_1 = "Teaser";

        public static final String KEY_2 = "Y6Fv5StfAxA";
        public static final String NAME_2 = "Main Trailer";
        public static final String TYPE_2 = "Trailer";
    }

    public class ReviewData {
        public static final String JSON = "{\"id\":293660,\"page\":1,\"results\":[{\"id\":\"56c146cac3a36817f900d5f0\",\"author\":\"huy.duc.eastagile\",\"content\":\"A funny movie with a romantic love story.\\r\\n\\r\\nWade Wilson (Ryan Reynolds) is a former Special Forces operative who now works as a mercenary. His world comes crashing down when evil scientist Ajax (Ed Skrein) tortures, disfigures and transforms him into Deadpool. \\r\\n\\r\\nThe rogue experiment leaves Deadpool with accelerated healing powers and a twisted sense of humor. With help from mutant allies Colossus and Negasonic Teenage Warhead (Brianna Hildebrand), Deadpool uses his new skills to hunt down the man who nearly destroyed his life.\",\"url\":\"http://j.mp/1ohFuvI\"},{\"id\":\"56ca035a9251414a7a0062f0\",\"author\":\"Wong\",\"content\":\"I actually enjoyed the movie so much that i'll recommend it to all my friends, at first i didn't really want to watch it because i'm not into super hero movies at all, but i did anyway, i mean people were talking so much about it i had to see it myself and what an awesome choice i made. The good thing about this movie is that Deadpool is a hero but in a very comedic way, you don't usually expect comedy from a superhero film but this one was full of comedy and the way they treated the plot was amazing, it was there, humor was there in every scene, even when there was fighting or romance or any other scene, the writers managed to add comedy everywhere in a very good way that'll surprisingly make you want to watch it again, and again. Thank you for taking the time read my review and if you're asking yourself if you should watch this movie, it's a definite Yes.\",\"url\":\"http://j.mp/1RVzjsp\"}],\"total_pages\":1,\"total_results\":2}";

        public static final long MOVIE_ID = 293660L;
        public static final String REVIEW_1 = "A funny movie with a romantic love story.\r\n\r\nWade Wilson (Ryan Reynolds) is a former Special Forces operative who now works as a mercenary. His world comes crashing down when evil scientist Ajax (Ed Skrein) tortures, disfigures and transforms him into Deadpool. \r\n\r\nThe rogue experiment leaves Deadpool with accelerated healing powers and a twisted sense of humor. With help from mutant allies Colossus and Negasonic Teenage Warhead (Brianna Hildebrand), Deadpool uses his new skills to hunt down the man who nearly destroyed his life.";
        public static final String REVIEW_2 = "I actually enjoyed the movie so much that i'll recommend it to all my friends, at first i didn't really want to watch it because i'm not into super hero movies at all, but i did anyway, i mean people were talking so much about it i had to see it myself and what an awesome choice i made. The good thing about this movie is that Deadpool is a hero but in a very comedic way, you don't usually expect comedy from a superhero film but this one was full of comedy and the way they treated the plot was amazing, it was there, humor was there in every scene, even when there was fighting or romance or any other scene, the writers managed to add comedy everywhere in a very good way that'll surprisingly make you want to watch it again, and again. Thank you for taking the time read my review and if you're asking yourself if you should watch this movie, it's a definite Yes.";
        public static final String AUTHOR_1 = "huy.duc.eastagile";
        public static final String AUTHOR_2 = "Wong";
    }
}