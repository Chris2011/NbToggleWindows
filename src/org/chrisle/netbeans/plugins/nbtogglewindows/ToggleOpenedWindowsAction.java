package org.chrisle.netbeans.plugins.nbtogglewindows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@ActionID(
    category = "Window",
    id = "org.chrisle.netbeans.plugins.nbhidewindows.ToggleOpenedWindowsAction"
)
@ActionRegistration(
    displayName = "#CTL_NbToggleWindowsAction"
)
@ActionReference(path = "Shortcuts", name = "DOS-ENTER")
@Messages("CTL_NbToggleWindowsAction=Toggle Windows")
public final class ToggleOpenedWindowsAction implements ActionListener {
    Collection<TopComponent> _displayedComps = new HashSet<>();

    @Override
    public void actionPerformed(ActionEvent e) {
        getDisplayedWindows();

        if (_displayedComps != null) {
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

    private void getDisplayedWindows() {
        final WindowManager wm = WindowManager.getDefault();

        for (Mode mode : wm.getModes()) {
            if (!wm.isEditorMode(mode)) {
                Arrays.asList(wm.getOpenedTopComponents(mode)).forEach(new Consumer<TopComponent>() {
                    @Override
                    public void accept(TopComponent t) {
                        if (t.isDisplayable() && !_displayedComps.contains(t)) {
                            _displayedComps.add(t);
                        }
                    }
                });
            }
        }
    }
}