package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ParameterDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField firstSpiderCountTField;
    private JTextField firstSpiderIpCountTField;
    private JTextField secondSpiderCountTField;
    private JTextField secondSpiderUrlCountTField;
    private JTextField resourceSpiderCountTField;
    private JTextField resourceSpiderUrlCountTField;
    private JTextField intervalField;
    private JTextArea keywordTArea;

    private static boolean isRight;

    private ParameterDialog() {

        setTitle("参数配置");
        setAlwaysOnTop(true);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here

        try {

            int fsc = Integer.valueOf(firstSpiderCountTField.getText());
            int fsic = Integer.valueOf(firstSpiderIpCountTField.getText());
            int ssc = Integer.valueOf(secondSpiderCountTField.getText());
            int ssuc = Integer.valueOf(secondSpiderUrlCountTField.getText());
            int rsc = Integer.valueOf(resourceSpiderCountTField.getText());
            int rsuc = Integer.valueOf(resourceSpiderUrlCountTField.getText());
            int st = Integer.valueOf(intervalField.getText());
            String[] keywordArray = keywordTArea.getText().split("\n");

            MainFrame mainFrame = (MainFrame) Frame.getFrames()[0];
            mainFrame.setFirstSpiderCount(fsc);
            mainFrame.setFirstSpiderIpCount(fsic);
            mainFrame.setSecondSpiderCount(ssc);
            mainFrame.setSecondSpiderUrlCount(ssuc);
            mainFrame.setResourceSpiderCount(rsc);
            mainFrame.setResourceSpiderUrlCount(rsuc);
            mainFrame.setSleepTime(st);
            mainFrame.setKeywordArray(keywordArray);

            isRight = true;

            dispose();
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"参数格式错误","错误",JOptionPane.ERROR_MESSAGE);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"未知错误",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * 调用参数弹框
     */
    public static boolean showDialog(){

        ParameterDialog dialog = new ParameterDialog();
        dialog.pack();
        dialog.setVisible(true);

        return isRight;
    }
}
