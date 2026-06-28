package vista;

import controlador.FacturaController;
import controlador.ReparacionController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.DefaultComboBoxModel;
import modelo.Factura;
import modelo.Reparacion;
import util.DialogUtil;
import util.ResultadoOperacion;
import util.Sesion;

/**
 * Diálogo para crear o editar facturas.
 *
 * @author Mendoza Sebastian
 */
public class FacturaDialog extends javax.swing.JDialog {

    private final FacturaController controller;
    private final ReparacionController reparacionController;
    private Factura factura = null;

    /**
     * Inicializa los componentes gráficos, carga las reparaciones disponibles y
     * los métodos de pago permitidos.
     *
     * Si la factura recibida no es null, el formulario se configura en modo
     * edición.
     *
     * @param parent ventana padre
     * @param modal indica si el diálogo será modal
     * @param factura factura a editar; null para registrar
     */
    public FacturaDialog(java.awt.Frame parent, boolean modal, Factura factura) {
        super(parent, modal);
        initComponents();

        controller = new FacturaController();
        reparacionController = new ReparacionController();
        this.factura = factura;

        cargarReparaciones();

        if (factura != null) {
            lblTitulo.setText("Editar Factura");
            btnGuardar.setText("Actualizar");
            btnGuardar.setBackground(new Color(102, 102, 255));
            cargarDatos();
        } else {
            lblTitulo.setText("Nueva Factura");
            btnGuardar.setText("Guardar");
            btnGuardar.setBackground(new Color(103, 201, 228));
        }

        cbxReparacion.setRenderer(new javax.swing.DefaultListCellRenderer() {

            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Reparacion r) {

                    String cliente = (r.getRecepcion() != null
                            && r.getRecepcion().getEquipoMovil() != null
                            && r.getRecepcion().getEquipoMovil().getCliente() != null)
                            ? r.getRecepcion().getEquipoMovil().getCliente().getNombre()
                            : "Sin cliente";

                    String equipo = (r.getRecepcion() != null
                            && r.getRecepcion().getEquipoMovil() != null)
                            ? r.getRecepcion().getEquipoMovil().getMarca() + " " + r.getRecepcion().getEquipoMovil().getModelo()
                            : "Sin equipo";

                    String textoCompleto = "#" + r.getId()
                            + " | " + cliente
                            + " | " + equipo;

                    String equipoShort = equipo.length() > 15 ? equipo.substring(0, 15) + "..." : equipo;
                    String clienteShort = cliente.length() > 15 ? cliente.substring(0, 15) + "..." : cliente;

                    String textoVisual = "#" + r.getId()
                            + " | " + clienteShort
                            + " | " + equipoShort;

                    setText("<html><div style='width:300px; white-space:nowrap; overflow:hidden;'>"
                            + textoVisual + "</div></html>");

                    setToolTipText(textoCompleto);
                }

                return this;
            }
        });

        txtCosto.setEditable(false);
        txtCosto.setBackground(new Color(240, 240, 240));

        cbxReparacion.addActionListener((ActionEvent e) -> {
            cargarCosto();
        });
    }

    /**
     * Carga el costo final de la reparación seleccionada
     */
    private void cargarCosto() {
        Reparacion r = (Reparacion) cbxReparacion.getSelectedItem();

        if (r != null) {

            BigDecimal total = BigDecimal.ZERO;

            if (r.getCostoServicio() != null && !r.getCostoServicio().trim().isEmpty()) {
                total = total.add(new BigDecimal(r.getCostoServicio()));
            }

            if (r.getCostoRepuestos() != null && !r.getCostoRepuestos().trim().isEmpty()) {
                total = total.add(new BigDecimal(r.getCostoRepuestos()));
            }

            txtCosto.setText(total.setScale(2, RoundingMode.HALF_UP).toString());

        } else {
            txtCosto.setText("");
        }
    }

    /**
     * Carga reparaciones finalizadas
     */
    private void cargarReparaciones() {
        DefaultComboBoxModel<Reparacion> model = new DefaultComboBoxModel<>();

        for (Reparacion r : reparacionController.listar()) {
            if (r.getEstado() == Reparacion.Estado.FINALIZADO) {
                model.addElement(r);
            }
        }

        cbxReparacion.setModel(model);
    }

    /**
     * Carga los datos de la factura en el formulario (modo edición).
     */
    private void cargarDatos() {
        cbxReparacion.setSelectedItem(factura.getReparacion());
        txtCosto.setText(String.valueOf(factura.getCostoTotal()));
        txtaObservaciones.setText(factura.getObservaciones());
    }

    /**
     * Valida y guarda los datos del formulario. Si la factura es null, realiza
     * un INSERT. Si existe, realiza un UPDATE.
     */
    private void guardarDatos() {
        try {
            Factura f = new Factura();

            if (factura != null) {
                f.setId(factura.getId());
            }

            f.setReparacion((Reparacion) cbxReparacion.getSelectedItem());
            f.setCostoTotal(txtCosto.getText().trim());
            f.setObservaciones(txtaObservaciones.getText().trim());
            f.setUsuario(Sesion.getUsuarioActual());

            ResultadoOperacion resultado = (factura == null)
                    ? controller.guardarFactura(f)
                    : controller.actualizarFactura(f);

            if (!resultado.isExito()) {
                DialogUtil.mostrarMensajeAdvertencia(this, resultado.getMensaje());
                return;
            }

            DialogUtil.mostrarMensajeInformacion(this, resultado.getMensaje());
            dispose();

        } catch (NumberFormatException e) {
            DialogUtil.mostrarMensajeAdvertencia(this, "El costo debe ser numérico");
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
        lblReparacion = new javax.swing.JLabel();
        cbxReparacion = new javax.swing.JComboBox<Reparacion>();
        lblObservaciones = new javax.swing.JLabel();
        scrObservaciones = new javax.swing.JScrollPane();
        txtaObservaciones = new javax.swing.JTextArea();
        lblCosto = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlFormulario.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Nueva Factura");

        lblReparacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblReparacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReparacion.setText("Reparación *:");

        cbxReparacion.setPreferredSize(null);

        lblObservaciones.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblObservaciones.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblObservaciones.setText("Observaciones :");

        txtaObservaciones.setColumns(20);
        txtaObservaciones.setLineWrap(true);
        txtaObservaciones.setRows(5);
        txtaObservaciones.setWrapStyleWord(true);
        scrObservaciones.setViewportView(txtaObservaciones);

        lblCosto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCosto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCosto.setText("Costo Total *:");

        txtCosto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

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
                .addGap(25, 25, 25)
                .addComponent(imagen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(270, 270, 270))
            .addGroup(pnlFormularioLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblObservaciones)
                    .addComponent(lblCosto)
                    .addComponent(lblReparacion))
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormularioLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxReparacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCosto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrObservaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFormularioLayout.setVerticalGroup(
            pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormularioLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReparacion))
                .addGap(18, 18, 18)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCosto))
                .addGap(18, 18, 18)
                .addGroup(pnlFormularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblObservaciones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFormularioLayout.createSequentialGroup()
                        .addComponent(scrObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(btnGuardar)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFormulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private javax.swing.JComboBox<Reparacion> cbxReparacion;
    private javax.swing.JLabel imagen;
    private javax.swing.JLabel lblCosto;
    private javax.swing.JLabel lblObservaciones;
    private javax.swing.JLabel lblReparacion;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JScrollPane scrObservaciones;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextArea txtaObservaciones;
    // End of variables declaration//GEN-END:variables
}
