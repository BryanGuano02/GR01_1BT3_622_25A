package servicios;

public class CalificacionResumen {
    private final int likes;
    private final int dislikes;

    public CalificacionResumen(int likes, int dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }
}
