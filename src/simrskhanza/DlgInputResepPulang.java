/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */

package simrskhanza;

import inventory.DlgBarang;
import inventory.DlgCariKonversi;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgInputResepPulang extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psobat,psobatasuransi,psOP,psi1,psi2,psi3;
    private ResultSet rs;
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgInputResepPulang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"Jumlah","Kode Barang","Nama Barang","Satuan","Dosis","Letak Barang","Harga(Rp)","Jenis Obat"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==4)) {
                    a=true;
                }
                return a;
             }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 8; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(85);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(60);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(130);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(100);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        } 
        try {
            psobat=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"+
                    " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"+
                    " databarang.letak_barang from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " where databarang.status='1' and databarang.kode_brng like ? or "+
                    " databarang.status='1' and databarang.nama_brng like ? or "+
                    " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            psobatasuransi=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                    " databarang.letak_barang from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                    " where databarang.status='1' and databarang.kode_brng like ? or "+
                    " databarang.status='1' and databarang.nama_brng like ? or "+
                    " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng"); 
            psOP=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat, resep_pulang.harga,"+
                " databarang.letak_barang,resep_pulang.jml_barang,resep_pulang.dosis "+
                " from databarang inner join jenis inner join resep_pulang on databarang.kdjns=jenis.kdjns  "+
                " and databarang.kode_brng=resep_pulang.kode_brng where resep_pulang.no_rawat=? order by databarang.nama_brng");
            psi1= koneksi.prepareStatement("insert into resep_pulang values(?,?,?,?,?,?)");
            psi2= koneksi.prepareStatement("insert into gudangbarang values(?,?,?)");
            psi3= koneksi.prepareStatement("update gudangbarang set stok=stok-? where kode_brng=? and kd_bangsal=?");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private DlgBarang barang=new DlgBarang(null,false);
    private String bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
    private double x=0,y=0,z=0,stokbarang=0,kenaikan=0;
    private int jml=0,i=0,index;
    private double[] jumlah,harga;
    private String[] kodebarang,namabarang,kodesatuan,letakbarang,namajenis,dosis;


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppOrder = new javax.swing.JMenu();
        ppOrderKode = new javax.swing.JMenuItem();
        ppOrderNama = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        ppHapusObat = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        TKdPny = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        KdPj = new widget.TextBox();
        kelas = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSeek5 = new widget.Button();
        BtnSimpan = new widget.Button();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppOrder.setBackground(new java.awt.Color(242, 242, 242));
        ppOrder.setForeground(new java.awt.Color(102, 51, 0));
        ppOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrder.setText("Urutkan Berdasar");
        ppOrder.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrder.setIconTextGap(8);
        ppOrder.setName("ppOrder"); // NOI18N
        ppOrder.setPreferredSize(new java.awt.Dimension(200, 25));

        ppOrderKode.setBackground(new java.awt.Color(255, 255, 255));
        ppOrderKode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderKode.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderKode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderKode.setText("Kode Obat");
        ppOrderKode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderKode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderKode.setIconTextGap(8);
        ppOrderKode.setName("ppOrderKode"); // NOI18N
        ppOrderKode.setPreferredSize(new java.awt.Dimension(200, 25));
        ppOrderKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderKodeActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderKode);

        ppOrderNama.setBackground(new java.awt.Color(255, 255, 255));
        ppOrderNama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppOrderNama.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderNama.setText("Nama Obat");
        ppOrderNama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderNama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderNama.setIconTextGap(8);
        ppOrderNama.setName("ppOrderNama"); // NOI18N
        ppOrderNama.setPreferredSize(new java.awt.Dimension(200, 25));
        ppOrderNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderNamaActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderNama);

        Popup.add(ppOrder);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(102, 51, 0));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppHapusObat.setBackground(new java.awt.Color(255, 255, 255));
        ppHapusObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusObat.setForeground(new java.awt.Color(102, 51, 0));
        ppHapusObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppHapusObat.setText("Hapus Obat Terpilih");
        ppHapusObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusObat.setIconTextGap(8);
        ppHapusObat.setName("ppHapusObat"); // NOI18N
        ppHapusObat.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusObatActionPerformed(evt);
            }
        });
        Popup.add(ppHapusObat);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setSelectionColor(new java.awt.Color(255, 255, 255));

        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.setSelectionColor(new java.awt.Color(255, 255, 255));

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setSelectionColor(new java.awt.Color(255, 255, 255));

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        Jam.setSelectionColor(new java.awt.Color(255, 255, 255));

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N
        KdPj.setSelectionColor(new java.awt.Color(255, 255, 255));
        KdPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPjKeyPressed(evt);
            }
        });

        kelas.setHighlighter(null);
        kelas.setName("kelas"); // NOI18N
        kelas.setSelectionColor(new java.awt.Color(255, 255, 255));
        kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Resep Pulang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setComponentPopupMenu(Popup);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(376, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('4');
        BtnSeek5.setToolTipText("Alt+4");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek5);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label12);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Utama", "VIP", "VVIP", "Beli Luar", "Karyawan" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(100, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        panelisi3.add(Jeniskelas);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void ppOrderKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderKodeActionPerformed
        tampil();
    }//GEN-LAST:event_ppOrderKodeActionPerformed

    private void ppOrderNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderNamaActionPerformed
        tampil();
    }//GEN-LAST:event_ppOrderNamaActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());           
    }//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TKdPny.getText().trim().equals("")){
            Valid.textKosong(TCari,"Data");
        }else if(bangsal.equals("")){
            Valid.textKosong(TCari,"Lokasi");
        }else{
            try {      
                koneksi.setAutoCommit(false);                
                for(i=0;i<tbKamar.getRowCount();i++){ 
                    if(Valid.SetAngka(tbKamar.getValueAt(i,0).toString())>0){
                            psi1.setString(1,TNoRw.getText());
                            psi1.setString(2,tbKamar.getValueAt(i,1).toString());
                            psi1.setString(3,tbKamar.getValueAt(i,0).toString());
                            psi1.setString(4,tbKamar.getValueAt(i,6).toString());
                            psi1.setDouble(5,Double.parseDouble(tbKamar.getValueAt(i,6).toString())*Double.parseDouble(tbKamar.getValueAt(i,0).toString()));
                            psi1.setString(6,tbKamar.getValueAt(i,4).toString());
                            psi1.executeUpdate();  
                            try{
                               psi2.setString(1,tbKamar.getValueAt(i,1).toString());
                               psi2.setString(2,bangsal);
                               psi2.setDouble(3,-Double.parseDouble(tbKamar.getValueAt(i,0).toString()));
                               psi2.executeUpdate();
                            }catch(NumberFormatException | SQLException ex){
                                psi3.setDouble(1,Double.parseDouble(tbKamar.getValueAt(i,0).toString()));
                                psi3.setString(2,tbKamar.getValueAt(i,1).toString());
                                psi3.setString(3,bangsal);
                                psi3.executeUpdate();                                 
                            }
                    }
                }  
                koneksi.setAutoCommit(true);
                dispose();
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Kemungkinan ada data yang sama dimasukkan sebelumnya...!");
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    DlgCariKonversi carikonversi=new DlgCariKonversi(null,false);
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek5KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbKamar.getRowCount();i++){ 
                tbKamar.setValueAt("",i,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void ppHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusObatActionPerformed
           try{
                i=tbKamar.getSelectedRow();
                if(bangsal.equals("")){
                    Valid.textKosong(TCari,"Lokasi");
                }else if(i!= -1){
                    if(!tbKamar.getValueAt(i,0).toString().equals("")){
                        Sequel.queryu("delete from resep_pulang where no_rawat='"+TNoRw.getText()+"' and kode_brng='"+tbKamar.getValueAt(i,1).toString()+"' ");
                       // Sequel.mengedit("databarang","kode_brng='"+tbKamar.getValueAt(i,1).toString()+"'","stok=stok+"+tbKamar.getValueAt(i,0).toString()+"");                    
                        Sequel.menyimpan("gudangbarang","'"+tbKamar.getValueAt(i,1).toString()+"','"+bangsal+"','"+Double.parseDouble(tbKamar.getValueAt(i,0).toString())+"'", 
                                        "stok=stok+'"+Double.parseDouble(tbKamar.getValueAt(i,0).toString())+"'","kode_brng='"+tbKamar.getValueAt(i,1).toString()+"' and kd_bangsal='"+bangsal+"'");
                        setObatPasien();
                        tampil();                          
                    }                    
                }                
            }catch(NumberFormatException e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
}//GEN-LAST:event_ppHapusObatActionPerformed

private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
       tampil(); 
}//GEN-LAST:event_JeniskelasItemStateChanged

private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
        Valid.pindah(evt, TCari,BtnKeluar);
}//GEN-LAST:event_JeniskelasKeyPressed

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                dispose();
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_tbKamarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void KdPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPjKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPjKeyPressed

    private void kelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgInputResepPulang dialog = new DlgInputResepPulang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.TextBox Jam;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox KdPj;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKdPny;
    private widget.TextBox TNoRw;
    private widget.TextBox Tanggal;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kelas;
    private widget.Label label12;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppHapusObat;
    private javax.swing.JMenu ppOrder;
    private javax.swing.JMenuItem ppOrderKode;
    private javax.swing.JMenuItem ppOrderNama;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        jml=0;
        for(i=0;i<tbKamar.getRowCount();i++){
            if(!tbKamar.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        
        jumlah=null;
        jumlah=new double[jml];
        harga=null;
        harga=new double[jml];
        kodebarang=null;
        kodebarang=new String[jml];
        namabarang=null;
        namabarang=new String[jml];
        kodesatuan=null;
        kodesatuan=new String[jml];
        letakbarang=null;
        letakbarang=new String[jml];
        namajenis=null;
        namajenis=new String[jml];
        dosis=null;
        dosis=new String[jml];
        
        index=0;        
        for(i=0;i<tbKamar.getRowCount();i++){
            if(!tbKamar.getValueAt(i,0).toString().equals("")){
                jumlah[index]=Double.parseDouble(tbKamar.getValueAt(i,0).toString());
                kodebarang[index]=tbKamar.getValueAt(i,1).toString();
                namabarang[index]=tbKamar.getValueAt(i,2).toString();
                kodesatuan[index]=tbKamar.getValueAt(i,3).toString();
                dosis[index]=tbKamar.getValueAt(i,4).toString();
                letakbarang[index]=tbKamar.getValueAt(i,5).toString();
                harga[index]=Double.parseDouble(tbKamar.getValueAt(i,6).toString());
                namajenis[index]=tbKamar.getValueAt(i,7).toString();
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode);
        
        for(i=0;i<jml;i++){
            Object[] data={jumlah[i],kodebarang[i],namabarang[i],
                           kodesatuan[i],dosis[i],letakbarang[i],harga[i],namajenis[i]};
            tabMode.addRow(data);
        }
        
        try{
            psobat.setString(1,"%"+TCari.getText().trim()+"%");
            psobat.setString(2,"%"+TCari.getText().trim()+"%");
            psobat.setString(3,"%"+TCari.getText().trim()+"%");
            rs=psobat.executeQuery();
            if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               "",
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("kelas1")),
                               rs.getString("nama")});
                }
            }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               "",
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("kelas2")),
                               rs.getString("nama")});
                }
            }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               "",
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("kelas3")),
                               rs.getString("nama")});
                }
            }else if(Jeniskelas.getSelectedItem().equals("Utama")){
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               "",
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("utama")),
                               rs.getString("nama")});
                }
            }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               "",
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("vip")),
                               rs.getString("nama")});
                }
             }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               "",
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("vvip")),
                               rs.getString("nama")});
                }
            }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               "",
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("beliluar")),
                               rs.getString("nama")});
                }
            }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                while(rs.next()){
                    tabMode.addRow(new Object[]{"",
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               "",
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("karyawan")),
                               rs.getString("nama")});
                }
            }  
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    public void emptTeks() {
        TCari.setText("");
        TCari.requestFocus();
    }

    public JTable getTable(){
        return tbKamar;
    }
    
    public void isCek(){        
        BtnTambah.setEnabled(var.getobat());
    }
    
    public void setNoRm(String norwt,String penyakit, String tanggal, String jam) {        
        TKdPny.setText(penyakit);
        TNoRw.setText(norwt);
        setObatPasien();
        bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
        Tanggal.setText(tanggal);
        Jam.setText(jam);
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt));
        kelas.setText(Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "+
                "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",norwt));
        if(kelas.getText().equals("Kelas 1")){
            Jeniskelas.setSelectedItem("Kelas 1");
        }else if(kelas.getText().equals("Kelas 2")){
            Jeniskelas.setSelectedItem("Kelas 2");
        }else if(kelas.getText().equals("Kelas 3")){
            Jeniskelas.setSelectedItem("Kelas 3");
        }else if(kelas.getText().equals("Kelas Utama")){
            Jeniskelas.setSelectedItem("Utama");
        }else if(kelas.getText().equals("Kelas VIP")){
            Jeniskelas.setSelectedItem("VIP");
        }else if(kelas.getText().equals("Kelas VVIP")){
            Jeniskelas.setSelectedItem("VVIP");
        }        
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ranap where kd_pj='"+KdPj.getText()+"' and kelas='"+kelas.getText()+"'");
    }
    
    private void setObatPasien(){
        Valid.tabelKosong(tabMode);
        try{
            psOP.setString(1,TNoRw.getText());
            rs=psOP.executeQuery();
            while(rs.next()){                
                tabMode.addRow(new Object[]{rs.getDouble("jml_barang"),
                               rs.getString("kode_brng"),
                               rs.getString("nama_brng"),
                               rs.getString("kode_sat"),
                               rs.getString("dosis"),
                               rs.getString("letak_barang"),
                               Valid.SetAngka2(rs.getDouble("harga")),
                               rs.getString("nama")});
            }     
        }catch(Exception e){
            System.out.println("Catatan Obat Pasien : "+e);
        }
    }
}
