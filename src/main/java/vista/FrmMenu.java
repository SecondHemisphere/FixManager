package vista;

import javax.swing.Box;

/**
 *
 * @author Cagua Derek
 */
public class FrmMenu extends javax.swing.JFrame {

    public FrmMenu() {
        initComponents();

        menuBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));

        menuBar.add(menuUsuario);
        menuBar.add(menuCliente);
        menuBar.add(menuEquipoMovil);
        menuBar.add(menuRecepcionEntrega);
        menuBar.add(menuReparacion);
        menuBar.add(menuFactura);

        menuBar.add(Box.createHorizontalGlue());

        menuBar.add(menuCerrarSesion);

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
        menuBar.setBackground(new java.awt.Color(80, 80, 80));
        menuBar.setForeground(java.awt.Color.WHITE);

        menuCerrarSesion.setOpaque(true);
        menuCerrarSesion.setBackground(new java.awt.Color(220, 53, 69));
        menuCerrarSesion.setForeground(java.awt.Color.WHITE);   
    }

    private void estilizarMenu(javax.swing.JMenu menu) {
        menu.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
        menu.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));

        menu.setOpaque(true);
        menu.setBackground(new java.awt.Color(80, 80, 80));
        menu.setForeground(java.awt.Color.WHITE);
    }

    private void estilizarItem(javax.swing.JMenuItem item) {
        item.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
        item.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20));
        item.setPreferredSize(new java.awt.Dimension(200, 30));

        item.setOpaque(true);
        item.setBackground(new java.awt.Color(80, 80, 80));
        item.setForeground(java.awt.Color.WHITE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        Imagen1 = new javax.swing.JLabel();
        lblFavicon = new javax.swing.JLabel();
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
        pnlPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Imagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/favicon.png"))); // NOI18N
        pnlPrincipal.add(Imagen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        lblFavicon.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        lblFavicon.setText("Fix Manager");
        pnlPrincipal.add(lblFavicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

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

        menuCerrarSesion.setText("Cerrar Sesión");
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
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemGestionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarClienteActionPerformed
    ClienteCRUDPanel v = new ClienteCRUDPanel();
    
    pnlPrincipal.removeAll();
    pnlPrincipal.add(v, new org.netbeans.lib.awtextra.AbsoluteConstraints(
    0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
    pnlPrincipal.revalidate();
    pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarClienteActionPerformed

    private void itemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCerrarSesionActionPerformed
        FrmLogin login = new FrmLogin();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_itemCerrarSesionActionPerformed

    private void itemGestionarEquipoMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarEquipoMovilActionPerformed
        EquipoMovilCRUDPanel v = new EquipoMovilCRUDPanel();

        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new org.netbeans.lib.awtextra.AbsoluteConstraints(
    0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarEquipoMovilActionPerformed

    private void itemGestionarRecepcionEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarRecepcionEntregaActionPerformed
        RecepcionEntregaCRUDPanel v = new RecepcionEntregaCRUDPanel();
    
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new org.netbeans.lib.awtextra.AbsoluteConstraints(
    0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarRecepcionEntregaActionPerformed

    private void itemGestionarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarFacturaActionPerformed
        FacturaCRUDPanel v = new FacturaCRUDPanel();
    
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new org.netbeans.lib.awtextra.AbsoluteConstraints(
    0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarFacturaActionPerformed

    private void itemGestionarReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarReparacionActionPerformed
        ReparacionCRUDPanel v = new ReparacionCRUDPanel();
    
        pnlPrincipal.removeAll();
        pnlPrincipal.add(v, new org.netbeans.lib.awtextra.AbsoluteConstraints(
    0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }//GEN-LAST:event_itemGestionarReparacionActionPerformed

    private void itemGestionarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemGestionarUsuarioActionPerformed
    UsuarioCRUDPanel v = new UsuarioCRUDPanel();
    
    pnlPrincipal.removeAll();
    pnlPrincipal.add(v, new org.netbeans.lib.awtextra.AbsoluteConstraints(
    0, 0, pnlPrincipal.getWidth(), pnlPrincipal.getHeight()));
    pnlPrincipal.revalidate();
    pnlPrincipal.repaint();                                                 
    }//GEN-LAST:event_itemGestionarUsuarioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen1;
    private javax.swing.JMenuItem itemCerrarSesion;
    private javax.swing.JMenuItem itemGestionarCliente;
    private javax.swing.JMenuItem itemGestionarEquipoMovil;
    private javax.swing.JMenuItem itemGestionarFactura;
    private javax.swing.JMenuItem itemGestionarRecepcionEntrega;
    private javax.swing.JMenuItem itemGestionarReparacion;
    private javax.swing.JMenuItem itemGestionarUsuario;
    private javax.swing.JLabel lblFavicon;
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
