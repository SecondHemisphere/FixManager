package vista;

import controlador.RecepcionEntregaController;
import java.awt.Color;
import java.awt.Image;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelo.RecepcionEntrega;
import util.DialogUtil;
import util.ResultadoOperacion;

/**
 * Panel de gestión CRUD de recepciones.
 *
 * @author Baque Diego
 */
public class RecepcionEntregaCRUDPanel extends javax.swing.JPanel {

    private final RecepcionEntregaController controlador = new RecepcionEntregaController();
    private int idRecepcion = 0;

    /**
     * Inicializa el panel de gestión de recepciones y entregas, configura los
     * componentes visuales, carga la tabla de datos y establece el fondo
     * personalizado del panel.
     */
    public RecepcionEntregaCRUDPanel() {
        initComponents();

        setOpaque(false);

        ImageIcon iconBuscar = new ImageIcon(getClass().getResource("/img/buscar.png"));
        Image imgBuscar = iconBuscar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnBuscar.setIcon(new ImageIcon(imgBuscar));

        pnlListado.setOpaque(false);
        pnlScroll.setOpaque(false);
        pnlScroll.getViewport().setOpaque(false);

        cargarTabla();

        JLabel fondo = new JLabel();

        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/fondo2.png"));
        Image imgFondo = imagen.getImage().getScaledInstance(897, 424, Image.SCALE_SMOOTH);

        fondo.setIcon(new ImageIcon(imgFondo));
        fondo.setBounds(0, 0, 897, 424);

        setLayout(null);

        pnlListado.setBounds(0, 0, 897, 424);

        add(fondo);
        add(pnlListado);

        setComponentZOrder(fondo, getComponentCount() - 1);
    }

    /**
     * Busca recepciones por cliente o equipo.
     *
     * @param texto texto ingresado en el buscador
     */
    private void buscar(String texto) {

        if ((texto.trim().isEmpty())
                || texto.equals("Buscar recepción por cliente o equipo...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblRecepciones.getModel();
        model.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (RecepcionEntrega r : controlador.filtrarRecepciones(texto)) {

            String equipo = (r.getEquipoMovil() != null)
                    ? r.getEquipoMovil().getMarca() + " " + r.getEquipoMovil().getModelo()
                    : "Sin equipo";

            String cliente = (r.getEquipoMovil() != null && r.getEquipoMovil().getCliente() != null)
                    ? r.getEquipoMovil().getCliente().getNombre()
                    : "Sin cliente";

            String fecha = (r.getFechaRecepcion() != null)
                    ? r.getFechaRecepcion().format(formatter)
                    : "";

            String usuario = (r.getUsuario() != null && r.getUsuario().getNombre() != null)
                    ? r.getUsuario().getNombre()
                    : "Sin usuario";

            model.addRow(new Object[]{
                r.getId(),
                fecha,
                cliente,
                equipo,
                r.getProblemaReportado(),
                r.getEstado(),
                usuario
            });
        }
    }

    /**
     * Captura el registro seleccionado en la tabla.
     */
    private void capturarSeleccion() {
        int fila = tblRecepciones.getSelectedRow();

        if (fila != -1) {
            idRecepcion = (int) tblRecepciones.getValueAt(fila, 0);
        }
    }

    /**
     * Carga todos los registros en la tabla.
     */
    private void cargarTabla() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Fecha", "Cliente", "Equipo", "Problema", "Estado", "Modificado Por"}, 0
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (RecepcionEntrega r : controlador.listarRecepciones()) {

            String equipo = (r.getEquipoMovil() != null)
                    ? r.getEquipoMovil().getMarca() + " " + r.getEquipoMovil().getModelo()
                    : "Sin equipo";

            String cliente = (r.getEquipoMovil() != null && r.getEquipoMovil().getCliente() != null)
                    ? r.getEquipoMovil().getCliente().getNombre()
                    : "Sin cliente";

            String usuario = (r.getUsuario() != null && r.getUsuario().getNombre() != null)
                    ? r.getUsuario().getNombre()
                    : "Sin usuario";

            String fecha = (r.getFechaRecepcion() != null)
                    ? r.getFechaRecepcion().format(formatter)
                    : "";

            model.addRow(new Object[]{
                r.getId(),
                fecha,
                cliente,
                equipo,
                r.getProblemaReportado(),
                r.getEstado(),
                usuario
            });
        }

        tblRecepciones.setModel(model);
        tblRecepciones.setDefaultEditor(Object.class, null);
        tblRecepciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Abre el formulario para editar una recepción seleccionada.
     */
    private void editar() {
        capturarSeleccion();

        if (idRecepcion == 0) {
            DialogUtil.mostrarMensajeAdvertencia(this, "Seleccione una recepción");
            return;
        }

        RecepcionEntrega r = controlador.obtenerRecepcion(idRecepcion);

        RecepcionEntregaDialog d = new RecepcionEntregaDialog(null, true, r);
        d.setLocationRelativeTo(this);
        d.setVisible(true);

        cargarTabla();
        idRecepcion = 0;
    }

    /**
     * Elimina una recepción seleccionada previa confirmación.
     */
    private void eliminar() {
        capturarSeleccion();

        if (idRecepcion == 0) {
            DialogUtil.mostrarMensajeAdvertencia(this, "Seleccione una recepción");
            return;
        }

        if (!DialogUtil.mostrarDialogoConfirmacion(this, "¿Eliminar esta recepción?")) {
            return;
        }

        ResultadoOperacion r = controlador.eliminarRecepcion(idRecepcion);

        if (!r.isExito()) {
            DialogUtil.mostrarMensajeError(this, r.getMensaje());
            return;
        }

        DialogUtil.mostrarMensajeInformacion(this, r.getMensaje());
        cargarTabla();
        idRecepcion = 0;
    }

    /**
     * Abre el formulario para registrar una nueva recepción.
     */
    private void registrar() {
        RecepcionEntregaDialog d = new RecepcionEntregaDialog(null, true, null);
        d.setLocationRelativeTo(this);
        d.setVisible(true);

        cargarTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlListado = new javax.swing.JPanel();
        pnlScroll = new javax.swing.JScrollPane();
        tblRecepciones = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();

        pnlListado.setBackground(new java.awt.Color(255, 204, 102));
        pnlListado.setOpaque(false);

        tblRecepciones.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblRecepciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRecepciones.setPreferredSize(null);
        tblRecepciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pnlScroll.setViewportView(tblRecepciones);

        btnEditar.setBackground(new java.awt.Color(102, 102, 255));
        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 51, 51));
        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnNuevo.setBackground(new java.awt.Color(103, 201, 228));
        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevo.setText("+ Nueva Recepción");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Listado De Recepciones y Entregas");

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSubtitulo.setText("Gestiona la información de tus recepciones y entregas registradas");

        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBuscar.setForeground(java.awt.Color.gray);
        txtBuscar.setText("Buscar recepción por cliente o equipo...");
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtBuscar.setPreferredSize(new java.awt.Dimension(200, 25));
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });

        btnBuscar.setBorder(null);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListadoLayout = new javax.swing.GroupLayout(pnlListado);
        pnlListado.setLayout(pnlListadoLayout);
        pnlListadoLayout.setHorizontalGroup(
            pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListadoLayout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlListadoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlListadoLayout.createSequentialGroup()
                        .addComponent(lblSubtitulo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlListadoLayout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlListadoLayout.createSequentialGroup()
                        .addGroup(pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlListadoLayout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(205, 205, 205)
                                .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(pnlScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(35, Short.MAX_VALUE))))
        );
        pnlListadoLayout.setVerticalGroup(
            pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListadoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlListadoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo)))
                .addGap(18, 18, 18)
                .addComponent(pnlScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlListado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlListado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        registrar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (txtBuscar.getText().isEmpty()) {
            txtBuscar.setText("Buscar recepción por cliente o equipo...");
            txtBuscar.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        if (txtBuscar.getText().equals("Buscar recepción por cliente o equipo...")) {
            txtBuscar.setText("");
            txtBuscar.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar(txtBuscar.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlListado;
    public static javax.swing.JScrollPane pnlScroll;
    public static javax.swing.JTable tblRecepciones;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
