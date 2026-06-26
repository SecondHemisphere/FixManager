package vista;

import controlador.ClienteController;
import controlador.EquipoMovilController;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.DefaultComboBoxModel;
import modelo.Cliente;
import modelo.EquipoMovil;
import util.DialogUtil;
import util.ResultadoOperacion;

/**
 * Diálogo para crear o editar equipos móviles.
 *
 * @author Gelves Jonathan
 */
public class EquipoMovilDialog extends javax.swing.JDialog {

    private final EquipoMovilController equipoController;
    private final ClienteController clienteController;
    private EquipoMovil equipo = null;

    /**
     * Inicializa el diálogo de gestión de equipos móviles.
     *
     * Configura los controladores, carga el listado de clientes y establece el
     * comportamiento visual del formulario dependiendo si se encuentra en modo
     * registro o edición.
     *
     * @param parent ventana padre del diálogo
     * @param modal indica si el diálogo será modal
     * @param equipo equipo móvil a editar; si es null, se crea un nuevo
     * registro
     */
    public EquipoMovilDialog(java.awt.Frame parent, boolean modal, EquipoMovil equipo) {
        super(parent, modal);
        initComponents();

        equipoController = new EquipoMovilController();
        clienteController = new ClienteController();

        this.equipo = equipo;

        cargarTipos();
        cargarClientes();

        if (equipo != null) {
            lblTitulo.setText("Editar Equipo Móvil");
            btnGuardar.setText("Actualizar");
            btnGuardar.setBackground(new Color(102, 102, 255));
            cargarDatos();
        } else {
            lblTitulo.setText("Nuevo Equipo Móvil");
            btnGuardar.setText("Guardar");
            btnGuardar.setBackground(new Color(103, 201, 228));
        }

        cbxCliente.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Cliente c) {
                    String nombreCompleto = (c.getNombre() != null) ? c.getNombre().trim() : "";

                    setToolTipText(nombreCompleto);

                    String nombreFormateado = nombreCompleto;
                    if (nombreFormateado.length() > 25) {
                        nombreFormateado = nombreFormateado.substring(0, 22) + "...";
                    }

                    setText("<html><table><tr><td width='180' style='overflow:hidden; white-space:nowrap;'>"
                            + nombreFormateado + "</td></tr></table></html>");
                }

                if (index == -1 && value != null) {
                    cbxCliente.setToolTipText(value.toString());
                }

                return this;
            }
        });

        cbxTipo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof EquipoMovil.TipoEquipo tipo) {
                    String tipoTexto = tipo.toString();

                    setToolTipText(tipoTexto);

                    setText("<html><table><tr><td width='180' style='overflow:hidden; white-space:nowrap;'>"
                            + tipoTexto + "</td></tr></table></html>");
                }

                if (index == -1 && value != null) {
                    cbxTipo.setToolTipText(value.toString());
                }

                return this;
            }
        });

        txtMarca.setPreferredSize(new Dimension(198, 22));
        txtModelo.setPreferredSize(new Dimension(198, 22));
        txtImei.setPreferredSize(new Dimension(198, 22));
    }

    /**
     * Carga combo de clientes
     */
    private void cargarClientes() {
        DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();

        for (Cliente c : clienteController.listarClientes()) {
            model.addElement(c);
        }

        cbxCliente.setModel(model);
    }

    /**
     * Carga los datos del equipo móvil en el formulario (modo edición).
     */
    private void cargarDatos() {
        txtMarca.setText(equipo.getMarca());
        txtModelo.setText(equipo.getModelo());
        txtImei.setText(equipo.getImei());
        cbxTipo.setSelectedItem(equipo.getTipo());
        cbxCliente.setSelectedItem(equipo.getCliente());
    }

    /**
     * Carga tipos de equipos móviles
     */
    private void cargarTipos() {
        cbxTipo.setModel(new DefaultComboBoxModel<>(EquipoMovil.TipoEquipo.values()));
    }

    /**
     * Valida y guarda los datos del formulario. Si el equipo móvil es null,
     * realiza un INSERT. Si existe, realiza un UPDATE.
     */
    private void guardarDatos() {
        try {
            EquipoMovil e = new EquipoMovil();

            if (equipo != null) {
                e.setId(equipo.getId());
            }

            e.setMarca(txtMarca.getText().trim());
            e.setModelo(txtModelo.getText().trim());
            e.setImei(txtImei.getText().trim());
            e.setTipo((EquipoMovil.TipoEquipo) cbxTipo.getSelectedItem());
            e.setCliente((Cliente) cbxCliente.getSelectedItem());

            ResultadoOperacion resultado = (equipo == null)
                    ? equipoController.guardarEquipo(e)
                    : equipoController.actualizarEquipo(e);

            if (!resultado.isExito()) {
                DialogUtil.mostrarMensajeAdvertencia(this, resultado.getMensaje());
                return;
            }

            DialogUtil.mostrarMensajeInformacion(this, resultado.getMensaje());
            dispose();

        } catch (Exception e) {
            DialogUtil.mostrarMensajeError(this, "Error: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFormulario = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        lblModelo = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        lblImei = new javax.swing.JLabel();
        txtImei = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cbxTipo = new javax.swing.JComboBox<EquipoMovil.TipoEquipo>();
        lblCliente = new javax.swing.JLabel();
        cbxCliente = new javax.swing.JComboBox<Cliente>();
        btnGuardar = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlFormulario.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Nuevo Equipo Móvil");

        lblMarca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMarca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMarca.setText("Marca *:");

        txtMarca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblModelo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblModelo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblModelo.setText("Modelo *:");

        txtModelo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblImei.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblImei.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblImei.setText("IMEI *:");

        txtImei.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblTipo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTipo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTipo.setText("Tipo *:");

        lblCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCliente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCliente.setText("Cliente *:");

        btnGuardar.setBackground(new java.awt.Color(103, 201, 228));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/favicon.png"))); // NOI18N

        javax.swing.GroupLayout pnlFormularioLayout = new javax.swing.GroupLayout(pnlFormulario);
        pnlFormulario.setLayout(pnlFormularioLayout);
        pnlFormularioLayout.setHorizontalGroup(
            pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioLayout.createSequentialGroup()
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(imagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitulo))
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTipo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblImei, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblModelo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMarca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMarca)
                            .addComponent(cbxCliente, 0, 198, Short.MAX_VALUE)
                            .addComponent(txtModelo)
                            .addComponent(txtImei)
                            .addComponent(cbxTipo, 0, 198, Short.MAX_VALUE)))
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        pnlFormularioLayout.setVerticalGroup(
            pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMarca)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModelo)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtImei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImei))
                .addGap(18, 18, 18)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblCliente))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(btnGuardar)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarDatos();
    }//GEN-LAST:event_btnGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<Cliente> cbxCliente;
    private javax.swing.JComboBox<EquipoMovil.TipoEquipo> cbxTipo;
    private javax.swing.JLabel imagen;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblImei;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JTextField txtImei;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    // End of variables declaration//GEN-END:variables
}
