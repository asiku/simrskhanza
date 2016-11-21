/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJadwal.java
 *
 * Created on May 22, 2010, 10:25:16 PM
 */

package keuangan;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public class DlgPengaturanRekening extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String Tindakan_Ralan, Laborat_Ralan, Radiologi_Ralan, Obat_Ralan, Registrasi_Ralan, 
            Tambahan_Ralan, Potongan_Ralan, Tindakan_Ranap, Laborat_Ranap, Radiologi_Ranap, 
            Obat_Ranap, Registrasi_Ranap, Tambahan_Ranap, Potongan_Ranap, Retur_Obat_Ranap, 
            Resep_Pulang_Ranap, Kamar_Inap, Operasi_Ranap, Harian_Ranap, Pengadaan_Obat, 
            Pemesanan_Obat, Kontra_Pemesanan_Obat, Bayar_Pemesanan_Obat, Penjualan_Obat, 
            Piutang_Obat, Kontra_Piutang_Obat, Retur_Ke_Suplayer, Kontra_Retur_Ke_Suplayer, 
            Retur_Dari_pembeli, Kontra_Retur_Dari_Pembeli, 
            Retur_Piutang_Obat, Kontra_Retur_Piutang_Obat,Pengadaan_Ipsrs, Stok_Keluar_Ipsrs, 
            Kontra_Stok_Keluar_Ipsrs,Uang_Muka_Ralan,Piutang_Pasien_Ralan,Uang_Muka_Ranap,
            Piutang_Pasien_Ranap,Bayar_Piutang_Pasien,Service_Ranap;
    private DlgRekeningTahun rekening=new DlgRekeningTahun(null,false);

    /** Creates new form DlgJadwal
     * @param parent
     * @param modal */
    public DlgPengaturanRekening(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Letak Akun Rekening","Kode Akun","Nama Akun","Tipe","Balance"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengaturan.setModel(tabMode);
        tbPengaturan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengaturan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 5; i++) {
            TableColumn column = tbPengaturan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(500);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(300);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(50);
            }
        }

        tbPengaturan.setDefaultRenderer(Object.class, new WarnaTable());
        
        rekening.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPengaturanRekening")){
                    if(rekening.getTabel().getSelectedRow()!= -1){    
                        if(tbPengaturan.getSelectedColumn()==1){
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturan.getSelectedRow(),1);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturan.getSelectedRow(),2);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),3).toString(),tbPengaturan.getSelectedRow(),3);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),4).toString(),tbPengaturan.getSelectedRow(),4);
                        }                    
                    }   
                    tbPengaturan.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        rekening.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgPengaturanRekening")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        try {
            ps=koneksi.prepareStatement("select * from set_akun");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPengaturan = new widget.Table();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengaturan Rekening ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPengaturan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPengaturan.setName("tbPengaturan"); // NOI18N
        tbPengaturan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengaturanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPengaturan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 56));
        panelGlass8.setLayout(null);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Update");
        BtnSimpan.setToolTipText("Alt+U");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);
        BtnSimpan.setBounds(6, 10, 100, 30);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);
        BtnKeluar.setBounds(108, 10, 100, 30);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        Tindakan_Ralan=tbPengaturan.getValueAt(0,1).toString();
        Laborat_Ralan=tbPengaturan.getValueAt(1,1).toString();
        Radiologi_Ralan=tbPengaturan.getValueAt(2,1).toString();
        Obat_Ralan=tbPengaturan.getValueAt(3,1).toString();
        Registrasi_Ralan=tbPengaturan.getValueAt(4,1).toString();
        Tambahan_Ralan=tbPengaturan.getValueAt(5,1).toString();
        Potongan_Ralan=tbPengaturan.getValueAt(6,1).toString();
        Uang_Muka_Ralan=tbPengaturan.getValueAt(7,1).toString();
        Piutang_Pasien_Ralan=tbPengaturan.getValueAt(8,1).toString();
        Tindakan_Ranap=tbPengaturan.getValueAt(9,1).toString();
        Laborat_Ranap=tbPengaturan.getValueAt(10,1).toString();
        Radiologi_Ranap=tbPengaturan.getValueAt(11,1).toString();
        Obat_Ranap=tbPengaturan.getValueAt(12,1).toString();
        Registrasi_Ranap=tbPengaturan.getValueAt(13,1).toString();
        Tambahan_Ranap=tbPengaturan.getValueAt(14,1).toString();
        Potongan_Ranap=tbPengaturan.getValueAt(15,1).toString();
        Retur_Obat_Ranap=tbPengaturan.getValueAt(16,1).toString();
        Resep_Pulang_Ranap=tbPengaturan.getValueAt(17,1).toString();
        Kamar_Inap=tbPengaturan.getValueAt(18,1).toString();
        Operasi_Ranap=tbPengaturan.getValueAt(19,1).toString();
        Harian_Ranap=tbPengaturan.getValueAt(20,1).toString();
        Uang_Muka_Ranap=tbPengaturan.getValueAt(21,1).toString();
        Piutang_Pasien_Ranap=tbPengaturan.getValueAt(22,1).toString();
        Pengadaan_Obat=tbPengaturan.getValueAt(23,1).toString();
        Pemesanan_Obat=tbPengaturan.getValueAt(24,1).toString();
        Kontra_Pemesanan_Obat=tbPengaturan.getValueAt(25,1).toString();
        Bayar_Pemesanan_Obat=tbPengaturan.getValueAt(26,1).toString();
        Penjualan_Obat=tbPengaturan.getValueAt(27,1).toString();
        Piutang_Obat=tbPengaturan.getValueAt(28,1).toString();
        Kontra_Piutang_Obat=tbPengaturan.getValueAt(29,1).toString();
        Retur_Ke_Suplayer=tbPengaturan.getValueAt(30,1).toString();
        Kontra_Retur_Ke_Suplayer=tbPengaturan.getValueAt(31,1).toString();
        Retur_Dari_pembeli=tbPengaturan.getValueAt(32,1).toString();
        Kontra_Retur_Dari_Pembeli=tbPengaturan.getValueAt(33,1).toString();
        Retur_Piutang_Obat=tbPengaturan.getValueAt(34,1).toString();
        Kontra_Retur_Piutang_Obat=tbPengaturan.getValueAt(35,1).toString();
        Pengadaan_Ipsrs=tbPengaturan.getValueAt(36,1).toString();
        Stok_Keluar_Ipsrs=tbPengaturan.getValueAt(37,1).toString();
        Kontra_Stok_Keluar_Ipsrs=tbPengaturan.getValueAt(38,1).toString();
        Bayar_Piutang_Pasien=tbPengaturan.getValueAt(39,1).toString();
        Service_Ranap=tbPengaturan.getValueAt(40,1).toString();
        
        if(Tindakan_Ralan.equals("")||Laborat_Ralan.equals("")||Radiologi_Ralan.equals("")||Obat_Ralan.equals("")||
            Registrasi_Ralan.equals("")||Tambahan_Ralan.equals("")||Potongan_Ralan.equals("")||Uang_Muka_Ralan.equals("")||
            Piutang_Pasien_Ralan.equals("")||Tindakan_Ranap.equals("")||Laborat_Ranap.equals("")||Radiologi_Ranap.equals("")||
            Obat_Ranap.equals("")||Registrasi_Ranap.equals("")||Tambahan_Ranap.equals("")||Potongan_Ranap.equals("")||
            Retur_Obat_Ranap.equals("")||Resep_Pulang_Ranap.equals("")||Kamar_Inap.equals("")||Operasi_Ranap.equals("")||
            Harian_Ranap.equals("")||Uang_Muka_Ranap.equals("")||Piutang_Pasien_Ranap.equals("")||Pengadaan_Obat.equals("")||
            Pemesanan_Obat.equals("")||Kontra_Pemesanan_Obat.equals("")||Bayar_Pemesanan_Obat.equals("")||Penjualan_Obat.equals("")||
            Piutang_Obat.equals("")||Kontra_Piutang_Obat.equals("")||Retur_Ke_Suplayer.equals("")||Retur_Dari_pembeli.equals("")||
            Retur_Piutang_Obat.equals("")||Kontra_Retur_Piutang_Obat.equals("")||Pengadaan_Ipsrs.equals("")||
            Stok_Keluar_Ipsrs.equals("")||Kontra_Stok_Keluar_Ipsrs.equals("")||Bayar_Piutang_Pasien.equals("")||
            Kontra_Retur_Ke_Suplayer.equals("")||Kontra_Retur_Dari_Pembeli.equals("")||Service_Ranap.equals("")){
                JOptionPane.showMessageDialog(null,"Silahkan lengkapi seluruh data Akun...!!!!");
                tbPengaturan.requestFocus();
        }else{
            Sequel.queryu("delete from set_akun");
            Sequel.menyimpan("set_akun","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",41,new String[]{
                Tindakan_Ralan,Laborat_Ralan,Radiologi_Ralan,Obat_Ralan,Registrasi_Ralan,Tambahan_Ralan,
                Potongan_Ralan,Uang_Muka_Ralan,Piutang_Pasien_Ralan,Tindakan_Ranap,Laborat_Ranap,Radiologi_Ranap,
                Obat_Ranap,Registrasi_Ranap,Tambahan_Ranap,Potongan_Ranap,Retur_Obat_Ranap,Resep_Pulang_Ranap,
                Kamar_Inap,Operasi_Ranap,Harian_Ranap,Uang_Muka_Ranap,Piutang_Pasien_Ranap,Pengadaan_Obat,
                Pemesanan_Obat,Kontra_Pemesanan_Obat,Bayar_Pemesanan_Obat,Penjualan_Obat,Piutang_Obat,
                Kontra_Piutang_Obat,Retur_Ke_Suplayer,Kontra_Retur_Ke_Suplayer,Retur_Dari_pembeli,
                Kontra_Retur_Dari_Pembeli,Retur_Piutang_Obat,Kontra_Retur_Piutang_Obat,Pengadaan_Ipsrs,
                Stok_Keluar_Ipsrs,Kontra_Stok_Keluar_Ipsrs,Bayar_Piutang_Pasien,Service_Ranap
            });
            JOptionPane.showMessageDialog(null,"Proses selesai...!!!!");
        }
            
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbPengaturanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengaturanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                var.setform("DlgPengaturanRekening");
                rekening.emptTeks();
                rekening.tampil();
                rekening.isCek();
                rekening.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                rekening.setLocationRelativeTo(internalFrame1);
                rekening.setVisible(true);
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                tabMode.setValueAt("",tbPengaturan.getSelectedRow(),1);
                tabMode.setValueAt("",tbPengaturan.getSelectedRow(),2);
            }
        }
    }//GEN-LAST:event_tbPengaturanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPengaturanRekening dialog = new DlgPengaturanRekening(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.ScrollPane Scroll;
    private widget.InternalFrame internalFrame1;
    private widget.panelisi panelGlass8;
    private widget.Table tbPengaturan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{            
            rs=ps.executeQuery();
            if(rs.next()){
                Tindakan_Ralan=rs.getString("Tindakan_Ralan");
                Laborat_Ralan=rs.getString("Laborat_Ralan");
                Radiologi_Ralan=rs.getString("Radiologi_Ralan");
                Obat_Ralan=rs.getString("Obat_Ralan");
                Registrasi_Ralan=rs.getString("Registrasi_Ralan");
                Tambahan_Ralan=rs.getString("Tambahan_Ralan");
                Potongan_Ralan=rs.getString("Potongan_Ralan");
                Uang_Muka_Ralan=rs.getString("Uang_Muka_Ralan");
                Piutang_Pasien_Ralan=rs.getString("Piutang_Pasien_Ralan");
                Tindakan_Ranap=rs.getString("Tindakan_Ranap");
                Laborat_Ranap=rs.getString("Laborat_Ranap");
                Radiologi_Ranap=rs.getString("Radiologi_Ranap");
                Obat_Ranap=rs.getString("Obat_Ranap");
                Registrasi_Ranap=rs.getString("Registrasi_Ranap");
                Tambahan_Ranap=rs.getString("Tambahan_Ranap");
                Potongan_Ranap=rs.getString("Potongan_Ranap");
                Retur_Obat_Ranap=rs.getString("Retur_Obat_Ranap");
                Resep_Pulang_Ranap=rs.getString("Resep_Pulang_Ranap");
                Kamar_Inap=rs.getString("Kamar_Inap");
                Operasi_Ranap=rs.getString("Operasi_Ranap");
                Harian_Ranap=rs.getString("Harian_Ranap");
                Pengadaan_Obat=rs.getString("Pengadaan_Obat");
                Pemesanan_Obat=rs.getString("Pemesanan_Obat");
                Kontra_Pemesanan_Obat=rs.getString("Kontra_Pemesanan_Obat");
                Bayar_Pemesanan_Obat=rs.getString("Bayar_Pemesanan_Obat");
                Penjualan_Obat=rs.getString("Penjualan_Obat");
                Piutang_Obat=rs.getString("Piutang_Obat");
                Kontra_Piutang_Obat=rs.getString("Kontra_Piutang_Obat");
                Retur_Ke_Suplayer=rs.getString("Retur_Ke_Suplayer");
                Kontra_Retur_Ke_Suplayer=rs.getString("Kontra_Retur_Ke_Suplayer");
                Retur_Dari_pembeli=rs.getString("Retur_Dari_pembeli");
                Kontra_Retur_Dari_Pembeli=rs.getString("Kontra_Retur_Dari_Pembeli");
                Retur_Piutang_Obat=rs.getString("Retur_Piutang_Obat");
                Kontra_Retur_Piutang_Obat=rs.getString("Kontra_Retur_Piutang_Obat");
                Pengadaan_Ipsrs=rs.getString("Pengadaan_Ipsrs");
                Stok_Keluar_Ipsrs=rs.getString("Stok_Keluar_Ipsrs");
                Kontra_Stok_Keluar_Ipsrs=rs.getString("Kontra_Stok_Keluar_Ipsrs");
                Uang_Muka_Ranap=rs.getString("Uang_Muka_Ranap");
                Piutang_Pasien_Ranap=rs.getString("Piutang_Pasien_Ranap"); 
                Bayar_Piutang_Pasien=rs.getString("Bayar_Piutang_Pasien");
                Service_Ranap=rs.getString("Service_Ranap");
            }else{
                Tindakan_Ralan="";
                Laborat_Ralan="";
                Radiologi_Ralan="";
                Obat_Ralan="";
                Registrasi_Ralan="";
                Tambahan_Ralan="";
                Potongan_Ralan="";
                Uang_Muka_Ralan="";
                Piutang_Pasien_Ralan="";
                Tindakan_Ranap="";
                Laborat_Ranap="";
                Radiologi_Ranap="";
                Obat_Ranap="";
                Registrasi_Ranap="";
                Tambahan_Ranap="";
                Potongan_Ranap="";
                Retur_Obat_Ranap="";
                Resep_Pulang_Ranap="";
                Kamar_Inap="";
                Operasi_Ranap="";
                Harian_Ranap="";
                Uang_Muka_Ranap="";
                Piutang_Pasien_Ranap="";
                Pengadaan_Obat="";
                Pemesanan_Obat="";
                Kontra_Pemesanan_Obat="";
                Bayar_Pemesanan_Obat="";
                Penjualan_Obat="";
                Piutang_Obat="";
                Kontra_Piutang_Obat="";
                Retur_Ke_Suplayer="";
                Kontra_Retur_Ke_Suplayer="";
                Retur_Dari_pembeli="";
                Kontra_Retur_Dari_Pembeli="";
                Retur_Piutang_Obat="";
                Kontra_Retur_Piutang_Obat="";
                Pengadaan_Ipsrs="";
                Stok_Keluar_Ipsrs="";
                Kontra_Stok_Keluar_Ipsrs="";
                Bayar_Piutang_Pasien="";
                Service_Ranap="";
            }
            tabMode.addRow(new Object[]{" Akun Pendapatan Tindakan pada menu Billing Rawat Jalan",Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Laborat pada menu Billing Rawat Jalan",Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Radiologi pada menu Billing Rawat Jalan",Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Obat pada menu Billing Rawat Jalan",Obat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Obat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Obat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Obat_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Registrasi pada menu Billing Rawat Jalan",Registrasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Registrasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Registrasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Registrasi_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Tambahan Biaya pada menu Billing Rawat Jalan",Tambahan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tambahan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tambahan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tambahan_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Potongan Biaya pada Billing menu Rawat Jalan",Potongan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Potongan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Potongan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Potongan_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Uang Muka Pasien pada Billing menu Rawat Jalan",Uang_Muka_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Uang_Muka_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Uang_Muka_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Uang_Muka_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Piutang Pasien pada Billing menu Rawat Jalan",Piutang_Pasien_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Piutang_Pasien_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Piutang_Pasien_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Piutang_Pasien_Ralan)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Tindakan pada menu Billing Rawat Inap",Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Laborat pada menu Billing Rawat Inap",Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Radiologi pada menu Billing Rawat Inap",Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Obat pada menu Billing Rawat Inap",Obat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Obat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Obat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Obat_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Registrasi pada menu Billing Rawat Inap",Registrasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Registrasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Registrasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Registrasi_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Tambahan Biaya pada menu Billing Rawat Inap",Tambahan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tambahan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tambahan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tambahan_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Potongan Biaya pada menu Billing Rawat Inap",Potongan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Potongan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Potongan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Potongan_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Retur Obat pada menu Billing Rawat Inap",Retur_Obat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Obat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Obat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Obat_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Resep Pulang pada menu Billing Rawat Inap",Resep_Pulang_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Resep_Pulang_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Resep_Pulang_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Resep_Pulang_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Kamar Inap pada menu Billing Rawat Inap",Kamar_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kamar_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kamar_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kamar_Inap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Operasi pada menu Billing Rawat Inap",Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Harian Ranap pada menu Billing Rawat Inap",Harian_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Harian_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Harian_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Harian_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Uang Muka Pasien pada Billing menu Rawat Inap",Uang_Muka_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Uang_Muka_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Uang_Muka_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Uang_Muka_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Piutang Pasien pada Billing menu Rawat Inap",Piutang_Pasien_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Piutang_Pasien_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Piutang_Pasien_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Piutang_Pasien_Ranap)
            });
            tabMode.addRow(new Object[]{" Akun Pengadaan Obat & BHP pada menu Pengadaan Obat & BHP",Pengadaan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengadaan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengadaan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengadaan_Obat)
            });
            tabMode.addRow(new Object[]{" Akun Pemesanan Obat & BHP pada menu Pemesanan Obat & BHP",Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" Kontra Akun Pemesanan Obat & BHP pada menu Pemesanan Obat & BHP",Kontra_Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" Akun Bayar Pemesanan Obat/BHP pada menu Bayar Pesan Obat/BHP",Bayar_Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" Akun Penjualan Obat & BHP pada menu Penjualan Obat & BHP",Penjualan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penjualan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penjualan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penjualan_Obat)
            });
            tabMode.addRow(new Object[]{" Akun Piutang Obat & BHP pada menu Piutang Obat & BHP",Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" Kontra Akun Piutang Obat & BHP pada menu Piutang Obat & BHP",Kontra_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" Akun Retur Obat & BHP ke Suplier pada menu Retur Ke Suplier",Retur_Ke_Suplayer,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Ke_Suplayer),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Ke_Suplayer),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Ke_Suplayer)
            });
            tabMode.addRow(new Object[]{" Kontra Akun Retur Obat & BHP ke Suplier pada menu Retur Ke Suplier",Kontra_Retur_Ke_Suplayer,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer)
            });
            tabMode.addRow(new Object[]{" Akun Retur Obat & BHP dari Pasien/Pembeli pada menu Retur Dari Pembeli",Retur_Dari_pembeli,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Dari_pembeli),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Dari_pembeli),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Dari_pembeli)
            });
            tabMode.addRow(new Object[]{" Kontra Akun Retur Obat & BHP dari Pasien/Pembeli pada menu Retur Dari Pembeli",Kontra_Retur_Dari_Pembeli,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli)
            });
            tabMode.addRow(new Object[]{" Akun Retur Piutang Obat & BHP pada menu Retur Piutang Pembeli",Retur_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" Kontra Akun Retur Piutang Obat & BHP pada menu Retur Piutang Pembeli",Kontra_Retur_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" Akun Pengadaan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Pengadaan Barang",Pengadaan_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengadaan_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengadaan_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengadaan_Ipsrs)
            });
            tabMode.addRow(new Object[]{" Akun Stok Keluar Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Stok Keluar",Stok_Keluar_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Stok_Keluar_Ipsrs)
            });
            tabMode.addRow(new Object[]{" Kontra Akun Stok Keluar Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Stok Keluar",Kontra_Stok_Keluar_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs)
            });        
            tabMode.addRow(new Object[]{" Akun Pembayaran Piutang Pasien pada menu Piutang Pasien",Bayar_Piutang_Pasien,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Piutang_Pasien),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Piutang_Pasien),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Piutang_Pasien)
            });
            tabMode.addRow(new Object[]{" Akun Pendapatan Biaya Service pada menu Billing Rawat Inap",Service_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Service_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Service_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Service_Ranap)
            });            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    public void isCek(){
        BtnSimpan.setEnabled(var.getpengaturan_rekening());
    }
        
    
}