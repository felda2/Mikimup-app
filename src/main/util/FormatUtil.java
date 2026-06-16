package main.util;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {

    public static String formatRupiah(double nilai) {
        Locale indonesia = Locale.forLanguageTag("id-ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(indonesia);
        return rupiah.format(nilai);
    }

    public static String formatTanggal(LocalDateTime tanggal) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return tanggal.format(formatter);
    }
}