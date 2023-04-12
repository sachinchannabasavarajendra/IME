package view.helpers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Histogram {

  public static int getMaxValueToScale(int[] redHistogram, int[] greenHistogram,
      int[] blueHistogram,
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

  public static JPanel createHistogramChartPanel(int[] redHistogram, int[] greenHistogram,
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
}
