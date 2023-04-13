package view;

import static java.util.UUID.randomUUID;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

/**
 * This is a class which handles the view or the frontend of the application.
 */
public class JFrameView extends JFrame implements IView {

  private final JPanel histogramPanel;
  private JLabel imageLabel;
  private JScrollPane imageScrollPane;
  private JButton loadImageButton;
  private JButton saveImageButton;
  private JButton blur;
  private JButton brighten;
  private JButton dither;
  private JButton greyscale;
  private JButton horizontalFlip;
  private JButton verticalFlip;
  private JButton rgbCombine;
  private JButton rgbSplit;
  private JButton greyscaleColorTransform;
  private JButton sepiaColorTransform;
  private JButton sharpen;
  private String currentImage;

  /**
   * This is a constructor used to instantiate the above class.
   *
   * @param caption the title for the frame
   */
  public JFrameView(String caption) {
    super(caption);

    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    this.currentImage = null;

    // Create the histogram panel
    histogramPanel = new JPanel();
    histogramPanel.setBounds(20, 20, 300, 400);
    histogramPanel.setBackground(Color.WHITE);
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));

    // Create the commands panel
    JPanel commandsPanel = new JPanel(new GridLayout(5, 2));
    commandsPanel.setBackground(Color.WHITE);
    commandsPanel.setBorder(BorderFactory.createTitledBorder("Commands"));

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Create the image panel
    JPanel imagePanel = new JPanel();
    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(
        new Dimension((int) screenSize.getWidth() - 600, (int) screenSize.getHeight() - 130));
    imagePanel.add(imageScrollPane);
    imagePanel.setBackground(Color.WHITE);
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));

    //load image
    loadImageButton = new JButton("Load Image");

    //save image
    saveImageButton = new JButton("Save Image");

    // Add the histogram panel and commands panel to the left side
    JPanel leftPanel = new JPanel(new GridLayout(2, 1));
    leftPanel.add(histogramPanel);
    leftPanel.add(commandsPanel);
    getContentPane().add(leftPanel, BorderLayout.LINE_START);

    // Add the image panel and buttons to the right side
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(imagePanel, BorderLayout.CENTER);
    JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
    buttonsPanel.add(loadImageButton);
    buttonsPanel.add(saveImageButton);
    rightPanel.add(buttonsPanel, BorderLayout.NORTH);
    getContentPane().add(rightPanel, BorderLayout.CENTER);

    //Commands
    blur = new JButton("Blur");
    blur.setActionCommand("Blur");
    commandsPanel.add(blur);

    brighten = new JButton("Brighten");
    brighten.setActionCommand("Brighten");
    commandsPanel.add(brighten);

    dither = new JButton("Dither");
    dither.setActionCommand("Dither");
    commandsPanel.add(dither);

    greyscale = new JButton("Greyscale");
    greyscale.setActionCommand("Greyscale");
    commandsPanel.add(greyscale);

    horizontalFlip = new JButton("Horizontal Flip");
    horizontalFlip.setActionCommand("Horizontal Flip");
    commandsPanel.add(horizontalFlip);

    verticalFlip = new JButton("Vertical Flip");
    verticalFlip.setActionCommand("Vertical Flip");
    commandsPanel.add(verticalFlip);

    rgbCombine = new JButton("RGB Combine");
    commandsPanel.add(rgbCombine);

    rgbSplit = new JButton("RGB Split");
    rgbSplit.setActionCommand("RGB Split");
    commandsPanel.add(rgbSplit);

    greyscaleColorTransform = new JButton("Greyscale Color Transform");
    greyscaleColorTransform.setActionCommand("Greyscale Color Transform");
    commandsPanel.add(greyscaleColorTransform);

    sepiaColorTransform = new JButton("Sepia Color Transform");
    sepiaColorTransform.setActionCommand("Sepia Color Transform");
    commandsPanel.add(sepiaColorTransform);

    sharpen = new JButton("Sharpen");
    sharpen.setActionCommand("Sharpen");
    commandsPanel.add(sharpen);

    setVisible(true);
  }

  /**
   * This is a method which handles the functionality of performing the action based on the set
   * action listener event.
   *
   * @param features the different methods supported by the application
   */
  @Override
  public void addFeatures(Features features) {
    loadImageButton.addActionListener(evt -> {
      final JFileChooser jFileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "Images", "jpg", "png", "bmp", "ppm", "jpeg");
      jFileChooser.setFileFilter(filter);
      int state = jFileChooser.showOpenDialog(JFrameView.this);
      if (state == JFileChooser.APPROVE_OPTION) {
        File f = jFileChooser.getSelectedFile();
        this.currentImage = randomUUID().toString();
        String imagePath = f.getAbsolutePath();
        features.loadImage(imagePath, currentImage);
        this.loadImageOnScreen(features);
      }
    });
    blur.addActionListener(evt -> {
      String destName = this.currentImage + "blur";
      features.blurImage(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    brighten.addActionListener(evt -> {
      String destName = this.currentImage + "brighten";
      String input = JOptionPane.showInputDialog(this,
          "Enter a value to alter the brightness of the image:");
      try {
        int value = Integer.parseInt(input);
        features.brighten(String.valueOf(value), this.currentImage, destName);
        this.currentImage = destName;
        this.loadImageOnScreen(features);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please enter an integer value.");
      }
    });
    dither.addActionListener(evt -> {
      String destName = this.currentImage + "dither";
      features.dither(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    greyscale.addActionListener(evt -> {
      String destName = this.currentImage + "greyscale";
      String component = getValueComponent();
      if (component != null) {
        features.greyscale(this.currentImage, destName, component);
        this.currentImage = destName;
        this.loadImageOnScreen(features);
      }
    });
    rgbCombine.addActionListener(evt -> {
      String destName = this.currentImage + "combine";
      List<String> component = getRGBCombineImages();
      if (component.size() == 3) {
        features.rgbCombine(destName, component);
        this.currentImage = destName;
        this.loadImageOnScreen(features);
      }
    });
    rgbSplit.addActionListener(evt -> {
      features.rgbSplit(currentImage, currentImage + "rsplit", currentImage + "gsplit",
          currentImage + "bsplit");
      BufferedImage red = features.getLoadedImage(currentImage + "rsplit");
      BufferedImage green = features.getLoadedImage(currentImage + "gsplit");
      BufferedImage blue = features.getLoadedImage(currentImage + "bsplit");
      List<String> component = getRGBSplitImages(red, green, blue);
      if (component.size() == 3) {
        features.saveImage(component.get(0), currentImage + "rsplit");
        features.saveImage(component.get(1), currentImage + "gsplit");
        features.saveImage(component.get(2), currentImage + "bsplit");
      }
    });
    horizontalFlip.addActionListener(evt -> {
      String destName = this.currentImage + "hf";
      features.horizontalFlip(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    verticalFlip.addActionListener(evt -> {
      String destName = this.currentImage + "vf";
      features.verticalFlip(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    greyscaleColorTransform.addActionListener(evt -> {
      String destName = this.currentImage + "greyscale";
      features.greyscaleColorTransform(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    sepiaColorTransform.addActionListener(evt -> {
      String destName = this.currentImage + "sepia";
      features.sepiaColorTransform(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    sharpen.addActionListener(evt -> {
      String destName = this.currentImage + "sharpen";
      features.sharpen(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    saveImageButton.addActionListener(evt -> {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "Images", "jpg", "png", "bmp", "ppm", "jpeg");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showSaveDialog(JFrameView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        features.saveImage(f.getAbsolutePath(), this.currentImage);
      }
    });
  }

  /**
   * This is a method used to display the histogram of the image that is currently being viewed by
   * the user of the application.
   *
   * @param image the buffered image
   */
  @Override
  public void drawHistogram(BufferedImage image) {
    int[] pixelData = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
        new int[image.getWidth() * image.getHeight()], 0, image.getWidth());
    double[] redHistogram = new double[256];
    double[] greenHistogram = new double[256];
    double[] blueHistogram = new double[256];
    double[] intensityHistogram = new double[256];
    int index = 0;
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Color color = new Color(pixelData[index++]);
        redHistogram[color.getRed()]++;
        greenHistogram[color.getGreen()]++;
        blueHistogram[color.getBlue()]++;
        intensityHistogram[(int) Math.round(
            ((double) color.getRed() + color.getGreen() + color.getBlue()) / 3)]++;
      }
    }

    DefaultXYDataset dataset = new DefaultXYDataset();
    dataset.addSeries("Red", getHistogramData(redHistogram));
    dataset.addSeries("Green", getHistogramData(greenHistogram));
    dataset.addSeries("Blue", getHistogramData(blueHistogram));
    dataset.addSeries("Intensity", getHistogramData(intensityHistogram));

    Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};

    JFreeChart chart = ChartFactory.createXYLineChart(
        "Histogram",
        "Pixel Values",
        "Frequency",
        dataset,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
    );

    XYPlot plot = (XYPlot) chart.getPlot();
    for (int i = 0; i < colors.length; i++) {
      plot.getRenderer().setSeriesPaint(i, colors[i]);
    }

    chart.setBackgroundPaint(Color.WHITE);

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(550, 360));

    histogramPanel.removeAll();
    histogramPanel.add(chartPanel);
    histogramPanel.revalidate();
    histogramPanel.repaint();
  }

  /**
   * This is a method used to display the image and its corresponding histogram on the screen.
   *
   * @param features the feature interface
   */
  private void loadImageOnScreen(Features features) {
    try {
      BufferedImage bufferedImage = features.getLoadedImage(this.currentImage);
      imageLabel.setIcon(new ImageIcon(bufferedImage));
      drawHistogram(bufferedImage);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * This is a method used to display the popup for the user to select the component to obtain the
   * greyscale image.
   *
   * @return the value component
   */
  private String getValueComponent() {
    JRadioButton redButton = new JRadioButton("red-component");
    JRadioButton greenButton = new JRadioButton("green-component");
    JRadioButton blueButton = new JRadioButton("blue-component");
    JRadioButton valueButton = new JRadioButton("value-component");
    JRadioButton intensityButton = new JRadioButton("intensity-component");
    JRadioButton lumaButton = new JRadioButton("luma-component");

    ButtonGroup group = new ButtonGroup();
    group.add(redButton);
    group.add(greenButton);
    group.add(blueButton);
    group.add(valueButton);
    group.add(intensityButton);
    group.add(lumaButton);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0, 1));
    panel.add(redButton);
    panel.add(greenButton);
    panel.add(blueButton);
    panel.add(valueButton);
    panel.add(intensityButton);
    panel.add(lumaButton);

    String component = "";
    int result = JOptionPane.showConfirmDialog(this, panel, "Select Component",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      JRadioButton selectedButton = null;
      for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements(); ) {
        AbstractButton button = buttons.nextElement();
        if (button.isSelected()) {
          selectedButton = (JRadioButton) button;
          break;
        }
      }
      if (selectedButton != null) {
        component = selectedButton.getText();
      }
    }
    return component;
  }

  /**
   * This is a method used to handle the popup for the rgb combine operation.
   *
   * @return the image names of the three images to be combined
   */
  private List<String> getRGBCombineImages() {
    JButton loadRed = new JButton("Select Red Scale Image");
    JLabel loadRedLabel = new JLabel("No File Selected");
    JButton loadGreen = new JButton("Select Green Scale Image");
    JLabel loadGreenLabel = new JLabel("No File Selected");
    JButton loadBlue = new JButton("Select Blue Scale Image");
    JLabel loadBlueLabel = new JLabel("No File Selected");

    List<String> images = new ArrayList<>();

    AtomicReference<String> redImagePath = new AtomicReference<>("");
    AtomicReference<String> greenImagePath = new AtomicReference<>("");
    AtomicReference<String> blueImagePath = new AtomicReference<>("");

    showFilePicker(loadRed, loadRedLabel, redImagePath);
    showFilePicker(loadGreen, loadGreenLabel, greenImagePath);
    showFilePicker(loadBlue, loadBlueLabel, blueImagePath);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0, 1));
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.add(loadRed);
    panel.add(loadRedLabel);
    panel.add(loadGreen);
    panel.add(loadGreenLabel);
    panel.add(loadBlue);
    panel.add(loadBlueLabel);

    int result = JOptionPane.showConfirmDialog(this, panel, "Select Images to Combine",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      if (redImagePath.toString().equals("") || greenImagePath.toString().equals("")
          || blueImagePath.toString().equals("")) {
        JOptionPane.showMessageDialog(this, "Please select all 3 images.");
      } else {
        images.add(redImagePath.toString());
        images.add(greenImagePath.toString());
        images.add(blueImagePath.toString());
        return images;
      }
    }
    return new ArrayList<>();
  }

  /**
   * This is a method used to handle the popup for the rgb split operation.
   *
   * @param red   the red component buffered image
   * @param green the green component buffered image
   * @param blue  the blue component buffered image
   * @return the path where the three split images needs to be saved at
   */
  private List<String> getRGBSplitImages(BufferedImage red, BufferedImage green,
      BufferedImage blue) {

    List<String> images = new ArrayList<>();
    JButton loadRed = new JButton("Save Red Scale Image");
    JLabel loadRedLabel = new JLabel("No Path Selected");
    JButton loadGreen = new JButton("Save Green Scale Image");
    JLabel loadGreenLabel = new JLabel("No Path Selected");
    JButton loadBlue = new JButton("Save Blue Scale Image");
    JLabel loadBlueLabel = new JLabel("No Path Selected");

    AtomicReference<String> redImagePath = new AtomicReference<>("");
    AtomicReference<String> greenImagePath = new AtomicReference<>("");
    AtomicReference<String> blueImagePath = new AtomicReference<>("");

    showSaveFilePicker(loadRed, loadRedLabel, redImagePath);
    showSaveFilePicker(loadGreen, loadGreenLabel, greenImagePath);
    showSaveFilePicker(loadBlue, loadBlueLabel, blueImagePath);

    JPanel imagePanelRed = new JPanel();
    imagePanelRed.setLayout(new BoxLayout(imagePanelRed, BoxLayout.PAGE_AXIS));
    JLabel imageLabelRed = new JLabel(new ImageIcon(red));
    JScrollPane imageScrollPaneRed = new JScrollPane(imageLabelRed);
    imageScrollPaneRed.setPreferredSize(
        new Dimension(300, 400));
    imagePanelRed.add(imageScrollPaneRed);
    imagePanelRed.add(loadRed);
    imagePanelRed.add(loadRedLabel);
    imagePanelRed.setBackground(Color.WHITE);
    imagePanelRed.setBorder(BorderFactory.createTitledBorder("Red Scale Image"));

    JPanel imagePanelGreen = new JPanel();
    imagePanelGreen.setLayout(new BoxLayout(imagePanelGreen, BoxLayout.PAGE_AXIS));
    JLabel imageLabelGreen = new JLabel(new ImageIcon(green));
    JScrollPane imageScrollPaneGreen = new JScrollPane(imageLabelGreen);
    imageScrollPaneGreen.setPreferredSize(
        new Dimension(300, 400));
    imagePanelGreen.add(imageScrollPaneGreen);
    imagePanelGreen.setBackground(Color.WHITE);
    imagePanelGreen.setBorder(BorderFactory.createTitledBorder("Green Scale Image"));
    imagePanelGreen.add(loadGreen);
    imagePanelGreen.add(loadGreenLabel);

    JPanel imagePanelBlue = new JPanel();
    imagePanelBlue.setLayout(new BoxLayout(imagePanelBlue, BoxLayout.PAGE_AXIS));
    JLabel imageLabelBlue = new JLabel(new ImageIcon(blue));
    JScrollPane imageScrollPaneBlue = new JScrollPane(imageLabelBlue);
    imageScrollPaneBlue.setPreferredSize(
        new Dimension(300, 400));
    imagePanelBlue.add(imageScrollPaneBlue);
    imagePanelBlue.setBackground(Color.WHITE);
    imagePanelBlue.setBorder(BorderFactory.createTitledBorder("Blue Scale Image"));
    imagePanelBlue.add(loadBlue);
    imagePanelBlue.add(loadBlueLabel);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0, 1));
    panel.setLayout(new BorderLayout());
    panel.add(imagePanelRed, BorderLayout.LINE_START);
    panel.add(imagePanelGreen, BorderLayout.CENTER);
    panel.add(imagePanelBlue, BorderLayout.LINE_END);

    int result = JOptionPane.showConfirmDialog(this, panel, "Save Split Images",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      if (redImagePath.toString().equals("") || greenImagePath.toString().equals("")
          || blueImagePath.toString().equals("")) {
        JOptionPane.showMessageDialog(this, "Please select path to save all 3 images.");
      } else {
        images.add(redImagePath.toString());
        images.add(greenImagePath.toString());
        images.add(blueImagePath.toString());
        return images;
      }
    }
    return new ArrayList<>();
  }

  /**
   * This is a method used to show the popup for the user to load the image on to the screen.
   *
   * @param load      the button upon clicking which the user can select the image and load it
   * @param loadLabel the label for the popup loader
   * @param imagePath the path selected by the user
   */
  private void showFilePicker(JButton load, JLabel loadLabel,
      AtomicReference<String> imagePath) {
    load.addActionListener(evt -> {
      final JFileChooser jFileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "Images", "jpg", "png", "bmp", "ppm", "jpeg");
      jFileChooser.setFileFilter(filter);
      int state = jFileChooser.showOpenDialog(JFrameView.this);
      if (state == JFileChooser.APPROVE_OPTION) {
        File f = jFileChooser.getSelectedFile();
        imagePath.set(f.getAbsolutePath());
        loadLabel.setText(f.getAbsolutePath());
      }
    });
  }

  /**
   * This is a method used to show the popup for the user to save the image displayed on the
   * screen.
   *
   * @param load      the button upon clicking which the user can save the image
   * @param loadLabel the label for the popup loader
   * @param imagePath the path selected by the user
   */
  private void showSaveFilePicker(JButton load, JLabel loadLabel,
      AtomicReference<String> imagePath) {
    load.addActionListener(evt -> {
      final JFileChooser jFileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "Images", "jpg", "png", "bmp", "ppm", "jpeg");
      jFileChooser.setFileFilter(filter);
      int state = jFileChooser.showSaveDialog(JFrameView.this);
      if (state == JFileChooser.APPROVE_OPTION) {
        File f = jFileChooser.getSelectedFile();
        imagePath.set(f.getAbsolutePath());
        String path = f.getAbsolutePath().replace("\\", "/");
        String[] splitPath = path.split("/");
        loadLabel.setText( ".../" + splitPath[splitPath.length - 1]);
      }
    });
  }

  /**
   * This is a helper method used to get the frequency histogram array data used to plot the
   * histogram.
   *
   * @param frequency the frequency array
   * @return the frequency histogram array data
   */
  private double[][] getHistogramData(double[] frequency) {
    double[][] histogramArray = new double[2][256];
    for (int i = 0; i < 256; i++) {
      histogramArray[0][i] = i;
      histogramArray[1][i] = frequency[i];
    }
    return histogramArray;
  }

  @Override
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE,
            new ImageIcon(getClass().getResource("error.png")));
  }
}
