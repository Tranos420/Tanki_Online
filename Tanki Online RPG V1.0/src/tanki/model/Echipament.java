package tanki.model;

/**
 * Clasă abstractă de bază pentru toate echipamentele (turete, hull-uri).
 * Conține atribute și metode comune.
 */
public abstract class Echipament {

    protected String nume; // comun tuturor echipamentelor

    public Echipament(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }
    // metodă abstractă ce obligă subclasele să ofere detalii proprii
    public abstract String getInfo();
}
