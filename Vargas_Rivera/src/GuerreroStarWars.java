public class GuerreroStarWars {
    private int codigo;
    private String nombre;
    private String afiliacion;
    private String planetaOrigen;
    private int nivelFuerza;

    public GuerreroStarWars(int codigo, String nombre, String afiliacion, String planetaOrigen, int nivelFuerza) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.afiliacion = afiliacion;
        this.planetaOrigen = planetaOrigen;
        this.nivelFuerza = nivelFuerza;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getAfiliacion() { return afiliacion; }
    public void setAfiliacion(String afiliacion) { this.afiliacion = afiliacion; }
    public String getPlanetaOrigen() { return planetaOrigen; }
    public void setPlanetaOrigen(String planetaOrigen) { this.planetaOrigen = planetaOrigen; }
    public int getNivelFuerza() { return nivelFuerza; }
    public void setNivelFuerza(int nivelFuerza) { this.nivelFuerza = nivelFuerza; }
}
