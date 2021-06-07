package main;

import main.algorithm.DSA;
import main.algorithm.Pair;
import main.algorithm.Session;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;

public class ApplicationDSA extends JFrame {

    public JTextField signTextFieldMessage, verifyTextFieldMessage, signKeyQTextField, signKeyPTextField,
            signKeyGTextField, signTextFieldPrivateX, signTextFieldPublicY, signTextFieldSignatureR, signTextFieldSignatureS,
            verifyKeyQTextField, verifyKeyGTextField, verifyKeyPTextField, verifyTextFieldPublicY, verifyTextFieldSignatureR, verifyTextFieldSignatureS;
    public JButton signButton;
    public Font appFont = new Font("Verdana", Font.PLAIN, 10);

    public ApplicationDSA() {

        super("Digital signature algorithm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(appFont);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        content.add(buttons, BorderLayout.NORTH);

        JPanel signTab = new JPanel();
        JPanel verifyTab = new JPanel();
        tabbedPane.addTab("Sign", signTab);
        tabbedPane.addTab("Verify", verifyTab);

        content.add(tabbedPane, BorderLayout.CENTER);

        signTab.setLayout(null);
        verifyTab.setLayout(null);

        final JLabel signMessageLabel = new JLabel("Message to sign");
        JLabel verifyMessageLabel = new JLabel("Message to verify");

        signTextFieldMessage = new JTextField(60);
        verifyTextFieldMessage = new JTextField(60);

        signButton = new JButton("Generate keys");
        signButton.addActionListener(e -> {
            signButton.setEnabled(false);
            signButton.setText("Please wait...");
            new Thread(() -> {
                Session session = Session.getInstance();
                Pair<BigInteger, BigInteger> privateKeys = session.getPrivateKey();
                signKeyGTextField.setText(session.getGlobalKeyG().toString());
                signKeyPTextField.setText(session.getGlobalKeyP().toString());
                signKeyQTextField.setText(session.getGlobalKeyQ().toString());
                signTextFieldPrivateX.setText(privateKeys.getFirst().toString());
                signTextFieldPublicY.setText(privateKeys.getSecond().toString());
                if (signTextFieldMessage.getText().length() != 0) {
                    Pair<BigInteger, BigInteger> signature = DSA.sign(signTextFieldMessage.getText(), session.getGlobalKeyG(),
                            session.getGlobalKeyP(), session.getGlobalKeyQ(), privateKeys.getFirst());
                    signTextFieldSignatureR.setText(signature.getFirst().toString());
                    signTextFieldSignatureS.setText(signature.getSecond().toString());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "To create signature keys message required!",
                            "Empty message",
                            JOptionPane.ERROR_MESSAGE);
                }
                signButton.setEnabled(true);
                signButton.setText("Generate keys");
            }).start();
        });
        JButton verifyButton = new JButton("Check signature");
        verifyButton.addActionListener(e -> {
            if (verifyKeyGTextField.getText().length() == 0 ||
                    verifyKeyPTextField.getText().length() == 0 ||
                    verifyKeyQTextField.getText().length() == 0 ||
                    verifyTextFieldMessage.getText().length() == 0 ||
                    verifyTextFieldPublicY.getText().length() == 0 ||
                    verifyTextFieldSignatureR.getText().length() == 0 ||
                    verifyTextFieldSignatureS.getText().length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "All fields required!",
                        "Fill fields",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                BigInteger r, s, g, p, q, y;
                boolean res = false;
                try {
                    r = new BigInteger(verifyTextFieldSignatureR.getText(), 10);
                    s = new BigInteger(verifyTextFieldSignatureS.getText(), 10);
                    g = new BigInteger(verifyKeyGTextField.getText(), 10);
                    p = new BigInteger(verifyKeyPTextField.getText(), 10);
                    q = new BigInteger(verifyKeyQTextField.getText(), 10);
                    y = new BigInteger(verifyTextFieldPublicY.getText(), 10);
                    res = DSA.verify(verifyTextFieldMessage.getText(), r, s, g, p, q, y);
                } catch (Exception event) {
                    JOptionPane.showMessageDialog(null,
                            event.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (res) {
                    JOptionPane.showMessageDialog(null,
                            "Approved!",
                            "Success!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Failed!",
                            "Error!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JLabel signLabelKeyQ = new JLabel("Global key Q");
        JLabel signLabelKeyP = new JLabel("Global key P");
        JLabel signLabelKeyG = new JLabel("Global key G");
        JLabel signLabelPrivateX = new JLabel("Private key X");
        JLabel signLabelPrivateY = new JLabel("Public key Y");
        JLabel signLabelSignatureR = new JLabel("Signature key R");
        JLabel signLabelSignatureS = new JLabel("Signature key S");

        JLabel verifyLabelKeyQ = new JLabel("Global key Q");
        JLabel verifyLabelKeyP = new JLabel("Global key P");
        JLabel verifyLabelKeyG = new JLabel("Global key G");
        JLabel verifyLabelPrivateY = new JLabel("Public key Y");
        JLabel verifyLabelSignatureR = new JLabel("Signature key R");
        JLabel verifyLabelSignatureS = new JLabel("Signature key S");

        signKeyQTextField = new JTextField(100);
        signKeyPTextField = new JTextField(100);
        signKeyGTextField = new JTextField(100);
        signTextFieldPrivateX = new JTextField(100);
        signTextFieldPublicY = new JTextField(100);
        signTextFieldSignatureR = new JTextField(100);
        signTextFieldSignatureS = new JTextField(100);

        verifyKeyQTextField = new JTextField(100);
        verifyKeyPTextField = new JTextField(100);
        verifyKeyGTextField = new JTextField(100);
        verifyTextFieldPublicY = new JTextField(100);
        verifyTextFieldSignatureR = new JTextField(100);
        verifyTextFieldSignatureS = new JTextField(100);

        signTab.add(signMessageLabel);
        verifyTab.add(verifyMessageLabel);

        signTab.add(signTextFieldMessage);
        verifyTab.add(verifyTextFieldMessage);

        signTab.add(signButton);
        verifyTab.add(verifyButton);

        // Sing Labels
        signTab.add(signLabelKeyG);
        signTab.add(signLabelKeyP);
        signTab.add(signLabelKeyQ);
        signTab.add(signLabelPrivateX);
        signTab.add(signLabelPrivateY);
        signTab.add(signLabelSignatureR);
        signTab.add(signLabelSignatureS);

        // Sign TextFields
        signTab.add(signKeyGTextField);
        signTab.add(signKeyQTextField);
        signTab.add(signKeyPTextField);
        signTab.add(signTextFieldPrivateX);
        signTab.add(signTextFieldPublicY);
        signTab.add(signTextFieldSignatureR);
        signTab.add(signTextFieldSignatureS);

        // Verify labels
        verifyTab.add(verifyLabelKeyG);
        verifyTab.add(verifyLabelKeyP);
        verifyTab.add(verifyLabelKeyQ);
        verifyTab.add(verifyLabelPrivateY);
        verifyTab.add(verifyLabelSignatureR);
        verifyTab.add(verifyLabelSignatureS);

        // Verify TextFields
        verifyTab.add(verifyKeyGTextField);
        verifyTab.add(verifyKeyQTextField);
        verifyTab.add(verifyKeyPTextField);
        verifyTab.add(verifyTextFieldPublicY);
        verifyTab.add(verifyTextFieldSignatureR);
        verifyTab.add(verifyTextFieldSignatureS);

        Insets insetsSign = signTab.getInsets();
        Insets insetsVerify = verifyTab.getInsets();

        Dimension size = signMessageLabel.getPreferredSize();
        signMessageLabel.setBounds(20 + insetsSign.left, 20 + insetsSign.top, size.width, size.height);

        size = verifyMessageLabel.getPreferredSize();
        verifyMessageLabel.setBounds(20 + insetsVerify.left, 20 + insetsVerify.top, size.width, size.height);

        size = signTextFieldMessage.getPreferredSize();
        signTextFieldMessage.setBounds(120 + insetsSign.left, 20 + insetsSign.top, size.width, size.height + 5);

        size = verifyTextFieldMessage.getPreferredSize();
        verifyTextFieldMessage.setBounds(125 + insetsVerify.left, 20 + insetsVerify.top, size.width, size.height + 5);

        size = signButton.getPreferredSize();
        signButton.setBounds(800 + insetsSign.left, 20 + insetsSign.top, size.width, size.height);

        size = verifyButton.getPreferredSize();
        verifyButton.setBounds(800 + insetsVerify.left, 20 + insetsVerify.top, size.width, size.height);

        size = signLabelKeyQ.getPreferredSize();
        signLabelKeyQ.setBounds(20 + insetsSign.left, 70 + insetsSign.top, size.width, size.height);

        size = signLabelKeyP.getPreferredSize();
        signLabelKeyP.setBounds(20 + insetsSign.left, 120 + insetsSign.top, size.width, size.height);

        size = signLabelKeyQ.getPreferredSize();
        signLabelKeyG.setBounds(20 + insetsSign.left, 170 + insetsSign.top, size.width, size.height);

        size = verifyLabelKeyQ.getPreferredSize();
        verifyLabelKeyQ.setBounds(20 + insetsVerify.left, 70 + insetsVerify.top, size.width, size.height);

        size = verifyLabelKeyP.getPreferredSize();
        verifyLabelKeyP.setBounds(20 + insetsVerify.left, 120 + insetsVerify.top, size.width, size.height);

        size = verifyLabelKeyQ.getPreferredSize();
        verifyLabelKeyG.setBounds(20 + insetsVerify.left, 170 + insetsVerify.top, size.width, size.height);


        size = signLabelPrivateX.getPreferredSize();
        signLabelPrivateX.setBounds(20 + insetsSign.left, 220 + insetsSign.top, size.width, size.height);

        size = signLabelPrivateY.getPreferredSize();
        signLabelPrivateY.setBounds(20 + insetsSign.left, 270 + insetsSign.top, size.width, size.height);

        size = signLabelSignatureR.getPreferredSize();
        signLabelSignatureR.setBounds(20 + insetsSign.left, 320 + insetsSign.top, size.width, size.height);

        size = signLabelSignatureS.getPreferredSize();
        signLabelSignatureS.setBounds(20 + insetsSign.left, 370 + insetsSign.top, size.width, size.height);


        size = verifyLabelPrivateY.getPreferredSize();
        verifyLabelPrivateY.setBounds(20 + insetsVerify.left, 220 + insetsVerify.top, size.width, size.height);

        size = verifyLabelSignatureR.getPreferredSize();
        verifyLabelSignatureR.setBounds(20 + insetsVerify.left, 270 + insetsVerify.top, size.width, size.height);

        size = verifyLabelSignatureS.getPreferredSize();
        verifyLabelSignatureS.setBounds(20 + insetsVerify.left, 320 + insetsVerify.top, size.width, size.height);



        size = signKeyQTextField.getPreferredSize();
        signKeyQTextField.setBounds(115 + insetsSign.left, 70 + insetsSign.top, size.width, size.height);

        size = signKeyPTextField.getPreferredSize();
        signKeyPTextField.setBounds(115 + insetsSign.left, 120 + insetsSign.top, size.width, size.height);

        size = signKeyQTextField.getPreferredSize();
        signKeyGTextField.setBounds(115 + insetsSign.left, 170 + insetsSign.top, size.width, size.height);

        size = signTextFieldPrivateX.getPreferredSize();
        signTextFieldPrivateX.setBounds(115 + insetsSign.left, 220 + insetsSign.top, size.width, size.height);

        size = signTextFieldPublicY.getPreferredSize();
        signTextFieldPublicY.setBounds(115 + insetsSign.left, 270 + insetsSign.top, size.width, size.height);

        size = signTextFieldSignatureR.getPreferredSize();
        signTextFieldSignatureR.setBounds(115 + insetsSign.left, 320 + insetsSign.top, size.width, size.height);

        size = signTextFieldSignatureS.getPreferredSize();
        signTextFieldSignatureS.setBounds(115 + insetsSign.left, 370 + insetsSign.top, size.width, size.height);

        size = verifyKeyQTextField.getPreferredSize();
        verifyKeyQTextField.setBounds(115 + insetsVerify.left, 70 + insetsVerify.top, size.width, size.height);

        size = verifyKeyPTextField.getPreferredSize();
        verifyKeyPTextField.setBounds(115 + insetsVerify.left, 120 + insetsVerify.top, size.width, size.height);

        size = verifyKeyQTextField.getPreferredSize();
        verifyKeyGTextField.setBounds(115 + insetsVerify.left, 170 + insetsVerify.top, size.width, size.height);

        size = verifyTextFieldPublicY.getPreferredSize();
        verifyTextFieldPublicY.setBounds(115 + insetsVerify.left, 220 + insetsVerify.top, size.width, size.height);

        size = verifyTextFieldSignatureR.getPreferredSize();
        verifyTextFieldSignatureR.setBounds(115 + insetsVerify.left, 270 + insetsVerify.top, size.width, size.height);

        size = verifyTextFieldSignatureS.getPreferredSize();
        verifyTextFieldSignatureS.setBounds(115 + insetsVerify.left, 320 + insetsVerify.top, size.width, size.height);

        getContentPane().add(content);
        setResizable(false);
        setPreferredSize(new Dimension(1250, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            new ApplicationDSA();
        });
    }
}