package main.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;

public class AnimationUtil {

    /**
     * Membuat efek transisi warna (Fade) halus pada JButton
     * @param button Komponen tombol yang ingin dianimasikan
     * @param targetColor Warna tujuan
     * @param durationMs Durasi animasi dalam milidetik (misal: 200ms)
     */
    public static void fadeButtonBackground(JButton button, Color targetColor, int durationMs) {
        // Mengambil warna awal tombol saat ini
        final Color startColor = button.getBackground();
        if (startColor.equals(targetColor)) return;

        final int frames = 15; // Jumlah langkah perubahan warna agar terlihat mulus
        final int delay = durationMs / frames; // Jeda waktu per frame

        // Hentikan animasi sebelumnya jika ada yang sedang berjalan pada tombol ini
        Object activeTimer = button.getClientProperty("activeFadeTimer");
        if (activeTimer instanceof Timer) {
            ((Timer) activeTimer).stop();
        }

        // Timer berjalan asynchronous seperti loop di background
        Timer timer = new Timer(delay, new ActionListener() {
            private int currentFrame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame++;
                // Menghitung persentase progres animasi (0.0 sampai 1.0)
                float fraction = (float) currentFrame / frames;

                if (fraction >= 1.0f) {
                    button.setBackground(targetColor);
                    ((Timer) e.getSource()).stop();
                } else {
                    // Interpolasi warna antara warna asal dan warna tujuan (Linear Blend)
                    int r = (int) (startColor.getRed() + (targetColor.getRed() - startColor.getRed()) * fraction);
                    int g = (int) (startColor.getGreen() + (targetColor.getGreen() - startColor.getGreen()) * fraction);
                    int b = (int) (startColor.getBlue() + (targetColor.getBlue() - startColor.getBlue()) * fraction);
                    
                    button.setBackground(new Color(r, g, b));
                }
            }
        });

        // Simpan timer ke dalam properti tombol agar bisa di-track/dihentikan jika hover terlalu cepat
        button.putClientProperty("activeFadeTimer", timer);
        timer.start();
    }
}