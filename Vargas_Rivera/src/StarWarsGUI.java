import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

public class StarWarsGUI extends JFrame {
    private GestionGuerreros gestionGuerreros;
    private JTable tablaGuerreros;

    public StarWarsGUI() {
        gestionGuerreros = new GestionGuerreros();
        setTitle("Gestión de Guerreros de Star Wars");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("Estructura y Registro", crearTabRegistro());
        tabbedPane.add("Búsqueda", crearTabBusqueda());
        tabbedPane.add("Filtrado y Ordenamiento", crearTabFiltrado());
        tabbedPane.add("Agrupación por Planeta", crearTabAgrupacion());

        add(tabbedPane);
    }

    private JPanel crearTabRegistro() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2));
        JTextField txtCodigo = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtPlaneta = new JTextField();
        JComboBox<String> comboAfiliacion = new JComboBox<>(new String[]{"Jedi", "Sith", "Mercenario", "Rebelde", "Imperial"});
        JComboBox<String> comboNivel = new JComboBox<>();
        for (int i = 1; i <= 10; i++) comboNivel.addItem(String.valueOf(i));

        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(txtCodigo);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Afiliación:"));
        panelFormulario.add(comboAfiliacion);
        panelFormulario.add(new JLabel("Planeta:"));
        panelFormulario.add(txtPlaneta);
        panelFormulario.add(new JLabel("Nivel de Fuerza:"));
        panelFormulario.add(comboNivel);

        JButton btnAgregar = new JButton("Agregar Guerrero");
        btnAgregar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText());
                String nombre = txtNombre.getText();
                String afiliacion = (String) comboAfiliacion.getSelectedItem();
                String planeta = txtPlaneta.getText();
                int nivelFuerza = Integer.parseInt((String) comboNivel.getSelectedItem());
                GuerreroStarWars guerrero = new GuerreroStarWars(codigo, nombre, afiliacion, planeta, nivelFuerza);

                if (gestionGuerreros.agregarGuerrero(guerrero)) {
                    actualizarTabla(tablaGuerreros, gestionGuerreros.obtenerLista());
                } else {
                    JOptionPane.showMessageDialog(this, "Código ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(btnAgregar, BorderLayout.CENTER);
        tablaGuerreros = new JTable();
        actualizarTabla(tablaGuerreros, gestionGuerreros.obtenerLista());
        panel.add(new JScrollPane(tablaGuerreros), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearTabBusqueda() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior: Formulario de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout());
        JTextField txtCodigoBusqueda = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Código:"));
        panelBusqueda.add(txtCodigoBusqueda);
        panelBusqueda.add(btnBuscar);

        // Panel central: Detalles del guerrero
        JPanel panelDetalles = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField txtNombre = new JTextField();
        JTextField txtAfiliacion = new JTextField();
        JTextField txtPlaneta = new JTextField();
        JTextField txtNivelFuerza = new JTextField();
        txtNombre.setEditable(false);
        txtAfiliacion.setEditable(false);
        txtPlaneta.setEditable(false);
        txtNivelFuerza.setEditable(false);

        panelDetalles.add(new JLabel("Nombre:"));
        panelDetalles.add(txtNombre);
        panelDetalles.add(new JLabel("Afiliación:"));
        panelDetalles.add(txtAfiliacion);
        panelDetalles.add(new JLabel("Planeta de Origen:"));
        panelDetalles.add(txtPlaneta);
        panelDetalles.add(new JLabel("Nivel de Fuerza:"));
        panelDetalles.add(txtNivelFuerza);

        // Acción del botón Buscar
        btnBuscar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigoBusqueda.getText());
                GuerreroStarWars guerrero = gestionGuerreros.buscarGuerrero(codigo);
                if (guerrero != null) {
                    txtNombre.setText(guerrero.getNombre());
                    txtAfiliacion.setText(guerrero.getAfiliacion());
                    txtPlaneta.setText(guerrero.getPlanetaOrigen());
                    txtNivelFuerza.setText(String.valueOf(guerrero.getNivelFuerza()));
                } else {
                    JOptionPane.showMessageDialog(this, "Guerrero no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un código numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(panelDetalles, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearTabFiltrado() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior: Selección de afiliación
        JPanel panelFiltro = new JPanel(new FlowLayout());
        JComboBox<String> comboAfiliacion = new JComboBox<>(new String[]{"Jedi", "Sith", "Mercenario", "Rebelde", "Imperial"});
        JButton btnFiltrar = new JButton("Filtrar y Ordenar");
        panelFiltro.add(new JLabel("Afiliación:"));
        panelFiltro.add(comboAfiliacion);
        panelFiltro.add(btnFiltrar);

        // Panel inferior: Tabla para mostrar resultados
        JTable tablaFiltrada = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tablaFiltrada);

        // Acción del botón Filtrar y Ordenar
        btnFiltrar.addActionListener(e -> {
            String afiliacionSeleccionada = (String) comboAfiliacion.getSelectedItem();
            LinkedList<GuerreroStarWars> filtrados = gestionGuerreros.filtrarPorAfiliacion(afiliacionSeleccionada);
            filtrados = gestionGuerreros.ordenarPorNivelFuerza(filtrados);
            actualizarTabla(tablaFiltrada, filtrados);
        });

        panel.add(panelFiltro, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);

        return panel;
    }


    private JPanel crearTabAgrupacion() {
        JPanel panel = new JPanel(new BorderLayout());

        // Botón para iniciar agrupación
        JButton btnAgrupar = new JButton("Agrupar por Planeta");
        JTextArea txtResultadoAgrupacion = new JTextArea();
        txtResultadoAgrupacion.setEditable(false);
        JScrollPane scrollResultado = new JScrollPane(txtResultadoAgrupacion);

        // Acción del botón Agrupar
        btnAgrupar.addActionListener(e -> {
            LinkedList<GuerreroStarWars> copiaLista = new LinkedList<>(gestionGuerreros.obtenerLista());
            String resultado = gestionGuerreros.contarPorPlaneta(copiaLista);
            txtResultadoAgrupacion.setText(resultado);
        });

        panel.add(btnAgrupar, BorderLayout.NORTH);
        panel.add(scrollResultado, BorderLayout.CENTER);

        return panel;
    }


    private void actualizarTabla(JTable tabla, LinkedList<GuerreroStarWars> lista) {
        String[] columnas = {"Código", "Nombre", "Afiliación", "Planeta", "Nivel"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        for (GuerreroStarWars g : lista) {
            modelo.addRow(new Object[]{g.getCodigo(), g.getNombre(), g.getAfiliacion(), g.getPlanetaOrigen(), g.getNivelFuerza()});
        }
        tabla.setModel(modelo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StarWarsGUI().setVisible(true));
    }
}
