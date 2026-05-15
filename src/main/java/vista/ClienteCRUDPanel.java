package vista;

import controlador.ClienteController;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import util.DialogUtil;
import util.ResultadoOperacion;

/**
 * Panel de gestión CRUD de clientes.
 *
 * @author Cagua Derek
 */
public class ClienteCRUDPanel extends javax.swing.JPanel {

    private final ClienteController controlador = new ClienteController();
    private int idCliente = 0;

    /**
     * Creates new form ClienteCRUDPanel
     */
    public ClienteCRUDPanel() {
        initComponents();

        setOpaque(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/buscar.png"));
        Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnBuscar.setIcon(new ImageIcon(img));

        pnlCrud.setOpaque(false);
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

        pnlCrud.setBounds(0, 0, 897, 124);
        pnlListado.setBounds(0, 124, 897, 300);

        add(fondo);
        add(pnlCrud);
        add(pnlListado);

        setComponentZOrder(fondo, getComponentCount() - 1);
    }

    /**
     * Busca clientes por nombre.
     *
     * @param texto texto ingresado en el buscador
     */
    private void buscar(String texto) {

        if ((texto.trim().isEmpty())
                || texto.equals("Buscar cliente por nombre...")) {
            cargarTabla();
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        model.setRowCount(0);

        for (Cliente c : controlador.filtrarClientes(texto)) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getCorreo(),
                c.getTelefono(),
                c.getDireccion()
            });
        }
    }

    /**
     * Captura el registro seleccionado en la tabla.
     */
    private void capturarSeleccion() {
        int fila = tblClientes.getSelectedRow();

        if (fila != -1) {
            idCliente = (int) tblClientes.getValueAt(fila, 0);
        }
    }

    /**
     * Carga todos los registros en la tabla.
     */
    private void cargarTabla() {

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Correo", "Teléfono", "Dirección"}, 0
        );

        for (Cliente c : controlador.listarClientes()) {
            model.addRow(new Object[]{
                c.getId(),
                c.getNombre(),
                c.getCorreo(),
                c.getTelefono(),
                c.getDireccion()
            });
        }

        tblClientes.setModel(model);
        tblClientes.setDefaultEditor(Object.class, null);
        tblClientes.setRowSelectionAllowed(true);
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Abre el formulario para editar un cliente seleccionado.
     */
    private void editar() {
        capturarSeleccion();

        if (idCliente == 0) {
            DialogUtil.mostrarMensajeAdvertencia(this, "Seleccione un cliente");
            return;
        }

        Cliente cliente = controlador.obtenerCliente(idCliente);
        ClienteDialog dialog = new ClienteDialog(null, true, cliente);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        cargarTabla();
        idCliente = 0;
    }

    /**
     * Elimina un cliente seleccionado previa confirmación.
     */
    private void eliminar() {
        capturarSeleccion();

        if (idCliente == 0) {
            DialogUtil.mostrarMensajeAdvertencia(this, "Seleccione un cliente");
            return;
        }

        if (!DialogUtil.mostrarDialogoConfirmacion(this, "¿Está seguro de eliminar este cliente?")) {
            return;
        }

        ResultadoOperacion resultado = controlador.eliminarCliente(idCliente);

        if (!resultado.isExito()) {
            DialogUtil.mostrarMensajeError(this, resultado.getMensaje());
            return;
        }

        DialogUtil.mostrarMensajeInformacion(this, resultado.getMensaje());
        cargarTabla();
        idCliente = 0;
    }

    /**
     * Abre el formulario para registrar un nuevo cliente.
     */
    private void registrar() {
        ClienteDialog dialog = new ClienteDialog(null, true, null);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

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

        pnlCrud = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        pnlListado = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        pnlScroll = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(897, 424));

        pnlCrud.setOpaque(false);
        pnlCrud.setPreferredSize(new java.awt.Dimension(897, 165));
        pnlCrud.setRequestFocusEnabled(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Listado De Clientes");

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSubtitulo.setText("Gestiona la información de tus clientes registrados");

        btnBuscar.setBorder(null);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBuscar.setForeground(java.awt.Color.gray);
        txtBuscar.setText("Buscar cliente por nombre...");
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

        btnNuevo.setBackground(new java.awt.Color(51, 204, 0));
        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevo.setText("+ Nuevo Cliente");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCrudLayout = new javax.swing.GroupLayout(pnlCrud);
        pnlCrud.setLayout(pnlCrudLayout);
        pnlCrudLayout.setHorizontalGroup(
            pnlCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrudLayout.createSequentialGroup()
                        .addGroup(pnlCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubtitulo)
                            .addComponent(lblTitulo))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlCrudLayout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 282, Short.MAX_VALUE)
                        .addComponent(btnNuevo)
                        .addGap(45, 45, 45))))
        );
        pnlCrudLayout.setVerticalGroup(
            pnlCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrudLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlCrudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20))
        );

        pnlListado.setOpaque(false);

        btnEliminar.setBackground(new java.awt.Color(255, 51, 51));
        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(102, 102, 255));
        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        tblClientes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblClientes.setPreferredSize(null);
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pnlScroll.setViewportView(tblClientes);

        javax.swing.GroupLayout pnlListadoLayout = new javax.swing.GroupLayout(pnlListado);
        pnlListado.setLayout(pnlListadoLayout);
        pnlListadoLayout.setHorizontalGroup(
            pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListadoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(302, 302, 302))
            .addGroup(pnlListadoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pnlListadoLayout.setVerticalGroup(
            pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlListadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlListado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlCrud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlCrud, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            txtBuscar.setText("Buscar cliente por nombre...");
            txtBuscar.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_txtBuscarFocusLost

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        if (txtBuscar.getText().equals("Buscar cliente por nombre...")) {
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
    private javax.swing.JPanel pnlCrud;
    private javax.swing.JPanel pnlListado;
    public static javax.swing.JScrollPane pnlScroll;
    public static javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
