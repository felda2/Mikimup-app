package main.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public class ThemeUtil {

    /*
    =====================================
    COLOR PALETTE
    =====================================
    */

    public static final Color PRIMARY =
            Color.decode("#E5395A");

    public static final Color PRIMARY_DARK =
            Color.decode("#C92C4A");

    public static final Color PINK =
            Color.decode("#FCE4E8");

    public static final Color WHITE =
            Color.decode("#FFFFFF");

    public static final Color BACKGROUND =
            Color.decode("#F8F8F8");

    public static final Color BORDER =
            Color.decode("#E5E7EB");

    public static final Color TEXT =
            Color.decode("#1F2937");

    public static final Color TEXT_SECONDARY =
            Color.decode("#4B5563");

    public static final Color PLACEHOLDER =
            Color.decode("#9CA3AF");

    public static final Color SUCCESS =
            Color.decode("#22C55E");

    public static final Color WARNING =
            Color.decode("#F59E0B");

    public static final Color ERROR =
            Color.decode("#EF4444");

    public static final Color INFO =
            Color.decode("#3B82F6");

    /*
    =====================================
    FONT
    =====================================
    */

    public static final Font TITLE_FONT =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    20);

    public static final Font SUBTITLE_FONT =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    16);

    public static final Font BOLD_FONT =
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    14);

    public static final Font NORMAL_FONT =
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    14);

    public static final Font SMALL_FONT =
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    12);

    /*
    =====================================
    BORDER
    =====================================
    */

    public static Border createCardBorder() {

        return BorderFactory.createCompoundBorder(

                BorderFactory.createLineBorder(
                        BORDER),

                BorderFactory.createEmptyBorder(
                        12,
                        12,
                        12,
                        12)

        );

    }

    /*
    =====================================
    PANEL
    =====================================
    */

    public static void stylePanel(JPanel panel) {

        panel.setBackground(WHITE);

        panel.setBorder(
                createCardBorder());

    }

    /*
    =====================================
    LABEL
    =====================================
    */

    public static void styleLabel(JLabel label) {

        label.setFont(NORMAL_FONT);

        label.setForeground(TEXT);

    }

    public static void styleTitle(JLabel label) {

        label.setFont(TITLE_FONT);

        label.setForeground(TEXT);

    }

    /*
    =====================================
    TEXTFIELD
    =====================================
    */

    public static void styleTextField(
            JTextField field) {

        field.setFont(NORMAL_FONT);

        field.setForeground(TEXT);

        field.setBackground(WHITE);

        field.setBorder(

                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                BORDER),

                        BorderFactory.createEmptyBorder(
                                6,
                                8,
                                6,
                                8)

                )

        );

    }

    /*
    =====================================
    TEXT AREA
    =====================================
    */

    public static void styleTextArea(
            JTextArea area) {

        area.setFont(NORMAL_FONT);

        area.setForeground(TEXT);

        area.setBackground(WHITE);

        area.setBorder(

                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                BORDER),

                        BorderFactory.createEmptyBorder(
                                8,
                                8,
                                8,
                                8)

                )

        );

    }

    /*
    =====================================
    COMBO BOX
    =====================================
    */

    public static void styleComboBox(
            JComboBox<?> comboBox) {

        comboBox.setFont(NORMAL_FONT);

        comboBox.setBackground(WHITE);

        comboBox.setForeground(TEXT);

    }

    /*
    =====================================
    TABLE
    =====================================
    */

    public static void styleTable(
            JTable table) {

        table.setFont(NORMAL_FONT);

        table.setForeground(TEXT);

        table.setBackground(WHITE);

        table.setGridColor(BORDER);

        table.setRowHeight(28);

        table.setSelectionBackground(PINK);

        table.setSelectionForeground(TEXT);

        JTableHeader header =
                table.getTableHeader();

        header.setFont(BOLD_FONT);

        header.setBackground(PRIMARY);

        header.setForeground(WHITE);

    }

    /*
    =====================================
    PRIMARY BUTTON
    =====================================
    */

    public static void stylePrimaryButton(
            JButton button) {

        button.setFont(BOLD_FONT);

        button.setBackground(PRIMARY);

        button.setForeground(WHITE);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

    }

    /*
    =====================================
    SUCCESS BUTTON
    =====================================
    */

    public static void styleSuccessButton(
            JButton button) {

        button.setFont(BOLD_FONT);

        button.setBackground(SUCCESS);

        button.setForeground(WHITE);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

    }

    /*
    =====================================
    ERROR BUTTON
    =====================================
    */

    public static void styleDangerButton(
            JButton button) {

        button.setFont(BOLD_FONT);

        button.setBackground(ERROR);

        button.setForeground(WHITE);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

    }

    /*
    =====================================
    INFO BUTTON
    =====================================
    */

    public static void styleInfoButton(
            JButton button) {

        button.setFont(BOLD_FONT);

        button.setBackground(INFO);

        button.setForeground(WHITE);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

    }

    /*
    =====================================
    WARNING BUTTON
    =====================================
    */

    public static void styleWarningButton(
            JButton button) {

        button.setFont(BOLD_FONT);

        button.setBackground(WARNING);

        button.setForeground(WHITE);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

    }

    /*
    =====================================
    GENERIC COMPONENT
    =====================================
    */

    public static void setWhiteBackground(
            JComponent component) {

        component.setBackground(WHITE);

        component.setForeground(TEXT);

    }

}