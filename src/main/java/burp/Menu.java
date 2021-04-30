package burp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Menu implements IContextMenuFactory {
    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        List<JMenuItem> menuList = new ArrayList<>();

        JMenu sqlSupMenu  = new JMenu("sql-sup(辅助)");
        JMenuItem paramoVerflowMenu  = new JMenuItem("参数溢出");
        JMenuItem garbageDataMenu  = new JMenuItem("垃圾数据");
        JMenuItem config = new JMenuItem("设置");

        sqlSupMenu.add(paramoVerflowMenu);
        sqlSupMenu.add(garbageDataMenu);
        sqlSupMenu.add(config);

        paramoVerflowMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IHttpRequestResponse iReqResp = invocation.getSelectedMessages()[0];
                byte[] request = new byte[0];
                try {
                    request = Util.paramoVerflowAction(iReqResp, Util.getSelect(invocation), Config.getParams_len());
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                if (iReqResp != null) {
                    iReqResp.setRequest(request);
                }
            }
        });

        garbageDataMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IHttpRequestResponse iReqResp = invocation.getSelectedMessages()[0];
                byte[] request = new byte[0];
                try {
                    request = Util.garbageDataAction(iReqResp, Util.getSelect(invocation));
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                if (iReqResp != null) {
                    iReqResp.setRequest(request);
                }
            }
        });

        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigDlg dlg = new ConfigDlg();
                BurpExtender.callbacks.customizeUiComponent(dlg);
                dlg.setVisible(true);
            }
        });

        menuList.add(sqlSupMenu);
        return menuList;
    }
}






















