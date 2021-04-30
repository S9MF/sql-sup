package burp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ConfigDlg extends JDialog {
    //定义组件
    private final JPanel mainPanel = new JPanel();
    private final JPanel toPanel = new JPanel();
    private final JPanel centerPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();;
    private final JSpinner spNum = new JSpinner(new SpinnerNumberModel(1,1,200,1));
    private final JSpinner spKey = new  JSpinner(new SpinnerNumberModel(1,1,200,1));
    private final JSpinner spValue = new  JSpinner(new SpinnerNumberModel(1,1,200,1));
    private final JSpinner spNumber = new  JSpinner(new SpinnerNumberModel(1,1,600,1));
    private final JSpinner spFuzzNumber = new  JSpinner(new SpinnerNumberModel(1,1,20,1));
    private final JLabel kbText = new JLabel("byte字节");
    private final JLabel filePathText = new JLabel();
    private final JButton btCancel = new JButton("Cancel");
    private final JButton btSave = new JButton("Save");
    private final JButton bCalc = new JButton("Calc");
    private final JButton bSelect = new JButton("浏览");

    public ConfigDlg() {
        initGUI();
        initEvent();
        initValue();
        this.setTitle("SQLSup Config");
    }
    //初始化组件
    private void initGUI() {
        toPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        toPanel.add(new JLabel("参数个数："));
        toPanel.add(spNum);
        toPanel.add(new JLabel("(1-200)"));
        toPanel.add(new JLabel(" key value："));
        toPanel.add(spKey);
        toPanel.add(new JLabel("-"));
        toPanel.add(spValue);
        toPanel.add(new JLabel("(1-200)"));
        toPanel.add(new JLabel(" number："));
        toPanel.add(spNumber);
        toPanel.add(new JLabel("(1-600)"));
        toPanel.add(kbText);

        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        centerPanel.add(new JLabel("Fuzz个数："));
        centerPanel.add(spFuzzNumber);
        centerPanel.add(new JLabel("(1-20)"));
        centerPanel.add(bSelect);
        centerPanel.add(filePathText);


        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btSave);
        bottomPanel.add(bCalc);
        bottomPanel.add(btCancel);
        btSave.setToolTipText("Save(保存)配置");
        bCalc.setToolTipText("先Save(保存)，再Calc(计算)");
        btCancel.setToolTipText("Cancel(取消)");

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(toPanel,BorderLayout.NORTH);
        mainPanel.add(centerPanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        this.setModal(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.add(mainPanel);
        //使配置窗口自动适应控件大小，防止部分控件无法显示
        this.pack();
        //居中显示配置窗口
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(screensize.width/2-this.getWidth()/2,screensize.height/2-this.getHeight()/2,this.getWidth(),this.getHeight());
    }
    //组件的事件响应
    private void initEvent() {
        //取消按钮
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigDlg.this.dispose();
            }
        });
        //保存按钮
        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer params_len = (Integer)spNum.getValue();
                Integer key_len = (Integer)spKey.getValue();
                Integer value_len = (Integer)spValue.getValue();
                Integer number_len = (Integer)spNumber.getValue();
                Integer fuzz_number = (Integer) spFuzzNumber.getValue();

                Config.setParams_len(params_len);
                Config.setKey_len(key_len);
                Config.setValue_len(value_len);
                Config.setNumber_len(number_len);
                Config.setFuzz_number(fuzz_number);

            }
        });
        //显示kb 先Save然后Calc
        bCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byte[] bytes = Util.getRandomString(Config.getKey_len(), Config.getValue_len(), Config.getNumber_len()).getBytes();
                int kbNum = bytes.length;
                String result = kbNum +"";
                kbText.setText("（" + result+ "byte）");
            }
        });
        //选择按钮
        bSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("文本文件(*.txt)", "txt"));
                int result = fileChooser.showOpenDialog(null);
                if (result == fileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    Config.setFilePath(filePath);
                }
            }
        });
    }
    //为控件赋值
    public void initValue() {
        spNum.setValue(Config.getParams_len());
        spKey.setValue(Config.getKey_len());
        spValue.setValue(Config.getValue_len());
        spNumber.setValue(Config.getNumber_len());
        spFuzzNumber.setValue(Config.getFuzz_number());
        filePathText.setText(Config.getFilePath());
    }
}
















