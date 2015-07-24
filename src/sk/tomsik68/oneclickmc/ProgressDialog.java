package sk.tomsik68.oneclickmc;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import sk.tomsik68.mclauncher.api.ui.IProgressMonitor;

public class ProgressDialog extends JDialog implements IProgressMonitor {
    private static final long serialVersionUID = 1L;
    private final JProgressBar progressBar;
    private final JLabel lbProgReport;
    private int max = 100;

    public ProgressDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(lbProgReport = new JLabel("                                       "), BorderLayout.NORTH);
        add(progressBar = new JProgressBar(), BorderLayout.CENTER);
        progressBar.setIndeterminate(true);
        centerFrame(200, 60);
    }

    private void centerFrame(int targetW, int targetH) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        setBounds(tk.getScreenSize().width / 2 - targetW / 2, tk.getScreenSize().height / 2 - targetH / 2, targetW, targetH);
    }

    public void setMessage(String message) {
        lbProgReport.setText(message);
        repaint();
    }

    @Override
    public void setProgress(int i) {
        progressBar.setValue((int) ((1.0 * i / max) * 100.0));
    }

    @Override
    public void setMax(int i) {
        max = i;
    }

    @Override
    public void incrementProgress(int i) {
        setProgress(progressBar.getValue() + ((int) ((1.0 * i / max) * 100)));
    }

    @Override
    public void setStatus(String s) {
        lbProgReport.setText(s);
    }
}
