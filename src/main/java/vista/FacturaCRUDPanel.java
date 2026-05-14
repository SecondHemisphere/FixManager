package vista;

import controlador.FacturaController;
import java.awt.Color;
import java.awt.Image;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
import util.DialogUtil;
import util.ResultadoOperacion;

/**
 * Panel de gestión CRUD de facturas.
 *
 * @author Mendoza Sebastian
 */
public class FacturaCRUDPanel extends javax.swing.JPanel {

    private final FacturaController controlador = new FacturaController();
    private int idFactura = 0;

    /**
     * Creates new form FacturaCRUDPanel
     */
    public FacturaCRUDPanel() {
        initComponents();

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/buscar.png"));
        Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnBuscar.setIcon(new ImageIcon(img));
        cargarTabla();
    }

    /**
     * Busca facturas por cliente o estado.
     *
     * @param texto texto ingresado en el buscador
     */
    private void buscar(String texto) {

        if ((texto.trim().isEmpty())
                || texto.equals("Buscar factura por cliente o estado...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblFacturas.getModel();
        model.setRowCount(0);

        for (Factura f : controlador.filtrarFacturas(texto)) {

            String cliente = (f.getReparacion() != null
                    && f.getReparacion().getRecepcion() != null
                    && f.getReparacion().getRecepcion().getEquipoMovil() != null
                    && f.getReparacion().getRecepcion().getEquipoMovil().getCliente() != null)
                    ? f.getReparacion().getRecepcion().getEquipoMovil().getCliente().getNombre()
                    : "Sin cliente";

            String usuario = (f.getUsuario() != null && f.getUsuario().getNombre() != null)
                    ? f.getUsuario().getNombre()
                    : "Sin usuario";

            String fechaFormateada = (f.getFechaEmision() != null)
                    ? f.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    : "";

            model.addRow(new Object[]{
                f.getId(),
                cliente,
                f.getCostoTotal(),
                fechaFormateada,
                f.getEstado(),
                f.getMetodoPago(),
                usuario
            });
        }
    }

    /**
     * Captura el registro seleccionado en la tabla.
     */
    private void capturarSeleccion() {
        int fila = tblFacturas.getSelectedRow();
        if (fila != -1) {
            idFactura = (int) tblFacturas.getValueAt(fila, 0);
        }
    }

    /**
     * Carga todos los registros en la tabla.
     */
    private void cargarTabla() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Cliente", "Costo", "Fecha", "Estado", "Método", "Modificado Por"}, 0
        );

        for (Factura f : controlador.listarFacturas()) {

            String cliente = (f.getReparacion() != null
                    && f.getReparacion().getRecepcion() != null
                    && f.getReparacion().getRecepcion().getEquipoMovil() != null
                    && f.getReparacion().getRecepcion().getEquipoMovil().getCliente() != null)
                    ? f.getReparacion().getRecepcion().getEquipoMovil().getCliente().getNombre()
                    : "Sin cliente";

            String usuario = (f.getUsuario() != null && f.getUsuario().getNombre() != null)
                    ? f.getUsuario().getNombre()
                    : "Sin usuario";

            String fechaFormateada = (f.getFechaEmision() != null)
                    ? f.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    : "";

            model.addRow(new Object[]{
                f.getId(),
                cliente,
                f.getCostoTotal(),
                fechaFormateada,
                f.getEstado(),
                f.getMetodoPago(),
                usuario
            });
        }

        tblFacturas.setModel(model);
        tblFacturas.setDefaultEditor(Object.class, null);
    }

    /**
     * Abre el formulario para editar una factura seleccionada.
     */
    private void editar() {
        capturarSeleccion();

        if (idFactura == 0) {
            DialogUtil.mostrarMensajeAdvertencia(this, "Seleccione una factura");
            return;
        }

        Factura f = controlador.obtenerFactura(idFactura);

        FacturaDialog d = new FacturaDialog(null, true, f);
        d.setLocationRelativeTo(this);
        d.setVisible(true);

        cargarTabla();
        idFactura = 0;
    }

    /**
     * Elimina una factura seleccionada previa confirmación.
     */
    private void eliminar() {
        capturarSeleccion();

        if (idFactura == 0) {
            DialogUtil.mostrarMensajeAdvertencia(this, "Seleccione una factura");
            return;
        }

        if (!DialogUtil.mostrarDialogoConfirmacion(this, "¿Está seguro de anular esta factura?")) {
            return;
        }

        ResultadoOperacion resultado = controlador.eliminarFactura(idFactura);

        if (!resultado.isExito()) {
            DialogUtil.mostrarMensajeError(this, resultado.getMensaje());
            return;
        }

        DialogUtil.mostrarMensajeInformacion(this, resultado.getMensaje());
        cargarTabla();
        idFactura = 0;
    }

    /**
     * Abre el formulario para registrar una nueva factura.
     */
    private void registrar() {
        FacturaDialog d = new FacturaDialog(null, true, null);
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
        tblFacturas = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 255, 204));

        pnlListado.setBackground(new java.awt.Color(255, 204, 102));
        pnlListado.setOpaque(false);

        tblFacturas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblFacturas.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFacturas.setPreferredSize(null);
        tblFacturas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pnlScroll.setViewportView(tblFacturas);

        btnEditar.setBackground(new java.awt.Color(51, 204, 0));
        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnNuevo.setBackground(new java.awt.Color(0, 204, 102));
        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevo.setText("+ Nueva Factura");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Listado De Facturas");

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSubtitulo.setText("Gestiona la información de tus facturas");

        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBuscar.setForeground(java.awt.Color.gray);
        txtBuscar.setText("Buscar factura por cliente o estado...");
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

        btnEliminar.setBackground(new java.awt.Color(255, 51, 51));
        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListadoLayout = new javax.swing.GroupLayout(pnlListado);
        pnlListado.setLayout(pnlListadoLayout);
        pnlListadoLayout.setHorizontalGroup(
            pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListadoLayout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176)
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

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        registrar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        if (txtBuscar.getText().isEmpty()) {
            txtBuscar.setText("Buscar factura por cliente o estado...");
            txtBuscar.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        if (txtBuscar.getText().equals("Buscar factura por cliente o estado...")) {
            txtBuscar.setText("");
            txtBuscar.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtBuscarFocusGained

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar(txtBuscar.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlListado;
    public static javax.swing.JScrollPane pnlScroll;
    public static javax.swing.JTable tblFacturas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
