package vista;

import controlador.ReparacionController;
import controlador.RecepcionEntregaController;
import java.awt.Color;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import modelo.Reparacion;
import modelo.RecepcionEntrega;
import util.DialogUtil;
import util.ResultadoOperacion;
import util.Sesion;

/**
 * Diálogo para crear o editar reparaciones.
 *
 * @author Pluas Kevin
 */
public class ReparacionDialog extends javax.swing.JDialog {

    private final ReparacionController controller;
    private final RecepcionEntregaController recepcionController;
    private Reparacion reparacion = null;

    /**
     * Crea el diálogo para registrar o editar reparaciones, inicializando
     * componentes, controladores y datos necesarios del formulario.
     *
     * @param parent ventana padre del diálogo
     * @param modal indica si el diálogo será modal
     * @param reparacion reparación a editar; si es null se crea una nueva
     */
    public ReparacionDialog(java.awt.Frame parent, boolean modal, Reparacion reparacion) {
        super(parent, modal);
        initComponents();

        controller = new ReparacionController();
        recepcionController = new RecepcionEntregaController();
        this.reparacion = reparacion;

        cargarRecepciones();
        cargarEstados();

        if (reparacion != null) {
            lblTitulo.setText("Editar Reparación");
            btnGuardar.setText("Actualizar");
            btnGuardar.setBackground(new Color(102, 102, 255));
            cargarDatos();
        } else {
            lblTitulo.setText("Nueva Reparación");
            btnGuardar.setText("Guardar");
            btnGuardar.setBackground(new Color(103, 201, 228));
        }

        cbxRecepcion.setRenderer(new javax.swing.DefaultListCellRenderer() {

            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof RecepcionEntrega r) {

                    int numOrden = r.getId();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String fecha = r.getFechaRecepcion().format(formatter);

                    String marca = r.getEquipoMovil().getMarca();
                    String modelo = r.getEquipoMovil().getModelo();
                    String nomCliente = r.getEquipoMovil().getCliente().getNombre();

                    String textoCompleto = "#" + numOrden + " | " + fecha
                            + " | " + marca + " " + modelo
                            + " | " + nomCliente;

                    String celular = marca + " " + modelo;
                    if (celular.length() > 15) {
                        celular = celular.substring(0, 15) + "...";
                    }

                    if (nomCliente.length() > 15) {
                        nomCliente = nomCliente.substring(0, 15) + "...";
                    }

                    String textoVisual = "#" + numOrden + " | " + fecha
                            + " | " + celular
                            + " | " + nomCliente;

                    setText("<html><table><tr><td width='240' style='white-space:nowrap; overflow:hidden;'>"
                            + textoVisual + "</td></tr></table></html>");

                    setToolTipText(textoCompleto);
                }

                return this;
            }
        });
    }

    /**
     * Carga combo de recepciones
     */
    private void cargarRecepciones() {
        DefaultComboBoxModel<RecepcionEntrega> model = new DefaultComboBoxModel<>();

        if (reparacion == null) {
            cbxRecepcion.setEnabled(true);

            for (RecepcionEntrega r : recepcionController.listarRecepciones()) {
                if (r.getEstado() == RecepcionEntrega.Estado.RECIBIDO && controller.obtenerReparacion(r.getId()) == null) {
                    model.addElement(r);
                }
            }
        } else {
            cbxRecepcion.setEnabled(false);
            model.addElement(reparacion.getRecepcion());
        }

        cbxRecepcion.setModel(model);
    }

    /**
     * Carga combo de estados
     */
    private void cargarEstados() {
        cbxEstado.setModel(new DefaultComboBoxModel<>(Reparacion.Estado.values()));
    }

    /**
     * Carga los datos de la reparación en el formulario (modo edición).
     */
    private void cargarDatos() {
        cbxRecepcion.setSelectedItem(reparacion.getRecepcion());
        txtaDiagnostico.setText(reparacion.getDiagnostico());
        txtaSolucion.setText(reparacion.getSolucion());
        txtaPiezas.setText(reparacion.getPiezasUsadas());
        txtCostoServ.setText(String.valueOf(reparacion.getCostoServicio()));
        txtCostoRep.setText(String.valueOf(reparacion.getCostoRepuestos()));
        cbxEstado.setSelectedItem(reparacion.getEstado());
    }

    /**
     * Valida y guarda los datos del formulario. Si la reparación es null,
     * realiza un INSERT. Si existe, realiza un UPDATE.
     */
    private void guardarDatos() {
        try {
            Reparacion r = new Reparacion();

            if (reparacion != null) {
                r.setId(reparacion.getId());
            }

            r.setRecepcion((RecepcionEntrega) cbxRecepcion.getSelectedItem());
            r.setDiagnostico(txtaDiagnostico.getText().trim());
            r.setSolucion(txtaSolucion.getText().trim());
            r.setPiezasUsadas(txtaPiezas.getText().trim());
            r.setCostoServicio(txtCostoServ.getText().trim().replace(",", "."));
            r.setCostoRepuestos(txtCostoRep.getText().trim().replace(",", "."));
            r.setEstado((Reparacion.Estado) cbxEstado.getSelectedItem());
            r.setUsuario(Sesion.getUsuarioActual());

            ResultadoOperacion resultado;

            if (reparacion == null) {
                resultado = controller.guardar(r);
            } else {
                resultado = controller.actualizar(r);
            }

            if (!resultado.isExito()) {
                DialogUtil.mostrarMensajeAdvertencia(this, resultado.getMensaje());
                return;
            }

            DialogUtil.mostrarMensajeInformacion(this, resultado.getMensaje());
            dispose();

        } catch (Exception ex) {
            DialogUtil.mostrarMensajeError(this, "Error: " + ex.getMessage());
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
        lblRecepcion = new javax.swing.JLabel();
        cbxRecepcion = new javax.swing.JComboBox<RecepcionEntrega>();
        lblDiagnostico = new javax.swing.JLabel();
        scrDiagnostico = new javax.swing.JScrollPane();
        txtaDiagnostico = new javax.swing.JTextArea();
        lblSolucion = new javax.swing.JLabel();
        scrSolucion = new javax.swing.JScrollPane();
        txtaSolucion = new javax.swing.JTextArea();
        lblPiezas = new javax.swing.JLabel();
        scrPiezas = new javax.swing.JScrollPane();
        txtaPiezas = new javax.swing.JTextArea();
        lblCostoServ = new javax.swing.JLabel();
        txtCostoServ = new javax.swing.JTextField();
        lblCostoRep = new javax.swing.JLabel();
        txtCostoRep = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<Reparacion.Estado>();
        btnGuardar = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlFormulario.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Nueva Reparación");

        lblRecepcion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRecepcion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRecepcion.setText("Recepcion *:");

        lblDiagnostico.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDiagnostico.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDiagnostico.setText("Diagnóstico *:");

        txtaDiagnostico.setColumns(20);
        txtaDiagnostico.setLineWrap(true);
        txtaDiagnostico.setRows(5);
        txtaDiagnostico.setWrapStyleWord(true);
        scrDiagnostico.setViewportView(txtaDiagnostico);

        lblSolucion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSolucion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSolucion.setText("Solución *:");

        txtaSolucion.setColumns(20);
        txtaSolucion.setLineWrap(true);
        txtaSolucion.setRows(5);
        txtaSolucion.setWrapStyleWord(true);
        scrSolucion.setViewportView(txtaSolucion);

        lblPiezas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPiezas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPiezas.setText("Piezas Usadas:");

        txtaPiezas.setColumns(20);
        txtaPiezas.setLineWrap(true);
        txtaPiezas.setRows(5);
        txtaPiezas.setWrapStyleWord(true);
        scrPiezas.setViewportView(txtaPiezas);

        lblCostoServ.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCostoServ.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCostoServ.setText("Costo Servicio *:");

        txtCostoServ.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblCostoRep.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCostoRep.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCostoRep.setText("Costo Repuestos *:");

        txtCostoRep.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEstado.setText("Estado *:");

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
                .addGap(23, 23, 23)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                        .addComponent(imagen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(238, 238, 238))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(181, 181, 181))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPiezas, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRecepcion)
                            .addComponent(lblCostoRep)
                            .addComponent(lblCostoServ))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEstado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCostoServ)
                            .addComponent(scrPiezas)
                            .addComponent(cbxRecepcion, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrDiagnostico)
                            .addComponent(scrSolucion)
                            .addComponent(txtCostoRep))
                        .addGap(25, 25, 25))))
        );
        pnlFormularioLayout.setVerticalGroup(
            pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioLayout.createSequentialGroup()
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lblTitulo)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imagen)
                        .addGap(18, 18, 18)))
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxRecepcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRecepcion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiagnostico, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrSolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSolucion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addComponent(scrPiezas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCostoServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCostoServ))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCostoRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCostoRep))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(btnGuardar))
                    .addComponent(lblPiezas))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFormulario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlFormulario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarDatos();
    }//GEN-LAST:event_btnGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<Reparacion.Estado> cbxEstado;
    private javax.swing.JComboBox<RecepcionEntrega> cbxRecepcion;
    private javax.swing.JLabel imagen;
    private javax.swing.JLabel lblCostoRep;
    private javax.swing.JLabel lblCostoServ;
    private javax.swing.JLabel lblDiagnostico;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblPiezas;
    private javax.swing.JLabel lblRecepcion;
    private javax.swing.JLabel lblSolucion;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JScrollPane scrDiagnostico;
    private javax.swing.JScrollPane scrPiezas;
    private javax.swing.JScrollPane scrSolucion;
    private javax.swing.JTextField txtCostoRep;
    private javax.swing.JTextField txtCostoServ;
    private javax.swing.JTextArea txtaDiagnostico;
    private javax.swing.JTextArea txtaPiezas;
    private javax.swing.JTextArea txtaSolucion;
    // End of variables declaration//GEN-END:variables
}
