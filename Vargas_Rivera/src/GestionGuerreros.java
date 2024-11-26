import java.util.LinkedList;

public class GestionGuerreros {
    private LinkedList<GuerreroStarWars> listaGuerreros;

    public GestionGuerreros() {
        listaGuerreros = new LinkedList<>();
        listaGuerreros.add(new GuerreroStarWars(1, "Luke Skywalker", "Jedi", "Tatooine", 9));
        listaGuerreros.add(new GuerreroStarWars(2, "Darth Vader", "Sith", "Tatooine", 10));
        listaGuerreros.add(new GuerreroStarWars(3, "Han Solo", "Mercenario", "Corellia", 7));
        listaGuerreros.add(new GuerreroStarWars(4, "Leia Organa", "Rebelde", "Alderaan", 8));
        listaGuerreros.add(new GuerreroStarWars(5, "Emperador Palpatine", "Imperial", "Naboo", 10));
    }

    public LinkedList<GuerreroStarWars> obtenerLista() {
        return listaGuerreros;
    }

    public boolean agregarGuerrero(GuerreroStarWars guerrero) {
        for (GuerreroStarWars g : listaGuerreros) {
            if (g.getCodigo() == guerrero.getCodigo()) {
                return false;
            }
        }
        listaGuerreros.addFirst(guerrero);
        return true;
    }

    public GuerreroStarWars buscarGuerrero(int codigo) {
        for (GuerreroStarWars g : listaGuerreros) {
            if (g.getCodigo() == codigo) {
                return g;
            }
        }
        return null;
    }

    public LinkedList<GuerreroStarWars> filtrarPorAfiliacion(String afiliacion) {
        LinkedList<GuerreroStarWars> filtrados = new LinkedList<>();
        for (GuerreroStarWars g : listaGuerreros) {
            if (!g.getAfiliacion().equals(afiliacion)) {
                filtrados.add(g);
            }
        }
        return filtrados;
    }

    public LinkedList<GuerreroStarWars> ordenarPorNivelFuerza(LinkedList<GuerreroStarWars> lista) {
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - i - 1; j++) {
                if (lista.get(j).getNivelFuerza() > lista.get(j + 1).getNivelFuerza()) {
                    GuerreroStarWars temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
        return lista;
    }

    public String contarPorPlaneta(LinkedList<GuerreroStarWars> lista) {
        return contarRecursivamente(lista, new LinkedList<>());
    }

    private String contarRecursivamente(LinkedList<GuerreroStarWars> lista, LinkedList<String> procesados) {
        if (lista.isEmpty()) {
            return "";
        }
        GuerreroStarWars actual = lista.removeFirst();
        String planeta = actual.getPlanetaOrigen();

        if (procesados.contains(planeta)) {
            return contarRecursivamente(lista, procesados);
        }

        int count = 1;
        for (GuerreroStarWars g : lista) {
            if (g.getPlanetaOrigen().equals(planeta)) {
                count++;
            }
        }
        procesados.add(planeta);
        return planeta + ": " + count + "\n" + contarRecursivamente(lista, procesados);
    }
}
