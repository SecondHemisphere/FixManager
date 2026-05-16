package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import util.Sesion;

/**
 * Ventana principal del sistema.
 *
 * Contiene el menú de navegación principal y permite acceder a los distintos
 * módulos del sistema según el rol del usuario.
 *
 * @author Mendoza Sebastian
 */
public class FrmMenu extends javax.swing.JFrame {

    private static final Color COLOR_MENU_PRINCIPAL = new Color(255, 255, 255);
    private static final Color COLOR_MENU_ITEM = new Color(245, 246, 250);
    private static final Color COLOR_TEXTO = new Color(45, 52, 54);

    private static final Color COLOR_CERRAR_SESION = new Color(255, 230, 230);
    private static final Color COLOR_CERRAR_SESION_HOVER = new Color(255, 175, 175);
    private static final Color COLOR_TEXTO_CERRAR_SESION = new Color(214, 48, 49);

    /**
     * Inicializa la ventana principal del sistema.
     *
     * Configura estilos visuales, iconos, permisos por rol y eventos del menú
     * principal.
     */
    public FrmMenu() {
        initComponents();

        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        estilizarMenu(menuUsuario);
        estilizarMenu(menuCliente);
        estilizarMenu(menuEquipoMovil);
        estilizarMenu(menuRecepcionEntrega);
        estilizarMenu(menuReparacion);
        estilizarMenu(menuFactura);
        estilizarMenu(menuCerrarSesion);

        estilizarItem(itemGestionarUsuario);
        estilizarItem(itemGestionarCliente);
        estilizarItem(itemGestionarEquipoMovil);
        estilizarItem(itemGestionarRecepcionEntrega);
        estilizarItem(itemGestionarReparacion);
        estilizarItem(itemGestionarFactura);
        estilizarItem(itemCerrarSesion);

        menuBar.setOpaque(true);
        menuBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(45, 52, 54)),
                BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));
        menuBar.setPreferredSize(new Dimension(900, 35));
        menuBar.setBackground(COLOR_MENU_PRINCIPAL);
        menuBar.setForeground(COLOR_TEXTO);

        menuCerrarSesion.setOpaque(true);
        menuCerrarSesion.setBackground(COLOR_CERRAR_SESION);
        menuCerrarSesion.setForeground(COLOR_TEXTO_CERRAR_SESION);

        menuCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuCerrarSesion.setBackground(COLOR_CERRAR_SESION_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuCerrarSesion.setBackground(COLOR_CERRAR_SESION);
            }
        });

        menuUsuario.setIcon(obtenerIcono("/img/usuario.png", 24, 24));
        menuCliente.setIcon(obtenerIcono("/img/cliente.png", 24, 24));
        menuEquipoMovil.setIcon(obtenerIcono("/img/equipo.png", 24, 24));
        menuRecepcionEntrega.setIcon(obtenerIcono("/img/recepcion.png", 24, 24));
        menuReparacion.setIcon(obtenerIcono("/img/reparacion.png", 24, 24));
        menuFactura.setIcon(obtenerIcono("/img/factura.png", 24, 24));
        menuCerrarSesion.setIcon(obtenerIcono("/img/logout.png", 24, 24));

        try {
            ImageIcon imagen = new ImageIcon(getClass().getResource("/img/fondo2.png"));
            Image img = imagen.getImage().getScaledInstance(897, 400, Image.SCALE_SMOOTH);

            JLabel fondo = new JLabel(new ImageIcon(img));
            fondo.setBounds(0, 0, 897, 400);

            pnlPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
            pnlPrincipal.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 897, 400));
            pnlPrincipal.setComponentZOrder(fondo, pnlPrincipal.getComponentCount() - 1);
        } catch (Exception e) {
        }

        configurarPermisos();

        menuBar.remove(menuCerrarSesion);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuCerrarSesion);
    }

    /**
     * Configura la visibilidad de los menús según el rol del usuario
     * autenticado.
     */
    private void configurarPermisos() {
        if (Sesion.getUsuarioActual() == null) {
            return;
        }

        if (Sesion.esTecnico()) {
            menuUsuario.setVisible(false);
            menuFactura.setVisible(false);
        } else if (Sesion.esCajero()) {
            menuUsuario.setVisible(false);
            menuCliente.setVisible(false);
            menuEquipoMovil.setVisible(false);
            menuRecepcionEntrega.setVisible(false);
            menuReparacion.setVisible(false);
        }
    }

    /**
     * Aplica estilos visuales personalizados a un menú.
     *
     * @param menu menú a estilizar
     */
    private void estilizarMenu(JMenu menu) {
        menu.setFont(new Font("Tahoma", Font.BOLD, 12));
        menu.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));

        menu.setOpaque(true);
        menu.setBackground(COLOR_MENU_PRINCIPAL);
        menu.setForeground(COLOR_TEXTO);

        menu.setHorizontalTextPosition(SwingConstants.RIGHT);
        menu.setVerticalTextPosition(SwingConstants.CENTER);
        menu.setIconTextGap(5);

        if (menu.getPopupMenu() != null) {
            menu.getPopupMenu().setBorder(BorderFactory.createLineBorder(new Color(220, 221, 225), 1));
            menu.getPopupMenu().setBackground(COLOR_MENU_ITEM);
        }
    }

    /**
     * Aplica estilos visuales a un item del menú.
     *
     * @param item item a estilizar
     */
    private void estilizarItem(JMenuItem item) {
        item.setFont(new Font("Tahoma", java.awt.Font.PLAIN, 12));
        item.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        item.setPreferredSize(new Dimension(190, 32));

        item.setOpaque(true);
        item.setBackground(COLOR_MENU_ITEM);
        item.setForeground(COLOR_TEXTO);

        item.setMargin(new java.awt.Insets(0, 0, 0, 0));
    }

    /**
     * Obtiene un icono redimensionado.
     *
     * @param ruta ruta de la imagen
     * @param ancho ancho deseado
     * @param alto alto deseado
     * @return icono escalado
     */
    private ImageIcon obtenerIcono(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
        Image img = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        imagen1 = new javax.swing.JLabel();
        favicon = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuUsuario = new javax.swing.JMenu();
        itemGestionarUsuario = new javax.swing.JMenuItem();
        menuCliente = new javax.swing.JMenu();
        itemGestionarCliente = new javax.swing.JMenuItem();
        menuEquipoMovil = new javax.swing.JMenu();
        itemGestionarEquipoMovil = new javax.swing.JMenuItem();
        menuRecepcionEntrega = new javax.swing.JMenu();
        itemGestionarRecepcionEntrega = new javax.swing.JMenuItem();
        menuReparacion = new javax.swing.JMenu();
        itemGestionarReparacion = new javax.swing.JMenuItem();
        menuFactura = new javax.swing.JMenu();
        itemGestionarFactura = new javax.swing.JMenuItem();
        menuCerrarSesion = new javax.swing.JMenu();
        itemCerrarSesion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fix Manager");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(900, 500));
        setName("frmMenu"); // NOI18N
        setResizable(false);

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        pnlPrincipal.setMinimumSize(new java.awt.Dimension(810, 390));
        pnlPrincipal.setPreferredSize(new java.awt.Dimension(810, 390));

        imagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/favicon.png"))); // NOI18N

        favicon.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        favicon.setText("Fix Manager");

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(440, 440, 440)
                        .addComponent(imagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(400, 400, 400)
                        .addComponent(favicon, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(409, 409, 409))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(favicon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(imagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(280, 280, 280))
        );

        menuBar.setFocusable(false);

        menuUsuario.setText("Usuarios");
        menuUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menuUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuUsuario.setIconTextGap(0);
        menuUsuario.setMargin(new java.awt.Insets(5, 10, 5, 10));

        itemGestionarUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        itemGestionarUsuario.setText("Gestionar Usuarios");
        itemGestionarUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemGestionarUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        itemGestionarUsuario.setMargin(null);
        itemGestionarUsuario.setPreferredSize(new java.awt.Dimension(180, 30));
        itemGestionarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionarUsuarioActionPerformed(evt);
            }
        });
        menuUsuario.add(itemGestionarUsuario);

        menuBar.add(menuUsuario);

        menuCliente.setText("Clientes");
        menuCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menuCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuCliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuCliente.setIconTextGap(0);
        menuCliente.setMargin(new java.awt.Insets(5, 10, 5, 10));

        itemGestionarCliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        itemGestionarCliente.setText("Gestionar Clientes");
        itemGestionarCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemGestionarCliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        itemGestionarCliente.setMargin(null);
        itemGestionarCliente.setPreferredSize(new java.awt.Dimension(180, 30));
        itemGestionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionarClienteActionPerformed(evt);
            }
        });
        menuCliente.add(itemGestionarCliente);

        menuBar.add(menuCliente);

        menuEquipoMovil.setText("Equipos Móviles");
        menuEquipoMovil.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menuEquipoMovil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuEquipoMovil.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuEquipoMovil.setIconTextGap(0);
        menuEquipoMovil.setMargin(new java.awt.Insets(5, 10, 5, 10));

        itemGestionarEquipoMovil.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        itemGestionarEquipoMovil.setText("Gestionar Equipos Móviles");
        itemGestionarEquipoMovil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemGestionarEquipoMovil.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        itemGestionarEquipoMovil.setIconTextGap(0);
        itemGestionarEquipoMovil.setMargin(null);
        itemGestionarEquipoMovil.setPreferredSize(new java.awt.Dimension(180, 30));
        itemGestionarEquipoMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionarEquipoMovilActionPerformed(evt);
            }
        });
        menuEquipoMovil.add(itemGestionarEquipoMovil);

        menuBar.add(menuEquipoMovil);

        menuRecepcionEntrega.setText("Recepciones/Entregas");
        menuRecepcionEntrega.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menuRecepcionEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuRecepcionEntrega.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuRecepcionEntrega.setIconTextGap(0);
        menuRecepcionEntrega.setMargin(new java.awt.Insets(5, 10, 5, 10));

        itemGestionarRecepcionEntrega.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        itemGestionarRecepcionEntrega.setText("Gestionar Recepciones");
        itemGestionarRecepcionEntrega.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemGestionarRecepcionEntrega.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        itemGestionarRecepcionEntrega.setIconTextGap(0);
        itemGestionarRecepcionEntrega.setMargin(null);
        itemGestionarRecepcionEntrega.setPreferredSize(new java.awt.Dimension(180, 30));
        itemGestionarRecepcionEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionarRecepcionEntregaActionPerformed(evt);
            }
        });
        menuRecepcionEntrega.add(itemGestionarRecepcionEntrega);

        menuBar.add(menuRecepcionEntrega);

        menuReparacion.setText("Reparaciones");
        menuReparacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menuReparacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuReparacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuReparacion.setIconTextGap(0);
        menuReparacion.setMargin(new java.awt.Insets(5, 10, 5, 10));

        itemGestionarReparacion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        itemGestionarReparacion.setText("Gestionar Reparaciones");
        itemGestionarReparacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemGestionarReparacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        itemGestionarReparacion.setIconTextGap(0);
        itemGestionarReparacion.setMargin(null);
        itemGestionarReparacion.setPreferredSize(new java.awt.Dimension(180, 30));
        itemGestionarReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionarReparacionActionPerformed(evt);
            }
        });
        menuReparacion.add(itemGestionarReparacion);

        menuBar.add(menuReparacion);

        menuFactura.setText("Facturas");
        menuFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menuFactura.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuFactura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuFactura.setIconTextGap(0);
        menuFactura.setMargin(new java.awt.Insets(5, 10, 5, 10));

        itemGestionarFactura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        itemGestionarFactura.setText("Gestionar Facturas");
        itemGestionarFactura.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemGestionarFactura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        itemGestionarFactura.setIconTextGap(0);
        itemGestionarFactura.setMargin(null);
        itemGestionarFactura.setPreferredSize(new java.awt.Dimension(180, 30));
        itemGestionarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionarFacturaActionPerformed(evt);
            }
        });
        menuFactura.add(itemGestionarFactura);

        menuBar.add(menuFactura);

        menuCerrarSesion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        menuCerrarSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menuCerrarSesion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menuCerrarSesion.setMargin(new java.awt.Insets(5, 10, 5, 10));

        itemCerrarSesion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        itemCerrarSesion.setText("Cerrar Sesión");
        itemCerrarSesion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemCerrarSesion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        itemCerrarSesion.setMargin(null);
        itemCerrarSesion.setPreferredSize(new java.awt.Dimension(150, 30));
        itemCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCerrarSesionActionPerformed(evt);
            }
        });
        menuCerrarSesion.add(itemCerrarSesion);

        menuBar.add(menuCerrarSesion);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemGestionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarClienteActionPerformed
        ClienteCRUDPanel v = new ClienteCRUDPanel();
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new AbsoluteConstraints(0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarClienteActionPerformed

    private void itemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCerrarSesionActionPerformed
        Sesion.cerrarSesion();

        FrmLogin login = new FrmLogin();
        login.setLocationRelativeTo(null);
        login.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_itemCerrarSesionActionPerformed

    private void itemGestionarEquipoMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarEquipoMovilActionPerformed
        EquipoMovilCRUDPanel v = new EquipoMovilCRUDPanel();
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new AbsoluteConstraints(0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarEquipoMovilActionPerformed

    private void itemGestionarRecepcionEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarRecepcionEntregaActionPerformed
        RecepcionEntregaCRUDPanel v = new RecepcionEntregaCRUDPanel();
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new AbsoluteConstraints(0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarRecepcionEntregaActionPerformed

    private void itemGestionarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarFacturaActionPerformed
        FacturaCRUDPanel v = new FacturaCRUDPanel();
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new AbsoluteConstraints(0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarFacturaActionPerformed

    private void itemGestionarReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarReparacionActionPerformed
        ReparacionCRUDPanel v = new ReparacionCRUDPanel();
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new AbsoluteConstraints(0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarReparacionActionPerformed

    private void itemGestionarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarUsuarioActionPerformed
        UsuarioCRUDPanel v = new UsuarioCRUDPanel();
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new AbsoluteConstraints(0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarUsuarioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel favicon;
    private javax.swing.JLabel imagen1;
    private javax.swing.JMenuItem itemCerrarSesion;
    private javax.swing.JMenuItem itemGestionarCliente;
    private javax.swing.JMenuItem itemGestionarEquipoMovil;
    private javax.swing.JMenuItem itemGestionarFactura;
    private javax.swing.JMenuItem itemGestionarRecepcionEntrega;
    private javax.swing.JMenuItem itemGestionarReparacion;
    private javax.swing.JMenuItem itemGestionarUsuario;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuCerrarSesion;
    private javax.swing.JMenu menuCliente;
    private javax.swing.JMenu menuEquipoMovil;
    private javax.swing.JMenu menuFactura;
    private javax.swing.JMenu menuRecepcionEntrega;
    private javax.swing.JMenu menuReparacion;
    private javax.swing.JMenu menuUsuario;
    private javax.swing.JPanel pnlPrincipal;
    // End of variables declaration//GEN-END:variables
}
