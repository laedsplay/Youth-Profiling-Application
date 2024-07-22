/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package loginform;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;

/**
 *
 * @author acer
 */
public class brgyView extends javax.swing.JFrame {

    /**
     * Creates new form brgyView
     */
    public brgyView() {
        initComponents();
        
        PieChartAplaya();
        PieChartBalibago();
        PieChartCaingin();
        PieChartDila();
        PieChartDita();
        PieChartDonJose();
    }
    public void PieChartAplaya() {
        DefaultPieDataset aplayaDataset = new DefaultPieDataset();
        aplayaDataset.setValue("Completed", 75);
        aplayaDataset.setValue("Pending", 25);

        JFreeChart Aplayapiechart = ChartFactory.createPieChart("BARANGAY APLAYA", aplayaDataset, true, true, false);

        PiePlot pieplot_aplaya = (PiePlot) Aplayapiechart.getPlot();

        pieplot_aplaya.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot_aplaya.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot_aplaya.setBackgroundPaint(Color.white);
        
        pieplot_aplaya.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot_aplaya.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot_aplaya.setLabelBackgroundPaint(Color.white);
        pieplot_aplaya.setLabelGap(0.02);
        pieplot_aplaya.setLabelLinkPaint(Color.black);
        pieplot_aplaya.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot_aplaya.setOutlineVisible(false);
        

         pieplot_aplaya.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = Aplayapiechart.getLegend();
        pielegend.setPosition(RectangleEdge.RIGHT);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 15));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie_aplaya = new ChartPanel(Aplayapiechart);
        aplayaChart.removeAll();
        aplayaChart.add(Pie_aplaya, BorderLayout.CENTER);
        aplayaChart.validate();
    }
    public void PieChartBalibago() {
         DefaultPieDataset balibagoDataset = new DefaultPieDataset();
        balibagoDataset.setValue("Completed", 74);
        balibagoDataset.setValue("Pending", 26);

        JFreeChart Balibagopiechart = ChartFactory.createPieChart("BARANGAY BALIBAGO", balibagoDataset, true, true, false);

        PiePlot pieplot_balibago = (PiePlot) Balibagopiechart.getPlot();

        pieplot_balibago.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot_balibago.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot_balibago.setBackgroundPaint(Color.white);
        
        pieplot_balibago.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot_balibago.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot_balibago.setLabelBackgroundPaint(Color.white);
        pieplot_balibago.setLabelGap(0.02);
        pieplot_balibago.setLabelLinkPaint(Color.black);
        pieplot_balibago.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot_balibago.setOutlineVisible(false);
        

         pieplot_balibago.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = Balibagopiechart.getLegend();
        pielegend.setPosition(RectangleEdge.RIGHT);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 15));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie_balibago = new ChartPanel(Balibagopiechart);
        balibagoChart.removeAll();
        balibagoChart.add(Pie_balibago, BorderLayout.CENTER);
        balibagoChart.validate();
    }
    public void PieChartCaingin() {
         DefaultPieDataset cainginDataset = new DefaultPieDataset();
        cainginDataset.setValue("Completed", 88);
        cainginDataset.setValue("Pending", 22);

        JFreeChart Cainginpiechart = ChartFactory.createPieChart("BARANGAY CAINGIN", cainginDataset, true, true, false);

        PiePlot pieplot_caingin = (PiePlot) Cainginpiechart.getPlot();

        pieplot_caingin.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot_caingin.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot_caingin.setBackgroundPaint(Color.white);
        
        pieplot_caingin.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot_caingin.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot_caingin.setLabelBackgroundPaint(Color.white);
        pieplot_caingin.setLabelGap(0.02);
        pieplot_caingin.setLabelLinkPaint(Color.black);
        pieplot_caingin.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot_caingin.setOutlineVisible(false);
        

         pieplot_caingin.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = Cainginpiechart.getLegend();
        pielegend.setPosition(RectangleEdge.RIGHT);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 15));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie_caingin = new ChartPanel(Cainginpiechart);
        cainginChart.removeAll();
        cainginChart.add(Pie_caingin, BorderLayout.CENTER);
        cainginChart.validate();
    }
    public void PieChartDila() {
        DefaultPieDataset dilaDataset = new DefaultPieDataset();
        dilaDataset.setValue("Completed", 25);
        dilaDataset.setValue("Pending", 75);

        JFreeChart Dilapiechart = ChartFactory.createPieChart("BARANGAY DILA", dilaDataset, true, true, false);

        PiePlot pieplot_dila = (PiePlot) Dilapiechart.getPlot();

        pieplot_dila.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot_dila.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot_dila.setBackgroundPaint(Color.white);
        
        pieplot_dila.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot_dila.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot_dila.setLabelBackgroundPaint(Color.white);
        pieplot_dila.setLabelGap(0.02);
        pieplot_dila.setLabelLinkPaint(Color.black);
        pieplot_dila.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot_dila.setOutlineVisible(false);
        

         pieplot_dila.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = Dilapiechart.getLegend();
        pielegend.setPosition(RectangleEdge.RIGHT);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 15));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie_dila = new ChartPanel(Dilapiechart);
        dilaChart.removeAll();
        dilaChart.add(Pie_dila, BorderLayout.CENTER);
        dilaChart.validate();
    }
    public void PieChartDita() {
        DefaultPieDataset ditaDataset = new DefaultPieDataset();
        ditaDataset.setValue("Completed", 20);
        ditaDataset.setValue("Pending", 80);

        JFreeChart Ditapiechart = ChartFactory.createPieChart("BARANGAY DITA", ditaDataset, true, true, false);

        PiePlot pieplot_dita = (PiePlot) Ditapiechart.getPlot();

        pieplot_dita.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot_dita.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot_dita.setBackgroundPaint(Color.white);
        
        pieplot_dita.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot_dita.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot_dita.setLabelBackgroundPaint(Color.white);
        pieplot_dita.setLabelGap(0.02);
        pieplot_dita.setLabelLinkPaint(Color.black);
        pieplot_dita.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot_dita.setOutlineVisible(false);
        

         pieplot_dita.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = Ditapiechart.getLegend();
        pielegend.setPosition(RectangleEdge.RIGHT);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 15));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie_dita = new ChartPanel(Ditapiechart);
        ditaChart.removeAll();
        ditaChart.add(Pie_dita, BorderLayout.CENTER);
        ditaChart.validate();
    }
    public void PieChartDonJose() {
         DefaultPieDataset aplayaDataset = new DefaultPieDataset();
        aplayaDataset.setValue("Completed", 14);
        aplayaDataset.setValue("Pending", 86);

        JFreeChart Donjosepiechart = ChartFactory.createPieChart("BARANGAY DON JOSE", aplayaDataset, true, true, false);

        PiePlot pieplot_donjose = (PiePlot) Donjosepiechart.getPlot();

        pieplot_donjose.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot_donjose.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot_donjose.setBackgroundPaint(Color.white);
        
        pieplot_donjose.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot_donjose.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot_donjose.setLabelBackgroundPaint(Color.white);
        pieplot_donjose.setLabelGap(0.02);
        pieplot_donjose.setLabelLinkPaint(Color.black);
        pieplot_donjose.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot_donjose.setOutlineVisible(false);
        

         pieplot_donjose.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = Donjosepiechart.getLegend();
        pielegend.setPosition(RectangleEdge.RIGHT);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 15));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie_donjose = new ChartPanel(Donjosepiechart);
        donjoseChart.removeAll();
        donjoseChart.add(Pie_donjose, BorderLayout.CENTER);
        donjoseChart.validate();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        aplayaChart = new javax.swing.JPanel();
        balibagoChart = new javax.swing.JPanel();
        cainginChart = new javax.swing.JPanel();
        dilaChart = new javax.swing.JPanel();
        ditaChart = new javax.swing.JPanel();
        donjoseChart = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setHorizontalScrollBar(null);

        aplayaChart.setBackground(new java.awt.Color(255, 255, 255));
        aplayaChart.setLayout(new java.awt.BorderLayout());

        balibagoChart.setBackground(new java.awt.Color(255, 255, 255));
        balibagoChart.setLayout(new java.awt.BorderLayout());

        cainginChart.setBackground(new java.awt.Color(255, 255, 255));
        cainginChart.setLayout(new java.awt.BorderLayout());

        dilaChart.setBackground(new java.awt.Color(255, 255, 255));
        dilaChart.setLayout(new java.awt.BorderLayout());

        ditaChart.setBackground(new java.awt.Color(255, 255, 255));
        ditaChart.setLayout(new java.awt.BorderLayout());

        donjoseChart.setBackground(new java.awt.Color(255, 255, 255));
        donjoseChart.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Back.png"))); // NOI18N
        jLabel3.setText("BACK");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.setIconTextGap(1);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(aplayaChart, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dilaChart, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(balibagoChart, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(cainginChart, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ditaChart, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(donjoseChart, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cainginChart, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(balibagoChart, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aplayaChart, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dilaChart, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ditaChart, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(donjoseChart, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        side_bar side_barFrame = new side_bar();
        side_barFrame.setVisible(true);
        side_barFrame.pack();
        side_barFrame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(brgyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(brgyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(brgyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(brgyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new brgyView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aplayaChart;
    private javax.swing.JPanel balibagoChart;
    private javax.swing.JPanel cainginChart;
    private javax.swing.JPanel dilaChart;
    private javax.swing.JPanel ditaChart;
    private javax.swing.JPanel donjoseChart;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
