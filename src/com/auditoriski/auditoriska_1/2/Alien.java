package auditoriski.auditoriska_1;
class Alien {
    private static final int SNAKE_ALIEN = 0;
    private static final int OGRE_ALIEN = 1;
    public static final int MARSHMALLOW_MAN_ALIEN = 2;
    private int type; // Stores one of the three above types
    private int health; // 0=dead, 100=full strength
    private String name;

    public Alien(int type, int health, String name) {
        this.type = type;
        this.health = health;
        this.name = name;
    }
}
