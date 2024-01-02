package com.example.capstone2project;

import java.io.Serializable;

/**
 * Kelas model untuk merepresentasikan data makanan.
 * Implementasi Serializable untuk memungkinkan pengiriman objek melalui Intent.
 */
public class FoodDataActivity implements Serializable {
    private String id; // ID makanan
    private String namaMakanan;
    private String img; // ID gambar sumber daya
    private String totalCalories;
    private String protein;
    private String lemak;
    private String karb;
    private String description;

    /**
     * Konstruktor untuk membuat objek FoodData.
     *
     * @param id             ID makanan.
     * @param namaMakanan    Nama makanan.
     * @param img            ID gambar sumber daya.
     * @param totalCalories  Total kalori.
     * @param protein        Jumlah protein.
     * @param lemak          Jumlah lemak.
     * @param karb           Jumlah karbohidrat.
     * @param description    Deskripsi makanan.
     */
    public FoodDataActivity(String id, String namaMakanan, String img, String totalCalories, String protein, String lemak, String karb, String description) {
        this.id = id;
        this.namaMakanan = namaMakanan;
        this.img = img;
        this.totalCalories = totalCalories;
        this.protein = protein;
        this.lemak = lemak;
        this.karb = karb;
        this.description = description;
    }

    /**
     * Metode untuk mendapatkan ID makanan.
     *
     * @return ID makanan.
     */
    public String getId() {
        return id;
    }

    /**
     * Metode untuk mendapatkan nama makanan.
     *
     * @return Nama makanan.
     */
    public String getNamaMakanan() {
        return namaMakanan;
    }

    /**
     * Metode untuk mendapatkan ID gambar sumber daya.
     *
     * @return ID gambar sumber daya.
     */
    public String getImg() {
        return img;
    }

    /**
     * Metode untuk mendapatkan total kalori.
     *
     * @return Total kalori.
     */
    public String getTotalCalories() {
        return totalCalories;
    }

    /**
     * Metode untuk mendapatkan jumlah protein.
     *
     * @return Jumlah protein.
     */
    public String getProtein() {
        return protein;
    }

    /**
     * Metode untuk mendapatkan jumlah lemak.
     *
     * @return Jumlah lemak.
     */
    public String getLemak() {
        return lemak;
    }

    /**
     * Metode untuk mendapatkan jumlah karbohidrat.
     *
     * @return Jumlah karbohidrat.
     */
    public String getKarb() {
        return karb;
    }

    /**
     * Metode untuk mendapatkan deskripsi makanan.
     *
     * @return Deskripsi makanan.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metode untuk memeriksa kesetaraan dua objek FoodData.
     *
     * @param obj Objek yang akan dibandingkan.
     * @return True jika objek-objek sama, false jika tidak.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        FoodDataActivity foodData = (FoodDataActivity) obj;

        if (getId() == null && foodData.getId() == null) return true;
        // Memeriksa apakah ID sama
        return getId() != null && getId().equals(foodData.getId());
    }
}
