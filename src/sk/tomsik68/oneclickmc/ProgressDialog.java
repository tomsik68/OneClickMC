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

    public ProgressDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(lbProgReport = new JLabel("                                       "), BorderLayout.NORTH);
        add(progressBar = new JProgressBar(), BorderLayout.CENTER);
        centerFrame(200,60);
    }

    private void centerFrame(int targetW, int targetH) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        setBounds(tk.getScreenSize().width / 2 - targetW / 2, tk.getScreenSize().height / 2 - targetH / 2, targetW, targetH);
    }

    public void setMessage(String message) {
        lbProgReport.setText(message);
    }

    @Override
    public void setProgress(int paramInt) {
        progressBar.setValue(paramInt);
    }

    @Override
    public void setMax(int paramInt) {
        progressBar.setMaximum(paramInt);
    }

    @Override
    public void incrementProgress(int paramInt) {
        setProgress(progressBar.getValue() + paramInt);
    }

    @Override
    public void finish() {
        setVisible(false);
    }

}
