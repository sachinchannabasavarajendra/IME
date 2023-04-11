package view;

import controller.Features;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.IMEModel;
import model.IPixel;

import static java.util.UUID.randomUUID;

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
    saveImageButton.setActionCommand("Save Image");

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
    rgbCombine.setActionCommand("RGB Combine");
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
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "Images", "jpg", "png", "bmp", "ppm", "jpeg");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(JFrameView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
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
      features.Brighten("10", this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    dither.addActionListener(evt -> {
      String destName = this.currentImage + "dither";
      features.Dither(this.currentImage, destName);
      this.currentImage = destName;
      this.loadImageOnScreen(features);
    });
    greyscale.addActionListener(evt -> {
      String destName = this.currentImage + "greyscale";
      features.GreyScale(this.currentImage, destName, "luma-component");
      this.currentImage = destName;
      this.loadImageOnScreen(features);
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
      features.GreyScale(this.currentImage, destName, "luma-component");
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
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Color color = new Color(pixelData[index++]);
        redHistogram[color.getRed()]++;
        greenHistogram[color.getGreen()]++;
        blueHistogram[color.getBlue()]++;
        intensityHistogram[(int) (0.299 * color.getRed() + 0.587 * color.getGreen()
            + 0.114 * color.getBlue())]++;
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

  private int getMaxValueToScale(int[] redHistogram, int[] greenHistogram, int[] blueHistogram,
      int[] intensityHistogram) {
    int maxValue = 0;
    for (int i = 0; i < 255; i++) {
      if (redHistogram[i] > maxValue) {
        maxValue = redHistogram[i];
      }
      if (greenHistogram[i] > maxValue) {
        maxValue = greenHistogram[i];
      }
      if (blueHistogram[i] > maxValue) {
        maxValue = blueHistogram[i];
      }
      if (intensityHistogram[i] > maxValue) {
        maxValue = intensityHistogram[i];
      }
    }
    return maxValue;
  }

  private JPanel createHistogramChartPanel(int[] redHistogram, int[] greenHistogram,
      int[] blueHistogram, int[] intensityHistogram, int maxValue) {
    JPanel chartPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(50, getHeight() - 50, getWidth() - 20, getHeight() - 50);
        g.drawLine(50, getHeight() - 50, 50, 30);

        g.setColor(Color.BLACK);
        for (int i = 0; i <= 256; i += 32) {
          int x = (int) ((double) i / 256 * (getWidth() - 70)) + 50;
          g.drawLine(x, getHeight() - 50, x, getHeight() - 45);
          g.drawString(Integer.toString(i), x - 10, getHeight() - 30);
        }

        g.drawString("Pixel Values", getWidth() / 2 - 20, getHeight() - 10);

        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(-Math.PI / 2);
        g2.drawString("Frequency", -getHeight() / 2 - 33, 12);
        g2.rotate(Math.PI / 2);

        g.setColor(Color.BLACK);
        for (int i = 0; i <= maxValue; i += maxValue / 3) {
          int y = getHeight() - (int) ((double) i / maxValue * (getHeight() - 110)) - 50;
          g.drawLine(47, y, 53, y);
          g.drawString(Integer.toString(i), 7, y + 5);
        }

        // Draw the red histogram
        g.setColor(Color.RED);
        for (int i = 0; i < 255; i++) {
          int x1 = (int) ((double) i / 256 * (getWidth() - 70)) + 50;
          int y1 =
              getHeight() - (int) ((double) redHistogram[i] / maxValue * (getHeight() - 110))
                  - 50;
          int x2 = (int) ((double) (i + 1) / 256 * (getWidth() - 70)) + 50;
          int y2 =
              getHeight() - (int) ((double) redHistogram[i + 1] / maxValue * (getHeight() - 110))
                  - 50;
          g.drawLine(x1, y1, x2, y2);
        }

        // Draw the green histogram
        g.setColor(Color.GREEN);
        for (int i = 0; i < 255; i++) {
          int x1 = (int) ((double) i / 256 * (getWidth() - 70)) + 50;
          int y1 =
              getHeight() - (int) ((double) greenHistogram[i] / maxValue * (getHeight() - 110))
                  - 50;
          int x2 = (int) ((double) (i + 1) / 256 * (getWidth() - 70)) + 50;
          int y2 =
              getHeight() - (int) ((double) greenHistogram[i + 1] / maxValue * (getHeight() - 110))
                  - 50;
          g.drawLine(x1, y1, x2, y2);
        }

        // Draw the blue histogram
        g.setColor(Color.BLUE);
        for (int i = 0; i < 255; i++) {
          int x1 = (int) ((double) i / 256 * (getWidth() - 70)) + 50;
          int y1 =
              getHeight() - (int) ((double) blueHistogram[i] / maxValue * (getHeight() - 110))
                  - 50;
          int x2 = (int) ((double) (i + 1) / 256 * (getWidth() - 70)) + 50;
          int y2 =
              getHeight() - (int) ((double) blueHistogram[i + 1] / maxValue * (getHeight() - 110))
                  - 50;
          g.drawLine(x1, y1, x2, y2);
        }

        // Draw the intensity histogram
        g.setColor(Color.BLACK);
        for (int i = 0; i < 255; i++) {
          int x1 = (int) ((double) i / 256 * (getWidth() - 70)) + 50;
          int y1 =
              getHeight() - (int) ((double) intensityHistogram[i] / maxValue * (getHeight() - 110))
                  - 50;
          int x2 = (int) ((double) (i + 1) / 256 * (getWidth() - 70)) + 50;
          int y2 =
              getHeight() - (int) ((double) intensityHistogram[i + 1] / maxValue * (getHeight()
                  - 110))
                  - 50;
          g.drawLine(x1, y1, x2, y2);
        }
      }
    };
    return chartPanel;
  }

  private void loadImageOnScreen(Features features) {
    String tempPath = "src/view/temp/" + currentImage + ".png";
    features.SaveImage(tempPath, currentImage);
    try {
      File tempFile = new File(tempPath);
      BufferedImage bufferedImage = ImageIO.read(tempFile);
      imageLabel.setIcon(new ImageIcon(bufferedImage));
      tempFile.delete();
      drawHistogram(bufferedImage);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
