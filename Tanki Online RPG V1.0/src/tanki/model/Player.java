package tanki.model;



/**
 * Clasa Player reprezintă un jucător al jocului.
 * Stochează numele, tancul ales, numărul de victorii/înfrângeri și rank-ul curent.
 */
public class Player {
    private String nume;
    private int victorii;
    private int infrangeri;
    private int rank;
    private Tank tank;

    public Player(String nume, Tank tank) {
        this.nume = nume;
        this.tank = tank;
        this.victorii = 0;
        this.infrangeri = 0;
        this.rank = 1;
    }

    // --- Getteri / Setteri ---
    public String getNume() {
        return nume;
    }


    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public void adaugaVictorie() {
        victorii++;
        actualizeazaRank();
    }

    public void adaugaInfrangere() {
        infrangeri++;
        actualizeazaRank();
    }

    private void actualizeazaRank() {
        // Rank simplu: 1 + (victorii / 3)
        this.rank = 1 + (victorii / 3);
    }

    @Override
    public String toString() {
        return nume + ";" + rank + ";" + victorii + ";" + infrangeri + ";" +
                tank.getTureta().getNume() + ";" + tank.getHull().getNume();
    }

    public static Player fromString(String line, Tureta[] turete, Hull[] hulluri) {
        // Format: nume;rank;victorii;infrangeri;tureta;hull
        String[] parts = line.split(";");
        if (parts.length < 6) return null;

        String nume = parts[0];
        int rank = Integer.parseInt(parts[1]);
        int victorii = Integer.parseInt(parts[2]);
        int infrangeri = Integer.parseInt(parts[3]);
        String numeTureta = parts[4];
        String numeHull = parts[5];

        Tureta tureta = null;
        Hull hull = null;

        for (Tureta t : turete)
            if (t.getNume().equalsIgnoreCase(numeTureta.trim())) tureta = t;

        for (Hull h : hulluri)
            if (h.getNume().equalsIgnoreCase(numeHull.trim())) hull = h;

        if (tureta == null || hull == null) return null;

        Player p = new Player(nume, new Tank(nume, tureta, hull));
        p.rank = rank;
        p.victorii = victorii;
        p.infrangeri = infrangeri;
        return p;
    }

    public void afiseazaProfil() {
        System.out.println("|--- PROFIL JUCĂTOR ---|");
        System.out.println("Nume: " + nume);
        System.out.println("Rank: " + rank);
        System.out.println("Victorii: " + victorii);
        System.out.println("Înfrângeri: " + infrangeri);
        System.out.println("Tank: " + tank.getTureta().getNume() + " / " + tank.getHull().getNume());
        System.out.println("|----------------------|");
    }
}
