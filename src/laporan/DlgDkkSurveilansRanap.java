/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package laporan;

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
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author perpustakaan
 */
public final class DlgDkkSurveilansRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i=0,hr0s7=0,hr8s28=0,kr1th=0,th1s4=0,th5s9=0,th10s14=0,th15s19=0,th20s44=0,th45s54=0,th55s59=0,
                th60s69=0,th70plus=0,laki=0,per=0,jml=0,ttl=0,jmltotal,meninggal;
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgDkkSurveilansRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowRwJlDr={"No.","Kode ICD 10","Jenis Penyakit","0-7 Hr","8-28 Hr","< 1","1-4","5-9","10-14","15-19",
                            "20-44","45-54","55-59","60-69","70+","Laki","Perp","Jumlah","Ttl.Kunjungan","Meninggal"};
        tabMode=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(100);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(45);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        try {
            ps=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as nm_penyakit from diagnosa_pasien inner join penyakit "+
                    "inner join reg_periksa on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
                    "where diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ? and diagnosa_pasien.kd_penyakit<>'-' group by diagnosa_pasien.kd_penyakit ");
            ps2=koneksi.prepareStatement("select pasien.umur,pasien.jk,pasien.no_rkm_medis from pasien inner join reg_periksa inner join diagnosa_pasien "+
                    "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=diagnosa_pasien.no_rawat where "+
                    "diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ? and diagnosa_pasien.kd_penyakit=? "+
                    "group by diagnosa_pasien.no_rawat");
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

        TKd = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppGrafikHidupMati = new javax.swing.JMenuItem();
        ppGrafikLakiPerempuan = new javax.swing.JMenuItem();
        ppGrafikGolonganUmur = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        BtnCari1 = new widget.Button();
        label12 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N
        TKd.setSelectionColor(new java.awt.Color(255, 255, 255));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikHidupMati.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikHidupMati.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikHidupMati.setForeground(java.awt.Color.darkGray);
        ppGrafikHidupMati.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikHidupMati.setText("Grafik Pie Perbandingan Pasien Hidup & Mati");
        ppGrafikHidupMati.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikHidupMati.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikHidupMati.setIconTextGap(5);
        ppGrafikHidupMati.setName("ppGrafikHidupMati"); // NOI18N
        ppGrafikHidupMati.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikHidupMati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikHidupMatiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikHidupMati);

        ppGrafikLakiPerempuan.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikLakiPerempuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikLakiPerempuan.setForeground(java.awt.Color.darkGray);
        ppGrafikLakiPerempuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikLakiPerempuan.setText("Grafik Pie Perbandingan Pasien Laki-Laki & Perempuan");
        ppGrafikLakiPerempuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikLakiPerempuan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikLakiPerempuan.setIconTextGap(5);
        ppGrafikLakiPerempuan.setName("ppGrafikLakiPerempuan"); // NOI18N
        ppGrafikLakiPerempuan.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikLakiPerempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikLakiPerempuanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikLakiPerempuan);

        ppGrafikGolonganUmur.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikGolonganUmur.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikGolonganUmur.setForeground(java.awt.Color.darkGray);
        ppGrafikGolonganUmur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikGolonganUmur.setText("Grafik Pie Perbandingan Pasien Berdasarkan Umur");
        ppGrafikGolonganUmur.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikGolonganUmur.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikGolonganUmur.setIconTextGap(5);
        ppGrafikGolonganUmur.setName("ppGrafikGolonganUmur"); // NOI18N
        ppGrafikGolonganUmur.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikGolonganUmur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikGolonganUmurActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikGolonganUmur);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surveilans Rawat Inap Kasus Baru ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbBangsal.setComponentPopupMenu(jPopupMenu1);
        tbBangsal.setName("tbBangsal"); // NOI18N
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Masuk :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(label11);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(label18);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(Tgl2);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnCari1);

        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(label12);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from temporary");
            Map<String, Object> param = new HashMap<>();
            param.put("tanggal",Tgl2.getDate());
            param.put("jmltotal",jmltotal+"");
            for(int r=0;r<tabMode.getRowCount();r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,13).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,14).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,15).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,16).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,17).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,18).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,19).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
            }
            Sequel.AutoComitTrue();
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
            Valid.MyReport("rptSurveilansRanap.jrxml","report","::[ Surveilans PD3I ]::",
                "select * from temporary order by no asc",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppGrafikHidupMatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikHidupMatiActionPerformed
        if(tbBangsal.getSelectedRow()>-1){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue("Hidup ("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),18).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),18).toString()));
            dpd.setValue("Meninggal ("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),19).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),19).toString()));
            JFreeChart freeChart = ChartFactory.createPieChart("Grafik Pie Perbandingan Pasien Hidup & Mati Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik Pasien Hidup & Mati",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true); 
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data penyakit terlebih dahulu");
        }      
    }//GEN-LAST:event_ppGrafikHidupMatiActionPerformed

    private void ppGrafikLakiPerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikLakiPerempuanActionPerformed
        if(tbBangsal.getSelectedRow()>-1){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue("Laki-Laki "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),15).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),15).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),15).toString()));
            dpd.setValue("Perempuan "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),16).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),16).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),16).toString()));
            JFreeChart freeChart = ChartFactory.createPieChart("Grafik Pie Perbandingan Pasien Laki-Laki & Perempuan Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik Pasien Laki-Laki & Perempuan",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true); 
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data penyakit terlebih dahulu");
        }
    }//GEN-LAST:event_ppGrafikLakiPerempuanActionPerformed

    private void ppGrafikGolonganUmurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikGolonganUmurActionPerformed
        if(tbBangsal.getSelectedRow()>-1){
            DefaultPieDataset dpd = new DefaultPieDataset();
            dpd.setValue("0-7 Hr "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),3).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),3).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),3).toString()));
            dpd.setValue("8-28 Hr "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),4).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),4).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),4).toString()));
            dpd.setValue("< 1 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString()));
            dpd.setValue("1-4 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString()));
            dpd.setValue("5-9 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()));
            dpd.setValue("10-14 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString()));
            dpd.setValue("15-19 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),9).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),9).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),9).toString()));
            dpd.setValue("20-44 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString()));
            dpd.setValue("45-54 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString()));
            dpd.setValue("55-59 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString()));
            dpd.setValue("60-69 "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString()));
            dpd.setValue("70+ "+Math.round(((Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),14).toString())/Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString()))*100))+"%("+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),14).toString()+")",Integer.parseInt(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),14).toString()));
            JFreeChart freeChart = ChartFactory.createPieChart("Grafik Pie Perbandingan Per Umur Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
            ChartFrame cf = new ChartFrame("Grafik Per Umur",freeChart);
            cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
            cf.setLocationRelativeTo(internalFrame1);
            cf.setAlwaysOnTop(false);
            cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
            cf.setVisible(true); 
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih data penyakit terlebih dahulu");
        }
    }//GEN-LAST:event_ppGrafikGolonganUmurActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDkkSurveilansRanap dialog = new DlgDkkSurveilansRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ScrollPane Scroll;
    private widget.TextBox TKd;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.panelisi panelGlass5;
    private javax.swing.JMenuItem ppGrafikGolonganUmur;
    private javax.swing.JMenuItem ppGrafikHidupMati;
    private javax.swing.JMenuItem ppGrafikLakiPerempuan;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);    
            ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
            ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
            rs=ps.executeQuery();
            i=1;
            jmltotal=0;
            while(rs.next()){
                hr0s7=0;hr8s28=0;kr1th=0;th1s4=0;th5s9=0;th10s14=0;th15s19=0;th20s44=0;th45s54=0;th55s59=0;th60s69=0;th70plus=0;laki=0;per=0;jml=0;ttl=0;meninggal=0;
                
                ps2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps2.setString(3,rs.getString("kd_penyakit"));
                rs2=ps2.executeQuery();
                while(rs2.next()){       
                    meninggal=meninggal+Sequel.cariInteger("select ifnull(count(pasien_mati.no_rkm_medis),0) from pasien_mati where pasien_mati.no_rkm_medis=?",rs2.getString("no_rkm_medis"));
                    ttl=ttl+1;
                    jmltotal=jmltotal+1;
                    if(Sequel.cariInteger("select count(diagnosa_pasien.no_rawat) from reg_periksa inner join diagnosa_pasien "+
                        "on reg_periksa.no_rawat=diagnosa_pasien.no_rawat where "+
                        "diagnosa_pasien.status='Ranap' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and "+
                        "'"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and diagnosa_pasien.kd_penyakit='"+rs.getString("kd_penyakit")+"' "+
                        "and reg_periksa.no_rkm_medis='"+rs2.getString("no_rkm_medis")+"'")==1){
                            switch (rs2.getString("jk")) {
                                case "L":
                                    laki=laki+1;
                                    break;
                                case "P":
                                    per=per+1;
                                    break;
                            }
                            if(rs2.getString("umur").contains("Hr")){
                                if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Hr","").replaceAll("Hr","").replaceAll(" ",""))<=7){
                                    hr0s7=hr0s7+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Hr","").replaceAll("Hr","").replaceAll(" ",""))<=28){
                                    hr8s28=hr8s28+1;
                                }
                            }else if(rs2.getString("umur").contains("Bl")){
                                kr1th=kr1th+1;
                            }else if(rs2.getString("umur").contains("Th")){
                                if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=4){
                                    th1s4=th1s4+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=9){
                                    th5s9=th5s9+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=14){
                                    th10s14=th10s14+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=19){
                                    th15s19=th15s19+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=44){
                                    th20s44=th20s44+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=54){
                                    th45s54=th45s54+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=59){
                                    th55s59=th55s59+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=69){
                                    th60s69=th60s69+1;
                                }else if(Valid.SetAngka(rs2.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))>=70){
                                    th70plus=th70plus+1;
                                }
                            }
                    }                                       
                }
                jml=per+laki;
                tabMode.addRow(new Object[]{
                   i,rs.getString("kd_penyakit"),rs.getString("nm_penyakit"),hr0s7,hr8s28,kr1th,th1s4,th5s9,th10s14,th15s19,th20s44,th45s54,th55s59,th60s69,th70plus,laki,per,jml,ttl,meninggal
                });
                i++;
            }
                
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }


}
