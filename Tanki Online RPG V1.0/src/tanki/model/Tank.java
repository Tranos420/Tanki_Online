package tanki.model;

/**
 * Clasa Tank combină o Tureta și un Hull pentru a forma un tanc complet.
 * Include metode pentru afișarea detaliilor și gestionarea HP-ului curent.
 */
public class Tank {
    private String nume;
    private Tureta tureta;
    private Hull hull;
    private int hpCurent;

    public Tank(String nume, Tureta tureta, Hull hull) {
        this.nume = nume;
        this.tureta = tureta;
        this.hull = hull;
        this.hpCurent = hull.getHP();
    }

    public String getNume() {
        return nume;
    }

    public Tureta getTureta() {
        return tureta;
    }

    public Hull getHull() {
        return hull;
    }

    public int getHpCurent() {
        return hpCurent;
    }

    public void setHpCurent(int hpCurent) {
        this.hpCurent = hpCurent;
    }

    public boolean esteDistrus() {
        return hpCurent <= 0;
    }

    public void afiseazaDetalii() {
        System.out.println("|---| DETALII TANK |---|");
        System.out.println("Nume: " + nume);
        System.out.println("Tureta: " + tureta.getNume());
        System.out.println("Hull: " + hull.getNume());
        System.out.println("HP curent: " + hpCurent);
        System.out.println("|---------------|");
    }

    @Override
    public String toString() {
        return "Tank{" +
                "nume='" + nume + '\'' +
                ", tureta=" + tureta.getNume() +
                ", hull=" + hull.getNume() +
                ", hpCurent=" + hpCurent +
                '}';
    }
}
