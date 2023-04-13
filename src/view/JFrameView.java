package view;

import static java.util.UUID.randomUUID;
import static view.helpers.Histogram.createHistogramChartPanel;
import static view.helpers.Histogram.getMaxValueToScale;

import controller.Features;
import service.imagefilesaver.SaveHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JFrameView extends JFrame implements IView {

  private final JPanel histogramPanel;
  private JPanel commandsPanel;
  private JPanel imagePanel;
  private JLabel imageLabel;
  private JScrollPane imageScrollPane;
  private JPanel leftPanel;
  private JPanel rightPanel;
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

  public JFrameView(String caption) {
    super(caption);

    setSize(800, 600);
    //setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());
    this.currentImage = null;

    // Create the histogram panel
    histogramPanel = new JPanel();
    histogramPanel.setBounds(20, 20, 300, 400);
    histogramPanel.setBackground(Color.WHITE);
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));

    // Create the commands panel
    commandsPanel = new JPanel(new GridLayout(5, 2));
    commandsPanel.setBackground(Color.WHITE);
    commandsPanel.setBorder(BorderFactory.createTitledBorder("Commands"));

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Create the image panel
    imagePanel = new JPanel();
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
    leftPanel = new JPanel(new GridLayout(2, 1));
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

    //pack();
    setVisible(true);
  }

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
        features.LoadImage(imagePath, currentImage);
        this.loadImageOnScreen(features);
      }
    });
    blur.addActionListener(evt -> {
      String destName = this.currentImage + "blur";
      features.BlurImage(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    brighten.addActionListener(evt -> {
      String destName = this.currentImage + "brighten";
      String input = JOptionPane.showInputDialog(this,
          "Enter a value to alter the brightness of the image:");
      try {
        int value = Integer.parseInt(input);
        features.Brighten(String.valueOf(value), this.currentImage, destName);
        this.currentImage = destName;
        this.loadImageOnScreen(features);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please enter an integer value.");
      }
    });
    dither.addActionListener(evt -> {
      String destName = this.currentImage + "dither";
      features.Dither(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    greyscale.addActionListener(evt -> {
      String destName = this.currentImage + "greyscale";
      String component = getValueComponent();
      if (component != null) {
        features.Greyscale(this.currentImage, destName, component);
        this.currentImage = destName;
        this.loadImageOnScreen(features);
      }
    });
    rgbCombine.addActionListener(evt -> {
      String destName = this.currentImage + "combine";
      List<String> component = getRGBCombineImages();
      if (component.size() == 3) {
        features.RGBCombine(destName, component);
        this.currentImage = destName;
        this.loadImageOnScreen(features);
      }
    });
    rgbSplit.addActionListener(evt -> {
      features.RGBSplit(currentImage, currentImage+"rsplit", currentImage+"gsplit",
              currentImage+"bsplit");
      BufferedImage red = features.GetLoadedImage(currentImage+"rsplit");
      BufferedImage green = features.GetLoadedImage(currentImage+"gsplit");
      BufferedImage blue = features.GetLoadedImage(currentImage+"bsplit");
      List<String> component = getRGBSplitImages(red, green, blue);
      if (component.size() == 3) {
        features.SaveImage(component.get(0), currentImage+"rsplit");
        features.SaveImage(component.get(1), currentImage+"gsplit");
        features.SaveImage(component.get(2), currentImage+"bsplit");
      }
    });
    horizontalFlip.addActionListener(evt -> {
      String destName = this.currentImage + "hf";
      features.HorizontalFlip(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    verticalFlip.addActionListener(evt -> {
      String destName = this.currentImage + "vf";
      features.VerticalFlip(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    greyscaleColorTransform.addActionListener(evt -> {
      String destName = this.currentImage + "greyscale";
      features.GreyscaleColorTransform(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    sepiaColorTransform.addActionListener(evt -> {
      String destName = this.currentImage + "sepia";
      features.Sepia(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    sharpen.addActionListener(evt -> {
      String destName = this.currentImage + "sharpen";
      features.Sharpen(this.currentImage, destName);
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
        features.SaveImage(f.getAbsolutePath(), this.currentImage);
      }
    });
  }

  @Override
  public void drawHistogram(BufferedImage image) {
    int[] pixelData = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
        new int[image.getWidth() * image.getHeight()], 0, image.getWidth());
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];
    int[] intensityHistogram = new int[256];
    int index = 0;
    for (int i = 0; i < image.getHeight(); i++ ) {
      for (int j = 0; j < image.getWidth(); j++) {
        Color color = new Color(pixelData[index++]);
        redHistogram[color.getRed()]++;
        greenHistogram[color.getGreen()]++;
        blueHistogram[color.getBlue()]++;
        intensityHistogram[(int) Math.round(
            ((double) color.getRed() + color.getGreen() + color.getBlue()) / 3)]++;
      }
    }

    int maxValue = getMaxValueToScale(redHistogram, greenHistogram, blueHistogram,
        intensityHistogram);

    JPanel chartPanel = createHistogramChartPanel(redHistogram, greenHistogram, blueHistogram,
        intensityHistogram, maxValue);

    chartPanel.setPreferredSize(new Dimension(550, 360));

    histogramPanel.removeAll();
    histogramPanel.add(chartPanel);
    histogramPanel.revalidate();
    histogramPanel.repaint();
  }

  private void loadImageOnScreen(Features features) {
    try {
      BufferedImage bufferedImage = features.GetLoadedImage(this.currentImage);
      imageLabel.setIcon(new ImageIcon(bufferedImage));
      drawHistogram(bufferedImage);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

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

  private List<String> getRGBCombineImages() {
    JButton load1 = new JButton("Select Red Scale Image");
    JLabel load1Label = new JLabel("No File Selected");
    JButton load2 = new JButton("Select Green Scale Image");
    JLabel load2Label = new JLabel("No File Selected");
    JButton load3 = new JButton("Select Blue Scale Image");
    JLabel load3Label = new JLabel("No File Selected");

    List<String> images =new ArrayList<String>();

    AtomicReference<String> redImagePath = new AtomicReference<>("");
    AtomicReference<String> greenImagePath = new AtomicReference<>("");
    AtomicReference<String> blueImagePath = new AtomicReference<>("");

    showFilePicker(load1, load1Label, redImagePath);
    showFilePicker(load2, load2Label, greenImagePath);
    showFilePicker(load3, load3Label, blueImagePath);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0, 1));
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.add(load1);
    panel.add(load1Label);
    panel.add(load2);
    panel.add(load2Label);
    panel.add(load3);
    panel.add(load3Label);

    int result = JOptionPane.showConfirmDialog(this, panel, "Select Images to Combine",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      if(redImagePath.toString().equals("") || greenImagePath.toString().equals("")
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

  private List<String> getRGBSplitImages(BufferedImage red, BufferedImage green,
                                         BufferedImage blue) {

    List<String> images =new ArrayList<String>();
    JButton load1 = new JButton("Save Red Scale Image");
    JLabel load1Label = new JLabel("No Path Selected");
    JButton load2 = new JButton("Save Green Scale Image");
    JLabel load2Label = new JLabel("No Path Selected");
    JButton load3 = new JButton("Save Blue Scale Image");
    JLabel load3Label = new JLabel("No Path Selected");

    AtomicReference<String> redImagePath = new AtomicReference<>("");
    AtomicReference<String> greenImagePath = new AtomicReference<>("");
    AtomicReference<String> blueImagePath = new AtomicReference<>("");

    showSaveFilePicker(load1, load1Label, redImagePath);
    showSaveFilePicker(load2, load2Label, greenImagePath);
    showSaveFilePicker(load3, load3Label, blueImagePath);

    JPanel imagePanel1 = new JPanel();
    imagePanel1.setLayout(new BoxLayout(imagePanel1, BoxLayout.PAGE_AXIS));
    JLabel imageLabel1 = new JLabel(new ImageIcon(red));
    JScrollPane imageScrollPane1 = new JScrollPane(imageLabel1);
    imageScrollPane1.setPreferredSize(
            new Dimension(300, 400));
    imagePanel1.add(imageScrollPane1);
    imagePanel1.add(load1);
    imagePanel1.add(load1Label);
    imagePanel1.setBackground(Color.WHITE);
    imagePanel1.setBorder(BorderFactory.createTitledBorder("Red Scale Image"));

    JPanel imagePanel2 = new JPanel();
    imagePanel2.setLayout(new BoxLayout(imagePanel2, BoxLayout.PAGE_AXIS));
    JLabel imageLabel2 = new JLabel(new ImageIcon(green));
    JScrollPane imageScrollPane2 = new JScrollPane(imageLabel2);
    imageScrollPane2.setPreferredSize(
            new Dimension(300, 400));
    imagePanel2.add(imageScrollPane2);
    imagePanel2.setBackground(Color.WHITE);
    imagePanel2.setBorder(BorderFactory.createTitledBorder("Green Scale Image"));
    imagePanel2.add(load2);
    imagePanel2.add(load2Label);

    JPanel imagePanel3 = new JPanel();
    imagePanel3.setLayout(new BoxLayout(imagePanel3, BoxLayout.PAGE_AXIS));
    JLabel imageLabel3 = new JLabel(new ImageIcon(blue));
    JScrollPane imageScrollPane3 = new JScrollPane(imageLabel3);
    imageScrollPane3.setPreferredSize(
            new Dimension(300, 400));
    imagePanel3.add(imageScrollPane3);
    imagePanel3.setBackground(Color.WHITE);
    imagePanel3.setBorder(BorderFactory.createTitledBorder("Blue Scale Image"));
    imagePanel3.add(load3);
    imagePanel3.add(load3Label);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0, 1));
    panel.setLayout(new BorderLayout());
    panel.add(imagePanel1, BorderLayout.LINE_START);
    panel.add(imagePanel2, BorderLayout.CENTER);
    panel.add(imagePanel3, BorderLayout.LINE_END);

    int result = JOptionPane.showConfirmDialog(this, panel, "Save Split Images",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      if(redImagePath.toString().equals("") || greenImagePath.toString().equals("")
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

  private void showFilePicker(JButton load1, JLabel load1Label, AtomicReference<String> redImagePath) {
    load1.addActionListener(evt -> {
      final JFileChooser jFileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Images", "jpg", "png", "bmp", "ppm", "jpeg");
      jFileChooser.setFileFilter(filter);
      int state = jFileChooser.showOpenDialog(JFrameView.this);
      if (state == JFileChooser.APPROVE_OPTION) {
        File f = jFileChooser.getSelectedFile();
        redImagePath.set(f.getAbsolutePath());
        load1Label.setText(f.getAbsolutePath());
      }
    });
  }

  private void showSaveFilePicker(JButton load1, JLabel load1Label, AtomicReference<String> redImagePath) {
    load1.addActionListener(evt -> {
      final JFileChooser jFileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Images", "jpg", "png", "bmp", "ppm", "jpeg");
      jFileChooser.setFileFilter(filter);
      int state = jFileChooser.showSaveDialog(JFrameView.this);
      if (state == JFileChooser.APPROVE_OPTION) {
        File f = jFileChooser.getSelectedFile();
        redImagePath.set(f.getAbsolutePath());
        String[] splitPath = f.getAbsolutePath().split("/");
        load1Label.setText( ".../" + splitPath[splitPath.length - 1]);
      }
    });
  }
}
