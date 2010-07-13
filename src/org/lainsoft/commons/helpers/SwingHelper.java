package org.lainsoft.commons.helpers;

import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.Icon;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import javax.swing.event.AncestorListener;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.LabelUI;
import javax.swing.plaf.ListUI;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.plaf.SpinnerUI;
import javax.swing.plaf.TextUI;
import javax.swing.text.Document;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import static javax.swing.SwingConstants.*;
import static java.awt.Toolkit.*;
import javax.swing.text.JTextComponent;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class SwingHelper{
 
    private static Log log = LogFactory.getLog(new SwingHelper().getClass());
    
    /**
     * 
     * @param c Container to center.
     * @param width Containter width.
     * @param height Containter height.
     */
    public static void centerScreen(Container c, int width, int height) {
        Dimension screenSize = getDefaultToolkit().getScreenSize();
        c.setBounds((screenSize.width-width)/2, 
                    (screenSize.height-height)/2, 
                    width, 
                    height);
    }
    
    public static JProgressBar generateProgressBarFor(Object... objs) {
        Container c = search(objs, Container.class);        
        String[] name_value = search(objs,String[].class);
        BoundedRangeModel model = search(objs, BoundedRangeModel.class);
        Integer[] params = search(objs, Integer[].class);
        ProgressBarUI ui = search(objs, ProgressBarUI.class);
        JProgressBar pbar = (params==null || params.length > 3 || params.length <1) ?
            new JProgressBar() : (params.length==1 ? 
                                  new JProgressBar(params[0]) : (params.length==2 ? 
                                                                 new JProgressBar(params[0],params[1]) : 
                                                                 new JProgressBar(params[0],params[1],params[2])));
        if(model!=null) pbar.setModel(model);
        if(ui!=null) pbar.setUI(ui);
        addJContainerListeners(pbar, objs);
        if(c!=null) addToContainer(c,pbar);
        return pbar;
    }
    
    public static JRadioButton generateRadioFor(Object... objs) {
        Container c = search(objs, Container.class);        
        String[] name_value = search(objs,String[].class);
        String text = search(objs, String.class);
        Icon icon = search(objs, Icon.class);
        Boolean selected = search(objs, Boolean.class);
        Action action = search(objs, Action.class);
        ButtonUI ui = search(objs, ButtonUI.class);
        ButtonGroup group = search(objs, ButtonGroup.class);
        Border border = search(objs, Border.class);
        Insets margin = search(objs, Insets.class);
        Rectangle bounds = search(objs, Rectangle.class);
        Color color = search(objs,Color.class);
        Dimension dimension = search(objs, Dimension.class);
        Font font = search(objs, Font.class);
        Character mnemonic = search(objs, Character.class);

        JRadioButton radio = new JRadioButton();
        if(mnemonic!=null) radio.setMnemonic(mnemonic);
        radio.setName(name_value == null ? "" : name_value[0]);
        if(color!=null) radio.setBackground(color);
        if(dimension!=null) radio.setPreferredSize(dimension);
        if(font!=null) radio.setFont(font);
        if(!isEmpty(text)) radio.setText(text);
        if(bounds!=null) radio.setBounds(bounds);
        if(border!=null) radio.setBorder(border);
        if(margin!=null) radio.setMargin(margin);
        if(action!=null) radio.setAction(action);
        if(selected!=null) radio.setSelected(selected);
        if(ui!=null) radio.setUI(ui);
        if(group!=null) group.add(radio);
        addAbstractButtonListeners(radio, objs);
        if(c!=null) addToContainer(c,radio);
        return radio;
    }

    public static JList generateListFor(Object... objs) {
        Container c = search(objs, Container.class);        
        String[] name_value = search(objs,String[].class);
        ListModel model = search(objs, ListModel.class);
        Object[] dataObjects = search(objs, Object[].class);
        Vector dataVector = search(objs, Vector.class);
        ListSelectionListener selectionListener = search(objs, ListSelectionListener.class);
        ListUI ui = search(objs, ListUI.class);
        ListSelectionModel selectionModel = search(objs, ListSelectionModel.class);
        ListCellRenderer cellRenderer = search(objs, ListCellRenderer.class);
        JList list = model==null ? 
            (dataObjects==null ? 
             (dataVector==null ? new JList() : new JList(dataVector)) 
             : new JList(dataObjects)) 
            : new JList(model);
        list.setName(name_value == null ? "" : name_value[0]);
        if(selectionListener!=null) list.addListSelectionListener(selectionListener);
        if(ui!=null) list.setUI(ui);
        if(selectionModel!=null) list.setSelectionModel(selectionModel);
        if(cellRenderer!=null) list.setCellRenderer(cellRenderer);
        addJContainerListeners(list, objs);
        if(c!=null) addToContainer(c,list);
        return list;
    }
    
    public static JPanel
    generatePanelFor(Object... objs){
        Container c = search(objs, Container.class);        
        String[] name_value = search(objs,String[].class);
        Color color = search(objs,Color.class);
        Dimension dimension = search(objs, Dimension.class);
        Font font = search(objs, Font.class);
        Rectangle bounds = search(objs, Rectangle.class);
        Border border = search(objs, Border.class);
        PanelUI ui = search(objs, PanelUI.class);
        LayoutManager layout = search(objs, LayoutManager.class);
        JPanel panel = layout==null ? createPanel() : createPanel(layout);
        if(border != null) panel.setBorder(border);
        if(color != null) panel.setBackground(color);
        if(dimension != null) panel.setPreferredSize(dimension);
        if(font != null) panel.setFont(font);
        if(bounds != null) panel.setBounds(bounds);
        if(ui != null) panel.setUI(ui);
        panel.setName(name_value == null ? "" : name_value[0]);
        addJContainerListeners(panel, objs);
        addToContainer(c,panel);
        return panel;
    }
    
    public static JSpinner
    generateSpinnerFor(Object... objs){
        Container c = search(objs, Container.class);        
        String[] name_value = search(objs,String[].class);
        ChangeListener listener = search(objs,ChangeListener.class);
        SpinnerModel model = search(objs,SpinnerModel.class);
        Color color = search(objs,Color.class);
        Dimension dimension = search(objs, Dimension.class);
        Font font = search(objs, Font.class);
        Rectangle bounds = search(objs, Rectangle.class);
        Border border = search(objs, Border.class);
        SpinnerUI ui = search(objs, SpinnerUI.class);
        JSpinner spinner = model==null ? createSpinner() : createSpinner(model);
        if(border != null) spinner.setBorder(border);
        if(color != null) spinner.setBackground(color);
        if(dimension != null) spinner.setPreferredSize(dimension);
        if(font != null) spinner.setFont(font);
        if(listener != null) spinner.addChangeListener(listener);
        if(bounds != null) spinner.setBounds(bounds);
        if(ui != null) spinner.setUI(ui);
        spinner.setName(name_value == null ? "" : name_value[0]);
        addJContainerListeners(spinner, objs);
        addToContainer(c,spinner);
        return spinner;
    }
    
    public static JButton
    generateButtonFor(Object... objs){
        Container c = search(objs, Container.class);        
        String []name_value = search(objs,String[].class);
        String value = (name_value == null || name_value.length < 2 ? (String)search(objs, String.class) : name_value[1]);
        Action action = search(objs,Action.class);
        Icon icon = search(objs,Icon.class);
        Color color = search(objs,Color.class);
        Dimension dimension = search(objs, Dimension.class);
        Font font = search(objs, Font.class);
        Rectangle bounds = search(objs, Rectangle.class);
        Border border = search(objs, Border.class);
        ButtonUI ui = search(objs, ButtonUI.class);
        Character mnemonic = search(objs, Character.class);
        JButton button = action == null ? createButton(value,icon) : createButton(action);
        if(mnemonic!=null) button.setMnemonic(mnemonic);
        button.setName(name_value == null ? "" : name_value[0]);
        if(border != null) button.setBorder(border);
        if(color != null) button.setBackground(color);
        if(bounds != null) button.setBounds(bounds);
        if(ui != null) button.setUI(ui);
        if(dimension!=null) button.setPreferredSize(dimension);
        if(font!=null) button.setFont(font);
        addAbstractButtonListeners(button, objs);
        addToContainer(c,button);
        return button;
    }

    public static JLabel
    generateLabelFor(Object... objs){
        Container c = search(objs, Container.class);
        String []name_value = search(objs,String[].class);
        String value = (name_value == null || name_value.length < 2 ? (String)search(objs, String.class) : name_value[1]);
        Dimension dimension = search(objs, Dimension.class);
        Integer align = search(objs,Integer.class);
        Icon icon = search(objs,Icon.class);
        Color color = search(objs,Color.class);
        Font font = search(objs,Font.class);
        Rectangle bounds = search(objs,Rectangle.class);
        Border border = search(objs, Border.class);
        LabelUI ui = search(objs, LabelUI.class);
        Character mnemonic = search(objs, Character.class);
        JLabel label = createLabel(value, (align == null? CENTER : align.intValue()), icon);
        if(mnemonic!=null) label.setDisplayedMnemonic(mnemonic);
        label.setName(name_value == null ? "" : name_value[0]);
        if(font != null)label.setFont(font);
        if(dimension!=null) label.setPreferredSize(dimension);
        if(border != null) label.setBorder(border);
        if(bounds != null) label.setBounds(bounds);
        if(ui != null) label.setUI(ui);
        if(color != null) label.setForeground(color);
        addJContainerListeners(label, objs);
        addToContainer(c, label);        
        return label;
    }    

    public static JComboBox
    generateComboBoxFor(Object... objs){
        Container c = search(objs, Container.class);
        ComboBoxModel model = null;
        Vector items = null;
        Object[] itms = null;
        Border border = search(objs, Border.class);
        Color color = search(objs, Color.class);
        ComboBoxUI ui = search(objs, ComboBoxUI.class);
        JComboBox combo = 
            (model = search(objs, ComboBoxModel.class)) == null 
            ? (items = search(objs, Vector.class)) == null 
            ? new JComboBox(itms = search(objs, Object[].class)) 
            : new JComboBox(items) 
            : new JComboBox(model);
        if(border!=null) combo.setBorder(border);
        if(ui != null) combo.setUI(ui);
        if(color!=null) combo.setBackground(color);
        addJContainerListeners(combo, objs);
        addToContainer(c,combo);
        return combo;
    }


    public static JTextField
    generateTextFieldFor(Object... objs){
        Container c = search(objs, Container.class);
        Document doc = search(objs, Document.class);
        String []name_value = search(objs,String[].class);
        String value = (name_value == null || name_value.length < 2 ? (String)search(objs, String.class) : name_value[1]);
        int []colums_align = search(objs, int[].class);
        Integer colums = 
            (colums_align == null || colums_align.length < 2 ? (Integer)search(objs, Integer.class) : new Integer(colums_align[1]));
        Color background = search(objs, Color.class);
        Boolean editable = search(objs, Boolean.class);
        Font font = search(objs,Font.class);
        Dimension dimension = search(objs, Dimension.class);
        Rectangle bounds = search(objs,Rectangle.class);
        Border border = search(objs, Border.class);
        TextUI ui = search(objs, TextUI.class);
        ActionListener actionListener = search(objs, ActionListener.class);
        JTextField textfield = new JTextField(doc,value,colums == null ? 0 : colums.intValue());
        textfield.setName(name_value == null ? "" : name_value[0]);        
        textfield.setBackground(background);
        if(border != null) textfield.setBorder(border);
        if(bounds!=null) textfield.setBounds(bounds);
        if(ui != null) textfield.setUI(ui);
        textfield.setEditable(editable == null ? true : editable.booleanValue());
        textfield.setHorizontalAlignment(colums_align == null ? LEFT : colums_align[0]);
        textfield.setFont(font);
        textfield.setPreferredSize(dimension);
        addJTextComponentListeners(textfield, objs);
        textfield.addActionListener(actionListener);
        addToContainer(c, textfield);
        return textfield;
    }

    public static JPasswordField
    generatePasswordFieldFor(Object... objs){
        Container c = search(objs, Container.class);
        Document doc = search(objs, Document.class);
        String []name_value = search(objs,String[].class);
        String value = (name_value == null || name_value.length < 2 ? (String)search(objs, String.class) : name_value[1]);
        int []colums_align = search(objs, int[].class);
        Integer colums = 
            (colums_align == null || colums_align.length < 2 ? (Integer)search(objs, Integer.class) : new Integer(colums_align[1]));
        Color background = search(objs, Color.class);
        Boolean editable = search(objs, Boolean.class);
        Font font = search(objs,Font.class);
        Dimension dimension = search(objs, Dimension.class);
        JPasswordField passfield = 
            new JPasswordField(doc,value,colums == null ? 0 : colums.intValue());
        passfield.setName(name_value == null ? "" : name_value[0]);        
        passfield.setBackground(background);
        passfield.setEditable(editable == null ? true : editable.booleanValue());
        passfield.setHorizontalAlignment(colums_align == null ? LEFT : colums_align[0]);
        passfield.setFont(font);
        passfield.setPreferredSize(dimension);
        addToContainer(c, passfield);
        return passfield;
    }



    private static void addAbstractButtonListeners(AbstractButton b, Object... objs) {
        if(b==null) return;
        addJContainerListeners(b,objs);
        ActionListener actionListener = search(objs, ActionListener.class);
        ChangeListener changeListener = search(objs, ChangeListener.class);
        ItemListener itemListener = search(objs, ItemListener.class);
        if(actionListener!=null) b.addActionListener(actionListener);
        if(changeListener!=null) b.addChangeListener(changeListener);
        if(itemListener!=null) b.addItemListener(itemListener);
    }

    private static void addJTextComponentListeners(JTextComponent c, Object... objs) {
        if(c==null) return;
        addJContainerListeners(c,objs);
        CaretListener caretListener = search(objs, CaretListener.class);
        InputMethodListener inputMethodListener = search(objs, InputMethodListener.class);
        if(caretListener!=null) c.addCaretListener(caretListener);
        if(inputMethodListener!=null) c.addInputMethodListener(inputMethodListener);
    }

    private static void addJContainerListeners(JComponent c, Object... objs) {
        if(c==null) return;
        addContainerListeners(c,objs);
        AncestorListener ancestorListener = search(objs, AncestorListener.class);
        VetoableChangeListener vetoableChangeListener = search(objs, VetoableChangeListener.class);
        if(ancestorListener!=null) c.addAncestorListener(ancestorListener);
        if(vetoableChangeListener!=null) c.addVetoableChangeListener(vetoableChangeListener);
    }

    private static void addContainerListeners(Container c, Object... objs) {
        if(c==null) return;
        addComponentListeners(c,objs);
        ContainerListener containerListener = search(objs, ContainerListener.class);
        PropertyChangeListener propertyChangeListener = search(objs, PropertyChangeListener.class);
        if(containerListener!=null) c.addContainerListener(containerListener);
        if(propertyChangeListener!=null) c.addPropertyChangeListener(propertyChangeListener);
    }

    private static void addComponentListeners(Component c, Object... objs) {
        if(c==null) return;
        ComponentListener componentListener = search(objs, ComponentListener.class);
        FocusListener focusListener = search(objs, FocusListener.class);
        HierarchyBoundsListener hierarchyBoundsListener = search(objs, HierarchyBoundsListener.class);
        HierarchyListener hierarchyListener = search(objs, HierarchyListener.class);
        InputMethodListener inputMethodListener = search(objs, InputMethodListener.class);
        KeyListener keyListener = search(objs, KeyListener.class);
        MouseListener mouseListener = search(objs, MouseListener.class);
        MouseMotionListener mouseMotionListener = search(objs, MouseMotionListener.class);
        MouseWheelListener mouseWheelListener = search(objs, MouseWheelListener.class);
        if(componentListener!=null) c.addComponentListener(componentListener);
        if(focusListener!=null) c.addFocusListener(focusListener);
        if(hierarchyBoundsListener!=null) c.addHierarchyBoundsListener(hierarchyBoundsListener);
        if(hierarchyListener!=null) c.addHierarchyListener(hierarchyListener);
        if(inputMethodListener!=null) c.addInputMethodListener(inputMethodListener);
        if(keyListener!=null) c.addKeyListener(keyListener);
        if(mouseListener!=null) c.addMouseListener(mouseListener);
        if(mouseMotionListener!=null) c.addMouseMotionListener(mouseMotionListener);
        if(mouseWheelListener!=null) c.addMouseWheelListener(mouseWheelListener);
    }

    public static List<JButton>
    getListenedButtonsFor(Container c){
        List <JButton> filter = null;
        List <JButton> result = new ArrayList<JButton>();
        searchFor(c, JButton.class, filter = new ArrayList<JButton>(), null);
        for (JButton button :filter){
            if (button.getActionListeners().length > 0){
                result.add(button);
            }
        }
        return result;
    }

    public static JTextField
    getTextField(String name, Container c){
        List <JTextField> filter = new ArrayList<JTextField>();
        searchFor(c,JTextField.class, filter, name);
        return filter.isEmpty() ? null : filter.get(0);
    }
    

    public static <T extends Component> T
             search(String name, Component comp){
        if (name.equals(comp.getName())){
            return (T)comp;
        }else if(comp instanceof Container){
            for(Component c : ((Container) comp).getComponents())
                if ((c = search(name, c)) != null) return (T)c;
        }
        return null;
    }

    // public static Map<String,String>
    //     params(String name, Container c){
    //         List <JTextField> filter = new ArrayList<JTextField>();
    //         Map <String, String>params = new HashMap<String,String>();
    //         searchFor(c, JTextField.class, filter, "^("+name+")\\[.*");
    //         for (JTextField text : filter){
    //             String comp_name = text.getName();
    //             params.put(comp_name.substring(comp_name.indexOf("[")+1,comp_name.lastIndexOf("]")), text.getText());
    //         }
    //         return params;
    //     }

    public static Map<String,String>
        params(String name, Container c){
        List <JTextField> text_filter = new ArrayList<JTextField>();
        List <JComboBox> box_filter = new ArrayList<JComboBox>();
        List <JDateChooser> date_filter = new ArrayList<JDateChooser>();
        Map <String, String>params = new HashMap<String,String>();
        searchFor(c, JTextField.class, text_filter, "^("+name+")\\[.*");
        for (JTextField text : text_filter){
            String comp_name = text.getName();
            log.debug("comp_name>"+comp_name);
            params.put(comp_name.substring(comp_name.indexOf("[")+1,comp_name.lastIndexOf("]")), text.getText());
        }
        searchFor(c, JComboBox.class, box_filter, "^("+name+")\\[.*");
        for (JComboBox box :box_filter){
            String comp_name = box.getName();
            log.debug("comp_name>"+comp_name);
            params.put(comp_name.substring(comp_name.indexOf("[")+1,comp_name.lastIndexOf("]")), box.getSelectedItem()== null ? "" :box.getSelectedItem().toString());
        }
        
        searchFor(c, JDateChooser.class, date_filter, "^("+name+")\\[.*");
        for (JDateChooser date :date_filter){
            String comp_name = date.getName();
            log.debug(((date.getDate() == null || date.getName() == null) ? "omitting " : "")+ "comp_name>"+comp_name);
            if(date.getDate() == null || date.getName() == null)
                continue;
            params.put(comp_name.substring(comp_name.indexOf("[")+1,comp_name.lastIndexOf("]")), new SimpleDateFormat("MMddyyyy").format(date.getDate()));
        }
        
        return params;
    }
    
    public static String
    param(String name, Container c){
        List <JTextField> filter = new ArrayList<JTextField>();
        searchFor(c, JTextField.class, filter, name.replaceAll("\\[", "\\[").replaceAll("\\]","\\]"));
        return filter.isEmpty() ? null : filter.get(0).getText();
    }
    
    private static void
    searchFor(Container c, Class cls, List list, String name){
        if (!isEmpty(c.getComponents())){
            for (Component comp : c.getComponents()){
                if (cls.isInstance(comp) && 
                    (name == null 
                     ? true 
                     : (comp.getName() != null && comp.getName().matches(name)))){                    
                    list.add(comp);
                }else if(comp instanceof Container){
                    searchFor((Container)comp, cls, list, name);
                }
            }
        }    
    }

    private static JLabel
    createLabel(String text, int align, Icon icon){
        return text == null 
            ? new JLabel(icon,align) 
            : new JLabel(text,icon,align);        
    }

    private static JButton
    createButton(String text, Icon icon){
        return new JButton(text, icon);
    }
    
    private static JButton
    createButton(Action action){
        return new JButton(action);
    }

    private static JPanel createPanel() {
        return new JPanel();
    }

    private static JPanel createPanel(LayoutManager layout) {
        return new JPanel(layout);
    }

    private static JSpinner createSpinner() {
        return new JSpinner();
    }

    private static JSpinner createSpinner(SpinnerModel model) {
        return new JSpinner(model);
    }

    private static void
    addToContainer(Container c, Component element){        
        if (c == null) return;
        c.add(element);
    }

    private static <T> T
    search(Object[] objs, Class c){
        for (Object o: objs){
            if (c.isInstance(o))                
                return (T)o;
        }
        return null;
    }
    
    public static boolean
    isEmpty(Object o){
        return o == null || (o instanceof String && o.toString().trim().equals(""));
    }
}
