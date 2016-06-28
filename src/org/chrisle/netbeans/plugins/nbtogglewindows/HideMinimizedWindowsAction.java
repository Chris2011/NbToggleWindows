package org.chrisle.netbeans.plugins.nbtogglewindows;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author chrl
 */
@ActionID(
    category = "Window",
    id = "org.chrisle.netbeans.plugins.nbhidewindows.NbToggleWindowsAction"
)
@ActionRegistration(
    displayName = "#CTL_HideClosedWindowsAction"
)
@ActionReference(path = "Shortcuts", name = "OS-H OS-M")
@NbBundle.Messages("CTL_HideClosedWindowsAction=Hide closed Windows")
public class HideMinimizedWindowsAction implements ActionListener {

    Collection<TopComponent> _displayedComps = new HashSet<>();

    @Override
    public void actionPerformed(ActionEvent e) {
        getMinimizedWindows();

        if(_displayedComps != null) {
            _displayedComps.forEach(new Consumer<TopComponent>() {
                @Override
                public void accept(TopComponent t) {
                    if (t.isDisplayable()) {
                        t.close();
                    } else {
                        t.open();
                    }
                }
            });
        }
    }

    private void getMinimizedWindows() {
        final WindowManager wm = WindowManager.getDefault();

        for (Mode mode : wm.getModes()) {
            if (!wm.isEditorMode(mode)) {
//                Arrays.asList(wm.getMainWindow().getComponents()).forEach(new Consumer<Component>() {
//                    @Override
//                    public void accept(Component t) {
//                        JOptionPane.showMessageDialog(null, t);
////                        wm.isTopComponentMinimized((TopComponent)t);
//                    }
//                });
                
//                Arrays.asList(wm.getOpenedTopComponents(mode)).forEach(new Consumer<TopComponent>() {
//                    @Override
//                    public void accept(TopComponent t) {
//                        if (!t.isDisplayable() && !_displayedComps.contains(t)) {
//                            _displayedComps.add(t);
//                        }
//                    }
//                });
            }
        }
    }   
}