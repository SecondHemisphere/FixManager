package vista;

import controlador.FacturaController;
import java.awt.Color;
import java.awt.Image;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;
import modelo.Factura.MetodoPago;
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

    private final ImageIcon iconPago;

    /**
     * Constructor del panel de facturas. Inicializa los componentes gráficos,
     * configura estilos, carga íconos, prepara la tabla y establece el fondo
     * personalizado del panel.
     *
     * @author Mendoza Sebastian
     */
    public FacturaCRUDPanel() {
        initComponents();

        setOpaque(false);

        ImageIcon iconBuscar = new ImageIcon(getClass().getResource("/img/buscar.png"));
        Image imgBuscar = iconBuscar.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        btnBuscar.setIcon(new ImageIcon(imgBuscar));

        ImageIcon iconDinero = new ImageIcon(getClass().getResource("/img/moneda.png"));
        Image imgDinero = iconDinero.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        this.iconPago = new ImageIcon(imgDinero);

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
     * Procesa el pago de una factura seleccionada.
     */
    private void pagar() {
        capturarSeleccion();

        if (idFactura == 0) {
            DialogUtil.mostrarMensajeAdvertencia(this, "Seleccione una factura");
            return;
        }

        boolean confirmar = DialogUtil.mostrarDialogoConfirmacion(this, """
                                                                            \u00bfEst\u00e1 seguro de iniciar el pago de esta factura?
                                                           
                                                                            Una vez pagada, la factura no podr\u00e1 ser modificada.""");

        if (!confirmar) {
            return;
        }

        MetodoPago metodo = (MetodoPago) JOptionPane.showInputDialog(
                this,
                "Seleccione método de pago",
                "Pagar factura",
                JOptionPane.QUESTION_MESSAGE,
                iconPago,
                MetodoPago.values(),
                MetodoPago.EFECTIVO
        );

        if (metodo == null) {
            return;
        }

        ResultadoOperacion resultado = controlador.pagarFactura(idFactura, metodo);

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

        jInternalFrame1 = new javax.swing.JInternalFrame();
        pnlListado = new javax.swing.JPanel();
        pnlScroll = new javax.swing.JScrollPane();
        tblFacturas = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        pnlListado1 = new javax.swing.JPanel();
        pnlScroll1 = new javax.swing.JScrollPane();
        tblFacturas1 = new javax.swing.JTable();
        lblTitulo1 = new javax.swing.JLabel();
        lblSubtitulo1 = new javax.swing.JLabel();
        txtBuscar1 = new javax.swing.JTextField();
        btnBuscar1 = new javax.swing.JButton();
        btnNuevo1 = new javax.swing.JButton();
        btnEditar1 = new javax.swing.JButton();
        btnPagar1 = new javax.swing.JButton();
        btnEliminar1 = new javax.swing.JButton();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(204, 255, 204));

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

        btnNuevo.setBackground(new java.awt.Color(103, 201, 228));
        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevo.setText("+ Nueva Factura");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
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

        btnPagar.setBackground(new java.awt.Color(20, 184, 166));
        btnPagar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPagar.setText("Pagar");
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
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
                .addGap(40, 40, 40)
                .addComponent(btnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
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
                    .addComponent(btnEliminar)
                    .addComponent(btnPagar))
                .addGap(16, 16, 16))
        );

        pnlListado1.setOpaque(false);

        tblFacturas1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblFacturas1.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFacturas1.setPreferredSize(null);
        tblFacturas1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        pnlScroll1.setViewportView(tblFacturas1);

        lblTitulo1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo1.setText("Listado De Facturas");

        lblSubtitulo1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSubtitulo1.setText("Gestiona la información de tus facturas");

        txtBuscar1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBuscar1.setForeground(java.awt.Color.gray);
        txtBuscar1.setText("Buscar factura por cliente o estado...");
        txtBuscar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtBuscar1.setPreferredSize(new java.awt.Dimension(200, 25));
        txtBuscar1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscar1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscar1FocusLost(evt);
            }
        });

        btnBuscar1.setBorder(null);
        btnBuscar1.setBorderPainted(false);
        btnBuscar1.setContentAreaFilled(false);
        btnBuscar1.setFocusPainted(false);
        btnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar1ActionPerformed(evt);
            }
        });

        btnNuevo1.setBackground(new java.awt.Color(51, 204, 0));
        btnNuevo1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevo1.setText("+ Nueva Factura");
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });

        btnEditar1.setBackground(new java.awt.Color(102, 102, 255));
        btnEditar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEditar1.setText("Editar");
        btnEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditar1ActionPerformed(evt);
            }
        });

        btnPagar1.setBackground(new java.awt.Color(0, 204, 102));
        btnPagar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPagar1.setText("Pagar");
        btnPagar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagar1ActionPerformed(evt);
            }
        });

        btnEliminar1.setBackground(new java.awt.Color(255, 51, 51));
        btnEliminar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar1.setText("Eliminar");
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListado1Layout = new javax.swing.GroupLayout(pnlListado1);
        pnlListado1.setLayout(pnlListado1Layout);
        pnlListado1Layout.setHorizontalGroup(
            pnlListado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListado1Layout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(btnEditar1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnPagar1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlListado1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlListado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlListado1Layout.createSequentialGroup()
                        .addComponent(lblSubtitulo1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlListado1Layout.createSequentialGroup()
                        .addComponent(lblTitulo1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlListado1Layout.createSequentialGroup()
                        .addGroup(pnlListado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlListado1Layout.createSequentialGroup()
                                .addComponent(txtBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(205, 205, 205)
                                .addComponent(btnNuevo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(pnlScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(35, Short.MAX_VALUE))))
        );
        pnlListado1Layout.setVerticalGroup(
            pnlListado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListado1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlListado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuscar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlListado1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo1)))
                .addGap(18, 18, 18)
                .addComponent(pnlScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlListado1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar1)
                    .addComponent(btnEliminar1)
                    .addComponent(btnPagar1))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlListado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlListado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlListado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlListado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
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

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        pagar();
    }//GEN-LAST:event_btnPagarActionPerformed

    private void txtBuscar1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscar1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar1FocusGained

    private void txtBuscar1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscar1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscar1FocusLost

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscar1ActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditar1ActionPerformed

    private void btnPagar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPagar1ActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditar1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnPagar1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblSubtitulo1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTitulo1;
    private javax.swing.JPanel pnlListado;
    private javax.swing.JPanel pnlListado1;
    public static javax.swing.JScrollPane pnlScroll;
    public static javax.swing.JScrollPane pnlScroll1;
    public static javax.swing.JTable tblFacturas;
    public static javax.swing.JTable tblFacturas1;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscar1;
    // End of variables declaration//GEN-END:variables
}
