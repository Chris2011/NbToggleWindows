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
    Collection<TopComponent> _minimizedComponents = new HashSet<>();
    final WindowManager _wm;

    public HideMinimizedWindowsAction() {
        this._wm = WindowManager.getDefault();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        getMinimizedWindows();

        if(_minimizedComponents != null) {
            _minimizedComponents.forEach(new Consumer<TopComponent>() {
                @Override
                public void accept(TopComponent t) {
                    if (_wm.isTopComponentMinimized(t)) {
                        t.close();
                    } else {
                        t.open();
                    }
                }
            });
        }
    }

    private void getMinimizedWindows() {
        for (Mode mode : _wm.getModes()) {
            if (!_wm.isEditorMode(mode)) {
                Arrays.asList(_wm.getOpenedTopComponents(mode)).forEach(new Consumer<TopComponent>() {
                    @Override
                    public void accept(TopComponent t) {
                        if (_wm.isTopComponentMinimized(t) && !_minimizedComponents.contains(t)) {
                            _minimizedComponents.add(t);
                        }
                    }
                });
            }
        }
    }   
}