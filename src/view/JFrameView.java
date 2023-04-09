package view;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFrameView extends JFrame implements IView {

  private JPanel histogramPanel;
  private JPanel commandsPanel;
  private JPanel imagePanel;
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

  public JFrameView(String caption) {
    super(caption);

    setSize(800, 600);
    //setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new BorderLayout());

    // Create the histogram panel
    histogramPanel = new JPanel();
    histogramPanel.setBackground(Color.WHITE);
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));

    // Create the commands panel
    commandsPanel = new JPanel(new GridLayout(5, 2));
    commandsPanel.setBackground(Color.WHITE);
    commandsPanel.setBorder(BorderFactory.createTitledBorder("Commands"));

    // Create the image panel
    imagePanel = new JPanel();
    imagePanel.setBackground(Color.WHITE);
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));

    //load image
    loadImageButton = new JButton("Load Image");
    loadImageButton.setActionCommand("Load Image");

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

  }
}
