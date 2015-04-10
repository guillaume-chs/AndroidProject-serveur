package socket.serveur;

/**
 * Liste de clients autorisés. En quelque sorte une "whitelist".
 */
public enum Client {
    GUILLAUME("Guillaume", 18),
    Jean("Jean", 24);

    /**
     * Taille minimale du pseudo.
     */
    public static final int PSEUDO_MIN_SIZE = 3;
    /**
     * Taille maximale du pseudo.
     */
    public static final int PSEUDO_MAX_SIZE = 15;

    public static final int AGE_MIN = 0;
    public static final int AGE_MAX = 120;

    // Champs spécifiques à chaque Client
    private final String pseudo;
    private final int age;

    /**
     * Créé un client. Chaque client a un pseudo et un âge.
     *
     * @param pseudo le pseudo du client
     * @param age    l'âge du client
     * @throws NullPointerException     si le pseudo est null
     * @throws IllegalArgumentException si le pseudo a une longueur invalide
     * @throws IllegalArgumentException si l'âge est invalide
     */
    Client(final String pseudo, final int age) {
        if (pseudo == null) {
            throw new NullPointerException("Le pseudo doit être précisé");
        }
        if (pseudo.length() < PSEUDO_MIN_SIZE || pseudo.length() > PSEUDO_MAX_SIZE) {
            throw new IllegalArgumentException("Le pseudo doit avoir une longueur comprise entre " + PSEUDO_MIN_SIZE + " et " + PSEUDO_MAX_SIZE);
        }
        if (age <= AGE_MIN || age > AGE_MAX) {
            throw new IllegalArgumentException("L'âge doit être compris entre " + AGE_MIN + " et " + AGE_MAX);
        }

        this.pseudo = pseudo;
        this.age = age;
    }
}
