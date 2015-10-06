/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chrisle.netbeans.plugins.nbtogglewindows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Sheet.Set;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@ActionID(
        category = "Window",
        id = "org.chrisle.netbeans.plugins.nbhidewindows.NbToggleWindowsAction"
)
@ActionRegistration(
        displayName = "#CTL_NbToggleWindowsAction"
)
@ActionReference(path = "Shortcuts", name = "DOS-ENTER")
@Messages("CTL_NbToggleWindowsAction=Toggle Windows")
public final class NbToggleWindowsAction implements ActionListener {
    Collection<TopComponent> _displayedComps = new HashSet<>();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Collection<TopComponent> comps = getDisplayedWindows();
        
        if (comps.size() > 0) {
            comps.forEach(new Consumer<TopComponent>() {
                @Override
                public void accept(TopComponent t) {
                    if (t.isDisplayable()) {
                        t.close();
                    } else {
                        t.open();
                    }
                }
            });
        } else {
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

    private Collection<TopComponent> getDisplayedWindows() {
        final ArrayList<TopComponent> result = new ArrayList<TopComponent>();
        final WindowManager wm = WindowManager.getDefault();

        for (Mode mode : wm.getModes()) {
            if (!wm.isEditorMode(mode)) {
                Arrays.asList(wm.getOpenedTopComponents(mode)).forEach(new Consumer<TopComponent>() {
                    @Override
                    public void accept(TopComponent t) {
                        if (t.isDisplayable() && !_displayedComps.contains(t)) {
                            _displayedComps.add(t);

                            result.add(t);
                        }
                    }
                });
                
            }
        }

        return result;
    }
}
