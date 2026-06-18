package main.util;

import java.awt.Color;
import java.awt.Font;

public class AppTheme {
    // --- COLOR PALETTE (Sesuai Request) ---
    public static final Color PRIMARY_RED      = new Color(0xE5, 0x39, 0x5A); // #E5395A
    public static final Color DARK_RED         = new Color(0xC9, 0x2C, 0x4A); // #C92C4A
    public static final Color SOFT_PINK        = new Color(0xFC, 0xE4, 0xE8); // #FCE4E8
    public static final Color WHITE            = new Color(0xFF, 0xFF, 0xFF); // #FFFFFF
    public static final Color BG_GRAY          = new Color(0xF8, 0xF8, 0xF8); // #F8F8F8
    public static final Color BORDER_GRAY      = new Color(0xE5, 0xE7, 0xEB); // #E5E7EB
    public static final Color TEXT_DARK        = new Color(0x1F, 0x29, 0x37); // #1F2937
    public static final Color TEXT_SECONDARY   = new Color(0x4B, 0x55, 0x63); // #4B5563
    public static final Color PLACEHOLDER      = new Color(0x9C, 0xA3, 0xAF); // #9CA3AF
    
    // Status Colors (Untuk Alert / JOptionPane kedepannya)
    public static final Color SUCCESS_GREEN    = new Color(0x22, 0xC5, 0x5E); // #22C55E
    public static final Color WARNING_YELLOW   = new Color(0xF5, 0x9E, 0x0B); // #F59E0B
    public static final Color ERROR_RED        = new Color(0xEF, 0x44, 0x44); // #EF4444
    public static final Color INFO_BLUE        = new Color(0x3B, 0x82, 0xF6); // #3B82F6

    // --- TYPOGRAPHY (Implementasi Segoe UI / Poppins) ---
    // Catatan: Jika OS user belum install font Poppins, Java otomatis fallback ke Segoe UI / SansSerif
    public static final Font FONT_TITLE    = new Font("Poppins", Font.BOLD, 20);
    public static final Font FONT_LABEL    = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_INPUT    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BUTTON   = new Font("Poppins", Font.BOLD, 13);
}