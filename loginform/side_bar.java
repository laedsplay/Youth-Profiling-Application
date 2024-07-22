/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package loginform;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import java.awt.Color;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.ChartFactory;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.BasicStroke;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.VerticalAlignment;
import java.text.NumberFormat;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

// dinagdag ko (odeth)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.regex.Pattern;

// JAMES NEW IMPORT
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
/**
 *
 * @author acer
 */

public class side_bar extends javax.swing.JFrame {
    
    private static final String username = "root";
    private static final String password = "";
    private static final String dataConn = "jdbc:mysql://localhost:3306/connector";
    
    Connection sqlConn = null;
    PreparedStatement pst = null;
    ResultSet rs =null;
    int q, i, id, deleteItem;

    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private CategoryPlot categoryPlot;
    private ChartPanel chartPanel;
    
    Color DefaultColor, ClickedColor;
    boolean a = true;
    public side_bar() {
        initComponents();
        
        DefaultColor = new Color(255,255,255);
        ClickedColor = new Color(255,102,0);
        //to color the buttons background
        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(DefaultColor);
        
        //content of hidemenu
        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(DefaultColor);
        
        showBarChart();
        PieChartShow1();
        PieChartShow2();
        PieChartShow3();
    }
    
    //====================================== FUNCTION ============================================
    // JAMES UPDATEDB
    public void upDateDB()
    {
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";
        
        String getAdminDataQuery = "SELECT * from connector";
        
        
         try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(getAdminDataQuery);
             
             ResultSet resultSet = statement.executeQuery()) {
             
             DefaultTableModel model_view = (DefaultTableModel) jTable1.getModel();
             
             model_view.setRowCount(0);
             
         
            while (resultSet.next()) {
                String get_FirstName = resultSet.getString("FirstName");
                String get_MiddleInitial = resultSet.getString("MiddleInitial");
                String get_LastName = resultSet.getString("LastName");
                String get_Suffix = resultSet.getString("Suffix");
                String get_Birthday = resultSet.getString("Birthday");
                String get_Age = resultSet.getString("Age");
                String get_Sex = resultSet.getString("Sex");
                String get_Email = resultSet.getString("Email");
                String get_PhoneNumber = resultSet.getString("PhoneNumber");
                String get_CivilStatus = resultSet.getString("CivilStatus");
                String get_YouthAgeGroup = resultSet.getString("YouthAgeGroup");
                String get_EducationalBackground = resultSet.getString("EducationalBackground");
                String get_YouthClass = resultSet.getString("YouthClass");
                String get_WorkStatus = resultSet.getString("WorkStatus");
                String get_SKVoter = resultSet.getString("SKVoter");
                String get_NationalVoter = resultSet.getString("NationalVoter");
                String get_AttendedKKAssembly = resultSet.getString("AttendedKKAssembly");
                
                // add current admin to admin row
                model_view.addRow(new Object[]{get_FirstName, get_MiddleInitial, get_LastName, get_Suffix, get_Birthday, get_Age, get_Sex, get_Email, get_PhoneNumber, get_CivilStatus, get_YouthAgeGroup, get_EducationalBackground, get_YouthClass, get_WorkStatus, get_SKVoter, get_NationalVoter, get_AttendedKKAssembly});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    //======================================  END FUNCTION ============================================
    
    public class PasswordHasher {
        private static final int SALT_LENGTH = 16; // Salt length in bytes
        private static final int HASH_LENGTH = 256; // Hash length in bits
        private static final int ITERATIONS = 10000; // Number of iterations

        // Generate a random salt
        public static byte[] generateSalt() {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            return salt;    
        }

        // Hash the password using PBKDF2WithHmacSHA1
        public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        }

        // Combine salt and hash into a single string
        public static String getSaltedHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
            byte[] salt = generateSalt();
            String hash = hashPassword(password, salt);
            return Base64.getEncoder().encodeToString(salt) + "$" + hash;
        }

        // Verify the password
        public static boolean verifyPassword(String password, String stored) throws NoSuchAlgorithmException, InvalidKeySpecException {
            String[] parts = stored.split("\\$");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            String hash = hashPassword(password, salt);
            return hash.equals(parts[1]);
        }
    }
    public void registerAdmin(){
        String first_name = FN_Field3.getText();
        String last_name = LN_Field3.getText();
        String email = Email_Field3.getText();
        String username = Contact_Field3.getText();
        String position = (String) Position_Field3.getSelectedItem();
        String barangay = (String) Barangay_Field3.getSelectedItem();
        char[] user_password = Password_Field3.getPassword();
        String to_hash_password = new String(user_password);
        boolean isValid = true;
        if (first_name.isEmpty() || !isValidName(first_name)){
            reg_fname_error.setText("Invalid Field.");
           isValid = false;
        } else {
            reg_fname_error.setText("");
        }
        if (last_name.isEmpty() || !isValidName(last_name)){
            reg_lname_error.setText("Invalid field.");
            isValid = false;
        } else {
            reg_lname_error.setText("");
        }
        if (email.isEmpty() || !isValidEmail(email)){
            reg_email_error.setText("Invalid field.");
            isValid = false;
        } else {
            reg_email_error.setText("");
        }
        if (username.isEmpty() || !isValidUsername(username)){
            reg_username_error.setText("Invalid field.");
            isValid = false;
        } else {
            reg_username_error.setText("");
        }
        if (to_hash_password.isEmpty() || !isValidPassword(to_hash_password)){
            reg_password_error.setText("Invalid field.");
            isValid = false;
        } else {
            reg_password_error.setText("");
        }
        if (isValid == true){
                try {
                    String hashed_password = PasswordHasher.getSaltedHash(to_hash_password);
                    // prepare connection
                    String url = "jdbc:mysql://localhost:3306/skprofiling";
                    String user = "root";
                    String password = "";
                    
                    String query = "INSERT INTO admin_accounts (first_name, last_name, email, username, position, barangay, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    
                    try (Connection connection = DriverManager.getConnection(url, user, password);
                            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        
                        // Set parameters
                        preparedStatement.setString(1, first_name);
                        preparedStatement.setString(2, last_name);
                        preparedStatement.setString(3, email);
                        preparedStatement.setString(4, username);
                        preparedStatement.setString(5, position);
                        preparedStatement.setString(6, barangay);
                        preparedStatement.setString(7, hashed_password);
                        // Execute the query
                        preparedStatement.executeUpdate();
                        
                        // Show success message
                        JOptionPane.showMessageDialog(this, "Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Clear form fields
                        FN_Field3.setText("");
                        LN_Field3.setText("");
                        Email_Field3.setText("");
                        Contact_Field3.setText("");
                        Password_Field3.setText("");
                        
                        // reload table
                        getAdmins();
                    } catch (SQLException ex) {
                        
                    } 
                    
                    
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    Logger.getLogger(side_bar.class.getName()).log(Level.SEVERE, null, ex);
                }    

            
        }
        
        
        
    }
    
    
    public void PieChartShow1() {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Completed", 78);
        pieDataset.setValue("Pending", 22);

        JFreeChart piechart = ChartFactory.createPieChart("", pieDataset, true, true, false);

        PiePlot pieplot = (PiePlot) piechart.getPlot();

        pieplot.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot.setBackgroundPaint(Color.white);
        
        pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot.setLabelBackgroundPaint(Color.white);
        pieplot.setLabelGap(0.02);
        pieplot.setLabelLinkPaint(Color.black);
        pieplot.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot.setOutlineVisible(false);
        

         pieplot.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = piechart.getLegend();
        pielegend.setPosition(RectangleEdge.BOTTOM);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 15));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie = new ChartPanel(piechart);
        PieChartPanel.removeAll();
        PieChartPanel.add(Pie, BorderLayout.CENTER);
        PieChartPanel.validate();
        
    }
    public void PieChartShow2() {
        DefaultPieDataset pieDataset2 = new DefaultPieDataset();
        pieDataset2.setValue("Completed", 88);
        pieDataset2.setValue("Pending", 12);

        JFreeChart piechart2 = ChartFactory.createPieChart("", pieDataset2, true, true, false);

        PiePlot pieplot2 = (PiePlot) piechart2.getPlot();

        pieplot2.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot2.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot2.setBackgroundPaint(Color.white);
        
   pieplot2.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot2.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot2.setLabelBackgroundPaint(Color.white);
        pieplot2.setLabelGap(0.02);
        pieplot2.setLabelLinkPaint(Color.black);
        pieplot2.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot2.setOutlineVisible(false);
        

         pieplot2.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = piechart2.getLegend();
        pielegend.setPosition(RectangleEdge.BOTTOM);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 20));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie2 = new ChartPanel(piechart2);
        sinalhanPieChart.removeAll();
        sinalhanPieChart.add(Pie2, BorderLayout.CENTER);
        sinalhanPieChart.validate();
    }
    
    public void PieChartShow3() {
        DefaultPieDataset pieDataset3 = new DefaultPieDataset();
        pieDataset3.setValue("Completed", 14);
        pieDataset3.setValue("Pending", 86);

        JFreeChart piechart3 = ChartFactory.createPieChart("", pieDataset3, true, true, false);

        PiePlot pieplot3 = (PiePlot) piechart3.getPlot();

        pieplot3.setSectionPaint("Completed", new Color(210, 105, 30));
        pieplot3.setSectionPaint("Pending", new Color(244, 164, 96));

        pieplot3.setBackgroundPaint(Color.white);
        
   pieplot3.setLabelGenerator(new StandardPieSectionLabelGenerator(
            "{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        ));

        pieplot3.setLabelFont(new Font("segoe IU", Font.PLAIN, 15));
        pieplot3.setLabelBackgroundPaint(Color.white);
        pieplot3.setLabelGap(0.02);
        pieplot3.setLabelLinkPaint(Color.black);
        pieplot3.setLabelLinkStroke(new BasicStroke(1.0f));

        pieplot3.setOutlineVisible(false);
        

         pieplot3.setLabelGap(0.0);
        // Position legend on the right
        LegendTitle pielegend = piechart3.getLegend();
        pielegend.setPosition(RectangleEdge.BOTTOM);

        // Customize legend appearance
        pielegend.setItemFont(new Font("Segoe UI", Font.PLAIN, 20));
        pielegend.setFrame(new BlockBorder(Color.white)); // Removes the border
        pielegend.setItemLabelPadding(new RectangleInsets(2, 2, 2, 2));
        pielegend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pielegend.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create chartPanel to display chart (graph)
        ChartPanel Pie3 = new ChartPanel(piechart3);
        aplayaPieChart.removeAll();
        aplayaPieChart.add(Pie3, BorderLayout.CENTER);
        aplayaPieChart.validate();
    }
            
            
            
            
      public void showBarChart() {
    dataset = new DefaultCategoryDataset();

    // Adding data for Male and Female from the image
    dataset.addValue(120, "Male", "Aplaya");
    dataset.addValue(210, "Female", "Aplaya");
    dataset.addValue(110, "Male", "Balibago");
    dataset.addValue(78, "Female", "Balibago");
    dataset.addValue(99, "Male", "Caingin");
    dataset.addValue(190, "Female", "Caingin");
    dataset.addValue(66, "Male", "Dila");
    dataset.addValue(105, "Female", "Dila");
    dataset.addValue(115, "Male", "Dita");
    dataset.addValue(135, "Female", "Dita");
    dataset.addValue(120, "Male", "Don Jose");
    dataset.addValue(230, "Female", "Don Jose");
    dataset.addValue(150, "Male", "Ibaba");
    dataset.addValue(129, "Female", "Ibaba");
    dataset.addValue(109, "Male", "Kanluran");
    dataset.addValue(87, "Female", "Kanluran");
    dataset.addValue(143, "Male", "Labas");
    dataset.addValue(111, "Female", "Labas");

    // Create chart
     chart = ChartFactory.createBarChart(
            "", "", "", 
            dataset, PlotOrientation.VERTICAL, true, true, false);

    categoryPlot = chart.getCategoryPlot();

    // Set background color
    categoryPlot.setBackgroundPaint(Color.WHITE);
    chart.setBackgroundPaint(Color.WHITE);
    
        
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setMaximumBarWidth(0.1f); 
        renderer.setItemMargin(0.05);
        
         renderer.setSeriesPaint(0, new Color(244,164, 96)); // Light orange for Male
         renderer.setSeriesPaint(1, new Color(210,105,30)); 
        
         
            CategoryAxis domainAxis = categoryPlot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12)); 
        
        categoryPlot.setDomainGridlinesVisible(false);
        categoryPlot.setRangeGridlinesVisible(true);

        // Set grid line color
        categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        //set the line to solid lines
        categoryPlot.setRangeGridlineStroke(new BasicStroke(0.20f));
        
        
        // Remove the outline of the chart
        categoryPlot.setOutlineVisible(false);

        // Remove the outline of the legend
        LegendTitle legend = chart.getLegend();
        legend.setFrame(BlockBorder.NONE);
        
    chartPanel = new ChartPanel(chart); // Show (put) the chart into a panel
    barChartPanel.removeAll();
    barChartPanel.add(chartPanel, BorderLayout.CENTER);
    barChartPanel.validate();
        
    }
      
    
      
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        proficon6 = new javax.swing.JLabel();
        menu = new javax.swing.JPanel();
        menuside = new javax.swing.JPanel();
        hidemenu = new javax.swing.JPanel();
        hidemenubutton = new javax.swing.JLabel();
        logo = new javax.swing.JPanel();
        logoicon = new javax.swing.JLabel();
        profilingClicked = new javax.swing.JPanel();
        profilingbutton = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        logoutbutton = new javax.swing.JLabel();
        dboardClicked = new javax.swing.JPanel();
        dboardbutton = new javax.swing.JLabel();
        resultClicked = new javax.swing.JPanel();
        resultbutton = new javax.swing.JLabel();
        manualClicked = new javax.swing.JPanel();
        manualbutton = new javax.swing.JLabel();
        settingsClicked = new javax.swing.JPanel();
        settingsbutton = new javax.swing.JLabel();
        menuhide = new javax.swing.JPanel();
        sktext = new javax.swing.JLabel();
        Citytext = new javax.swing.JLabel();
        menufooter = new javax.swing.JPanel();
        logoutlabel = new javax.swing.JLabel();
        bgdboard = new javax.swing.JPanel();
        dboardlabel = new javax.swing.JLabel();
        bgprofiling = new javax.swing.JPanel();
        profilinglabel = new javax.swing.JLabel();
        bgresult = new javax.swing.JPanel();
        resultlabel = new javax.swing.JLabel();
        bgmanual = new javax.swing.JPanel();
        manuallabel = new javax.swing.JLabel();
        bgsettings = new javax.swing.JPanel();
        settingslabel = new javax.swing.JLabel();
        pagechanger = new javax.swing.JPanel();
        dashboard = new javax.swing.JPanel();
        sidecharts = new javax.swing.JPanel();
        notificon = new javax.swing.JLabel();
        proficon = new javax.swing.JLabel();
        PieChartPanel = new javax.swing.JPanel();
        viewall = new javax.swing.JButton();
        bgprogress = new javax.swing.JPanel();
        aplayaPieChart = new javax.swing.JPanel();
        sinalhanPieChart = new javax.swing.JPanel();
        aplayalabel = new javax.swing.JLabel();
        sinalhanlabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        OCR_chart = new javax.swing.JTextPane();
        line1 = new javax.swing.JSeparator();
        jScrollPane10 = new javax.swing.JScrollPane();
        progressstats3 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        barChartPanel = new javax.swing.JPanel();
        activities = new javax.swing.JPanel();
        countbox1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        count2022 = new javax.swing.JTextPane();
        count1 = new javax.swing.JLabel();
        countbox2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        count2023 = new javax.swing.JTextPane();
        count2 = new javax.swing.JLabel();
        countbox3 = new javax.swing.JPanel();
        count4 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        count2025 = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        act_text = new javax.swing.JTextPane();
        youthprofiling = new javax.swing.JPanel();
        notificon1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtxtFName = new javax.swing.JTextField();
        jtxtMInitial = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtxtLName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtxtSuffix = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtxtBday = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtxtAge = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jtxtEmail = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jtxtPNumber = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jtxtSexB = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cboCivil = new javax.swing.JComboBox<>();
        cboYouthAge = new javax.swing.JComboBox<>();
        cboEducBack = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cboYouthClass = new javax.swing.JComboBox<>();
        cboWorkStatus = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        cboSKVoter = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        cboNVoter = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        cboKKAssembly = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jbtnAddNew = new javax.swing.JButton();
        jbtnUpdateData = new javax.swing.JButton();
        jbtnPrint = new javax.swing.JButton();
        jbtnReset = new javax.swing.JButton();
        jbtnDelete = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        user_manual = new javax.swing.JPanel();
        notificon2 = new javax.swing.JLabel();
        proficon2 = new javax.swing.JLabel();
        YouthProfiling_description = new javax.swing.JPanel();
        youth_profiling_text = new javax.swing.JLabel();
        application_text = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        faqsbtn_Panel = new javax.swing.JPanel();
        faqs_btn = new javax.swing.JLabel();
        featurebtn_Panel = new javax.swing.JPanel();
        feature_btn = new javax.swing.JLabel();
        page = new javax.swing.JPanel();
        features = new javax.swing.JPanel();
        feature14 = new javax.swing.JPanel();
        f2_left12 = new javax.swing.JButton();
        f2_right13 = new javax.swing.JButton();
        title_f14 = new javax.swing.JLabel();
        f2_pic13 = new javax.swing.JPanel();
        f1_pict13 = new javax.swing.JLabel();
        f2_textcont13 = new javax.swing.JScrollPane();
        f1_desc13 = new javax.swing.JTextPane();
        f2_page14 = new javax.swing.JLabel();
        feature15 = new javax.swing.JPanel();
        f2_left13 = new javax.swing.JButton();
        f2_right14 = new javax.swing.JButton();
        title_f15 = new javax.swing.JLabel();
        f2_pic14 = new javax.swing.JPanel();
        f1_pict14 = new javax.swing.JLabel();
        f2_textcont14 = new javax.swing.JScrollPane();
        f1_desc14 = new javax.swing.JTextPane();
        f2_page15 = new javax.swing.JLabel();
        feature16 = new javax.swing.JPanel();
        f2_left14 = new javax.swing.JButton();
        f2_right15 = new javax.swing.JButton();
        title_f16 = new javax.swing.JLabel();
        f2_pic15 = new javax.swing.JPanel();
        f1_pict15 = new javax.swing.JLabel();
        f2_textcont15 = new javax.swing.JScrollPane();
        f1_desc15 = new javax.swing.JTextPane();
        f2_page16 = new javax.swing.JLabel();
        feature2 = new javax.swing.JPanel();
        f2_left = new javax.swing.JButton();
        f2_right1 = new javax.swing.JButton();
        title_f2 = new javax.swing.JLabel();
        f2_pic1 = new javax.swing.JPanel();
        f1_pict1 = new javax.swing.JLabel();
        f2_textcont1 = new javax.swing.JScrollPane();
        f1_desc1 = new javax.swing.JTextPane();
        f2_page2 = new javax.swing.JLabel();
        feature1 = new javax.swing.JPanel();
        f1_left = new javax.swing.JButton();
        f1_right = new javax.swing.JButton();
        f1_title = new javax.swing.JLabel();
        f1_pic = new javax.swing.JPanel();
        f1_pict = new javax.swing.JLabel();
        f1_textcont = new javax.swing.JScrollPane();
        f1_desc = new javax.swing.JTextPane();
        f1_page1 = new javax.swing.JLabel();
        feature5 = new javax.swing.JPanel();
        f2_left1 = new javax.swing.JButton();
        f2_right2 = new javax.swing.JButton();
        title_f3 = new javax.swing.JLabel();
        f2_pic2 = new javax.swing.JPanel();
        f1_pict2 = new javax.swing.JLabel();
        f2_textcont2 = new javax.swing.JScrollPane();
        f1_desc2 = new javax.swing.JTextPane();
        f2_page3 = new javax.swing.JLabel();
        feature6 = new javax.swing.JPanel();
        f2_left2 = new javax.swing.JButton();
        f2_right3 = new javax.swing.JButton();
        title_f4 = new javax.swing.JLabel();
        f2_pic3 = new javax.swing.JPanel();
        f1_pict3 = new javax.swing.JLabel();
        f2_textcont3 = new javax.swing.JScrollPane();
        f1_desc3 = new javax.swing.JTextPane();
        f2_page4 = new javax.swing.JLabel();
        feature7 = new javax.swing.JPanel();
        f2_left3 = new javax.swing.JButton();
        f2_right4 = new javax.swing.JButton();
        title_f5 = new javax.swing.JLabel();
        f2_pic4 = new javax.swing.JPanel();
        f1_pict4 = new javax.swing.JLabel();
        f2_textcont4 = new javax.swing.JScrollPane();
        f1_desc4 = new javax.swing.JTextPane();
        f2_page5 = new javax.swing.JLabel();
        feature8 = new javax.swing.JPanel();
        f2_left4 = new javax.swing.JButton();
        f2_right5 = new javax.swing.JButton();
        title_f6 = new javax.swing.JLabel();
        f2_pic5 = new javax.swing.JPanel();
        f1_pict5 = new javax.swing.JLabel();
        f2_textcont5 = new javax.swing.JScrollPane();
        f1_desc5 = new javax.swing.JTextPane();
        f2_page6 = new javax.swing.JLabel();
        feature9 = new javax.swing.JPanel();
        f2_left5 = new javax.swing.JButton();
        f2_right6 = new javax.swing.JButton();
        title_f7 = new javax.swing.JLabel();
        f2_pic6 = new javax.swing.JPanel();
        f1_pict6 = new javax.swing.JLabel();
        f2_textcont6 = new javax.swing.JScrollPane();
        f1_desc6 = new javax.swing.JTextPane();
        f2_page7 = new javax.swing.JLabel();
        feature10 = new javax.swing.JPanel();
        f2_left6 = new javax.swing.JButton();
        f2_right7 = new javax.swing.JButton();
        title_f8 = new javax.swing.JLabel();
        f2_pic7 = new javax.swing.JPanel();
        f1_pict7 = new javax.swing.JLabel();
        f2_textcont7 = new javax.swing.JScrollPane();
        f1_desc7 = new javax.swing.JTextPane();
        f2_page8 = new javax.swing.JLabel();
        feature11 = new javax.swing.JPanel();
        f2_left7 = new javax.swing.JButton();
        f2_right8 = new javax.swing.JButton();
        title_f9 = new javax.swing.JLabel();
        f2_pic8 = new javax.swing.JPanel();
        f1_pict8 = new javax.swing.JLabel();
        f2_textcont8 = new javax.swing.JScrollPane();
        f1_desc8 = new javax.swing.JTextPane();
        f2_page9 = new javax.swing.JLabel();
        feature12 = new javax.swing.JPanel();
        f2_left8 = new javax.swing.JButton();
        f2_right9 = new javax.swing.JButton();
        title_f10 = new javax.swing.JLabel();
        f2_pic9 = new javax.swing.JPanel();
        f1_pict9 = new javax.swing.JLabel();
        f2_textcont9 = new javax.swing.JScrollPane();
        f1_desc9 = new javax.swing.JTextPane();
        f2_page10 = new javax.swing.JLabel();
        feature13 = new javax.swing.JPanel();
        f2_left9 = new javax.swing.JButton();
        f2_right10 = new javax.swing.JButton();
        title_f11 = new javax.swing.JLabel();
        f2_pic10 = new javax.swing.JPanel();
        f1_pict10 = new javax.swing.JLabel();
        f2_textcont10 = new javax.swing.JScrollPane();
        f1_desc10 = new javax.swing.JTextPane();
        f2_page11 = new javax.swing.JLabel();
        feature17 = new javax.swing.JPanel();
        f2_left10 = new javax.swing.JButton();
        f2_right11 = new javax.swing.JButton();
        title_f12 = new javax.swing.JLabel();
        f2_pic11 = new javax.swing.JPanel();
        f1_pict11 = new javax.swing.JLabel();
        f2_textcont11 = new javax.swing.JScrollPane();
        f1_desc11 = new javax.swing.JTextPane();
        f2_page12 = new javax.swing.JLabel();
        feature18 = new javax.swing.JPanel();
        f2_left11 = new javax.swing.JButton();
        f2_right12 = new javax.swing.JButton();
        title_f13 = new javax.swing.JLabel();
        f2_pic12 = new javax.swing.JPanel();
        f1_pict12 = new javax.swing.JLabel();
        f2_textcont12 = new javax.swing.JScrollPane();
        f1_desc12 = new javax.swing.JTextPane();
        f2_page13 = new javax.swing.JLabel();
        faqs = new javax.swing.JPanel();
        title_FAQs = new javax.swing.JLabel();
        faq_1 = new javax.swing.JPanel();
        faq_1Ques = new javax.swing.JLabel();
        faq1_containerDesc = new javax.swing.JScrollPane();
        faq1_desc = new javax.swing.JTextPane();
        faq_2 = new javax.swing.JPanel();
        faq_2Ques = new javax.swing.JLabel();
        faq2_containerDesc = new javax.swing.JScrollPane();
        faq2_desc = new javax.swing.JTextPane();
        faq_5 = new javax.swing.JPanel();
        faq_2Ques2 = new javax.swing.JLabel();
        faq2_containerDesc2 = new javax.swing.JScrollPane();
        faq2_desc2 = new javax.swing.JTextPane();
        results = new javax.swing.JPanel();
        right = new javax.swing.JPanel();
        head_title = new javax.swing.JLabel();
        youth_container = new javax.swing.JPanel();
        year_container = new javax.swing.JPanel();
        count3 = new javax.swing.JLabel();
        year_title = new javax.swing.JPanel();
        year_desc = new javax.swing.JLabel();
        youth_container2 = new javax.swing.JPanel();
        count5 = new javax.swing.JLabel();
        year_title2 = new javax.swing.JPanel();
        year_desc2 = new javax.swing.JLabel();
        youth_container3 = new javax.swing.JPanel();
        count6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        year_hdesc = new javax.swing.JLabel();
        member_container = new javax.swing.JPanel();
        members_head = new javax.swing.JScrollPane();
        members_table = new javax.swing.JTable();
        members_title = new javax.swing.JLabel();
        target_container = new javax.swing.JPanel();
        target_title = new javax.swing.JLabel();
        target_separator = new javax.swing.JLabel();
        target_count = new javax.swing.JLabel();
        current_container = new javax.swing.JPanel();
        current_separator = new javax.swing.JLabel();
        current_title = new javax.swing.JLabel();
        current_count = new javax.swing.JLabel();
        proficon3 = new javax.swing.JLabel();
        notificon3 = new javax.swing.JLabel();
        settings = new javax.swing.JPanel();
        notificon4 = new javax.swing.JLabel();
        proficon4 = new javax.swing.JLabel();
        ContainerSettings = new javax.swing.JPanel();
        SetTitleSettings = new java.awt.Label();
        SearchButton = new javax.swing.JButton();
        SearchField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        ManageUserPanel = new javax.swing.JPanel();
        btn_TermsServices1 = new javax.swing.JLabel();
        TermsServicesPanel = new javax.swing.JPanel();
        btn_TermsServices = new javax.swing.JLabel();
        NotificationsPanel = new javax.swing.JPanel();
        btn_Notifications = new javax.swing.JLabel();
        PrivacySettingsPanel = new javax.swing.JPanel();
        btn_PrivacySettings = new javax.swing.JLabel();
        SettingsPage = new javax.swing.JPanel();
        ManageUser = new javax.swing.JPanel();
        List1 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        admin_list = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        Register1 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        FirstName3 = new javax.swing.JLabel();
        FN_Field3 = new javax.swing.JTextField();
        LastName3 = new javax.swing.JLabel();
        LN_Field3 = new javax.swing.JTextField();
        Email3 = new javax.swing.JLabel();
        Email_Field3 = new javax.swing.JTextField();
        ContactNumber3 = new javax.swing.JLabel();
        Contact_Field3 = new javax.swing.JTextField();
        Position3 = new javax.swing.JLabel();
        Barangay3 = new javax.swing.JLabel();
        Password3 = new javax.swing.JLabel();
        register = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Barangay_Field3 = new javax.swing.JComboBox<>();
        Position_Field3 = new javax.swing.JComboBox<>();
        Password_Field3 = new javax.swing.JPasswordField();
        Password4 = new javax.swing.JLabel();
        Confirm_Field = new javax.swing.JPasswordField();
        reg_fname_error = new javax.swing.JLabel();
        reg_lname_error = new javax.swing.JLabel();
        reg_email_error = new javax.swing.JLabel();
        reg_username_error = new javax.swing.JLabel();
        reg_password_error = new javax.swing.JLabel();
        reg_confirm_error = new javax.swing.JLabel();
        Delete = new javax.swing.JPanel();
        doDelete = new javax.swing.JButton();
        delete_field = new javax.swing.JTextField();
        jScrollPane19 = new javax.swing.JScrollPane();
        admin_list_delete = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        Edit = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        admin_list_edit = new javax.swing.JTable();
        Notifications_2 = new javax.swing.JPanel();
        TodayText = new javax.swing.JLabel();
        NotifSurvey_1 = new javax.swing.JLabel();
        LineSeperator_1 = new javax.swing.JSeparator();
        LineSeparator_2 = new javax.swing.JSeparator();
        NotifSurvey_2 = new javax.swing.JLabel();
        LineSeparator_3 = new javax.swing.JSeparator();
        NotifSurvey_3 = new javax.swing.JLabel();
        LineSeparator_4 = new javax.swing.JSeparator();
        NotifSurvey_4 = new javax.swing.JLabel();
        LineSeparator_5 = new javax.swing.JSeparator();
        TodayText1 = new javax.swing.JLabel();
        LineSeparator_6 = new javax.swing.JSeparator();
        NotifSurvey_7 = new javax.swing.JLabel();
        LineSeparator_7 = new javax.swing.JSeparator();
        NotifSurvey_8 = new javax.swing.JLabel();
        LineSeparator_8 = new javax.swing.JSeparator();
        NotifSurvey_9 = new javax.swing.JLabel();
        LineSeparator_9 = new javax.swing.JSeparator();
        NotifSurvey_10 = new javax.swing.JLabel();
        PrivacySettings_3 = new javax.swing.JPanel();
        PrivacyPolicy = new javax.swing.JLabel();
        Input_1 = new javax.swing.JScrollPane();
        PrivacyPolicy_Pane1 = new javax.swing.JTextPane();
        InformationWeCollect = new javax.swing.JLabel();
        Input_2 = new javax.swing.JScrollPane();
        InformationWeCollect_Pane2 = new javax.swing.JTextPane();
        PurposePrivacyPolicy = new javax.swing.JLabel();
        Input_3 = new javax.swing.JScrollPane();
        PurposePrivacyPolicy_Pane3 = new javax.swing.JTextPane();
        TermsServices_4 = new javax.swing.JPanel();
        TermsServices = new javax.swing.JLabel();
        Input_4 = new javax.swing.JScrollPane();
        TermsServices_Pane4 = new javax.swing.JTextPane();
        WelcomeYouthProfiling = new javax.swing.JLabel();
        Input_5 = new javax.swing.JScrollPane();
        WelcomeYouthProfiling_Pane5 = new javax.swing.JTextPane();
        UsingServices = new javax.swing.JLabel();
        Input_7 = new javax.swing.JScrollPane();
        UsingServices_Pane7 = new javax.swing.JTextPane();
        profile = new javax.swing.JPanel();
        notificon5 = new javax.swing.JLabel();
        proficon5 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        display_FName = new javax.swing.JTextField();
        display_Username = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        display_Email = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        display_ID = new javax.swing.JTextField();
        display_Password = new javax.swing.JPasswordField();
        jLabel41 = new javax.swing.JLabel();
        disable = new javax.swing.JLabel();
        show = new javax.swing.JLabel();
        edit_prof = new javax.swing.JButton();
        display_LName = new javax.swing.JTextField();
        display_Position = new javax.swing.JComboBox<>();
        display_Barangay = new javax.swing.JComboBox<>();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        edit_profile = new javax.swing.JPanel();
        notificon7 = new javax.swing.JLabel();
        proficon7 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        edit_FName = new javax.swing.JTextField();
        edit_Username = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        edit_Email = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        edit_ID = new javax.swing.JTextField();
        edit_Password = new javax.swing.JPasswordField();
        jLabel49 = new javax.swing.JLabel();
        disable1 = new javax.swing.JLabel();
        show1 = new javax.swing.JLabel();
        save_prof = new javax.swing.JButton();
        edit_LName = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        edit_Barangay = new javax.swing.JComboBox<>();
        edit_Position = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();

        proficon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Male User-30.png"))); // NOI18N
        proficon6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        proficon6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proficon6MouseClicked(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menu.setPreferredSize(new java.awt.Dimension(200, 600));
        menu.setLayout(new java.awt.BorderLayout());

        menuside.setBackground(new java.awt.Color(255, 255, 255));
        menuside.setPreferredSize(new java.awt.Dimension(50, 733));
        menuside.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hidemenu.setBackground(new java.awt.Color(255, 255, 255));

        hidemenubutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/menuicon.png"))); // NOI18N
        hidemenubutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hidemenubuttonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout hidemenuLayout = new javax.swing.GroupLayout(hidemenu);
        hidemenu.setLayout(hidemenuLayout);
        hidemenuLayout.setHorizontalGroup(
            hidemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hidemenuLayout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(hidemenubutton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        hidemenuLayout.setVerticalGroup(
            hidemenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hidemenuLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(hidemenubutton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuside.add(hidemenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 50));

        logo.setBackground(new java.awt.Color(255, 255, 255));

        logoicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/skicon.jpg"))); // NOI18N

        javax.swing.GroupLayout logoLayout = new javax.swing.GroupLayout(logo);
        logo.setLayout(logoLayout);
        logoLayout.setHorizontalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logoicon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        logoLayout.setVerticalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoicon)
                .addGap(14, 14, 14))
        );

        menuside.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 50, 60));

        profilingClicked.setBackground(new java.awt.Color(255, 255, 255));

        profilingbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/youthProfiling.png"))); // NOI18N
        profilingbutton.setText("jLabel1");
        profilingbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profilingbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profilingbuttonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout profilingClickedLayout = new javax.swing.GroupLayout(profilingClicked);
        profilingClicked.setLayout(profilingClickedLayout);
        profilingClickedLayout.setHorizontalGroup(
            profilingClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilingClickedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profilingbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE))
        );
        profilingClickedLayout.setVerticalGroup(
            profilingClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilingClickedLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(profilingbutton)
                .addContainerGap())
        );

        menuside.add(profilingClicked, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 40, 40));

        logout.setBackground(new java.awt.Color(255, 102, 0));

        logoutbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        logoutbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutbuttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutbuttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutbuttonMouseExited(evt);
            }
        });

        javax.swing.GroupLayout logoutLayout = new javax.swing.GroupLayout(logout);
        logout.setLayout(logoutLayout);
        logoutLayout.setHorizontalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoutLayout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(logoutbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(logoutbutton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuside.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 730, -1, -1));

        dboardClicked.setBackground(new java.awt.Color(255, 255, 255));

        dboardbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Dashicon.png"))); // NOI18N
        dboardbutton.setText("jLabel1");
        dboardbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dboardbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dboardbuttonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dboardClickedLayout = new javax.swing.GroupLayout(dboardClicked);
        dboardClicked.setLayout(dboardClickedLayout);
        dboardClickedLayout.setHorizontalGroup(
            dboardClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dboardClickedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dboardbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE))
        );
        dboardClickedLayout.setVerticalGroup(
            dboardClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dboardClickedLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dboardbutton)
                .addContainerGap())
        );

        menuside.add(dboardClicked, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 40));

        resultClicked.setBackground(new java.awt.Color(255, 255, 255));

        resultbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/resulticon.png"))); // NOI18N
        resultbutton.setText("jLabel1");
        resultbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resultbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultbuttonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout resultClickedLayout = new javax.swing.GroupLayout(resultClicked);
        resultClicked.setLayout(resultClickedLayout);
        resultClickedLayout.setHorizontalGroup(
            resultClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resultClickedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE))
        );
        resultClickedLayout.setVerticalGroup(
            resultClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resultClickedLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resultbutton)
                .addContainerGap())
        );

        menuside.add(resultClicked, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, 40));

        manualClicked.setBackground(new java.awt.Color(255, 255, 255));

        manualbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user-manual.png"))); // NOI18N
        manualbutton.setText("jLabel1");
        manualbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manualbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manualbuttonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout manualClickedLayout = new javax.swing.GroupLayout(manualClicked);
        manualClicked.setLayout(manualClickedLayout);
        manualClickedLayout.setHorizontalGroup(
            manualClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manualClickedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(manualbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE))
        );
        manualClickedLayout.setVerticalGroup(
            manualClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manualClickedLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(manualbutton)
                .addContainerGap())
        );

        menuside.add(manualClicked, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, 40));

        settingsClicked.setBackground(new java.awt.Color(255, 255, 255));

        settingsbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/settings.png"))); // NOI18N
        settingsbutton.setText("jLabel1");
        settingsbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        settingsbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsbuttonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout settingsClickedLayout = new javax.swing.GroupLayout(settingsClicked);
        settingsClicked.setLayout(settingsClickedLayout);
        settingsClickedLayout.setHorizontalGroup(
            settingsClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsClickedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, Short.MAX_VALUE))
        );
        settingsClickedLayout.setVerticalGroup(
            settingsClickedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsClickedLayout.createSequentialGroup()
                .addComponent(settingsbutton)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        menuside.add(settingsClicked, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, -1, 40));

        menu.add(menuside, java.awt.BorderLayout.LINE_START);

        menuhide.setBackground(new java.awt.Color(255, 255, 255));
        menuhide.setPreferredSize(new java.awt.Dimension(150, 733));
        menuhide.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sktext.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        sktext.setText("SK FEDERATION");
        menuhide.add(sktext, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 62, -1, -1));

        Citytext.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        Citytext.setText("City of Santa Rosa");
        menuhide.add(Citytext, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 87, 109, -1));

        menufooter.setBackground(new java.awt.Color(255, 102, 0));

        logoutlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        logoutlabel.setText("Log Out");
        logoutlabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutlabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menufooterLayout = new javax.swing.GroupLayout(menufooter);
        menufooter.setLayout(menufooterLayout);
        menufooterLayout.setHorizontalGroup(
            menufooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menufooterLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(logoutlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        menufooterLayout.setVerticalGroup(
            menufooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menufooterLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(logoutlabel)
                .addGap(16, 16, 16))
        );

        menuhide.add(menufooter, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 730, 150, 50));

        bgdboard.setBackground(new java.awt.Color(255, 255, 255));

        dboardlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dboardlabel.setText("Dashboard");
        dboardlabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dboardlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dboardlabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bgdboardLayout = new javax.swing.GroupLayout(bgdboard);
        bgdboard.setLayout(bgdboardLayout);
        bgdboardLayout.setHorizontalGroup(
            bgdboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgdboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dboardlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        bgdboardLayout.setVerticalGroup(
            bgdboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgdboardLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(dboardlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuhide.add(bgdboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 140, 40));

        bgprofiling.setBackground(new java.awt.Color(255, 255, 255));

        profilinglabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        profilinglabel.setText("Youth Profiling");
        profilinglabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        profilinglabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profilinglabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bgprofilingLayout = new javax.swing.GroupLayout(bgprofiling);
        bgprofiling.setLayout(bgprofilingLayout);
        bgprofilingLayout.setHorizontalGroup(
            bgprofilingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgprofilingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profilinglabel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        bgprofilingLayout.setVerticalGroup(
            bgprofilingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgprofilingLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(profilinglabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuhide.add(bgprofiling, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 140, 40));

        bgresult.setBackground(new java.awt.Color(255, 255, 255));

        resultlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        resultlabel.setText("Result");
        resultlabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resultlabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultlabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bgresultLayout = new javax.swing.GroupLayout(bgresult);
        bgresult.setLayout(bgresultLayout);
        bgresultLayout.setHorizontalGroup(
            bgresultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgresultLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        bgresultLayout.setVerticalGroup(
            bgresultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgresultLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(resultlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuhide.add(bgresult, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 140, 40));

        bgmanual.setBackground(new java.awt.Color(255, 255, 255));
        bgmanual.setPreferredSize(new java.awt.Dimension(1024, 600));

        manuallabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        manuallabel.setText("User Manual");
        manuallabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manuallabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manuallabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bgmanualLayout = new javax.swing.GroupLayout(bgmanual);
        bgmanual.setLayout(bgmanualLayout);
        bgmanualLayout.setHorizontalGroup(
            bgmanualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgmanualLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(manuallabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        bgmanualLayout.setVerticalGroup(
            bgmanualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgmanualLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(manuallabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuhide.add(bgmanual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 140, 40));

        bgsettings.setBackground(new java.awt.Color(255, 255, 255));

        settingslabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        settingslabel.setText("Settings");
        settingslabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingslabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bgsettingsLayout = new javax.swing.GroupLayout(bgsettings);
        bgsettings.setLayout(bgsettingsLayout);
        bgsettingsLayout.setHorizontalGroup(
            bgsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgsettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        bgsettingsLayout.setVerticalGroup(
            bgsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgsettingsLayout.createSequentialGroup()
                .addComponent(settingslabel, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuhide.add(bgsettings, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 150, 40));

        menu.add(menuhide, java.awt.BorderLayout.CENTER);

        getContentPane().add(menu, java.awt.BorderLayout.LINE_START);

        pagechanger.setBackground(new java.awt.Color(234, 234, 234));
        pagechanger.setLayout(new java.awt.CardLayout());

        dashboard.setBackground(new java.awt.Color(234, 234, 234));
        dashboard.setPreferredSize(new java.awt.Dimension(1616, 733));

        sidecharts.setBackground(new java.awt.Color(255, 255, 255));
        sidecharts.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        notificon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notif-30.png"))); // NOI18N
        notificon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificonMouseClicked(evt);
            }
        });
        sidecharts.add(notificon, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 30, 30));

        proficon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Male User-30.png"))); // NOI18N
        proficon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        proficon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proficonMouseClicked(evt);
            }
        });
        sidecharts.add(proficon, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 6, 30, 40));

        PieChartPanel.setLayout(new java.awt.BorderLayout());
        sidecharts.add(PieChartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 180, 140));

        viewall.setBackground(new java.awt.Color(255, 102, 0));
        viewall.setForeground(new java.awt.Color(255, 255, 255));
        viewall.setText("View All");
        viewall.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        viewall.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewallMouseClicked(evt);
            }
        });
        viewall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewallActionPerformed(evt);
            }
        });
        sidecharts.add(viewall, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 730, 150, 30));

        aplayaPieChart.setBackground(new java.awt.Color(255, 255, 255));
        aplayaPieChart.setLayout(new java.awt.BorderLayout());

        sinalhanPieChart.setBackground(new java.awt.Color(255, 255, 255));
        sinalhanPieChart.setLayout(new java.awt.BorderLayout());

        aplayalabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        aplayalabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/iconaddress.png"))); // NOI18N
        aplayalabel.setText("Brgy.Aplaya");

        sinalhanlabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        sinalhanlabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/iconaddress.png"))); // NOI18N
        sinalhanlabel.setText("Brgy.Sinalhan");

        javax.swing.GroupLayout bgprogressLayout = new javax.swing.GroupLayout(bgprogress);
        bgprogress.setLayout(bgprogressLayout);
        bgprogressLayout.setHorizontalGroup(
            bgprogressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgprogressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgprogressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sinalhanlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aplayalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(sinalhanPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(aplayaPieChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        bgprogressLayout.setVerticalGroup(
            bgprogressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgprogressLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(sinalhanlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sinalhanPieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aplayalabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aplayaPieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sidecharts.add(bgprogress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 180, 410));

        jScrollPane5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane5.setEnabled(false);
        jScrollPane5.setFocusable(false);

        OCR_chart.setEditable(false);
        OCR_chart.setBackground(new java.awt.Color(255, 255, 255));
        OCR_chart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        OCR_chart.setText("           OVERALL COMPLETION RATE");
        OCR_chart.setFocusable(false);
        jScrollPane5.setViewportView(OCR_chart);

        sidecharts.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 140, -1));

        line1.setBackground(new java.awt.Color(0, 0, 0));
        line1.setForeground(new java.awt.Color(0, 0, 0));
        sidecharts.add(line1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 160, 10));

        jScrollPane10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane10.setEnabled(false);
        jScrollPane10.setFocusable(false);

        progressstats3.setEditable(false);
        progressstats3.setBackground(new java.awt.Color(255, 255, 255));
        progressstats3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        progressstats3.setText("Progress Status");
        progressstats3.setFocusable(false);
        jScrollPane10.setViewportView(progressstats3);

        sidecharts.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 170, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Dashboard Overview");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Total Population ");

        barChartPanel.setBackground(new java.awt.Color(255, 255, 255));
        barChartPanel.setLayout(new java.awt.BorderLayout());

        activities.setBackground(new java.awt.Color(239, 181, 113));

        countbox1.setBackground(new java.awt.Color(255, 255, 255));
        countbox1.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(0, 151, 51)));
        countbox1.setForeground(new java.awt.Color(255, 255, 255));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane4.setEnabled(false);
        jScrollPane4.setFocusable(false);

        count2022.setEditable(false);
        count2022.setBackground(new java.awt.Color(255, 255, 255));
        count2022.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        count2022.setText("2022 TOTAL YOUTH PROFILED:");
        count2022.setFocusable(false);
        jScrollPane4.setViewportView(count2022);

        count1.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        count1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count1.setText("450,000");

        javax.swing.GroupLayout countbox1Layout = new javax.swing.GroupLayout(countbox1);
        countbox1.setLayout(countbox1Layout);
        countbox1Layout.setHorizontalGroup(
            countbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(countbox1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(countbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(count1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        countbox1Layout.setVerticalGroup(
            countbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(countbox1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(count1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        countbox2.setBackground(new java.awt.Color(255, 255, 255));
        countbox2.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(9, 116, 162)));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane2.setEnabled(false);
        jScrollPane2.setFocusable(false);

        count2023.setEditable(false);
        count2023.setBackground(new java.awt.Color(255, 255, 255));
        count2023.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        count2023.setText("2023 TOTAL YOUTH PROFILED:");
        count2023.setFocusable(false);
        jScrollPane2.setViewportView(count2023);

        count2.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        count2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count2.setText("120,000");

        javax.swing.GroupLayout countbox2Layout = new javax.swing.GroupLayout(countbox2);
        countbox2.setLayout(countbox2Layout);
        countbox2Layout.setHorizontalGroup(
            countbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(countbox2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(countbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(count2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        countbox2Layout.setVerticalGroup(
            countbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(countbox2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(count2)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        countbox3.setBackground(new java.awt.Color(255, 255, 255));
        countbox3.setBorder(javax.swing.BorderFactory.createMatteBorder(35, 0, 0, 0, new java.awt.Color(233, 20, 20)));

        count4.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        count4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count4.setText("230,000");

        jScrollPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane7.setEnabled(false);
        jScrollPane7.setFocusable(false);

        count2025.setEditable(false);
        count2025.setBackground(new java.awt.Color(255, 255, 255));
        count2025.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        count2025.setText("2024 TOTAL YOUTH PROFILED:");
        count2025.setFocusable(false);
        jScrollPane7.setViewportView(count2025);

        javax.swing.GroupLayout countbox3Layout = new javax.swing.GroupLayout(countbox3);
        countbox3.setLayout(countbox3Layout);
        countbox3Layout.setHorizontalGroup(
            countbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(countbox3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(countbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(count4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        countbox3Layout.setVerticalGroup(
            countbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(countbox3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(count4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Activities");

        jScrollPane6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane6.setEnabled(false);
        jScrollPane6.setFocusable(false);

        act_text.setEditable(false);
        act_text.setBackground(new java.awt.Color(239, 181, 113));
        act_text.setText("No activites detected in the past 24 hours.");
        act_text.setFocusable(false);
        jScrollPane6.setViewportView(act_text);

        javax.swing.GroupLayout activitiesLayout = new javax.swing.GroupLayout(activities);
        activities.setLayout(activitiesLayout);
        activitiesLayout.setHorizontalGroup(
            activitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(activitiesLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(activitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(activitiesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(countbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(countbox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(countbox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        activitiesLayout.setVerticalGroup(
            activitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(activitiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(activitiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(countbox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(countbox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(countbox3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(activitiesLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6)))
                .addContainerGap())
        );

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardLayout.createSequentialGroup()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(barChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(activities, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sidecharts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(303, 303, 303))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sidecharts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(barChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(activities, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(98, 98, 98))
        );

        pagechanger.add(dashboard, "card2");

        youthprofiling.setPreferredSize(new java.awt.Dimension(1616, 733));

        notificon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notif-30.png"))); // NOI18N
        notificon1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificon1MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel9.setText("YOUTH PROFILING");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel10.setText("Please fill up all the forms and answer the question with all honesty. Do not leave each item blanked/unanswered.");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel11.setText("Middle Initial:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("First Name:");

        jtxtFName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jtxtMInitial.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Last Name:");

        jtxtLName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setText("Sex Assigned by Birth:");

        jtxtSuffix.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setText("Birthday:");

        jtxtBday.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel16.setText("Age:");

        jtxtAge.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setText("Email:");

        jtxtEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setText("Phone Number:");

        jtxtPNumber.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("Suffix:");

        jtxtSexB.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setText("DEMONGRAPHIC CHARACTERISTICS");

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setText("Civil Status:");

        jLabel22.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel22.setText("Educational Background:");

        jLabel23.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel23.setText("Youth Age Group:");

        cboCivil.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Single", "Married", "Widowed", "Divorced", "Separated", "Annulled", "Unknown", "Live-in" }));

        cboYouthAge.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboYouthAge.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Child Youth (15-17 yrs old)", "Core Youth (18-24 yrs old)", "Young Adult (25-30 yrs old)" }));

        cboEducBack.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboEducBack.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elementary Level", "Elementary Grad", "Highschool Level", "Highschool Grad", "Vocational Grad", "College Level", "College Grad", "Masters Level", "Masters Grad", "Doctorate Level", "Doctorate Graduate" }));

        jLabel24.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel24.setText("Youth Classification:");

        jLabel25.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel25.setText("Work Status:");

        cboYouthClass.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboYouthClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "In School Youth", "Out of School Youth", "Working Youth", "Youth w/Specific needs" }));

        cboWorkStatus.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboWorkStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employed", "Unemployed", "Self-Employed", "Currently looking for a Job", "Non-interested Looking for a Job" }));

        jLabel26.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel26.setText("Registered SK Voter?");

        cboSKVoter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboSKVoter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setText("Registered National Voter?");

        cboNVoter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboNVoter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setText("Have you already attended KK Assembly?");

        cboKKAssembly.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboKKAssembly.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboWorkStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(10, 10, 10)
                                .addComponent(cboYouthClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtFName, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel11))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtBday, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtAge, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtPNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtSexB, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jtxtMInitial, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jtxtLName, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboYouthAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboEducBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(80, 80, 80)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboKKAssembly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboNVoter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboSKVoter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(jLabel20)))
                .addGap(0, 366, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jtxtMInitial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jtxtLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtBday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jtxtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jtxtSexB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jtxtPNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(cboCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(cboSKVoter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cboYouthAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(cboNVoter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cboEducBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(cboKKAssembly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboYouthClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboWorkStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(24, 24, 24))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jbtnAddNew.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jbtnAddNew.setText("Add New");
        jbtnAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddNewActionPerformed(evt);
            }
        });

        jbtnUpdateData.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jbtnUpdateData.setText("Update Data");
        jbtnUpdateData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateDataActionPerformed(evt);
            }
        });

        jbtnPrint.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jbtnPrint.setText("Print");
        jbtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPrintActionPerformed(evt);
            }
        });

        jbtnReset.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jbtnReset.setText("Reset");
        jbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnResetActionPerformed(evt);
            }
        });

        jbtnDelete.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jbtnDelete.setText("Archive");
        jbtnDelete.setToolTipText("");
        jbtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jbtnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnUpdateData, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtnUpdateData, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jTable1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "First Name", "Middle Initial", "Last Name", "Suffix", "Birthday", "Age", "Sex", "Email", "Phone Number", "Civil Status", "Youth Age Group", "Educational Background", "Youth Class", "Work Status", "SK Voter", "National Voter", "Attended KK Assembly"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout youthprofilingLayout = new javax.swing.GroupLayout(youthprofiling);
        youthprofiling.setLayout(youthprofilingLayout);
        youthprofilingLayout.setHorizontalGroup(
            youthprofilingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(youthprofilingLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(youthprofilingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(youthprofilingLayout.createSequentialGroup()
                        .addGroup(youthprofilingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notificon1)
                .addGap(24, 24, 24))
        );
        youthprofilingLayout.setVerticalGroup(
            youthprofilingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(youthprofilingLayout.createSequentialGroup()
                .addGroup(youthprofilingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(youthprofilingLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel9)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(youthprofilingLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(notificon1)))
                .addContainerGap())
        );

        pagechanger.add(youthprofiling, "card3");

        notificon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notif-30.png"))); // NOI18N
        notificon2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificon2MouseClicked(evt);
            }
        });

        proficon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Male User-30.png"))); // NOI18N
        proficon2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        proficon2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proficon2MouseClicked(evt);
            }
        });

        YouthProfiling_description.setBackground(new java.awt.Color(255, 153, 51));

        youth_profiling_text.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        youth_profiling_text.setForeground(new java.awt.Color(255, 255, 255));
        youth_profiling_text.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user-mnl.png"))); // NOI18N
        youth_profiling_text.setText("YOUTH PROFILING");

        application_text.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        application_text.setForeground(new java.awt.Color(255, 255, 255));
        application_text.setText("APPLICATION");

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(255, 153, 51));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Youth Profiling Application was designed and developed to resolve the existing profiling processes of the Sangguniang Kabataan ng Barangay Pooc at Santa Rosa City.");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextArea1.setFocusable(false);
        jScrollPane8.setViewportView(jTextArea1);

        javax.swing.GroupLayout YouthProfiling_descriptionLayout = new javax.swing.GroupLayout(YouthProfiling_description);
        YouthProfiling_description.setLayout(YouthProfiling_descriptionLayout);
        YouthProfiling_descriptionLayout.setHorizontalGroup(
            YouthProfiling_descriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(YouthProfiling_descriptionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(YouthProfiling_descriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(youth_profiling_text, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, YouthProfiling_descriptionLayout.createSequentialGroup()
                        .addComponent(application_text, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
        );
        YouthProfiling_descriptionLayout.setVerticalGroup(
            YouthProfiling_descriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(YouthProfiling_descriptionLayout.createSequentialGroup()
                .addGroup(YouthProfiling_descriptionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(YouthProfiling_descriptionLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(youth_profiling_text)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(application_text))
                    .addGroup(YouthProfiling_descriptionLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        faqsbtn_Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                faqsbtn_PanelMouseClicked(evt);
            }
        });

        faqs_btn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        faqs_btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        faqs_btn.setText("FAQS");
        faqs_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                faqs_btnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout faqsbtn_PanelLayout = new javax.swing.GroupLayout(faqsbtn_Panel);
        faqsbtn_Panel.setLayout(faqsbtn_PanelLayout);
        faqsbtn_PanelLayout.setHorizontalGroup(
            faqsbtn_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faqsbtn_PanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(faqs_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        faqsbtn_PanelLayout.setVerticalGroup(
            faqsbtn_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, faqsbtn_PanelLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(faqs_btn)
                .addGap(14, 14, 14))
        );

        featurebtn_Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                featurebtn_PanelMouseClicked(evt);
            }
        });

        feature_btn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        feature_btn.setText("FEATURES");
        feature_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                feature_btnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout featurebtn_PanelLayout = new javax.swing.GroupLayout(featurebtn_Panel);
        featurebtn_Panel.setLayout(featurebtn_PanelLayout);
        featurebtn_PanelLayout.setHorizontalGroup(
            featurebtn_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(featurebtn_PanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(feature_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );
        featurebtn_PanelLayout.setVerticalGroup(
            featurebtn_PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, featurebtn_PanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(feature_btn)
                .addGap(15, 15, 15))
        );

        page.setLayout(new java.awt.CardLayout());

        features.setBackground(java.awt.SystemColor.control);
        features.setLayout(new java.awt.CardLayout());

        feature14.setBackground(new java.awt.Color(255, 255, 255));

        f2_left12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left12MouseClicked(evt);
            }
        });
        f2_left12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left12ActionPerformed(evt);
            }
        });

        f2_right13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        f2_right13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right13MouseClicked(evt);
            }
        });

        title_f14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f14.setText("YOUTH PROFILING APP STARTING PAGE");

        f2_pic13.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/opening.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic13Layout = new javax.swing.GroupLayout(f2_pic13);
        f2_pic13.setLayout(f2_pic13Layout);
        f2_pic13Layout.setHorizontalGroup(
            f2_pic13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict13, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic13Layout.setVerticalGroup(
            f2_pic13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict13, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc13.setEditable(false);
        f1_desc13.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc13.setText("This is the starting page of the app when it is launched. By clicking \"getting started\", the user will officially start his/her session with the application. ");
        f1_desc13.setFocusable(false);
        f1_desc13.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont13.setViewportView(f1_desc13);

        f2_page14.setText("01");

        javax.swing.GroupLayout feature14Layout = new javax.swing.GroupLayout(feature14);
        feature14.setLayout(feature14Layout);
        feature14Layout.setHorizontalGroup(
            feature14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature14Layout.createSequentialGroup()
                .addGroup(feature14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature14Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont13, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature14Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page14))
                            .addGroup(feature14Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right13))))
                    .addGroup(feature14Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f14, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        feature14Layout.setVerticalGroup(
            feature14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature14Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f14)
                .addGap(19, 19, 19)
                .addGroup(feature14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature14Layout.createSequentialGroup()
                        .addComponent(f2_pic13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature14Layout.createSequentialGroup()
                        .addGroup(feature14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature14Layout.createSequentialGroup()
                                .addComponent(f2_left12)
                                .addGap(126, 126, 126))
                            .addGroup(feature14Layout.createSequentialGroup()
                                .addComponent(f2_right13)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page14)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature14Layout.createSequentialGroup()
                        .addComponent(f2_textcont13, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature14, "card2");

        feature15.setBackground(new java.awt.Color(255, 255, 255));

        f2_left13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left13MouseClicked(evt);
            }
        });
        f2_left13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left13ActionPerformed(evt);
            }
        });

        f2_right14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right14.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right14MouseClicked(evt);
            }
        });

        title_f15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f15.setText("LOGIN PAGE");

        f2_pic14.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/login.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic14Layout = new javax.swing.GroupLayout(f2_pic14);
        f2_pic14.setLayout(f2_pic14Layout);
        f2_pic14Layout.setHorizontalGroup(
            f2_pic14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict14, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic14Layout.setVerticalGroup(
            f2_pic14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict14, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc14.setEditable(false);
        f1_desc14.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc14.setText("If the user proceeds with getting started, the system will display the login page and ask login credentials from the user, such as:\n\n1. ID Number\n2. Username, and\n3. Password\n\nAfter which the user finished filling up, the user shall be directed to the application's dashboard.");
        f1_desc14.setFocusable(false);
        f1_desc14.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont14.setViewportView(f1_desc14);

        f2_page15.setText("02");

        javax.swing.GroupLayout feature15Layout = new javax.swing.GroupLayout(feature15);
        feature15.setLayout(feature15Layout);
        feature15Layout.setHorizontalGroup(
            feature15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature15Layout.createSequentialGroup()
                .addGroup(feature15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature15Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont14, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature15Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page15))
                            .addGroup(feature15Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right14))))
                    .addGroup(feature15Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f15, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        feature15Layout.setVerticalGroup(
            feature15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature15Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f15)
                .addGap(19, 19, 19)
                .addGroup(feature15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature15Layout.createSequentialGroup()
                        .addComponent(f2_pic14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature15Layout.createSequentialGroup()
                        .addGroup(feature15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature15Layout.createSequentialGroup()
                                .addComponent(f2_left13)
                                .addGap(126, 126, 126))
                            .addGroup(feature15Layout.createSequentialGroup()
                                .addComponent(f2_right14)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page15)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature15Layout.createSequentialGroup()
                        .addComponent(f2_textcont14, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature15, "card2");

        feature16.setBackground(new java.awt.Color(255, 255, 255));

        f2_left14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left14.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left14MouseClicked(evt);
            }
        });
        f2_left14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left14ActionPerformed(evt);
            }
        });

        f2_right15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right15.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right15MouseClicked(evt);
            }
        });

        title_f16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f16.setText("DASHBOARD PAGE");

        f2_pic15.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/prof-page.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic15Layout = new javax.swing.GroupLayout(f2_pic15);
        f2_pic15.setLayout(f2_pic15Layout);
        f2_pic15Layout.setHorizontalGroup(
            f2_pic15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict15, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic15Layout.setVerticalGroup(
            f2_pic15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict15, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc15.setEditable(false);
        f1_desc15.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc15.setText("After login-in, the user will be welcomed by the dashboard page composed primarily by charts or visual representation of the profiling process' results.");
        f1_desc15.setFocusable(false);
        f1_desc15.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont15.setViewportView(f1_desc15);

        f2_page16.setText("03");

        javax.swing.GroupLayout feature16Layout = new javax.swing.GroupLayout(feature16);
        feature16.setLayout(feature16Layout);
        feature16Layout.setHorizontalGroup(
            feature16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature16Layout.createSequentialGroup()
                .addGroup(feature16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature16Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont15, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature16Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page16))
                            .addGroup(feature16Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right15))))
                    .addGroup(feature16Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f16, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        feature16Layout.setVerticalGroup(
            feature16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature16Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f16)
                .addGap(19, 19, 19)
                .addGroup(feature16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature16Layout.createSequentialGroup()
                        .addComponent(f2_pic15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature16Layout.createSequentialGroup()
                        .addGroup(feature16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature16Layout.createSequentialGroup()
                                .addComponent(f2_left14)
                                .addGap(126, 126, 126))
                            .addGroup(feature16Layout.createSequentialGroup()
                                .addComponent(f2_right15)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page16)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature16Layout.createSequentialGroup()
                        .addComponent(f2_textcont15, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature16, "card2");

        feature2.setBackground(new java.awt.Color(255, 255, 255));

        f2_left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_leftMouseClicked(evt);
            }
        });
        f2_left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_leftActionPerformed(evt);
            }
        });

        f2_right1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right1MouseClicked(evt);
            }
        });

        title_f2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f2.setText("PROFILING PAGE");

        f2_pic1.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user-profile.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic1Layout = new javax.swing.GroupLayout(f2_pic1);
        f2_pic1.setLayout(f2_pic1Layout);
        f2_pic1Layout.setHorizontalGroup(
            f2_pic1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic1Layout.setVerticalGroup(
            f2_pic1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc1.setEditable(false);
        f1_desc1.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc1.setText("Upon clicking the \"Youth Profiling\" from the side bar, the system will display the digitalized form for the Youth Profiling process. This includes input fields and drop down buttons for civil status, age group, education background, and etc. \n\nSeveral buttons are also presented and is seating right between the form and the table that displays the inputted data. ");
        f1_desc1.setFocusable(false);
        f1_desc1.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont1.setViewportView(f1_desc1);

        f2_page2.setText("04");

        javax.swing.GroupLayout feature2Layout = new javax.swing.GroupLayout(feature2);
        feature2.setLayout(feature2Layout);
        feature2Layout.setHorizontalGroup(
            feature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature2Layout.createSequentialGroup()
                .addGroup(feature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature2Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature2Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page2))
                            .addGroup(feature2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right1))))
                    .addGroup(feature2Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f2)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature2Layout.setVerticalGroup(
            feature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f2)
                .addGroup(feature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(feature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature2Layout.createSequentialGroup()
                                .addComponent(f2_pic1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature2Layout.createSequentialGroup()
                                .addGroup(feature2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(feature2Layout.createSequentialGroup()
                                        .addComponent(f2_left)
                                        .addGap(126, 126, 126))
                                    .addGroup(feature2Layout.createSequentialGroup()
                                        .addComponent(f2_right1)
                                        .addGap(119, 119, 119)))
                                .addComponent(f2_page2)
                                .addGap(34, 34, 34))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_textcont1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature2, "card2");

        feature1.setBackground(new java.awt.Color(255, 255, 255));

        f1_left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f1_left.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f1_left.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f1_leftMouseClicked(evt);
            }
        });
        f1_left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f1_leftActionPerformed(evt);
            }
        });

        f1_right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f1_right.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f1_right.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f1_rightMouseClicked(evt);
            }
        });

        f1_title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        f1_title.setText("PROFILING PAGE - ADD BUTTON");

        f1_pic.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profiling page.png"))); // NOI18N

        javax.swing.GroupLayout f1_picLayout = new javax.swing.GroupLayout(f1_pic);
        f1_pic.setLayout(f1_picLayout);
        f1_picLayout.setHorizontalGroup(
            f1_picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f1_picLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f1_picLayout.setVerticalGroup(
            f1_picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f1_picLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc.setEditable(false);
        f1_desc.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc.setText("Upon clicking the \"Youth Profiling\" from the side bar, the system will display the digitalized form for the Youth Profiling process. This includes input fields and drop down buttons for civil status, age group, education background, and etc. \n\nSeveral buttons are also presented and is seating right between the form and the table that displays the inputted data. ");
        f1_desc.setFocusable(false);
        f1_desc.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f1_textcont.setViewportView(f1_desc);

        f1_page1.setText("03");

        javax.swing.GroupLayout feature1Layout = new javax.swing.GroupLayout(feature1);
        feature1.setLayout(feature1Layout);
        feature1Layout.setHorizontalGroup(
            feature1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature1Layout.createSequentialGroup()
                .addGroup(feature1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature1Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f1_left)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f1_pic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f1_textcont, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f1_page1))
                            .addGroup(feature1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f1_right))))
                    .addGroup(feature1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(f1_title, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature1Layout.setVerticalGroup(
            feature1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature1Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(f1_title)
                .addGap(19, 19, 19)
                .addGroup(feature1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature1Layout.createSequentialGroup()
                        .addComponent(f1_pic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature1Layout.createSequentialGroup()
                        .addGroup(feature1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature1Layout.createSequentialGroup()
                                .addComponent(f1_left)
                                .addGap(126, 126, 126))
                            .addGroup(feature1Layout.createSequentialGroup()
                                .addComponent(f1_right)
                                .addGap(119, 119, 119)))
                        .addComponent(f1_page1)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature1Layout.createSequentialGroup()
                        .addComponent(f1_textcont, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature1, "card2");

        feature5.setBackground(new java.awt.Color(255, 255, 255));

        f2_left1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left1MouseClicked(evt);
            }
        });
        f2_left1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left1ActionPerformed(evt);
            }
        });

        f2_right2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right2MouseClicked(evt);
            }
        });

        title_f3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f3.setText("PROFILING PAGE - UPDATE DATA BUTTON");

        f2_pic2.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profiling page.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic2Layout = new javax.swing.GroupLayout(f2_pic2);
        f2_pic2.setLayout(f2_pic2Layout);
        f2_pic2Layout.setHorizontalGroup(
            f2_pic2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict2, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic2Layout.setVerticalGroup(
            f2_pic2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc2.setEditable(false);
        f1_desc2.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc2.setText("Upon clicking the \"Youth Profiling\" from the side bar, the system will display the digitalized form for the Youth Profiling process. This includes input fields and drop down buttons for civil status, age group, education background, and etc. \n\nSeveral buttons are also presented and is seating right between the form and the table that displays the inputted data. ");
        f1_desc2.setFocusable(false);
        f1_desc2.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont2.setViewportView(f1_desc2);

        f2_page3.setText("04");

        javax.swing.GroupLayout feature5Layout = new javax.swing.GroupLayout(feature5);
        feature5.setLayout(feature5Layout);
        feature5Layout.setHorizontalGroup(
            feature5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature5Layout.createSequentialGroup()
                .addGroup(feature5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature5Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature5Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page3))
                            .addGroup(feature5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right2))))
                    .addGroup(feature5Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f3, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature5Layout.setVerticalGroup(
            feature5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature5Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f3)
                .addGap(19, 19, 19)
                .addGroup(feature5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature5Layout.createSequentialGroup()
                        .addComponent(f2_pic2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature5Layout.createSequentialGroup()
                        .addGroup(feature5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature5Layout.createSequentialGroup()
                                .addComponent(f2_left1)
                                .addGap(126, 126, 126))
                            .addGroup(feature5Layout.createSequentialGroup()
                                .addComponent(f2_right2)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page3)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature5Layout.createSequentialGroup()
                        .addComponent(f2_textcont2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature5, "card2");

        feature6.setBackground(new java.awt.Color(255, 255, 255));

        f2_left2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left2MouseClicked(evt);
            }
        });
        f2_left2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left2ActionPerformed(evt);
            }
        });

        f2_right3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right3MouseClicked(evt);
            }
        });

        title_f4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f4.setText("PROFILING PAGE - PRINT BUTTON");

        f2_pic3.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profiling page.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic3Layout = new javax.swing.GroupLayout(f2_pic3);
        f2_pic3.setLayout(f2_pic3Layout);
        f2_pic3Layout.setHorizontalGroup(
            f2_pic3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict3, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic3Layout.setVerticalGroup(
            f2_pic3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict3, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc3.setEditable(false);
        f1_desc3.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc3.setText("Upon clicking the \"Print\" from the profiling page, the system will display the ");
        f1_desc3.setFocusable(false);
        f1_desc3.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont3.setViewportView(f1_desc3);

        f2_page4.setText("05");

        javax.swing.GroupLayout feature6Layout = new javax.swing.GroupLayout(feature6);
        feature6.setLayout(feature6Layout);
        feature6Layout.setHorizontalGroup(
            feature6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature6Layout.createSequentialGroup()
                .addGroup(feature6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature6Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont3, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature6Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page4))
                            .addGroup(feature6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right3))))
                    .addGroup(feature6Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f4, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature6Layout.setVerticalGroup(
            feature6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature6Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f4)
                .addGap(19, 19, 19)
                .addGroup(feature6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature6Layout.createSequentialGroup()
                        .addComponent(f2_pic3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature6Layout.createSequentialGroup()
                        .addGroup(feature6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature6Layout.createSequentialGroup()
                                .addComponent(f2_left2)
                                .addGap(126, 126, 126))
                            .addGroup(feature6Layout.createSequentialGroup()
                                .addComponent(f2_right3)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page4)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature6Layout.createSequentialGroup()
                        .addComponent(f2_textcont3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature6, "card2");

        feature7.setBackground(new java.awt.Color(255, 255, 255));

        f2_left3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                f2_left3MouseEntered(evt);
            }
        });
        f2_left3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left3ActionPerformed(evt);
            }
        });

        f2_right4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right4MouseClicked(evt);
            }
        });

        title_f5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f5.setText("PROFILING PAGE - RESET BUTTON");

        f2_pic4.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profiling page.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic4Layout = new javax.swing.GroupLayout(f2_pic4);
        f2_pic4.setLayout(f2_pic4Layout);
        f2_pic4Layout.setHorizontalGroup(
            f2_pic4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict4, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic4Layout.setVerticalGroup(
            f2_pic4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict4, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc4.setEditable(false);
        f1_desc4.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc4.setText("Upon clicking the \"Reset\" from the youth profiling tab, the system will display ");
        f1_desc4.setFocusable(false);
        f1_desc4.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont4.setViewportView(f1_desc4);

        f2_page5.setText("06");

        javax.swing.GroupLayout feature7Layout = new javax.swing.GroupLayout(feature7);
        feature7.setLayout(feature7Layout);
        feature7Layout.setHorizontalGroup(
            feature7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature7Layout.createSequentialGroup()
                .addGroup(feature7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature7Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont4, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature7Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page5))
                            .addGroup(feature7Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right4))))
                    .addGroup(feature7Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f5, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature7Layout.setVerticalGroup(
            feature7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature7Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f5)
                .addGap(19, 19, 19)
                .addGroup(feature7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature7Layout.createSequentialGroup()
                        .addComponent(f2_pic4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature7Layout.createSequentialGroup()
                        .addGroup(feature7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature7Layout.createSequentialGroup()
                                .addComponent(f2_left3)
                                .addGap(126, 126, 126))
                            .addGroup(feature7Layout.createSequentialGroup()
                                .addComponent(f2_right4)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page5)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature7Layout.createSequentialGroup()
                        .addComponent(f2_textcont4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature7, "card2");

        feature8.setBackground(new java.awt.Color(255, 255, 255));

        f2_left4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left4MouseClicked(evt);
            }
        });
        f2_left4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left4ActionPerformed(evt);
            }
        });

        f2_right5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right5MouseClicked(evt);
            }
        });

        title_f6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f6.setText("PROFILING PAGE - DELETE BUTTON");

        f2_pic5.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profiling page.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic5Layout = new javax.swing.GroupLayout(f2_pic5);
        f2_pic5.setLayout(f2_pic5Layout);
        f2_pic5Layout.setHorizontalGroup(
            f2_pic5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict5, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic5Layout.setVerticalGroup(
            f2_pic5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict5, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc5.setEditable(false);
        f1_desc5.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc5.setText("Upon clicking the \"Delete\" from the side youth profiling tab, the system will display the ");
        f1_desc5.setFocusable(false);
        f1_desc5.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont5.setViewportView(f1_desc5);

        f2_page6.setText("07");

        javax.swing.GroupLayout feature8Layout = new javax.swing.GroupLayout(feature8);
        feature8.setLayout(feature8Layout);
        feature8Layout.setHorizontalGroup(
            feature8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature8Layout.createSequentialGroup()
                .addGroup(feature8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature8Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont5, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature8Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page6))
                            .addGroup(feature8Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right5))))
                    .addGroup(feature8Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f6, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature8Layout.setVerticalGroup(
            feature8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature8Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f6)
                .addGap(19, 19, 19)
                .addGroup(feature8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature8Layout.createSequentialGroup()
                        .addComponent(f2_pic5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature8Layout.createSequentialGroup()
                        .addGroup(feature8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature8Layout.createSequentialGroup()
                                .addComponent(f2_left4)
                                .addGap(126, 126, 126))
                            .addGroup(feature8Layout.createSequentialGroup()
                                .addComponent(f2_right5)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page6)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature8Layout.createSequentialGroup()
                        .addComponent(f2_textcont5, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature8, "card2");

        feature9.setBackground(new java.awt.Color(255, 255, 255));

        f2_left5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left5MouseClicked(evt);
            }
        });
        f2_left5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left5ActionPerformed(evt);
            }
        });

        f2_right6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right6MouseClicked(evt);
            }
        });

        title_f7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f7.setText("SETTINGS PAGE");

        f2_pic6.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/SETTINGS page.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic6Layout = new javax.swing.GroupLayout(f2_pic6);
        f2_pic6.setLayout(f2_pic6Layout);
        f2_pic6Layout.setHorizontalGroup(
            f2_pic6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict6, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic6Layout.setVerticalGroup(
            f2_pic6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict6, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc6.setEditable(false);
        f1_desc6.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc6.setText("Upon clicking the \"Settings\" from the side bar, the system will display the \"Manage Users\" category initially which also have several buttons to navigate.");
        f1_desc6.setFocusable(false);
        f1_desc6.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont6.setViewportView(f1_desc6);

        f2_page7.setText("08");

        javax.swing.GroupLayout feature9Layout = new javax.swing.GroupLayout(feature9);
        feature9.setLayout(feature9Layout);
        feature9Layout.setHorizontalGroup(
            feature9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature9Layout.createSequentialGroup()
                .addGroup(feature9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature9Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont6, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature9Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page7))
                            .addGroup(feature9Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right6))))
                    .addGroup(feature9Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f7, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature9Layout.setVerticalGroup(
            feature9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature9Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f7)
                .addGap(19, 19, 19)
                .addGroup(feature9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature9Layout.createSequentialGroup()
                        .addComponent(f2_pic6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature9Layout.createSequentialGroup()
                        .addGroup(feature9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature9Layout.createSequentialGroup()
                                .addComponent(f2_left5)
                                .addGap(126, 126, 126))
                            .addGroup(feature9Layout.createSequentialGroup()
                                .addComponent(f2_right6)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page7)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature9Layout.createSequentialGroup()
                        .addComponent(f2_textcont6, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature9, "card2");

        feature10.setBackground(new java.awt.Color(255, 255, 255));

        f2_left6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left6MouseClicked(evt);
            }
        });
        f2_left6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left6ActionPerformed(evt);
            }
        });

        f2_right7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right7MouseClicked(evt);
            }
        });

        title_f8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f8.setText("SETTINGS PAGE - ADD BUTTON");

        f2_pic7.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/SETTINGS page - ADD.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic7Layout = new javax.swing.GroupLayout(f2_pic7);
        f2_pic7.setLayout(f2_pic7Layout);
        f2_pic7Layout.setHorizontalGroup(
            f2_pic7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict7, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic7Layout.setVerticalGroup(
            f2_pic7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict7, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc7.setEditable(false);
        f1_desc7.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc7.setText("If the user clicked on the \"Add\" button from the top right of the Manage User category, the system will display a form for which the user can register a new account as per needed. \n\nAfter registering, an alert notification indicating that the registration was completed will be displayed promptly. ");
        f1_desc7.setFocusable(false);
        f1_desc7.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont7.setViewportView(f1_desc7);

        f2_page8.setText("09");

        javax.swing.GroupLayout feature10Layout = new javax.swing.GroupLayout(feature10);
        feature10.setLayout(feature10Layout);
        feature10Layout.setHorizontalGroup(
            feature10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature10Layout.createSequentialGroup()
                .addGroup(feature10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature10Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont7, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature10Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page8))
                            .addGroup(feature10Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right7))))
                    .addGroup(feature10Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f8, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature10Layout.setVerticalGroup(
            feature10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature10Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f8)
                .addGap(19, 19, 19)
                .addGroup(feature10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature10Layout.createSequentialGroup()
                        .addComponent(f2_pic7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature10Layout.createSequentialGroup()
                        .addGroup(feature10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature10Layout.createSequentialGroup()
                                .addComponent(f2_left6)
                                .addGap(126, 126, 126))
                            .addGroup(feature10Layout.createSequentialGroup()
                                .addComponent(f2_right7)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page8)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature10Layout.createSequentialGroup()
                        .addComponent(f2_textcont7, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature10, "card2");

        feature11.setBackground(new java.awt.Color(255, 255, 255));

        f2_left7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left7MouseClicked(evt);
            }
        });
        f2_left7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left7ActionPerformed(evt);
            }
        });

        f2_right8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right8MouseClicked(evt);
            }
        });

        title_f9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f9.setText("SETTINGS PAGE - DELETE BUTTON");

        f2_pic8.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/SETTINGS page - DELETE.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic8Layout = new javax.swing.GroupLayout(f2_pic8);
        f2_pic8.setLayout(f2_pic8Layout);
        f2_pic8Layout.setHorizontalGroup(
            f2_pic8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict8, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic8Layout.setVerticalGroup(
            f2_pic8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict8, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc8.setEditable(false);
        f1_desc8.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc8.setText("If the user clicked on the \"Delete\" button, the system will display a search bar on the top part to where the user can look for the username of the record to be deleted.\n\nAfter successfully deleting a record, the application will notify the user  as shown in the figure.");
        f1_desc8.setFocusable(false);
        f1_desc8.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont8.setViewportView(f1_desc8);

        f2_page9.setText("10");

        javax.swing.GroupLayout feature11Layout = new javax.swing.GroupLayout(feature11);
        feature11.setLayout(feature11Layout);
        feature11Layout.setHorizontalGroup(
            feature11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature11Layout.createSequentialGroup()
                .addGroup(feature11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature11Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont8, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature11Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page9))
                            .addGroup(feature11Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right8))))
                    .addGroup(feature11Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f9, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature11Layout.setVerticalGroup(
            feature11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature11Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f9)
                .addGap(19, 19, 19)
                .addGroup(feature11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature11Layout.createSequentialGroup()
                        .addComponent(f2_pic8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature11Layout.createSequentialGroup()
                        .addGroup(feature11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature11Layout.createSequentialGroup()
                                .addComponent(f2_left7)
                                .addGap(126, 126, 126))
                            .addGroup(feature11Layout.createSequentialGroup()
                                .addComponent(f2_right8)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page9)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature11Layout.createSequentialGroup()
                        .addComponent(f2_textcont8, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature11, "card2");

        feature12.setBackground(new java.awt.Color(255, 255, 255));

        f2_left8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left8MouseClicked(evt);
            }
        });
        f2_left8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left8ActionPerformed(evt);
            }
        });

        f2_right9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right9MouseClicked(evt);
            }
        });

        title_f10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f10.setText("SETTINGS PAGE - EDIT BUTTON");

        f2_pic9.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/SETTINGS page - EDIT.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic9Layout = new javax.swing.GroupLayout(f2_pic9);
        f2_pic9.setLayout(f2_pic9Layout);
        f2_pic9Layout.setHorizontalGroup(
            f2_pic9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict9, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic9Layout.setVerticalGroup(
            f2_pic9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict9, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc9.setEditable(false);
        f1_desc9.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc9.setText("A button for editing records are also given to the user. Upon clicking the button, specific field will be highlighted to indicate that the user can now edit that field. ");
        f1_desc9.setFocusable(false);
        f1_desc9.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont9.setViewportView(f1_desc9);

        f2_page10.setText("11");

        javax.swing.GroupLayout feature12Layout = new javax.swing.GroupLayout(feature12);
        feature12.setLayout(feature12Layout);
        feature12Layout.setHorizontalGroup(
            feature12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature12Layout.createSequentialGroup()
                .addGroup(feature12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature12Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont9, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature12Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page10))
                            .addGroup(feature12Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right9))))
                    .addGroup(feature12Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f10, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature12Layout.setVerticalGroup(
            feature12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature12Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f10)
                .addGap(19, 19, 19)
                .addGroup(feature12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature12Layout.createSequentialGroup()
                        .addComponent(f2_pic9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature12Layout.createSequentialGroup()
                        .addGroup(feature12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature12Layout.createSequentialGroup()
                                .addComponent(f2_left8)
                                .addGap(126, 126, 126))
                            .addGroup(feature12Layout.createSequentialGroup()
                                .addComponent(f2_right9)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page10)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature12Layout.createSequentialGroup()
                        .addComponent(f2_textcont9, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature12, "card2");

        feature13.setBackground(new java.awt.Color(255, 255, 255));

        f2_left9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left9MouseClicked(evt);
            }
        });
        f2_left9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left9ActionPerformed(evt);
            }
        });

        f2_right10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right10MouseClicked(evt);
            }
        });

        title_f11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f11.setText("SETTINGS PAGE - PRIVACY POLICY");

        f2_pic10.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/SETTINGS page - PRIV.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic10Layout = new javax.swing.GroupLayout(f2_pic10);
        f2_pic10.setLayout(f2_pic10Layout);
        f2_pic10Layout.setHorizontalGroup(
            f2_pic10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict10, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic10Layout.setVerticalGroup(
            f2_pic10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict10, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc10.setEditable(false);
        f1_desc10.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc10.setText("The application also provides a privacy policy section to which the users can access and is advised to read. ");
        f1_desc10.setFocusable(false);
        f1_desc10.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont10.setViewportView(f1_desc10);

        f2_page11.setText("12");

        javax.swing.GroupLayout feature13Layout = new javax.swing.GroupLayout(feature13);
        feature13.setLayout(feature13Layout);
        feature13Layout.setHorizontalGroup(
            feature13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature13Layout.createSequentialGroup()
                .addGroup(feature13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature13Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont10, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature13Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page11))
                            .addGroup(feature13Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right10))))
                    .addGroup(feature13Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f11, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature13Layout.setVerticalGroup(
            feature13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature13Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f11)
                .addGap(19, 19, 19)
                .addGroup(feature13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature13Layout.createSequentialGroup()
                        .addComponent(f2_pic10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature13Layout.createSequentialGroup()
                        .addGroup(feature13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature13Layout.createSequentialGroup()
                                .addComponent(f2_left9)
                                .addGap(126, 126, 126))
                            .addGroup(feature13Layout.createSequentialGroup()
                                .addComponent(f2_right10)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page11)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature13Layout.createSequentialGroup()
                        .addComponent(f2_textcont10, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature13, "card2");

        feature17.setBackground(new java.awt.Color(255, 255, 255));

        f2_left10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left10MouseClicked(evt);
            }
        });
        f2_left10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left10ActionPerformed(evt);
            }
        });

        f2_right11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right11.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right11MouseClicked(evt);
            }
        });

        title_f12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f12.setText("SETTINGS PAGE - TERMS OF SERVICE");

        f2_pic11.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/SETTINGS page - TOS.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic11Layout = new javax.swing.GroupLayout(f2_pic11);
        f2_pic11.setLayout(f2_pic11Layout);
        f2_pic11Layout.setHorizontalGroup(
            f2_pic11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict11, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic11Layout.setVerticalGroup(
            f2_pic11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict11, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc11.setEditable(false);
        f1_desc11.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc11.setText("The application also provides its Terms of Services which users are advised to read before using the system. ");
        f1_desc11.setFocusable(false);
        f1_desc11.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont11.setViewportView(f1_desc11);

        f2_page12.setText("13");

        javax.swing.GroupLayout feature17Layout = new javax.swing.GroupLayout(feature17);
        feature17.setLayout(feature17Layout);
        feature17Layout.setHorizontalGroup(
            feature17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature17Layout.createSequentialGroup()
                .addGroup(feature17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature17Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont11, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature17Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page12))
                            .addGroup(feature17Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right11))))
                    .addGroup(feature17Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f12, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature17Layout.setVerticalGroup(
            feature17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature17Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f12)
                .addGap(19, 19, 19)
                .addGroup(feature17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature17Layout.createSequentialGroup()
                        .addComponent(f2_pic11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature17Layout.createSequentialGroup()
                        .addGroup(feature17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature17Layout.createSequentialGroup()
                                .addComponent(f2_left10)
                                .addGap(126, 126, 126))
                            .addGroup(feature17Layout.createSequentialGroup()
                                .addComponent(f2_right11)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page12)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature17Layout.createSequentialGroup()
                        .addComponent(f2_textcont11, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature17, "card2");

        feature18.setBackground(new java.awt.Color(255, 255, 255));

        f2_left11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow1-btn.png"))); // NOI18N
        f2_left11.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_left11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_left11MouseClicked(evt);
            }
        });
        f2_left11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f2_left11ActionPerformed(evt);
            }
        });

        f2_right12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-btn.png"))); // NOI18N
        f2_right12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        f2_right12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                f2_right12MouseClicked(evt);
            }
        });

        title_f13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title_f13.setText("USER PROFILE PAGE");

        f2_pic12.setBackground(new java.awt.Color(255, 255, 255));

        f1_pict12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user-profile.png"))); // NOI18N

        javax.swing.GroupLayout f2_pic12Layout = new javax.swing.GroupLayout(f2_pic12);
        f2_pic12.setLayout(f2_pic12Layout);
        f2_pic12Layout.setHorizontalGroup(
            f2_pic12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict12, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        f2_pic12Layout.setVerticalGroup(
            f2_pic12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(f2_pic12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(f1_pict12, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        f1_desc12.setEditable(false);
        f1_desc12.setBackground(new java.awt.Color(255, 255, 255));
        f1_desc12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        f1_desc12.setText("Upon clicking the profile icon on the navigation bar of the application, the system will display the user profile section where users can edit their own account information. ");
        f1_desc12.setFocusable(false);
        f1_desc12.setMargin(new java.awt.Insets(2, 10, 2, 10));
        f2_textcont12.setViewportView(f1_desc12);

        f2_page13.setText("14");

        javax.swing.GroupLayout feature18Layout = new javax.swing.GroupLayout(feature18);
        feature18.setLayout(feature18Layout);
        feature18Layout.setHorizontalGroup(
            feature18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature18Layout.createSequentialGroup()
                .addGroup(feature18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(feature18Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(f2_left11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(f2_pic12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(f2_textcont12, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(feature18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(feature18Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(f2_page13))
                            .addGroup(feature18Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(f2_right12))))
                    .addGroup(feature18Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(title_f13, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        feature18Layout.setVerticalGroup(
            feature18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(feature18Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(title_f13)
                .addGap(19, 19, 19)
                .addGroup(feature18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature18Layout.createSequentialGroup()
                        .addComponent(f2_pic12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature18Layout.createSequentialGroup()
                        .addGroup(feature18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(feature18Layout.createSequentialGroup()
                                .addComponent(f2_left11)
                                .addGap(126, 126, 126))
                            .addGroup(feature18Layout.createSequentialGroup()
                                .addComponent(f2_right12)
                                .addGap(119, 119, 119)))
                        .addComponent(f2_page13)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, feature18Layout.createSequentialGroup()
                        .addComponent(f2_textcont12, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );

        features.add(feature18, "card2");

        page.add(features, "card2");

        faqs.setBackground(java.awt.SystemColor.control);

        title_FAQs.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        title_FAQs.setText("Frequently Asked Questions ");

        faq_1.setBackground(new java.awt.Color(255, 255, 255));

        faq_1Ques.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        faq_1Ques.setText("What is the Youth Profiling Application?");

        faq1_desc.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        faq1_desc.setText("The Youth Profiling Application is a tool designed to help organizations and individuals create, manage, and analyze profiles of young individuals. ");
        faq1_desc.setFocusable(false);
        faq1_containerDesc.setViewportView(faq1_desc);

        javax.swing.GroupLayout faq_1Layout = new javax.swing.GroupLayout(faq_1);
        faq_1.setLayout(faq_1Layout);
        faq_1Layout.setHorizontalGroup(
            faq_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faq_1Layout.createSequentialGroup()
                .addGroup(faq_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(faq_1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(faq_1Ques))
                    .addGroup(faq_1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(faq1_containerDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        faq_1Layout.setVerticalGroup(
            faq_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faq_1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(faq_1Ques)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(faq1_containerDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        faq_2.setBackground(new java.awt.Color(255, 255, 255));

        faq_2Ques.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        faq_2Ques.setText("Who can use this application?");

        faq2_desc.setEditable(false);
        faq2_desc.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        faq2_desc.setText("The application is intended for use by Sangguniang Kabataan ng barangaya Pooc officials and kagawad themselves. ");
        faq2_desc.setFocusable(false);
        faq2_containerDesc.setViewportView(faq2_desc);

        javax.swing.GroupLayout faq_2Layout = new javax.swing.GroupLayout(faq_2);
        faq_2.setLayout(faq_2Layout);
        faq_2Layout.setHorizontalGroup(
            faq_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faq_2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(faq_2Ques)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, faq_2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(faq2_containerDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        faq_2Layout.setVerticalGroup(
            faq_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faq_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(faq_2Ques)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(faq2_containerDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        faq_5.setBackground(new java.awt.Color(255, 255, 255));

        faq_2Ques2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        faq_2Ques2.setText(" How do I create an account?");

        faq2_desc2.setEditable(false);
        faq2_desc2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        faq2_desc2.setText("You don't. Accounts in Youth Profiling App are all pre-defined by the admin.");
        faq2_desc2.setFocusable(false);
        faq2_containerDesc2.setViewportView(faq2_desc2);

        javax.swing.GroupLayout faq_5Layout = new javax.swing.GroupLayout(faq_5);
        faq_5.setLayout(faq_5Layout);
        faq_5Layout.setHorizontalGroup(
            faq_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faq_5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(faq_2Ques2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, faq_5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(faq2_containerDesc2, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        faq_5Layout.setVerticalGroup(
            faq_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faq_5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(faq_2Ques2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(faq2_containerDesc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout faqsLayout = new javax.swing.GroupLayout(faqs);
        faqs.setLayout(faqsLayout);
        faqsLayout.setHorizontalGroup(
            faqsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faqsLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(faqsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(title_FAQs, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(faqsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(faq_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(faq_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(faq_5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(201, Short.MAX_VALUE))
        );
        faqsLayout.setVerticalGroup(
            faqsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(faqsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(title_FAQs)
                .addGap(31, 31, 31)
                .addComponent(faq_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(faq_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(faq_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        page.add(faqs, "card3");

        javax.swing.GroupLayout user_manualLayout = new javax.swing.GroupLayout(user_manual);
        user_manual.setLayout(user_manualLayout);
        user_manualLayout.setHorizontalGroup(
            user_manualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(user_manualLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(notificon2)
                .addGap(6, 6, 6)
                .addComponent(proficon2)
                .addGap(39, 39, 39))
            .addGroup(user_manualLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(user_manualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(page, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(YouthProfiling_description, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 433, Short.MAX_VALUE))
            .addGroup(user_manualLayout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(featurebtn_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(faqsbtn_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        user_manualLayout.setVerticalGroup(
            user_manualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(user_manualLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(user_manualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(user_manualLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(notificon2))
                    .addComponent(proficon2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addComponent(YouthProfiling_description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(user_manualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(user_manualLayout.createSequentialGroup()
                        .addComponent(featurebtn_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(page, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(faqsbtn_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pagechanger.add(user_manual, "card4");

        right.setBackground(new java.awt.Color(204, 204, 204));
        right.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        head_title.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        head_title.setForeground(new java.awt.Color(51, 51, 51));
        head_title.setText("BARANGAY POOC");

        youth_container.setBackground(new java.awt.Color(255, 255, 255));

        count3.setFont(new java.awt.Font("Segoe UI", 3, 50)); // NOI18N
        count3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count3.setText("29,000");

        year_title.setBackground(new java.awt.Color(204, 51, 0));

        year_desc.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        year_desc.setForeground(new java.awt.Color(255, 255, 255));
        year_desc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        year_desc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Decline.png"))); // NOI18N
        year_desc.setText("2023");
        year_desc.setIconTextGap(8);

        javax.swing.GroupLayout year_titleLayout = new javax.swing.GroupLayout(year_title);
        year_title.setLayout(year_titleLayout);
        year_titleLayout.setHorizontalGroup(
            year_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(year_titleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(year_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        year_titleLayout.setVerticalGroup(
            year_titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(year_titleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(year_desc, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout year_containerLayout = new javax.swing.GroupLayout(year_container);
        year_container.setLayout(year_containerLayout);
        year_containerLayout.setHorizontalGroup(
            year_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(year_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(year_containerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(count3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        year_containerLayout.setVerticalGroup(
            year_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, year_containerLayout.createSequentialGroup()
                .addComponent(year_title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(count3)
                .addGap(35, 35, 35))
        );

        count5.setFont(new java.awt.Font("Segoe UI", 3, 50)); // NOI18N
        count5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count5.setText("54,000");

        year_title2.setBackground(new java.awt.Color(0, 102, 204));

        year_desc2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        year_desc2.setForeground(new java.awt.Color(255, 255, 255));
        year_desc2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        year_desc2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Positive Dynamic.png"))); // NOI18N
        year_desc2.setText("2022");
        year_desc2.setIconTextGap(8);

        javax.swing.GroupLayout year_title2Layout = new javax.swing.GroupLayout(year_title2);
        year_title2.setLayout(year_title2Layout);
        year_title2Layout.setHorizontalGroup(
            year_title2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(year_title2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(year_desc2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        year_title2Layout.setVerticalGroup(
            year_title2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(year_title2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(year_desc2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout youth_container2Layout = new javax.swing.GroupLayout(youth_container2);
        youth_container2.setLayout(youth_container2Layout);
        youth_container2Layout.setHorizontalGroup(
            youth_container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(year_title2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(youth_container2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(count5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        youth_container2Layout.setVerticalGroup(
            youth_container2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, youth_container2Layout.createSequentialGroup()
                .addComponent(year_title2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(count5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        count6.setFont(new java.awt.Font("Segoe UI", 3, 50)); // NOI18N
        count6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        count6.setText("52,000");

        jPanel10.setBackground(new java.awt.Color(0, 153, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Positive Dynamic.png"))); // NOI18N
        jLabel6.setText("2021");
        jLabel6.setIconTextGap(8);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout youth_container3Layout = new javax.swing.GroupLayout(youth_container3);
        youth_container3.setLayout(youth_container3Layout);
        youth_container3Layout.setHorizontalGroup(
            youth_container3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, youth_container3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(count6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        youth_container3Layout.setVerticalGroup(
            youth_container3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, youth_container3Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(count6)
                .addGap(34, 34, 34))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("TOTAL NUMBER OF YOUTH PROFILED");

        year_hdesc.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        year_hdesc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        year_hdesc.setText("for three (3) consecutive years");

        javax.swing.GroupLayout youth_containerLayout = new javax.swing.GroupLayout(youth_container);
        youth_container.setLayout(youth_containerLayout);
        youth_containerLayout.setHorizontalGroup(
            youth_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(youth_containerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(year_hdesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, youth_containerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(youth_containerLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(youth_container3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(youth_container2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(year_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        youth_containerLayout.setVerticalGroup(
            youth_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(youth_containerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(year_hdesc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(youth_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(youth_container3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(youth_container2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(year_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        member_container.setBackground(new java.awt.Color(255, 255, 255));

        members_table.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        members_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Full Name", "Barangay", "Position", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        members_head.setViewportView(members_table);

        members_title.setBackground(new java.awt.Color(255, 255, 255));
        members_title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        members_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        members_title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/People.png"))); // NOI18N
        members_title.setText("MEMBERS OF THE COUNCIL");

        javax.swing.GroupLayout member_containerLayout = new javax.swing.GroupLayout(member_container);
        member_container.setLayout(member_containerLayout);
        member_containerLayout.setHorizontalGroup(
            member_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(member_containerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(members_head)
                .addContainerGap())
            .addComponent(members_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        member_containerLayout.setVerticalGroup(
            member_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(member_containerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(members_title)
                .addGap(18, 18, 18)
                .addComponent(members_head, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addContainerGap())
        );

        target_container.setBackground(new java.awt.Color(255, 153, 0));

        target_title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        target_title.setForeground(new java.awt.Color(255, 255, 255));
        target_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        target_title.setText("TARGET POPULATION");

        target_separator.setDisplayedMnemonic('1');
        target_separator.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        target_separator.setForeground(new java.awt.Color(255, 255, 255));
        target_separator.setText("____________________________________________________________________");

        target_count.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        target_count.setForeground(new java.awt.Color(255, 255, 255));
        target_count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        target_count.setText("35,000");

        javax.swing.GroupLayout target_containerLayout = new javax.swing.GroupLayout(target_container);
        target_container.setLayout(target_containerLayout);
        target_containerLayout.setHorizontalGroup(
            target_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(target_containerLayout.createSequentialGroup()
                .addGroup(target_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(target_containerLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(target_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(target_separator, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(target_count, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(target_containerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(target_title, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        target_containerLayout.setVerticalGroup(
            target_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(target_containerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(target_title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(target_separator, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(target_count)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        current_container.setBackground(new java.awt.Color(0, 153, 204));

        current_separator.setForeground(new java.awt.Color(255, 255, 255));
        current_separator.setText("__________________________________________________________________________");

        current_title.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        current_title.setForeground(new java.awt.Color(255, 255, 255));
        current_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        current_title.setText("CURRENT NUMBER OF PROFILED YOUTH");

        current_count.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        current_count.setForeground(new java.awt.Color(255, 255, 255));
        current_count.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        current_count.setText("13,000");

        javax.swing.GroupLayout current_containerLayout = new javax.swing.GroupLayout(current_container);
        current_container.setLayout(current_containerLayout);
        current_containerLayout.setHorizontalGroup(
            current_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(current_containerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(current_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(current_separator, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(current_title, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, current_containerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(current_count, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        current_containerLayout.setVerticalGroup(
            current_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(current_containerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(current_title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(current_separator)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(current_count)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout rightLayout = new javax.swing.GroupLayout(right);
        right.setLayout(rightLayout);
        rightLayout.setHorizontalGroup(
            rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rightLayout.createSequentialGroup()
                        .addComponent(head_title, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(member_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(rightLayout.createSequentialGroup()
                                .addComponent(youth_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(target_container, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(current_container, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                        .addGap(78, 78, 78))))
        );
        rightLayout.setVerticalGroup(
            rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightLayout.createSequentialGroup()
                .addComponent(head_title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rightLayout.createSequentialGroup()
                        .addComponent(target_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(current_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(youth_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(member_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );

        proficon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Male User-30.png"))); // NOI18N
        proficon3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        proficon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proficon3MouseClicked(evt);
            }
        });

        notificon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notif-30.png"))); // NOI18N
        notificon3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificon3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificon3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout resultsLayout = new javax.swing.GroupLayout(results);
        results.setLayout(resultsLayout);
        resultsLayout.setHorizontalGroup(
            resultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resultsLayout.createSequentialGroup()
                .addContainerGap(1353, Short.MAX_VALUE)
                .addComponent(notificon3)
                .addGap(6, 6, 6)
                .addComponent(proficon3)
                .addGap(225, 225, 225))
            .addGroup(resultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );
        resultsLayout.setVerticalGroup(
            resultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resultsLayout.createSequentialGroup()
                .addGroup(resultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(resultsLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(notificon3))
                    .addComponent(proficon3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pagechanger.add(results, "card5");

        settings.setPreferredSize(new java.awt.Dimension(1616, 733));
        settings.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                settingsComponentShown(evt);
            }
        });

        notificon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notif-30.png"))); // NOI18N
        notificon4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificon4MouseClicked(evt);
            }
        });

        proficon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Male User-30.png"))); // NOI18N
        proficon4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        proficon4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proficon4MouseClicked(evt);
            }
        });

        ContainerSettings.setBackground(new java.awt.Color(255, 255, 255));
        ContainerSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContainerSettingsMouseClicked(evt);
            }
        });
        ContainerSettings.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SetTitleSettings.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        SetTitleSettings.setText("Settings");
        ContainerSettings.add(SetTitleSettings, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 19, -1, -1));

        SearchButton.setBackground(new java.awt.Color(204, 204, 204));
        SearchButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search .png"))); // NOI18N
        SearchButton.setText("Search");
        SearchButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        SearchButton.setFocusPainted(false);
        SearchButton.setFocusable(false);
        SearchButton.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                SearchButtonAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        ContainerSettings.add(SearchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 100, 30));

        SearchField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        SearchField.setFocusable(false);
        ContainerSettings.add(SearchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 330, -1));

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        ContainerSettings.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 0, 10, 550));

        ManageUserPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.hoverBackground"));

        btn_TermsServices1.setBackground(new java.awt.Color(255, 255, 255));
        btn_TermsServices1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_TermsServices1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_TermsServices1.setText("Manage Users                                                          >");
        btn_TermsServices1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_TermsServices1MouseClicked(evt);
            }
        });
        ManageUserPanel.add(btn_TermsServices1);

        ContainerSettings.add(ManageUserPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 380, 30));

        TermsServicesPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.hoverBackground"));

        btn_TermsServices.setBackground(new java.awt.Color(255, 255, 255));
        btn_TermsServices.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_TermsServices.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_TermsServices.setText("Terms of Services                                                >");
        btn_TermsServices.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_TermsServicesMouseClicked(evt);
            }
        });
        TermsServicesPanel.add(btn_TermsServices);

        ContainerSettings.add(TermsServicesPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 380, 30));

        NotificationsPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.hoverBackground"));

        btn_Notifications.setBackground(new java.awt.Color(255, 255, 255));
        btn_Notifications.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Notifications.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_Notifications.setText("Notifications                                                          >");
        btn_Notifications.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_NotificationsMouseClicked(evt);
            }
        });
        NotificationsPanel.add(btn_Notifications);

        ContainerSettings.add(NotificationsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 380, 30));

        PrivacySettingsPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.hoverBackground"));

        btn_PrivacySettings.setBackground(new java.awt.Color(255, 255, 255));
        btn_PrivacySettings.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_PrivacySettings.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_PrivacySettings.setText("Privacy Policy                                                       >");
        btn_PrivacySettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_PrivacySettingsMouseClicked(evt);
            }
        });
        PrivacySettingsPanel.add(btn_PrivacySettings);

        ContainerSettings.add(PrivacySettingsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 380, 30));

        SettingsPage.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                SettingsPageComponentShown(evt);
            }
        });
        SettingsPage.setLayout(new java.awt.CardLayout());

        ManageUser.setBackground(new java.awt.Color(255, 255, 255));
        ManageUser.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                ManageUserComponentShown(evt);
            }
        });
        ManageUser.setLayout(new java.awt.CardLayout());

        List1.setBackground(new java.awt.Color(255, 255, 255));
        List1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                List1ComponentShown(evt);
            }
        });

        admin_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Email", "Username", "Position", "Barangay"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(admin_list);
        if (admin_list.getColumnModel().getColumnCount() > 0) {
            admin_list.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"User", "Admin", "Super Admin"})));
        }

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add-1.png"))); // NOI18N
        jButton1.setText("Add");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete-1.png"))); // NOI18N
        jButton2.setText("Delete");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit.png"))); // NOI18N
        jToggleButton1.setText("Edit");
        jToggleButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout List1Layout = new javax.swing.GroupLayout(List1);
        List1.setLayout(List1Layout);
        List1Layout.setHorizontalGroup(
            List1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, List1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(List1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        List1Layout.setVerticalGroup(
            List1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, List1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(List1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(List1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        ManageUser.add(List1, "card3");

        Register1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setText("REGISTER AN ACCOUNT");

        FirstName3.setForeground(new java.awt.Color(102, 102, 102));
        FirstName3.setText("First Name");

        FN_Field3.setForeground(new java.awt.Color(102, 102, 102));
        FN_Field3.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        FN_Field3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FN_Field3ActionPerformed(evt);
            }
        });

        LastName3.setForeground(new java.awt.Color(102, 102, 102));
        LastName3.setText("Last Name");

        LN_Field3.setForeground(new java.awt.Color(102, 102, 102));
        LN_Field3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LN_Field3ActionPerformed(evt);
            }
        });

        Email3.setForeground(new java.awt.Color(102, 102, 102));
        Email3.setText("Email");

        Email_Field3.setForeground(new java.awt.Color(102, 102, 102));
        Email_Field3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Email_Field3ActionPerformed(evt);
            }
        });

        ContactNumber3.setForeground(new java.awt.Color(102, 102, 102));
        ContactNumber3.setText("Username");

        Contact_Field3.setForeground(new java.awt.Color(102, 102, 102));
        Contact_Field3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Contact_Field3ActionPerformed(evt);
            }
        });

        Position3.setForeground(new java.awt.Color(102, 102, 102));
        Position3.setText("Position");

        Barangay3.setForeground(new java.awt.Color(102, 102, 102));
        Barangay3.setText("Barangay");

        Password3.setForeground(new java.awt.Color(102, 102, 102));
        Password3.setText("Password");

        register.setBackground(new java.awt.Color(255, 153, 0));
        register.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        register.setForeground(new java.awt.Color(255, 255, 255));
        register.setText("Register");
        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerMouseClicked(evt);
            }
        });
        register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Back.png"))); // NOI18N
        jLabel5.setText("BACK");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.setIconTextGap(1);
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        Barangay_Field3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tagapo" }));
        Barangay_Field3.setEnabled(false);

        Position_Field3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin", "Super Admin" }));
        Position_Field3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Position_Field3ActionPerformed(evt);
            }
        });

        Password4.setForeground(new java.awt.Color(102, 102, 102));
        Password4.setText("Confirm Password");

        javax.swing.GroupLayout Register1Layout = new javax.swing.GroupLayout(Register1);
        Register1.setLayout(Register1Layout);
        Register1Layout.setHorizontalGroup(
            Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Register1Layout.createSequentialGroup()
                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Register1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Register1Layout.createSequentialGroup()
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(Password_Field3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Barangay_Field3, javax.swing.GroupLayout.Alignment.LEADING, 0, 172, Short.MAX_VALUE))
                                    .addComponent(Password3)
                                    .addComponent(reg_password_error))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Password4)
                                    .addComponent(reg_confirm_error)
                                    .addComponent(Confirm_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel56)
                            .addGroup(Register1Layout.createSequentialGroup()
                                .addComponent(FirstName3)
                                .addGap(130, 130, 130)
                                .addComponent(LastName3))
                            .addComponent(Barangay3)
                            .addGroup(Register1Layout.createSequentialGroup()
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FN_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(reg_fname_error))
                                .addGap(15, 15, 15)
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(reg_lname_error)
                                    .addComponent(LN_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(Register1Layout.createSequentialGroup()
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(Register1Layout.createSequentialGroup()
                                            .addComponent(Email_Field3)
                                            .addGap(15, 15, 15))
                                        .addGroup(Register1Layout.createSequentialGroup()
                                            .addComponent(Email3)
                                            .addGap(158, 158, 158)))
                                    .addGroup(Register1Layout.createSequentialGroup()
                                        .addComponent(reg_email_error)
                                        .addGap(150, 150, 150)))
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(reg_username_error)
                                    .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Position_Field3, 0, 181, Short.MAX_VALUE)
                                        .addComponent(Position3)
                                        .addComponent(ContactNumber3)
                                        .addComponent(Contact_Field3))))))
                    .addGroup(Register1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Register1Layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(register, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        Register1Layout.setVerticalGroup(
            Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Register1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Register1Layout.createSequentialGroup()
                        .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Register1Layout.createSequentialGroup()
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(FirstName3)
                                    .addComponent(LastName3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(FN_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LN_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(reg_fname_error)
                                    .addComponent(reg_lname_error))
                                .addGap(4, 4, 4)
                                .addComponent(Email3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Email_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Register1Layout.createSequentialGroup()
                                .addComponent(ContactNumber3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Contact_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reg_email_error)
                            .addComponent(reg_username_error))
                        .addGap(7, 7, 7)
                        .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Barangay3)
                            .addComponent(Position3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Barangay_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Position_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(Password3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Password_Field3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Register1Layout.createSequentialGroup()
                        .addComponent(Password4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Confirm_Field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Register1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reg_password_error)
                    .addComponent(reg_confirm_error))
                .addGap(67, 67, 67)
                .addComponent(register, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        ManageUser.add(Register1, "card2");

        Delete.setBackground(new java.awt.Color(255, 255, 255));

        doDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Search .png"))); // NOI18N
        doDelete.setText("Search");
        doDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                doSearchMouseClicked(evt);
            }
        });
        doDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doDeleteActionPerformed(evt);
            }
        });

        delete_field.setForeground(new java.awt.Color(102, 102, 102));
        delete_field.setText("Username");
        delete_field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                delete_fieldFocusGained(evt);
            }
        });
        delete_field.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                delete_fieldComponentHidden(evt);
            }
        });
        delete_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_fieldActionPerformed(evt);
            }
        });

        admin_list_delete.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Email", "Username", "Position", "Barangay"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane19.setViewportView(admin_list_delete);
        if (admin_list_delete.getColumnModel().getColumnCount() > 0) {
            admin_list_delete.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"User", "Admin", "Super Admin"})));
        }

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

        javax.swing.GroupLayout DeleteLayout = new javax.swing.GroupLayout(Delete);
        Delete.setLayout(DeleteLayout);
        DeleteLayout.setHorizontalGroup(
            DeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeleteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(doDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(delete_field, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(DeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DeleteLayout.setVerticalGroup(
            DeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeleteLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(DeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(doDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete_field))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(13, 13, 13))
        );

        ManageUser.add(Delete, "card4");

        Edit.setBackground(new java.awt.Color(255, 255, 255));
        Edit.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                EditComponentShown(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add-1.png"))); // NOI18N
        jButton3.setText("Add");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete-1.png"))); // NOI18N
        jButton4.setText("Delete");

        jToggleButton2.setBackground(new java.awt.Color(255, 153, 0));
        jToggleButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Save.png"))); // NOI18N
        jToggleButton2.setText("Save Changes");
        jToggleButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton2MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Back.png"))); // NOI18N
        jLabel8.setText("BACK");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.setIconTextGap(1);
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        admin_list_edit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Email", "Username", "Position", "Barangay"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane18.setViewportView(admin_list_edit);
        if (admin_list_edit.getColumnModel().getColumnCount() > 0) {
            admin_list_edit.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"User", "Admin", "Super Admin"})));
        }

        javax.swing.GroupLayout EditLayout = new javax.swing.GroupLayout(Edit);
        Edit.setLayout(EditLayout);
        EditLayout.setHorizontalGroup(
            EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(EditLayout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        EditLayout.setVerticalGroup(
            EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(13, 13, 13))
        );

        ManageUser.add(Edit, "card3");

        SettingsPage.add(ManageUser, "card6");

        Notifications_2.setBackground(new java.awt.Color(255, 255, 255));

        TodayText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TodayText.setText("Today");

        NotifSurvey_1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NotifSurvey_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/William-Richard-Forbishvr2.png"))); // NOI18N
        NotifSurvey_1.setText("    Chairmain Laedie Emperador survey 21 youth");

        LineSeperator_1.setBackground(new java.awt.Color(153, 153, 153));
        LineSeperator_1.setForeground(new java.awt.Color(153, 153, 153));

        LineSeparator_2.setBackground(new java.awt.Color(153, 153, 153));
        LineSeparator_2.setForeground(new java.awt.Color(153, 153, 153));

        NotifSurvey_2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NotifSurvey_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/William-Richard-Forbishvr2.png"))); // NOI18N
        NotifSurvey_2.setText("    Chairmain Laedie Emperador survey 21 youth");

        LineSeparator_3.setBackground(new java.awt.Color(153, 153, 153));
        LineSeparator_3.setForeground(new java.awt.Color(153, 153, 153));

        NotifSurvey_3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NotifSurvey_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/William-Richard-Forbishvr2.png"))); // NOI18N
        NotifSurvey_3.setText("    Chairmain Laedie Emperador survey 21 youth");

        LineSeparator_4.setBackground(new java.awt.Color(153, 153, 153));
        LineSeparator_4.setForeground(new java.awt.Color(153, 153, 153));

        NotifSurvey_4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NotifSurvey_4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/William-Richard-Forbishvr2.png"))); // NOI18N
        NotifSurvey_4.setText("    Chairmain Laedie Emperador survey 21 youth");

        LineSeparator_5.setBackground(new java.awt.Color(153, 153, 153));
        LineSeparator_5.setForeground(new java.awt.Color(153, 153, 153));

        TodayText1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TodayText1.setText("Yesterday");

        LineSeparator_6.setBackground(new java.awt.Color(153, 153, 153));
        LineSeparator_6.setForeground(new java.awt.Color(153, 153, 153));

        NotifSurvey_7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NotifSurvey_7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/William-Richard-Forbishvr2.png"))); // NOI18N
        NotifSurvey_7.setText("    Chairmain Laedie Emperador survey 21 youth");

        LineSeparator_7.setBackground(new java.awt.Color(153, 153, 153));
        LineSeparator_7.setForeground(new java.awt.Color(153, 153, 153));

        NotifSurvey_8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NotifSurvey_8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/William-Richard-Forbishvr2.png"))); // NOI18N
        NotifSurvey_8.setText("    Chairmain Laedie Emperador survey 21 youth");

        LineSeparator_8.setBackground(new java.awt.Color(153, 153, 153));
        LineSeparator_8.setForeground(new java.awt.Color(153, 153, 153));

        NotifSurvey_9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NotifSurvey_9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/William-Richard-Forbishvr2.png"))); // NOI18N
        NotifSurvey_9.setText("    Chairmain Laedie Emperador survey 21 youth");

        LineSeparator_9.setBackground(new java.awt.Color(153, 153, 153));
        LineSeparator_9.setForeground(new java.awt.Color(153, 153, 153));

        NotifSurvey_10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NotifSurvey_10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/William-Richard-Forbishvr2.png"))); // NOI18N
        NotifSurvey_10.setText("    Chairmain Laedie Emperador survey 21 youth");

        javax.swing.GroupLayout Notifications_2Layout = new javax.swing.GroupLayout(Notifications_2);
        Notifications_2.setLayout(Notifications_2Layout);
        Notifications_2Layout.setHorizontalGroup(
            Notifications_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LineSeperator_1)
            .addGroup(Notifications_2Layout.createSequentialGroup()
                .addGroup(Notifications_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LineSeparator_2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LineSeparator_3)
                    .addComponent(LineSeparator_4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LineSeparator_5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LineSeparator_6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LineSeparator_7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Notifications_2Layout.createSequentialGroup()
                        .addGroup(Notifications_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(NotifSurvey_1))
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(NotifSurvey_2))
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(NotifSurvey_3))
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(TodayText))
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(NotifSurvey_4))
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(TodayText1))
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(NotifSurvey_7))
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(NotifSurvey_8)))
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addGroup(Notifications_2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Notifications_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LineSeparator_9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LineSeparator_8)
                            .addGroup(Notifications_2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(Notifications_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NotifSurvey_10)
                                    .addComponent(NotifSurvey_9))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        Notifications_2Layout.setVerticalGroup(
            Notifications_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Notifications_2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(TodayText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LineSeperator_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NotifSurvey_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LineSeparator_2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NotifSurvey_2)
                .addGap(12, 12, 12)
                .addComponent(LineSeparator_3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NotifSurvey_3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LineSeparator_4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NotifSurvey_4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LineSeparator_5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TodayText1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LineSeparator_6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NotifSurvey_7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LineSeparator_7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NotifSurvey_8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LineSeparator_8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NotifSurvey_9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LineSeparator_9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NotifSurvey_10)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        SettingsPage.add(Notifications_2, "card3");

        PrivacySettings_3.setBackground(new java.awt.Color(255, 255, 255));

        PrivacyPolicy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PrivacyPolicy.setText("Privacy Policy");

        PrivacyPolicy_Pane1.setEditable(false);
        PrivacyPolicy_Pane1.setBackground(new java.awt.Color(255, 255, 255));
        PrivacyPolicy_Pane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        PrivacyPolicy_Pane1.setText("This Privacy Policy outlines how we collect, use, disclose, and protect your information when you use Youth Profiling Application. We are committed in safeguarding your privacy and ensuring the security of your personal information. By using this application, you consent us to utilize your information in any way we deemed as rightful.");
        PrivacyPolicy_Pane1.setAutoscrolls(false);
        PrivacyPolicy_Pane1.setCaretColor(new java.awt.Color(255, 255, 255));
        PrivacyPolicy_Pane1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        PrivacyPolicy_Pane1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        PrivacyPolicy_Pane1.setOpaque(false);
        Input_1.setViewportView(PrivacyPolicy_Pane1);

        InformationWeCollect.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        InformationWeCollect.setText("Information We Collect");

        InformationWeCollect_Pane2.setEditable(false);
        InformationWeCollect_Pane2.setBackground(new java.awt.Color(255, 255, 255));
        InformationWeCollect_Pane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        InformationWeCollect_Pane2.setText("We collect personal information such as name, email address, barangay, and position when you create an account or use the Application. Additionally, we gather data for profiling including date of birth, civil status, official home address, age, and other personal information.");
        InformationWeCollect_Pane2.setAutoscrolls(false);
        InformationWeCollect_Pane2.setCaretColor(new java.awt.Color(255, 255, 255));
        InformationWeCollect_Pane2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        InformationWeCollect_Pane2.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        InformationWeCollect_Pane2.setOpaque(false);
        Input_2.setViewportView(InformationWeCollect_Pane2);

        PurposePrivacyPolicy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PurposePrivacyPolicy.setText("Disclosure of Information");

        PurposePrivacyPolicy_Pane3.setEditable(false);
        PurposePrivacyPolicy_Pane3.setBackground(new java.awt.Color(255, 255, 255));
        PurposePrivacyPolicy_Pane3.setBorder(null);
        PurposePrivacyPolicy_Pane3.setText("We do not sell, trade, or otherwise transfer your personal information to outside parties without your consent, except as described in this policy. We use the information we collect to provide, maintain, and improve the Application, to communicate with you, and to contribute to the organization hereby declared.");
        PurposePrivacyPolicy_Pane3.setAutoscrolls(false);
        PurposePrivacyPolicy_Pane3.setCaretColor(new java.awt.Color(255, 255, 255));
        PurposePrivacyPolicy_Pane3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        PurposePrivacyPolicy_Pane3.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        PurposePrivacyPolicy_Pane3.setOpaque(false);
        Input_3.setViewportView(PurposePrivacyPolicy_Pane3);

        javax.swing.GroupLayout PrivacySettings_3Layout = new javax.swing.GroupLayout(PrivacySettings_3);
        PrivacySettings_3.setLayout(PrivacySettings_3Layout);
        PrivacySettings_3Layout.setHorizontalGroup(
            PrivacySettings_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrivacySettings_3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(PrivacySettings_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Input_3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PurposePrivacyPolicy)
                    .addComponent(Input_2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InformationWeCollect)
                    .addComponent(PrivacyPolicy)
                    .addComponent(Input_1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        PrivacySettings_3Layout.setVerticalGroup(
            PrivacySettings_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrivacySettings_3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(PrivacyPolicy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Input_1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(InformationWeCollect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Input_2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PurposePrivacyPolicy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Input_3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        SettingsPage.add(PrivacySettings_3, "card4");

        TermsServices_4.setBackground(new java.awt.Color(255, 255, 255));

        TermsServices.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TermsServices.setText("Terms of Services");

        TermsServices_Pane4.setEditable(false);
        TermsServices_Pane4.setBackground(new java.awt.Color(255, 255, 255));
        TermsServices_Pane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        TermsServices_Pane4.setText("When you use the Application, you agree to follow all applicable laws and regulations and to use the Application only for lawful purposes. You are responsible for maintaining the confidentiality of your account information and for all activities that occur uder your account. You must not misuse our services by interfering with their normal operation or attempting to access them using a method other than the interface and instructions we provide.");
        TermsServices_Pane4.setAutoscrolls(false);
        TermsServices_Pane4.setCaretColor(new java.awt.Color(255, 255, 255));
        TermsServices_Pane4.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        TermsServices_Pane4.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        TermsServices_Pane4.setOpaque(false);
        Input_4.setViewportView(TermsServices_Pane4);

        WelcomeYouthProfiling.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        WelcomeYouthProfiling.setText("Welcome to Youth Profiling");

        WelcomeYouthProfiling_Pane5.setEditable(false);
        WelcomeYouthProfiling_Pane5.setBackground(new java.awt.Color(255, 255, 255));
        WelcomeYouthProfiling_Pane5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        WelcomeYouthProfiling_Pane5.setText("Welcome to the Youth Profiling Application (the \"Application\"). By accessing or using our services, you agree to comply with and be bound by our Terms of Services. Please read them carefully, and if you do not agree with any part of our terms, you must not use our sevices.");
        WelcomeYouthProfiling_Pane5.setAutoscrolls(false);
        WelcomeYouthProfiling_Pane5.setCaretColor(new java.awt.Color(255, 255, 255));
        WelcomeYouthProfiling_Pane5.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        WelcomeYouthProfiling_Pane5.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        WelcomeYouthProfiling_Pane5.setOpaque(false);
        Input_5.setViewportView(WelcomeYouthProfiling_Pane5);

        UsingServices.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UsingServices.setText("Governing Law");

        UsingServices_Pane7.setEditable(false);
        UsingServices_Pane7.setBackground(new java.awt.Color(255, 255, 255));
        UsingServices_Pane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        UsingServices_Pane7.setText("These Terms of Service shall be governed by and construed in accordance with the laws of the Philippines, without regard to its conflict of law principles. In no event shall we (developers of the Application), be liable for any indirect, incidental, special, consequential, or punitive damages arising out of r in connection with your use of the Application.");
        UsingServices_Pane7.setAutoscrolls(false);
        UsingServices_Pane7.setCaretColor(new java.awt.Color(255, 255, 255));
        UsingServices_Pane7.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        UsingServices_Pane7.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        UsingServices_Pane7.setOpaque(false);
        Input_7.setViewportView(UsingServices_Pane7);

        javax.swing.GroupLayout TermsServices_4Layout = new javax.swing.GroupLayout(TermsServices_4);
        TermsServices_4.setLayout(TermsServices_4Layout);
        TermsServices_4Layout.setHorizontalGroup(
            TermsServices_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TermsServices_4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(TermsServices_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Input_7, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UsingServices)
                    .addComponent(Input_5, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(WelcomeYouthProfiling)
                    .addComponent(Input_4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TermsServices))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        TermsServices_4Layout.setVerticalGroup(
            TermsServices_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TermsServices_4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(TermsServices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Input_4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WelcomeYouthProfiling)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Input_5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(UsingServices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Input_7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        SettingsPage.add(TermsServices_4, "card5");

        ContainerSettings.add(SettingsPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 410, 530));

        javax.swing.GroupLayout settingsLayout = new javax.swing.GroupLayout(settings);
        settings.setLayout(settingsLayout);
        settingsLayout.setHorizontalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(notificon4)
                .addGap(6, 6, 6)
                .addComponent(proficon4)
                .addGap(39, 39, 39))
            .addGroup(settingsLayout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(ContainerSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(479, Short.MAX_VALUE))
        );
        settingsLayout.setVerticalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(notificon4))
                    .addComponent(proficon4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(ContainerSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );

        pagechanger.add(settings, "card6");

        profile.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                profileComponentShown(evt);
            }
        });

        notificon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notif-30.png"))); // NOI18N
        notificon5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificon5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificon5MouseClicked(evt);
            }
        });

        proficon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Male User-30.png"))); // NOI18N
        proficon5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        proficon5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proficon5MouseClicked(evt);
            }
        });

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bg.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText("First Name");

        display_FName.setEditable(false);
        display_FName.setFocusable(false);
        display_FName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                display_FNameActionPerformed(evt);
            }
        });

        display_Username.setEditable(false);
        display_Username.setFocusable(false);

        jLabel36.setForeground(new java.awt.Color(102, 102, 102));
        jLabel36.setText("Username");

        display_Email.setEditable(false);
        display_Email.setFocusable(false);

        jLabel37.setForeground(new java.awt.Color(102, 102, 102));
        jLabel37.setText("Email");

        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText("Barangay");

        jLabel39.setForeground(new java.awt.Color(102, 102, 102));
        jLabel39.setText("Password");

        jLabel40.setForeground(new java.awt.Color(102, 102, 102));
        jLabel40.setText("ID No.");

        display_ID.setEditable(false);
        display_ID.setEnabled(false);
        display_ID.setFocusable(false);

        display_Password.setEditable(false);
        display_Password.setFocusable(false);

        jLabel41.setForeground(new java.awt.Color(51, 153, 0));
        jLabel41.setText("change password?");
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });

        disable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        disable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Invisible.png"))); // NOI18N
        disable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        disable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disableMouseClicked(evt);
            }
        });

        show.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        show.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Eye.png"))); // NOI18N
        show.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        show.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMouseClicked(evt);
            }
        });

        edit_prof.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        edit_prof.setText("EDIT");
        edit_prof.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_profMouseClicked(evt);
            }
        });
        edit_prof.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_profActionPerformed(evt);
            }
        });

        display_LName.setEditable(false);
        display_LName.setFocusable(false);
        display_LName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                display_LNameActionPerformed(evt);
            }
        });

        display_Position.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin", "Super Admin" }));
        display_Position.setEnabled(false);
        display_Position.setFocusable(false);
        display_Position.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                display_PositionActionPerformed(evt);
            }
        });

        display_Barangay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tagapo" }));
        display_Barangay.setEnabled(false);
        display_Barangay.setFocusable(false);
        display_Barangay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                display_BarangayActionPerformed(evt);
            }
        });

        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("Position");

        jLabel52.setForeground(new java.awt.Color(102, 102, 102));
        jLabel52.setText("Last Name");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 884, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(display_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(show, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(disable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(edit_prof, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(243, 243, 243))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 10, Short.MAX_VALUE))
                                    .addComponent(display_ID)
                                    .addComponent(display_Username)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(display_Email)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(display_FName, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(display_LName, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(display_Position, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(76, 76, 76))))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(593, Short.MAX_VALUE)
                    .addComponent(display_Barangay, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(286, 286, 286)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(display_FName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(display_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(display_LName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(jLabel51)))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(display_Position)
                    .addComponent(display_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(display_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(display_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(disable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(show, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41)
                .addGap(78, 78, 78)
                .addComponent(edit_prof, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(177, 177, 177)
                    .addComponent(display_Barangay, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(285, Short.MAX_VALUE)))
        );

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("USER PROFILE");

        javax.swing.GroupLayout profileLayout = new javax.swing.GroupLayout(profile);
        profile.setLayout(profileLayout);
        profileLayout.setHorizontalGroup(
            profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(notificon5)
                .addGap(6, 6, 6)
                .addComponent(proficon5)
                .addGap(39, 39, 39))
            .addGroup(profileLayout.createSequentialGroup()
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(profileLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel33))))
                .addContainerGap(406, Short.MAX_VALUE))
        );
        profileLayout.setVerticalGroup(
            profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proficon5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(profileLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(notificon5)
                            .addComponent(jLabel34))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pagechanger.add(profile, "card7");

        edit_profile.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                edit_profileComponentShown(evt);
            }
        });

        notificon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/notif-30.png"))); // NOI18N
        notificon7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificon7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificon7MouseClicked(evt);
            }
        });

        proficon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Male User-30.png"))); // NOI18N
        proficon7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        proficon7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proficon7MouseClicked(evt);
            }
        });

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bg.png"))); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel43.setForeground(new java.awt.Color(102, 102, 102));
        jLabel43.setText("First Name");

        edit_FName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_FNameActionPerformed(evt);
            }
        });

        jLabel44.setForeground(new java.awt.Color(102, 102, 102));
        jLabel44.setText("Username");

        jLabel45.setForeground(new java.awt.Color(102, 102, 102));
        jLabel45.setText("Email");

        jLabel46.setForeground(new java.awt.Color(102, 102, 102));
        jLabel46.setText("Barangay");

        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setText("Password");

        jLabel48.setForeground(new java.awt.Color(102, 102, 102));
        jLabel48.setText("ID No.");

        edit_ID.setEditable(false);
        edit_ID.setText("TGP001");
        edit_ID.setEnabled(false);

        edit_Password.setEnabled(false);

        jLabel49.setForeground(new java.awt.Color(0, 51, 204));
        jLabel49.setText("save password");
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
        });

        disable1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        disable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Invisible.png"))); // NOI18N
        disable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        disable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disable1MouseClicked(evt);
            }
        });

        show1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        show1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Eye.png"))); // NOI18N
        show1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        show1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show1MouseClicked(evt);
            }
        });

        save_prof.setBackground(new java.awt.Color(51, 102, 0));
        save_prof.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        save_prof.setForeground(new java.awt.Color(255, 255, 255));
        save_prof.setText("SAVE CHANGES");
        save_prof.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                save_profMouseClicked(evt);
            }
        });
        save_prof.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_profActionPerformed(evt);
            }
        });

        edit_LName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_LNameActionPerformed(evt);
            }
        });

        jLabel55.setForeground(new java.awt.Color(102, 102, 102));
        jLabel55.setText("Last Name");

        edit_Barangay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tagapo" }));

        edit_Position.setEnabled(false);

        jLabel57.setForeground(new java.awt.Color(102, 102, 102));
        jLabel57.setText("Position");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(362, 362, 362)
                        .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(212, 212, 212))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(edit_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel49))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(show1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(disable1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(96, 96, 96)
                                .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(212, 212, 212))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                                .addComponent(edit_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(edit_Email)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(edit_FName, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(edit_LName, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(edit_Barangay, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edit_Position, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(320, 320, 320)
                                .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(edit_Username))))))
                .addGap(31, 31, 31))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(456, 456, 456)
                .addComponent(save_prof, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel44)
                    .addComponent(jLabel55))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edit_FName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edit_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edit_LName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(jLabel46)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(edit_Email, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(edit_Position)
                    .addComponent(edit_Barangay))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(edit_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(edit_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(show1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(disable1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addGap(46, 46, 46)
                .addComponent(save_prof, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jLabel50.setBackground(new java.awt.Color(255, 255, 255));
        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("USER PROFILE");

        javax.swing.GroupLayout edit_profileLayout = new javax.swing.GroupLayout(edit_profile);
        edit_profile.setLayout(edit_profileLayout);
        edit_profileLayout.setHorizontalGroup(
            edit_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, edit_profileLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(notificon7)
                .addGap(6, 6, 6)
                .addComponent(proficon7)
                .addGap(39, 39, 39))
            .addGroup(edit_profileLayout.createSequentialGroup()
                .addGroup(edit_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(edit_profileLayout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addGroup(edit_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(edit_profileLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel42))))
                .addContainerGap(442, Short.MAX_VALUE))
        );
        edit_profileLayout.setVerticalGroup(
            edit_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(edit_profileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(edit_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proficon7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(edit_profileLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(edit_profileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(notificon7)
                            .addComponent(jLabel50))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        pagechanger.add(edit_profile, "card9");

        getContentPane().add(pagechanger, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public void hideshow(JPanel menushowhide, boolean dashboard){
        if(dashboard == true) {
            menushowhide.setPreferredSize(new Dimension(50, menushowhide.getHeight()));
        }
        else {
            menushowhide.setPreferredSize(new Dimension(200, menushowhide.getHeight()));
        }
    }
    
    private void logoutbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutbuttonMouseClicked
        Opening OpeningFrame = new Opening();
        OpeningFrame.setVisible(true);
        OpeningFrame.pack();
        OpeningFrame.setLocationRelativeTo(null);
        this.dispose(); 
    }//GEN-LAST:event_logoutbuttonMouseClicked

    private void logoutbuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutbuttonMouseEntered
 
    }//GEN-LAST:event_logoutbuttonMouseEntered

    private void logoutbuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutbuttonMouseExited

    }//GEN-LAST:event_logoutbuttonMouseExited

    private void hidemenubuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hidemenubuttonMouseClicked
           if(a == true) {
            hideshow(menu, a);
            SwingUtilities.updateComponentTreeUI(this);
            a=false;
        }
        else {
            hideshow(menu, a);
            SwingUtilities.updateComponentTreeUI(this);
            a=true;
        }
    }//GEN-LAST:event_hidemenubuttonMouseClicked

    private void dboardbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dboardbuttonMouseClicked
        // TODO add your handling code here:
        dashboard.setVisible(true);
        dboardClicked.setBackground(ClickedColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(DefaultColor);
        
        bgdboard.setBackground(ClickedColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(DefaultColor);
        bgsettings.setBackground(DefaultColor);
    }//GEN-LAST:event_dboardbuttonMouseClicked

    private void dboardlabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dboardlabelMouseClicked
        dashboard.setVisible(true);
        dboardClicked.setBackground(ClickedColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(DefaultColor);
        
        bgdboard.setBackground(ClickedColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(DefaultColor);
        bgsettings.setBackground(DefaultColor);
    }//GEN-LAST:event_dboardlabelMouseClicked

    private void profilingbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilingbuttonMouseClicked
        youthprofiling.setVisible(true);
        dashboard.setVisible(false);
        user_manual.setVisible(false);
        settings.setVisible(false);
        results.setVisible(false);

        
        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(ClickedColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(DefaultColor);
        
        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(ClickedColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(DefaultColor);
        bgsettings.setBackground(DefaultColor);
    }//GEN-LAST:event_profilingbuttonMouseClicked

    private void profilinglabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilinglabelMouseClicked
        youthprofiling.setVisible(true);
        dashboard.setVisible(false);
        user_manual.setVisible(false);
        settings.setVisible(false);
        results.setVisible(false);

        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(ClickedColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(DefaultColor);
        
        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(ClickedColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(DefaultColor);
        bgsettings.setBackground(DefaultColor);
    }//GEN-LAST:event_profilinglabelMouseClicked

    private void resultbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultbuttonMouseClicked
        results.setVisible(true);
        dashboard.setVisible(false);
        user_manual.setVisible(false);
        settings.setVisible(false);
        youthprofiling.setVisible(false);
        
        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(ClickedColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(DefaultColor);
        
        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(ClickedColor);
        bgmanual.setBackground(DefaultColor);
        bgsettings.setBackground(DefaultColor);
    }//GEN-LAST:event_resultbuttonMouseClicked

    private void resultlabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultlabelMouseClicked
        results.setVisible(true);
        dashboard.setVisible(false);
        user_manual.setVisible(false);
        settings.setVisible(false);
        youthprofiling.setVisible(false);
        
        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(ClickedColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(DefaultColor);
        
        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(ClickedColor);
        bgmanual.setBackground(DefaultColor);
        bgsettings.setBackground(DefaultColor);
    }//GEN-LAST:event_resultlabelMouseClicked

    private void manualbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manualbuttonMouseClicked
        user_manual.setVisible(true);
        dashboard.setVisible(false);
        results.setVisible(false);
        settings.setVisible(false);
        youthprofiling.setVisible(false);
        
        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(ClickedColor);
        settingsClicked.setBackground(DefaultColor);
        
        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(ClickedColor);
        bgsettings.setBackground(DefaultColor);
        
    }//GEN-LAST:event_manualbuttonMouseClicked

    private void settingsbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsbuttonMouseClicked
        settings.setVisible(true);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        
        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(ClickedColor);
        
        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(DefaultColor);
        bgsettings.setBackground(ClickedColor);
    }//GEN-LAST:event_settingsbuttonMouseClicked

    private void viewallMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewallMouseClicked
        // TODO add your handling code here:
        brgyView openingFrame = new brgyView();
        openingFrame.setVisible(true);
        openingFrame.pack();
        openingFrame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_viewallMouseClicked

    private void proficonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proficonMouseClicked
        profile.setVisible(true);
        settings.setVisible(false);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        edit_profile.setVisible(false);
    }//GEN-LAST:event_proficonMouseClicked

    private void manuallabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manuallabelMouseClicked
        user_manual.setVisible(true);
        settings.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        
        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(ClickedColor);
        settingsClicked.setBackground(DefaultColor);

        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(ClickedColor);
        bgsettings.setBackground(DefaultColor);
        
        

    }//GEN-LAST:event_manuallabelMouseClicked

    private void logoutlabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutlabelMouseClicked
        Opening OpeningFrame = new Opening();
        OpeningFrame.setVisible(true);
        OpeningFrame.pack();
        OpeningFrame.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_logoutlabelMouseClicked

    private void proficon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proficon3MouseClicked
        profile.setVisible(true);
        settings.setVisible(false);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
    }//GEN-LAST:event_proficon3MouseClicked

    private void proficon4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proficon4MouseClicked
        profile.setVisible(true);
        settings.setVisible(false);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        edit_profile.setVisible(false);
    }//GEN-LAST:event_proficon4MouseClicked

    private void proficon5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proficon5MouseClicked
        profile.setVisible(true);
        settings.setVisible(false);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        edit_profile.setVisible(false);
    }//GEN-LAST:event_proficon5MouseClicked

    private void viewallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewallActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewallActionPerformed

    private void display_FNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_display_FNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_display_FNameActionPerformed

    private void disableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disableMouseClicked
        display_Password.setEchoChar((char)0);
        disable.setVisible(false);
        disable.setEnabled(false);
        show.setEnabled(true);
        show.setEnabled(true);
    }//GEN-LAST:event_disableMouseClicked

    private void showMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showMouseClicked
        display_Password.setEchoChar((char)8226);
        disable.setVisible(true);
        disable.setEnabled(true);
        show.setEnabled(false);
        show.setEnabled(false);
    }//GEN-LAST:event_showMouseClicked

    private void proficon7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proficon7MouseClicked
        edit_profile.setVisible(false);
       settings.setVisible(false);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(true);
    }//GEN-LAST:event_proficon7MouseClicked

    private void edit_profMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_profMouseClicked
       edit_profile.setVisible(true);
       settings.setVisible(false);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(false);
    }//GEN-LAST:event_edit_profMouseClicked

    private void edit_profActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_profActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_profActionPerformed

    private void save_profActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_profActionPerformed

        String first_name = edit_FName.getText();
        String last_name = edit_LName.getText();
        String email = edit_Email.getText();
        String username = edit_Username.getText();
        String barangay = (String) edit_Barangay.getSelectedItem();
        char[] user_password = edit_Password.getPassword();
        String to_hash_password = new String(user_password);
        try {
            String hashed_password = PasswordHasher.getSaltedHash(to_hash_password);
            // prepare connection
            String url = "jdbc:mysql://localhost:3306/skprofiling";
            String user = "root";
            String password = "";
            
            String query = "UPDATE admin_accounts SET first_name = ?, last_name = ?, email = ?, username = ?, barangay = ?, password = ? WHERE username = ?";

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                String current_user = "odeytskieee"; // logged in
                
                // Set parameters
                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, last_name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, barangay);
                preparedStatement.setString(6, hashed_password);
                preparedStatement.setString(7, current_user);
                // Execute the query
                preparedStatement.executeUpdate();

                // Show success message
                JOptionPane.showMessageDialog(this, "You have edited your account", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "There's an error editing your account.", "Error", JOptionPane.ERROR_MESSAGE);
            }    
            
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
        }
        
        edit_profile.setVisible(false);
       settings.setVisible(false);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(true);
       
       
    }//GEN-LAST:event_save_profActionPerformed

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
       edit_profile.setVisible(true);
       settings.setVisible(false);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(false);
    }//GEN-LAST:event_jLabel41MouseClicked
//Settings --------------------------------------------------------------------------------------------- //
    private void SearchButtonAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_SearchButtonAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchButtonAncestorAdded

    private void btn_TermsServicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_TermsServicesMouseClicked

        Notifications_2.setVisible(false);
        PrivacySettings_3.setVisible(false);
        TermsServices_4.setVisible(true);
        ManageUser.setVisible(false);


        NotificationsPanel.setBackground(DefaultColor);
        PrivacySettingsPanel.setBackground(DefaultColor);
        TermsServicesPanel.setBackground(ClickedColor);
        ManageUserPanel.setBackground(DefaultColor);
    }//GEN-LAST:event_btn_TermsServicesMouseClicked

    private void btn_NotificationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_NotificationsMouseClicked

        Notifications_2.setVisible(true);
        PrivacySettings_3.setVisible(false);
        TermsServices_4.setVisible(false);
        ManageUser.setVisible(false);

        NotificationsPanel.setBackground(ClickedColor);
        PrivacySettingsPanel.setBackground(DefaultColor);
        TermsServicesPanel.setBackground(DefaultColor);
        ManageUserPanel.setBackground(DefaultColor);
        
    }//GEN-LAST:event_btn_NotificationsMouseClicked

    private void btn_PrivacySettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_PrivacySettingsMouseClicked

        Notifications_2.setVisible(false);
        PrivacySettings_3.setVisible(true);
        TermsServices_4.setVisible(false);
        ManageUser.setVisible(false);

        NotificationsPanel.setBackground(DefaultColor);
        PrivacySettingsPanel.setBackground(ClickedColor);
        TermsServicesPanel.setBackground(DefaultColor);
        ManageUserPanel.setBackground(DefaultColor);
    }//GEN-LAST:event_btn_PrivacySettingsMouseClicked

    private void ContainerSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContainerSettingsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ContainerSettingsMouseClicked

    private void notificonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificonMouseClicked
       edit_profile.setVisible(false);
       settings.setVisible(true);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(false);
    }//GEN-LAST:event_notificonMouseClicked

    private void notificon5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificon5MouseClicked
        edit_profile.setVisible(false);
       settings.setVisible(true);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(false);
    }//GEN-LAST:event_notificon5MouseClicked

    private void notificon7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificon7MouseClicked
        edit_profile.setVisible(false);
       settings.setVisible(true);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(false);
    }//GEN-LAST:event_notificon7MouseClicked

    private void notificon4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificon4MouseClicked
        edit_profile.setVisible(false);
       settings.setVisible(true);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(false);
    }//GEN-LAST:event_notificon4MouseClicked

    private void notificon3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificon3MouseClicked
        edit_profile.setVisible(false);
       settings.setVisible(true);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(false);
    }//GEN-LAST:event_notificon3MouseClicked

    private void notificon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificon1MouseClicked
       edit_profile.setVisible(false);
       settings.setVisible(true);
       user_manual.setVisible(false);
       dashboard.setVisible(false);
       results.setVisible(false);
       youthprofiling.setVisible(false);
       profile.setVisible(false);
    }//GEN-LAST:event_notificon1MouseClicked

    private void settingslabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingslabelMouseClicked
        settings.setVisible(true);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        
        dboardClicked.setBackground(DefaultColor);
        profilingClicked.setBackground(DefaultColor);
        resultClicked.setBackground(DefaultColor);
        manualClicked.setBackground(DefaultColor);
        settingsClicked.setBackground(ClickedColor);

        
        bgdboard.setBackground(DefaultColor);
        bgprofiling.setBackground(DefaultColor);
        bgresult.setBackground(DefaultColor);
        bgmanual.setBackground(DefaultColor);
        bgsettings.setBackground(ClickedColor);

    }//GEN-LAST:event_settingslabelMouseClicked

    private void btn_TermsServices1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_TermsServices1MouseClicked

        Notifications_2.setVisible(false);
        PrivacySettings_3.setVisible(false);
        TermsServices_4.setVisible(false);
        ManageUser.setVisible(true);


        NotificationsPanel.setBackground(DefaultColor);
        PrivacySettingsPanel.setBackground(DefaultColor);
        TermsServicesPanel.setBackground(DefaultColor);
        ManageUserPanel.setBackground(ClickedColor);
    }//GEN-LAST:event_btn_TermsServices1MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Register1.setVisible(true);
        admin_list.setVisible(false);

        Delete.setVisible(false);
        Edit.setVisible(false);
        List1.setVisible(false);
        NotificationsPanel.setBackground(DefaultColor);
        PrivacySettingsPanel.setBackground(DefaultColor);
        TermsServicesPanel.setBackground(DefaultColor);
        ManageUserPanel.setBackground(ClickedColor);
    }//GEN-LAST:event_jButton1MouseClicked
    // regex validations
    private boolean isValidName(String input) {
        String regex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(regex, input);
    }
    private boolean isValidUsername(String username) {
        String usernameRegex = "^[A-Za-z\\d]+$";
        return Pattern.matches(usernameRegex, username);
    }
    private boolean isValidPassword(String password) {
        String passwordRegex = "^[A-Za-z\\d@$^!%*?&]{8,20}$";
        return Pattern.matches(passwordRegex, password);
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton3MouseClicked

    private void jToggleButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton1MouseClicked
        Register1.setVisible(false);
        List1.setVisible(false);
        admin_list.setVisible(false);
        Delete.setVisible(false);
        Edit.setVisible(true);
        profile.setVisible(false);

        NotificationsPanel.setBackground(DefaultColor);
        PrivacySettingsPanel.setBackground(DefaultColor);
        TermsServicesPanel.setBackground(DefaultColor);
        ManageUserPanel.setBackground(ClickedColor);
    }//GEN-LAST:event_jToggleButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Register1.setVisible(false);
        admin_list.setVisible(false);
        Delete.setVisible(true);
        Edit.setVisible(false);
        
        NotificationsPanel.setBackground(DefaultColor);
        PrivacySettingsPanel.setBackground(DefaultColor);
        TermsServicesPanel.setBackground(DefaultColor);
        ManageUserPanel.setBackground(ClickedColor);
    }//GEN-LAST:event_jButton2ActionPerformed
    public void getAdmins(){
        
        
        // prepare connection
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";
        
        String getAdminDataQuery = "SELECT * FROM admin_accounts";
        
        
         try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(getAdminDataQuery);
             
             ResultSet resultSet = statement.executeQuery()) {
             
             DefaultTableModel model_view = (DefaultTableModel) admin_list.getModel();
             DefaultTableModel model_edit = (DefaultTableModel) admin_list_edit.getModel();
             model_edit.addTableModelListener(new TableModelListener() {
                 @Override
                 public void tableChanged(TableModelEvent e){
                         int row = e.getFirstRow();
                         int col = e.getColumn();
                         int username_column_index = 3;
                         
                         if (e.getType() == TableModelEvent.UPDATE) {
                            // get username bc unique
                            Object get_user = admin_list_edit.getValueAt(row, username_column_index);
                            String username = get_user.toString();

                            // Get the updated position from the JComboBox
                            Object get_pos = admin_list_edit.getValueAt(row, col);
                            String position = get_pos.toString();
                            
                            changePosition(username, position);  

                         }
                 }
             });
             DefaultTableModel model_delete = (DefaultTableModel) admin_list_delete.getModel();
             model_view.setRowCount(0);
             model_edit.setRowCount(0);
             model_delete.setRowCount(0);
         
            while (resultSet.next()) {
                String get_first_name = resultSet.getString("first_name");
                String get_last_name = resultSet.getString("last_name");
                String get_email = resultSet.getString("email");
                String get_username = resultSet.getString("username");
                String get_position = resultSet.getString("position");
                String get_barangay = resultSet.getString("barangay");
                
                // add current admin to admin row
                model_view.addRow(new Object[]{get_first_name, get_last_name, get_email, get_username, get_position, get_barangay});
                model_edit.addRow(new Object[]{get_first_name, get_last_name, get_email, get_username, get_position, get_barangay});
                model_delete.addRow(new Object[]{get_first_name, get_last_name, get_email, get_username, get_position, get_barangay});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void changePosition(String username, String position){
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";
        
        String updateAdminPositionQuery = "UPDATE admin_accounts SET position = ? WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement updateStatement = conn.prepareStatement(updateAdminPositionQuery)) {
        
        updateStatement.setString(1, position);
        updateStatement.setString(2, username);
        
        updateStatement.executeUpdate();
        System.out.println("changed " + username + " position to " + position);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception as needed
        }
        
    }
    private void ManageUserComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ManageUserComponentShown
            // TODO add your handling code here:
          getAdmins();
         
            
    }//GEN-LAST:event_ManageUserComponentShown

    private void jToggleButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton2MouseClicked
        Register1.setVisible(false);
        admin_list.setVisible(true);
        Delete.setVisible(false);
        Edit.setVisible(false);

        NotificationsPanel.setBackground(DefaultColor);
        PrivacySettingsPanel.setBackground(DefaultColor);
        TermsServicesPanel.setBackground(DefaultColor);
        ManageUserPanel.setBackground(ClickedColor);
        getAdmins();
    }//GEN-LAST:event_jToggleButton2MouseClicked
    public void deleteAdmin(String username){
        // get value of delete field
        String admin_to_delete = username;
        // prepare connection
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";

        String query = "DELETE FROM admin_accounts WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters
            preparedStatement.setString(1, admin_to_delete);

            // Execute the query
            int rows = preparedStatement.executeUpdate();
            
            if (rows > 0){
                JOptionPane.showMessageDialog(this, "User deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No user found with the specified username", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clear form fields
            delete_field.setText("");
            
            // reload table
            getAdmins();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting user from database", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
    private void doSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_doSearchMouseClicked
        // TODO add your handling code here:
        
        String admin_to_delete = delete_field.getText();
        System.out.println(admin_to_delete);
        // prepare connection
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM admin_accounts WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            // Set parameters
            preparedStatement.setString(1, admin_to_delete);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            // Execute the query
            if (resultSet.next()){
               int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete admin '" + admin_to_delete + "' ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION){
                    deleteAdmin(admin_to_delete);
                } 
            } else {
                JOptionPane.showMessageDialog(this, "No user found with the specified username", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clear form fields
            delete_field.setText("");
            
            // reload table
            getAdmins();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for user", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        
    }//GEN-LAST:event_doSearchMouseClicked

    private void delete_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_delete_fieldActionPerformed

    private void delete_fieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_delete_fieldFocusGained
        // TODO add your handling code here:
        delete_field.setText("");
    }//GEN-LAST:event_delete_fieldFocusGained

    private void doDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_doDeleteActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        Edit.setVisible(false);
        Delete.setVisible(false);
        Register1.setVisible(false);
        admin_list.setVisible(true);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        Edit.setVisible(false);
        Delete.setVisible(false);
        Register1.setVisible(false);
        admin_list.setVisible(true);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void delete_fieldComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_delete_fieldComponentHidden
        // TODO add your handling code here:
        delete_field.setText("Username");
    }//GEN-LAST:event_delete_fieldComponentHidden

    private void display_LNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_display_LNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_display_LNameActionPerformed

    private void display_PositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_display_PositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_display_PositionActionPerformed

    private void display_BarangayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_display_BarangayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_display_BarangayActionPerformed

    private void show1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show1MouseClicked
        edit_Password.setEchoChar((char)8226);
        disable.setVisible(true);
        disable.setEnabled(true);
        show.setEnabled(false);
        show.setEnabled(false);
    }//GEN-LAST:event_show1MouseClicked

    private void disable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disable1MouseClicked
        edit_Password.setEchoChar((char)0);
        disable.setVisible(false);
        disable.setEnabled(false);
        show.setEnabled(true);
        show.setEnabled(true);
    }//GEN-LAST:event_disable1MouseClicked

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        edit_profile.setVisible(false);
        settings.setVisible(false);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        profile.setVisible(true);
    }//GEN-LAST:event_jLabel49MouseClicked

    private void edit_FNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_FNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_FNameActionPerformed

    private void edit_LNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_LNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_LNameActionPerformed
    public void getProfile(){
        
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM admin_accounts WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            String username = "odeytskieee"; // logged in
            // Set parameters
            preparedStatement.setString(1, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            // Execute the query
            if (resultSet.next()){
                display_FName.setText(resultSet.getString("first_name"));
                display_LName.setText(resultSet.getString("last_name"));
                display_Email.setText(resultSet.getString("email"));
                display_Barangay.setSelectedItem(resultSet.getString("barangay"));
                display_Position.setSelectedItem(resultSet.getString("position"));
                display_Username.setText(resultSet.getString("username"));
                display_Password.setText(resultSet.getString("password"));
                display_ID.setText(resultSet.getString("admin_id"));
                // reload table
                getAdmins();    
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying data", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
    private void profileComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_profileComponentShown
        // TODO add your handling code here:
        getProfile();
    }//GEN-LAST:event_profileComponentShown

    private void edit_profileComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_edit_profileComponentShown
        // TODO add your handling code here:
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM admin_accounts WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            String username = "odeytskieee";
            // Set parameters
            preparedStatement.setString(1, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            // Execute the query
            
            
            if (resultSet.next()){
                edit_FName.setText(resultSet.getString("first_name"));
                edit_LName.setText(resultSet.getString("last_name"));
                edit_Email.setText(resultSet.getString("email"));
                edit_Barangay.setSelectedItem(resultSet.getString("barangay"));
                edit_Position.setSelectedItem(resultSet.getString("position"));
                edit_Username.setText(resultSet.getString("username"));
                edit_Password.setText(resultSet.getString("password"));
                edit_ID.setText(resultSet.getString("admin_id"));
                   
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying data", "Error", JOptionPane.ERROR_MESSAGE);
        } 
    }//GEN-LAST:event_edit_profileComponentShown

    private void List1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_List1ComponentShown
        // TODO add your handling code here:
        getAdmins();
    }//GEN-LAST:event_List1ComponentShown

    private void SettingsPageComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_SettingsPageComponentShown
        // TODO add your handling code here:
        getAdmins();
    }//GEN-LAST:event_SettingsPageComponentShown

    private void save_profMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save_profMouseClicked
        // TODO add your handling code here:
        String first_name = edit_FName.getText();
        String last_name = edit_LName.getText();
        String email = edit_Email.getText();
        String username = edit_Username.getText();
        String barangay = (String) edit_Barangay.getSelectedItem();
        char[] user_password = edit_Password.getPassword();
        String to_hash_password = new String(user_password);
        try {
            String hashed_password = PasswordHasher.getSaltedHash(to_hash_password);
            // prepare connection
            String url = "jdbc:mysql://localhost:3306/skprofiling";
            String user = "root";
            String password = "";
            String query = "UPDATE admin_accounts SET first_name = ?, last_name = ?, email = ?, username = ?, barangay = ?, password = ? WHERE username = ?";

            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                String current_user = "odeytskieee"; // logged in
                
                // Set parameters
                preparedStatement.setString(1, first_name);
                preparedStatement.setString(2, last_name);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, barangay);
                preparedStatement.setString(6, hashed_password);
                preparedStatement.setString(7, current_user);
                // Execute the query
                preparedStatement.executeUpdate();

                // Show success message
                JOptionPane.showMessageDialog(this, "You have edited your account", "Success", JOptionPane.INFORMATION_MESSAGE);
                getProfile();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "There's an error editing your account.", "Error", JOptionPane.ERROR_MESSAGE);
            }    
            
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_save_profMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        Register1.setVisible(false);
        admin_list.setVisible(false);
        
        Delete.setVisible(true);
        Edit.setVisible(false);
        List1.setVisible(false);
    }//GEN-LAST:event_jButton2MouseClicked

    private void EditComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_EditComponentShown
        // TODO add your handling code here:
        getAdmins();
    }//GEN-LAST:event_EditComponentShown

    private void settingsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_settingsComponentShown
        // TODO add your handling code here:
        profile.setVisible(false);
        edit_profile.setVisible(false);
    }//GEN-LAST:event_settingsComponentShown

    private void Position_Field3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Position_Field3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Position_Field3ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        Register1.setVisible(false);
        admin_list.setVisible(true);

        NotificationsPanel.setBackground(DefaultColor);
        PrivacySettingsPanel.setBackground(DefaultColor);
        TermsServicesPanel.setBackground(DefaultColor);
        ManageUserPanel.setBackground(ClickedColor);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registerActionPerformed

    private void registerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerMouseClicked

            registerAdmin();
   

    }//GEN-LAST:event_registerMouseClicked

    private void Contact_Field3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Contact_Field3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Contact_Field3ActionPerformed

    private void Email_Field3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Email_Field3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Email_Field3ActionPerformed

    private void LN_Field3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LN_Field3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LN_Field3ActionPerformed

    private void FN_Field3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FN_Field3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FN_Field3ActionPerformed

    private void jbtnAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddNewActionPerformed
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";
                    
                    String query = "INSERT INTO connector (FirstName, MiddleInitial, LastName, Suffix, Birthday, Age, Sex, Email, PhoneNumber, CivilStatus, YouthAgeGroup, EducationalBackground, YouthClass, WorkStatus, SKVoter, NationalVoter, AttendedKKAssembly) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    try (Connection connection = DriverManager.getConnection(url, user, password);
                            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        
                        // Set parameters
                        preparedStatement.setString(1, jtxtFName.getText());
                        preparedStatement.setString(2, jtxtMInitial.getText());
                        preparedStatement.setString(3, jtxtLName.getText());
                        preparedStatement.setString(4, jtxtSuffix.getText());
                        preparedStatement.setString(5, jtxtBday.getText());
                        preparedStatement.setString(6, jtxtAge.getText());
                        preparedStatement.setString(7, jtxtSexB.getText());
                        preparedStatement.setString(8, jtxtEmail.getText());
                        preparedStatement.setString(9, jtxtPNumber.getText());
                        preparedStatement.setString(10, cboCivil.getSelectedItem().toString());
                        preparedStatement.setString(11, cboYouthAge.getSelectedItem().toString());
                        preparedStatement.setString(12, cboEducBack.getSelectedItem().toString());
                        preparedStatement.setString(13, cboYouthClass.getSelectedItem().toString());
                        preparedStatement.setString(14, cboWorkStatus.getSelectedItem().toString());
                        preparedStatement.setString(15, cboSKVoter.getSelectedItem().toString());
                        preparedStatement.setString(16, cboNVoter.getSelectedItem().toString());
                        preparedStatement.setString(17, cboKKAssembly.getSelectedItem().toString());

                        // Execute the query
                        preparedStatement.executeUpdate();
                        
                        // Show success message
                        JOptionPane.showMessageDialog(this, "Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        upDateDB();
                       
                    } catch (SQLException ex) {
                        
                        JOptionPane.showMessageDialog(this, "Error adding to database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
                    }
        
    }//GEN-LAST:event_jbtnAddNewActionPerformed

    private void jbtnUpdateDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateDataActionPerformed
        String url = "jdbc:mysql://localhost:3306/skprofiling";
        String user = "root";
        String password = "";
                    
                    String query = "Update connector set FirstName =?, MiddleInitial =?, LastName =?, Suffix =?, Birthday =?, Age =?, Sex =?,Email =?, PhoneNumber =?, CivilStatus =?, YouthAgeGroup =?, EducationalBackground =?, YouthClass =?,WorkStatus =?, SKVoter =?, NationalVoter =?, AttendedKKAssembly =?)";
                    
                    try (Connection connection = DriverManager.getConnection(url, user, password);
                            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        
                        // Set parameters
                        pst.setString(1, jtxtFName.getText());
                        pst.setString(2, jtxtMInitial.getText());
                        pst.setString(3, jtxtLName.getText());
                        pst.setString(4, jtxtSuffix.getText());
                        pst.setString(5, jtxtBday.getText());
                        pst.setString(6, jtxtAge.getText());
                        pst.setString(7, jtxtSexB.getText());
                        pst.setString(8, jtxtEmail.getText());
                        pst.setString(9, jtxtPNumber.getText());
                        pst.setString(10, cboCivil.getSelectedItem().toString());
                        pst.setString(11, cboYouthAge.getSelectedItem().toString());
                        pst.setString(12, cboEducBack.getSelectedItem().toString());
                        pst.setString(13, cboYouthClass.getSelectedItem().toString());
                        pst.setString(14, cboWorkStatus.getSelectedItem().toString());
                        pst.setString(15, cboSKVoter.getSelectedItem().toString());
                        pst.setString(16, cboNVoter.getSelectedItem().toString());
                        pst.setString(17, cboKKAssembly.getSelectedItem().toString());

                        // Execute the query
                        preparedStatement.executeUpdate();
                        
                        // Show success message
                        JOptionPane.showMessageDialog(this, "Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        
                       
                    } catch (SQLException ex) {
                        
                        JOptionPane.showMessageDialog(this, "Error adding to database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);    
                    }

    }//GEN-LAST:event_jbtnUpdateDataActionPerformed

    private void jbtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPrintActionPerformed
        MessageFormat header = new MessageFormat("Printing in progress");
        MessageFormat footer = new MessageFormat("Page {0, number, integer}");

        try
        {
            jTable1.print(JTable.PrintMode.NORMAL,header,footer);
        }

        catch(java.awt.print.PrinterException e)
        {
            System.err.format("No Printer found", e.getMessage());
        }
    }//GEN-LAST:event_jbtnPrintActionPerformed

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnResetActionPerformed
        jtxtFName.setText("");
        jtxtMInitial.setText("");
        jtxtLName.setText("");
        jtxtSuffix.setText("");
        jtxtBday.setText("");
        jtxtAge.setText("");
        jtxtSexB.setText("");
        jtxtEmail.setText("");
        jtxtPNumber.setText("");
        cboCivil.setSelectedIndex(0);
        cboYouthAge.setSelectedIndex(0);
        cboEducBack.setSelectedIndex(0);
        cboYouthClass.setSelectedIndex(0);
        cboWorkStatus.setSelectedIndex(0);
        cboSKVoter.setSelectedIndex(0);
        cboNVoter.setSelectedIndex(0);
        cboKKAssembly.setSelectedIndex(0);

    }//GEN-LAST:event_jbtnResetActionPerformed

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed
        DefaultTableModel RecordTable = (DefaultTableModel)jTable1.getModel();
        int SelectedRow = jTable1.getSelectedRow();

        try
        {
            id = Integer.parseInt(RecordTable.getValueAt(SelectedRow, 0).toString());

            deleteItem = JOptionPane.showConfirmDialog(null, "Confirm if you want to delete item", "Warning", JOptionPane.YES_NO_OPTION);
            if (deleteItem ==JOptionPane.YES_NO_OPTION)
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                sqlConn = DriverManager.getConnection(dataConn,username,password);
                pst = sqlConn.prepareStatement("delete from youthprofiling where no=?");

                pst.setInt(1, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this,"Youth Profiling Record Updated");
                upDateDB();

                jtxtFName.setText("");
                jtxtMInitial.setText("");
                jtxtLName.setText("");
                jtxtSuffix.setText("");
                jtxtBday.setText("");
                jtxtAge.setText("");
                jtxtSexB.setText("");
                jtxtEmail.setText("");
                jtxtPNumber.setText("");
                cboCivil.setSelectedIndex(0);
                cboYouthAge.setSelectedIndex(0);
                cboEducBack.setSelectedIndex(0);
                cboYouthClass.setSelectedIndex(0);
                cboWorkStatus.setSelectedIndex(0);
                cboSKVoter.setSelectedIndex(0);
                cboNVoter.setSelectedIndex(0);
                cboKKAssembly.setSelectedIndex(0);

            }
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(side_bar.class.getName()).log(java.util.logging.Level.SEVERE,null,ex);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }//GEN-LAST:event_jbtnDeleteActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel RecordTable = (DefaultTableModel)jTable1.getModel();
        int SelectedRows = jTable1.getSelectedRow();

        jtxtFName.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
        jtxtMInitial.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
        jtxtLName.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
        jtxtSuffix.setText(RecordTable.getValueAt(SelectedRows, 4).toString());
        jtxtBday.setText(RecordTable.getValueAt(SelectedRows, 5).toString());
        jtxtAge.setText(RecordTable.getValueAt(SelectedRows, 6).toString());
        jtxtSexB.setText(RecordTable.getValueAt(SelectedRows, 7).toString());
        jtxtEmail.setText(RecordTable.getValueAt(SelectedRows, 8).toString());
        jtxtPNumber.setText(RecordTable.getValueAt(SelectedRows, 9).toString());
        cboCivil.setSelectedItem(RecordTable.getValueAt(SelectedRows, 10).toString());
        cboYouthAge.setSelectedItem(RecordTable.getValueAt(SelectedRows, 11).toString());
        cboEducBack.setSelectedItem(RecordTable.getValueAt(SelectedRows, 12).toString());
        cboYouthClass.setSelectedItem(RecordTable.getValueAt(SelectedRows, 13).toString());
        cboWorkStatus.setSelectedItem(RecordTable.getValueAt(SelectedRows, 14).toString());
        cboSKVoter.setSelectedItem(RecordTable.getValueAt(SelectedRows, 15).toString());
        cboNVoter.setSelectedItem(RecordTable.getValueAt(SelectedRows, 16).toString());
        cboKKAssembly.setSelectedItem(RecordTable.getValueAt(SelectedRows, 17).toString());

    }//GEN-LAST:event_jTable1MouseClicked

    private void proficon6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proficon6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_proficon6MouseClicked

    private void notificon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificon2MouseClicked
        edit_profile.setVisible(false);
        settings.setVisible(true);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        profile.setVisible(false);
    }//GEN-LAST:event_notificon2MouseClicked

    private void proficon2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proficon2MouseClicked
        profile.setVisible(true);
        settings.setVisible(false);
        user_manual.setVisible(false);
        dashboard.setVisible(false);
        results.setVisible(false);
        youthprofiling.setVisible(false);
        edit_profile.setVisible(false);

    }//GEN-LAST:event_proficon2MouseClicked

    private void faqs_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_faqs_btnMouseClicked
        // TODO add your handling code here:
        faqs.setVisible(true);
        features.setVisible(false);

        faqsbtn_Panel.setBackground(ClickedColor);
        featurebtn_Panel.setBackground(DefaultColor);
    }//GEN-LAST:event_faqs_btnMouseClicked

    private void faqsbtn_PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_faqsbtn_PanelMouseClicked
        faqs.setVisible(true);
        features.setVisible(false);

        faqsbtn_Panel.setBackground(ClickedColor);
        featurebtn_Panel.setBackground(DefaultColor);
    }//GEN-LAST:event_faqsbtn_PanelMouseClicked

    private void feature_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feature_btnMouseClicked
        // TODO add your handling code here:
        features.setVisible(true);
        faqs.setVisible(false);

        faqsbtn_Panel.setBackground(DefaultColor);
        featurebtn_Panel.setBackground(ClickedColor);
    }//GEN-LAST:event_feature_btnMouseClicked

    private void featurebtn_PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_featurebtn_PanelMouseClicked
        features.setVisible(true);
        faqs.setVisible(false);

        faqsbtn_Panel.setBackground(DefaultColor);
        featurebtn_Panel.setBackground(ClickedColor);
    }//GEN-LAST:event_featurebtn_PanelMouseClicked

    private void f2_left12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left12MouseClicked

    private void f2_left12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left12ActionPerformed

    private void f2_right13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right13MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(true);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right13MouseClicked

    private void f2_left13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left13MouseClicked
        feature14.setVisible(true);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left13MouseClicked

    private void f2_left13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left13ActionPerformed

    private void f2_right14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right14MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature16.setVisible(true);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right14MouseClicked

    private void f2_left14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left14MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(true);
        feature16.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left14MouseClicked

    private void f2_left14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left14ActionPerformed

    private void f2_right15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right15MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature16.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(true);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right15MouseClicked

    private void f2_leftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_leftMouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature16.setVisible(true);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_leftMouseClicked

    private void f2_leftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_leftActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_leftActionPerformed

    private void f2_right1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right1MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(true);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right1MouseClicked

    private void f1_leftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f1_leftMouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature16.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(true);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f1_leftMouseClicked

    private void f1_leftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f1_leftActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f1_leftActionPerformed

    private void f1_rightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f1_rightMouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f1_rightMouseClicked

    private void f2_left1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left1MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(true);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left1MouseClicked

    private void f2_left1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left1ActionPerformed

    private void f2_right2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right2MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right2MouseClicked

    private void f2_left2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left2MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left2MouseClicked

    private void f2_left2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left2ActionPerformed

    private void f2_right3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right3MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(true);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right3MouseClicked

    private void f2_left3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left3MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left3MouseClicked

    private void f2_left3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left3MouseEntered

    private void f2_left3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left3ActionPerformed

    private void f2_right4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right4MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(true);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right4MouseClicked

    private void f2_left4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left4MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(true);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left4MouseClicked

    private void f2_left4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left4ActionPerformed

    private void f2_right5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right5MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(true);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right5MouseClicked

    private void f2_left5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left5MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(true);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left5MouseClicked

    private void f2_left5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left5ActionPerformed

    private void f2_right6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right6MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(true);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right6MouseClicked

    private void f2_left6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left6MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(true);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left6MouseClicked

    private void f2_left6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left6ActionPerformed

    private void f2_right7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right7MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(true);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right7MouseClicked

    private void f2_left7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left7MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(true);
        feature9.setVisible(false);
        feature10.setVisible(true);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left7MouseClicked

    private void f2_left7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left7ActionPerformed

    private void f2_right8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right8MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(true);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right8MouseClicked

    private void f2_left8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left8MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(true);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left8MouseClicked

    private void f2_left8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left8ActionPerformed

    private void f2_right9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right9MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(true);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right9MouseClicked

    private void f2_left9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left9MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(true);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left9MouseClicked

    private void f2_left9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left9ActionPerformed

    private void f2_right10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right10MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(true);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_right10MouseClicked

    private void f2_left10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left10MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(true);
        feature12.setVisible(false);
        feature13.setVisible(false);
    }//GEN-LAST:event_f2_left10MouseClicked

    private void f2_left10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left10ActionPerformed

    private void f2_right11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right11MouseClicked
        feature14.setVisible(false);
        feature15.setVisible(false);
        feature1.setVisible(false);
        feature2.setVisible(false);
        feature5.setVisible(false);
        feature6.setVisible(false);
        feature7.setVisible(false);
        feature8.setVisible(false);
        feature9.setVisible(false);
        feature10.setVisible(false);
        feature11.setVisible(false);
        feature12.setVisible(false);
        feature13.setVisible(true);
    }//GEN-LAST:event_f2_right11MouseClicked

    private void f2_left11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_left11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left11MouseClicked

    private void f2_left11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f2_left11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_left11ActionPerformed

    private void f2_right12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f2_right12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_f2_right12MouseClicked
    
    /**
     * @param args the command line arguments
     */
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Barangay3;
    private javax.swing.JComboBox<String> Barangay_Field3;
    private javax.swing.JLabel Citytext;
    private javax.swing.JPasswordField Confirm_Field;
    private javax.swing.JLabel ContactNumber3;
    private javax.swing.JTextField Contact_Field3;
    private javax.swing.JPanel ContainerSettings;
    private javax.swing.JPanel Delete;
    private javax.swing.JPanel Edit;
    private javax.swing.JLabel Email3;
    private javax.swing.JTextField Email_Field3;
    private javax.swing.JTextField FN_Field3;
    private javax.swing.JLabel FirstName3;
    private javax.swing.JLabel InformationWeCollect;
    private javax.swing.JTextPane InformationWeCollect_Pane2;
    private javax.swing.JScrollPane Input_1;
    private javax.swing.JScrollPane Input_2;
    private javax.swing.JScrollPane Input_3;
    private javax.swing.JScrollPane Input_4;
    private javax.swing.JScrollPane Input_5;
    private javax.swing.JScrollPane Input_7;
    private javax.swing.JTextField LN_Field3;
    private javax.swing.JLabel LastName3;
    private javax.swing.JSeparator LineSeparator_2;
    private javax.swing.JSeparator LineSeparator_3;
    private javax.swing.JSeparator LineSeparator_4;
    private javax.swing.JSeparator LineSeparator_5;
    private javax.swing.JSeparator LineSeparator_6;
    private javax.swing.JSeparator LineSeparator_7;
    private javax.swing.JSeparator LineSeparator_8;
    private javax.swing.JSeparator LineSeparator_9;
    private javax.swing.JSeparator LineSeperator_1;
    private javax.swing.JPanel List1;
    private javax.swing.JPanel ManageUser;
    private javax.swing.JPanel ManageUserPanel;
    private javax.swing.JLabel NotifSurvey_1;
    private javax.swing.JLabel NotifSurvey_10;
    private javax.swing.JLabel NotifSurvey_2;
    private javax.swing.JLabel NotifSurvey_3;
    private javax.swing.JLabel NotifSurvey_4;
    private javax.swing.JLabel NotifSurvey_7;
    private javax.swing.JLabel NotifSurvey_8;
    private javax.swing.JLabel NotifSurvey_9;
    private javax.swing.JPanel NotificationsPanel;
    private javax.swing.JPanel Notifications_2;
    private javax.swing.JTextPane OCR_chart;
    private javax.swing.JLabel Password3;
    private javax.swing.JLabel Password4;
    private javax.swing.JPasswordField Password_Field3;
    private javax.swing.JPanel PieChartPanel;
    private javax.swing.JLabel Position3;
    private javax.swing.JComboBox<String> Position_Field3;
    private javax.swing.JLabel PrivacyPolicy;
    private javax.swing.JTextPane PrivacyPolicy_Pane1;
    private javax.swing.JPanel PrivacySettingsPanel;
    private javax.swing.JPanel PrivacySettings_3;
    private javax.swing.JLabel PurposePrivacyPolicy;
    private javax.swing.JTextPane PurposePrivacyPolicy_Pane3;
    private javax.swing.JPanel Register1;
    private javax.swing.JButton SearchButton;
    private javax.swing.JTextField SearchField;
    private java.awt.Label SetTitleSettings;
    private javax.swing.JPanel SettingsPage;
    private javax.swing.JLabel TermsServices;
    private javax.swing.JPanel TermsServicesPanel;
    private javax.swing.JPanel TermsServices_4;
    private javax.swing.JTextPane TermsServices_Pane4;
    private javax.swing.JLabel TodayText;
    private javax.swing.JLabel TodayText1;
    private javax.swing.JLabel UsingServices;
    private javax.swing.JTextPane UsingServices_Pane7;
    private javax.swing.JLabel WelcomeYouthProfiling;
    private javax.swing.JTextPane WelcomeYouthProfiling_Pane5;
    private javax.swing.JPanel YouthProfiling_description;
    private javax.swing.JTextPane act_text;
    private javax.swing.JPanel activities;
    private javax.swing.JTable admin_list;
    private javax.swing.JTable admin_list_delete;
    private javax.swing.JTable admin_list_edit;
    private javax.swing.JPanel aplayaPieChart;
    private javax.swing.JLabel aplayalabel;
    private javax.swing.JLabel application_text;
    private javax.swing.JPanel barChartPanel;
    private javax.swing.JPanel bgdboard;
    private javax.swing.JPanel bgmanual;
    private javax.swing.JPanel bgprofiling;
    private javax.swing.JPanel bgprogress;
    private javax.swing.JPanel bgresult;
    private javax.swing.JPanel bgsettings;
    private javax.swing.JLabel btn_Notifications;
    private javax.swing.JLabel btn_PrivacySettings;
    private javax.swing.JLabel btn_TermsServices;
    private javax.swing.JLabel btn_TermsServices1;
    private javax.swing.JComboBox<String> cboCivil;
    private javax.swing.JComboBox<String> cboEducBack;
    private javax.swing.JComboBox<String> cboKKAssembly;
    private javax.swing.JComboBox<String> cboNVoter;
    private javax.swing.JComboBox<String> cboSKVoter;
    private javax.swing.JComboBox<String> cboWorkStatus;
    private javax.swing.JComboBox<String> cboYouthAge;
    private javax.swing.JComboBox<String> cboYouthClass;
    private javax.swing.JLabel count1;
    private javax.swing.JLabel count2;
    private javax.swing.JTextPane count2022;
    private javax.swing.JTextPane count2023;
    private javax.swing.JTextPane count2025;
    private javax.swing.JLabel count3;
    private javax.swing.JLabel count4;
    private javax.swing.JLabel count5;
    private javax.swing.JLabel count6;
    private javax.swing.JPanel countbox1;
    private javax.swing.JPanel countbox2;
    private javax.swing.JPanel countbox3;
    private javax.swing.JPanel current_container;
    private javax.swing.JLabel current_count;
    private javax.swing.JLabel current_separator;
    private javax.swing.JLabel current_title;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel dboardClicked;
    private javax.swing.JLabel dboardbutton;
    private javax.swing.JLabel dboardlabel;
    private javax.swing.JTextField delete_field;
    private javax.swing.JLabel disable;
    private javax.swing.JLabel disable1;
    private javax.swing.JComboBox<String> display_Barangay;
    private javax.swing.JTextField display_Email;
    private javax.swing.JTextField display_FName;
    private javax.swing.JTextField display_ID;
    private javax.swing.JTextField display_LName;
    private javax.swing.JPasswordField display_Password;
    private javax.swing.JComboBox<String> display_Position;
    private javax.swing.JTextField display_Username;
    private javax.swing.JButton doDelete;
    private javax.swing.JComboBox<String> edit_Barangay;
    private javax.swing.JTextField edit_Email;
    private javax.swing.JTextField edit_FName;
    private javax.swing.JTextField edit_ID;
    private javax.swing.JTextField edit_LName;
    private javax.swing.JPasswordField edit_Password;
    private javax.swing.JComboBox<String> edit_Position;
    private javax.swing.JTextField edit_Username;
    private javax.swing.JButton edit_prof;
    private javax.swing.JPanel edit_profile;
    private javax.swing.JTextPane f1_desc;
    private javax.swing.JTextPane f1_desc1;
    private javax.swing.JTextPane f1_desc10;
    private javax.swing.JTextPane f1_desc11;
    private javax.swing.JTextPane f1_desc12;
    private javax.swing.JTextPane f1_desc13;
    private javax.swing.JTextPane f1_desc14;
    private javax.swing.JTextPane f1_desc15;
    private javax.swing.JTextPane f1_desc2;
    private javax.swing.JTextPane f1_desc3;
    private javax.swing.JTextPane f1_desc4;
    private javax.swing.JTextPane f1_desc5;
    private javax.swing.JTextPane f1_desc6;
    private javax.swing.JTextPane f1_desc7;
    private javax.swing.JTextPane f1_desc8;
    private javax.swing.JTextPane f1_desc9;
    private javax.swing.JButton f1_left;
    private javax.swing.JLabel f1_page1;
    private javax.swing.JPanel f1_pic;
    private javax.swing.JLabel f1_pict;
    private javax.swing.JLabel f1_pict1;
    private javax.swing.JLabel f1_pict10;
    private javax.swing.JLabel f1_pict11;
    private javax.swing.JLabel f1_pict12;
    private javax.swing.JLabel f1_pict13;
    private javax.swing.JLabel f1_pict14;
    private javax.swing.JLabel f1_pict15;
    private javax.swing.JLabel f1_pict2;
    private javax.swing.JLabel f1_pict3;
    private javax.swing.JLabel f1_pict4;
    private javax.swing.JLabel f1_pict5;
    private javax.swing.JLabel f1_pict6;
    private javax.swing.JLabel f1_pict7;
    private javax.swing.JLabel f1_pict8;
    private javax.swing.JLabel f1_pict9;
    private javax.swing.JButton f1_right;
    private javax.swing.JScrollPane f1_textcont;
    private javax.swing.JLabel f1_title;
    private javax.swing.JButton f2_left;
    private javax.swing.JButton f2_left1;
    private javax.swing.JButton f2_left10;
    private javax.swing.JButton f2_left11;
    private javax.swing.JButton f2_left12;
    private javax.swing.JButton f2_left13;
    private javax.swing.JButton f2_left14;
    private javax.swing.JButton f2_left2;
    private javax.swing.JButton f2_left3;
    private javax.swing.JButton f2_left4;
    private javax.swing.JButton f2_left5;
    private javax.swing.JButton f2_left6;
    private javax.swing.JButton f2_left7;
    private javax.swing.JButton f2_left8;
    private javax.swing.JButton f2_left9;
    private javax.swing.JLabel f2_page10;
    private javax.swing.JLabel f2_page11;
    private javax.swing.JLabel f2_page12;
    private javax.swing.JLabel f2_page13;
    private javax.swing.JLabel f2_page14;
    private javax.swing.JLabel f2_page15;
    private javax.swing.JLabel f2_page16;
    private javax.swing.JLabel f2_page2;
    private javax.swing.JLabel f2_page3;
    private javax.swing.JLabel f2_page4;
    private javax.swing.JLabel f2_page5;
    private javax.swing.JLabel f2_page6;
    private javax.swing.JLabel f2_page7;
    private javax.swing.JLabel f2_page8;
    private javax.swing.JLabel f2_page9;
    private javax.swing.JPanel f2_pic1;
    private javax.swing.JPanel f2_pic10;
    private javax.swing.JPanel f2_pic11;
    private javax.swing.JPanel f2_pic12;
    private javax.swing.JPanel f2_pic13;
    private javax.swing.JPanel f2_pic14;
    private javax.swing.JPanel f2_pic15;
    private javax.swing.JPanel f2_pic2;
    private javax.swing.JPanel f2_pic3;
    private javax.swing.JPanel f2_pic4;
    private javax.swing.JPanel f2_pic5;
    private javax.swing.JPanel f2_pic6;
    private javax.swing.JPanel f2_pic7;
    private javax.swing.JPanel f2_pic8;
    private javax.swing.JPanel f2_pic9;
    private javax.swing.JButton f2_right1;
    private javax.swing.JButton f2_right10;
    private javax.swing.JButton f2_right11;
    private javax.swing.JButton f2_right12;
    private javax.swing.JButton f2_right13;
    private javax.swing.JButton f2_right14;
    private javax.swing.JButton f2_right15;
    private javax.swing.JButton f2_right2;
    private javax.swing.JButton f2_right3;
    private javax.swing.JButton f2_right4;
    private javax.swing.JButton f2_right5;
    private javax.swing.JButton f2_right6;
    private javax.swing.JButton f2_right7;
    private javax.swing.JButton f2_right8;
    private javax.swing.JButton f2_right9;
    private javax.swing.JScrollPane f2_textcont1;
    private javax.swing.JScrollPane f2_textcont10;
    private javax.swing.JScrollPane f2_textcont11;
    private javax.swing.JScrollPane f2_textcont12;
    private javax.swing.JScrollPane f2_textcont13;
    private javax.swing.JScrollPane f2_textcont14;
    private javax.swing.JScrollPane f2_textcont15;
    private javax.swing.JScrollPane f2_textcont2;
    private javax.swing.JScrollPane f2_textcont3;
    private javax.swing.JScrollPane f2_textcont4;
    private javax.swing.JScrollPane f2_textcont5;
    private javax.swing.JScrollPane f2_textcont6;
    private javax.swing.JScrollPane f2_textcont7;
    private javax.swing.JScrollPane f2_textcont8;
    private javax.swing.JScrollPane f2_textcont9;
    private javax.swing.JScrollPane faq1_containerDesc;
    private javax.swing.JTextPane faq1_desc;
    private javax.swing.JScrollPane faq2_containerDesc;
    private javax.swing.JScrollPane faq2_containerDesc2;
    private javax.swing.JTextPane faq2_desc;
    private javax.swing.JTextPane faq2_desc2;
    private javax.swing.JPanel faq_1;
    private javax.swing.JLabel faq_1Ques;
    private javax.swing.JPanel faq_2;
    private javax.swing.JLabel faq_2Ques;
    private javax.swing.JLabel faq_2Ques2;
    private javax.swing.JPanel faq_5;
    private javax.swing.JPanel faqs;
    private javax.swing.JLabel faqs_btn;
    private javax.swing.JPanel faqsbtn_Panel;
    private javax.swing.JPanel feature1;
    private javax.swing.JPanel feature10;
    private javax.swing.JPanel feature11;
    private javax.swing.JPanel feature12;
    private javax.swing.JPanel feature13;
    private javax.swing.JPanel feature14;
    private javax.swing.JPanel feature15;
    private javax.swing.JPanel feature16;
    private javax.swing.JPanel feature17;
    private javax.swing.JPanel feature18;
    private javax.swing.JPanel feature2;
    private javax.swing.JPanel feature5;
    private javax.swing.JPanel feature6;
    private javax.swing.JPanel feature7;
    private javax.swing.JPanel feature8;
    private javax.swing.JPanel feature9;
    private javax.swing.JLabel feature_btn;
    private javax.swing.JPanel featurebtn_Panel;
    private javax.swing.JPanel features;
    private javax.swing.JLabel head_title;
    private javax.swing.JPanel hidemenu;
    private javax.swing.JLabel hidemenubutton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JButton jbtnAddNew;
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnPrint;
    private javax.swing.JButton jbtnReset;
    private javax.swing.JButton jbtnUpdateData;
    private javax.swing.JTextField jtxtAge;
    private javax.swing.JTextField jtxtBday;
    private javax.swing.JTextField jtxtEmail;
    private javax.swing.JTextField jtxtFName;
    private javax.swing.JTextField jtxtLName;
    private javax.swing.JTextField jtxtMInitial;
    private javax.swing.JTextField jtxtPNumber;
    private javax.swing.JTextField jtxtSexB;
    private javax.swing.JTextField jtxtSuffix;
    private javax.swing.JSeparator line1;
    private javax.swing.JPanel logo;
    private javax.swing.JLabel logoicon;
    private javax.swing.JPanel logout;
    private javax.swing.JLabel logoutbutton;
    private javax.swing.JLabel logoutlabel;
    private javax.swing.JPanel manualClicked;
    private javax.swing.JLabel manualbutton;
    private javax.swing.JLabel manuallabel;
    private javax.swing.JPanel member_container;
    private javax.swing.JScrollPane members_head;
    private javax.swing.JTable members_table;
    private javax.swing.JLabel members_title;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menufooter;
    private javax.swing.JPanel menuhide;
    private javax.swing.JPanel menuside;
    private javax.swing.JLabel notificon;
    private javax.swing.JLabel notificon1;
    private javax.swing.JLabel notificon2;
    private javax.swing.JLabel notificon3;
    private javax.swing.JLabel notificon4;
    private javax.swing.JLabel notificon5;
    private javax.swing.JLabel notificon7;
    private javax.swing.JPanel page;
    private javax.swing.JPanel pagechanger;
    private javax.swing.JLabel proficon;
    private javax.swing.JLabel proficon2;
    private javax.swing.JLabel proficon3;
    private javax.swing.JLabel proficon4;
    private javax.swing.JLabel proficon5;
    private javax.swing.JLabel proficon6;
    private javax.swing.JLabel proficon7;
    private javax.swing.JPanel profile;
    private javax.swing.JPanel profilingClicked;
    private javax.swing.JLabel profilingbutton;
    private javax.swing.JLabel profilinglabel;
    private javax.swing.JTextPane progressstats3;
    private javax.swing.JLabel reg_confirm_error;
    private javax.swing.JLabel reg_email_error;
    private javax.swing.JLabel reg_fname_error;
    private javax.swing.JLabel reg_lname_error;
    private javax.swing.JLabel reg_password_error;
    private javax.swing.JLabel reg_username_error;
    private javax.swing.JButton register;
    private javax.swing.JPanel resultClicked;
    private javax.swing.JLabel resultbutton;
    private javax.swing.JLabel resultlabel;
    private javax.swing.JPanel results;
    private javax.swing.JPanel right;
    private javax.swing.JButton save_prof;
    private javax.swing.JPanel settings;
    private javax.swing.JPanel settingsClicked;
    private javax.swing.JLabel settingsbutton;
    private javax.swing.JLabel settingslabel;
    private javax.swing.JLabel show;
    private javax.swing.JLabel show1;
    private javax.swing.JPanel sidecharts;
    private javax.swing.JPanel sinalhanPieChart;
    private javax.swing.JLabel sinalhanlabel;
    private javax.swing.JLabel sktext;
    private javax.swing.JPanel target_container;
    private javax.swing.JLabel target_count;
    private javax.swing.JLabel target_separator;
    private javax.swing.JLabel target_title;
    private javax.swing.JLabel title_FAQs;
    private javax.swing.JLabel title_f10;
    private javax.swing.JLabel title_f11;
    private javax.swing.JLabel title_f12;
    private javax.swing.JLabel title_f13;
    private javax.swing.JLabel title_f14;
    private javax.swing.JLabel title_f15;
    private javax.swing.JLabel title_f16;
    private javax.swing.JLabel title_f2;
    private javax.swing.JLabel title_f3;
    private javax.swing.JLabel title_f4;
    private javax.swing.JLabel title_f5;
    private javax.swing.JLabel title_f6;
    private javax.swing.JLabel title_f7;
    private javax.swing.JLabel title_f8;
    private javax.swing.JLabel title_f9;
    private javax.swing.JPanel user_manual;
    private javax.swing.JButton viewall;
    private javax.swing.JPanel year_container;
    private javax.swing.JLabel year_desc;
    private javax.swing.JLabel year_desc2;
    private javax.swing.JLabel year_hdesc;
    private javax.swing.JPanel year_title;
    private javax.swing.JPanel year_title2;
    private javax.swing.JPanel youth_container;
    private javax.swing.JPanel youth_container2;
    private javax.swing.JPanel youth_container3;
    private javax.swing.JLabel youth_profiling_text;
    private javax.swing.JPanel youthprofiling;
    // End of variables declaration//GEN-END:variables
}
