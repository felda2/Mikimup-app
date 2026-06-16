package main.service;

import java.util.ArrayList;
import main.model.DetailTransaksi;
import main.model.Transaksi;

public class TransaksiService {

    private ArrayList<Transaksi> daftarTransaksi;

    public TransaksiService() {
        daftarTransaksi = new ArrayList<>();
    }

    public void simpanTransaksi(Transaksi transaksi) {
        daftarTransaksi.add(transaksi);
    }

    public ArrayList<Transaksi> getDaftarTransaksi() {
        return daftarTransaksi;
    }

    public int jumlahTransaksi() {
        return daftarTransaksi.size();
    }

    public double hitungPendapatan() {

        double total = 0;

        for (Transaksi transaksi : daftarTransaksi) {
            total += transaksi.hitungTotal();
        }

        return total;
    }

    public String tampilkanRiwayat() {

        if (daftarTransaksi.isEmpty()) {
            return "Belum ada transaksi.";
        }

        StringBuilder sb = new StringBuilder();

        for (Transaksi transaksi : daftarTransaksi) {

            sb.append(transaksi.getIdTransaksi())
              .append(" | Rp ")
              .append(transaksi.hitungTotal())
              .append("\n");
        }

        return sb.toString();
    }

    public String generateStruk(Transaksi transaksi) {

        StringBuilder sb = new StringBuilder();

        sb.append("========================\n");
        sb.append("       MIKIMUP\n");
        sb.append("========================\n");

        sb.append("ID : ")
          .append(transaksi.getIdTransaksi())
          .append("\n\n");

        for (DetailTransaksi detail :
                transaksi.getDaftarDetail()) {

            sb.append(
                    detail.getBarang().getNamaBarang())
              .append("\n");

            sb.append(
                    detail.getJumlah())
              .append(" x Rp ")
              .append(detail.getBarang().getHarga())
              .append(" = Rp ")
              .append(detail.hitungSubtotal())
              .append("\n\n");
        }

        sb.append("------------------------\n");

        sb.append("TOTAL : Rp ")
          .append(transaksi.hitungTotal())
          .append("\n");

        sb.append("BAYAR : Rp ")
          .append(transaksi.getTotalBayar())
          .append("\n");

        sb.append("KEMBALIAN : Rp ")
          .append(transaksi.hitungKembalian())
          .append("\n");

        sb.append("========================\n");
        sb.append("Terima Kasih");

        return sb.toString();
    }
}