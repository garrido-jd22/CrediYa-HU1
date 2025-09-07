package co.com.bancolombia.model.rol;

public enum UserRole {
    ADMIN(1L, "ADMIN"),
    ASESOR(2L, "ASESOR"),
    SOLICITANTE(3L, "SOLICITANTE");

    private final Long id;
    private final String name;

    UserRole(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static UserRole fromId(Long id) {
        for (UserRole role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role id: " + id);
    }
}
